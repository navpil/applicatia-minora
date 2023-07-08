package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Configuration;
import ua.lviv.navpil.sim.points.Field;
import ua.lviv.navpil.sim.points.Point;
import ua.lviv.navpil.sim.points.PointUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RabbitsGame implements Game {

    public static final int MAX_GRASS = 10;
    private final Field field;
    private final Configuration octogonal = new Configuration(8, false);
    private final Configuration orthogonal = new Configuration(4, false);

    public static void main(String[] args) {
        RunSimulation.startSimulation(new RabbitsGame(300, 150),
                "Fox/Rabbits/Wolfs/Bears", 4, new GameRunner.Speeds(50, 5));
    }

    private final int xmax;

    private final int ymax;

    private final Random random = new Random();
    private int[][] grass;
    private final Animal[][] animals;

    private String info = "";

    private boolean burnFire = true;
    private int simCount = 0;

    public RabbitsGame(int xmax, int ymax) {
        this.xmax = xmax;
        this.ymax = ymax;
        this.grass = initGrass();
        field = new RabbitGameField();
        animals = new Animal[xmax][];
        for (int i = 0; i < animals.length; i++) {
            animals[i] = new Animal[ymax];
        }
        withAll((x, y) -> {
            if (random.nextDouble() < 0.4) {
                animals[x][y] = new Rabbit();
            } else if (random.nextDouble() < 0.04) {
                animals[x][y] = new Fox();
            } else if (random.nextDouble() < 0.004) {
                animals[x][y] = new Wolf();
            } else if (random.nextDouble() < 0.004) {
                animals[x][y] = new Bear();
            }
        });

    }

    private int[][] initGrass() {
        int[][] grass = new int[xmax][];
        for (int i = 0; i < grass.length; i++) {
            grass[i] = new int[ymax];
            for (int j = 0; j < ymax; j++) {
                grass[i][j] = random.nextInt(5);
            }
        }
        return grass;
    }

    @Override
    public Field getBoxes() {
        return field;
    }

    @Override
    public void step(Runnable callback) {

//        withAll((x, y) -> grass[x][y] = random.nextDouble() < 0.95 ? Math.min(grass[x][y] + 1, MAX_GRASS) : grass[x][y]);
        withAll((x, y) -> grass[x][y] = Math.min(grass[x][y] + 1, MAX_GRASS));

        HashMap<Class<?>, AtomicInteger> map = new HashMap<>();
        map.put(Fox.class, new AtomicInteger());
        map.put(Rabbit.class, new AtomicInteger());
        map.put(Wolf.class, new AtomicInteger());
        map.put(Bear.class, new AtomicInteger());

        class AnimalWithPoint {
            private final Animal animal;
            private final int x;
            private final int y;

            AnimalWithPoint(Animal animal, int x, int y) {
                this.animal = animal;
                this.x = x;
                this.y = y;
            }
        }

        java.util.List<AnimalWithPoint> all = new ArrayList<>();

        withAll((x, y) -> {
            Animal animal = animals[x][y];
            if (animal != null) {
                all.add(new AnimalWithPoint(animal, x, y));
                map.get(animal.getClass()).incrementAndGet();
            }
        });
        info = "R: " +  map.get(Rabbit.class).get() +", F: " + map.get(Fox.class).get() +
                ", W: " + map.get(Wolf.class).get() + ", B: " + map.get(Bear.class).get();

        Collections.shuffle(all);
        try {
            for (AnimalWithPoint animalWithPoint : all) {
                animalWithPoint.animal.act(animalWithPoint.x, animalWithPoint.y);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e);
            e.printStackTrace();
        }

        if (simCount < 5) {
            simCount++;
            return;
        }
        if (!burnFire) {
            return;
        }
        burnTrees(callback);

    }

    private void burnTrees(Runnable callback) {
        int x = random.nextInt(xmax);
        int y = random.nextInt(ymax);
        //Check if thrown on a tree
        int cutoff = 4;
        if (grass[x][y] > cutoff) {
            //burn the tree
            grass[x][y] = -10;
            animals[x][y] = null;
            //notify that state have changed
            callback.run();

            //burn all surroundings
            Point point = new Point(x, y);
            Set<Point> adjacent = getAdjacent(point);
            while (!adjacent.isEmpty()) {
                HashSet<Point> temp = new HashSet<>(adjacent);
                adjacent.clear();
                for (Point p : temp) {
                    if (grass[p.x()][p.y()] > cutoff && random.nextDouble() < 0.7) {
//                        allBurned.add(p);
                        grass[p.x()][p.y()] = -10;
                        animals[p.x()][p.y()] = null;
                        adjacent.addAll(getAdjacent(p));
                    }
                }
                callback.run();
            }
        }
    }

    private Set<Point> getAdjacent(Point p) {
        return PointUtil.findAdjacent(p, orthogonal, xmax, ymax);
    }

    private void withAll(BiConsumer<Integer, Integer> consumer) {
        for (int x = 0; x < xmax; x++) {
            for (int y = 0; y < ymax; y++) {
                consumer.accept(x, y);
            }
        }

    }

    private static abstract class Animal {

        private boolean dead;
        public abstract void act(int x, int y);

        public abstract int size();

        public void die() {
            dead = true;
        }

        public boolean dead() {
            return dead;
        }
    }

    private class Rabbit extends Animal {

        private int foodLevel;
        private int age = 0;

        public Rabbit() {
            this(10);
        }

        public Rabbit(int foodLevel) {
            this.foodLevel = foodLevel;
        }

        @Override
        public void act(int x, int y) {
            if (dead()) {
                return;
            }
            //hunger
            foodLevel -= 2;
            age++;
            if (foodLevel <= 0 || age > 15) {
                //die
                die();
                animals[x][y] = null;
                return;
            }
            //if enough food at my place, stay and eat;
            if (grass[x][y] > 0) {
                int maxRequiredFoodLevel = 20 - foodLevel;
                int fl = Math.min(maxRequiredFoodLevel, 5);
                foodLevel += fl;
                grass[x][y] -= fl;
            }
            //if not - scout
            else {
                java.util.List<Point> adjacent = PointUtil.findAdjacent(new Point(x, y), octogonal, xmax, ymax)
                        .stream().filter(p -> animals[p.x()][p.y()] == null).collect(Collectors.toList());
                if (adjacent.isEmpty()) {
                    die();
                    animals[x][y] = null;
                    return;
                } else {
                    Point point = adjacent.get(random.nextInt(adjacent.size()));
                    animals[point.x()][point.y()] = animals[x][y];
                    animals[x][y] = null;
                }
            }
            //if full enough - breed
            if (foodLevel > 6 && age > 2) {
                java.util.List<Point> adjacent = PointUtil.findAdjacent(new Point(x, y), octogonal, xmax, ymax)
                        .stream().filter(p -> animals[p.x()][p.y()] == null).collect(Collectors.toList());
                if (adjacent.isEmpty()) {
                    return;
                }
                int share = foodLevel / 2;
                foodLevel -= share;
                Point point = adjacent.get(random.nextInt(adjacent.size()));
                animals[point.x()][point.y()] = new Rabbit(share);
            }
        }

        @Override
        public int size() {
            return foodLevel;
        }
    }

    private abstract class Predator extends Animal {

        private int foodLevel;
        private int age = 0;

        protected int FOOD_DECREASE = 3;
        protected int MAX_AGE = 40;
        protected int BIRTH_FOOD_LEVEL = 40;
        protected int BIRTH_AGE = 20;
        protected int BIRTH_DIVISION = 3;

        public Predator(int foodLevel) {
            this.foodLevel = foodLevel;
        }

        @Override
        public void act(int x, int y) {
            if (dead()) {
                return;
            }
            //hunger
            foodLevel -= FOOD_DECREASE;
            age++;
            if (foodLevel <= 0 || age > MAX_AGE) {
                //die
                die();
                animals[x][y] = null;
                return;
            }
            //Check if has food:
            Set<Point> allAdjacent = PointUtil.findAdjacent(new Point(x, y), octogonal, xmax, ymax);
            java.util.List<Point> withFood = allAdjacent
                    .stream().filter(p -> canConsume(animals[p.x()][p.y()])).collect(Collectors.toList());
            if (!withFood.isEmpty()) {
                Point p = withFood.get(random.nextInt(withFood.size()));
                foodLevel += animals[p.x()][p.y()].size();
                animals[p.x()][p.y()].die();
                animals[p.x()][p.y()] = animals[x][y];
                animals[x][y] = null;
            } else {
                java.util.List<Point> empty = allAdjacent
                        .stream().filter(p -> canStepOn(animals[p.x()][p.y()])).collect(Collectors.toList());
                if (empty.isEmpty()) {
                    //die
                    die();
                    animals[x][y] = null;
                    return;
                }
                Point point = empty.get(random.nextInt(empty.size()));
                if (animals[point.x()][point.y()] != null) {
                    animals[point.x()][point.y()].die();
                }
                animals[point.x()][point.y()] = animals[x][y];
                animals[x][y] = null;

            }
            //if full enough - breed
            if (foodLevel > BIRTH_FOOD_LEVEL && age > BIRTH_AGE) {
                java.util.List<Point> adjacent = PointUtil.findAdjacent(new Point(x, y), octogonal, xmax, ymax)
                        .stream().filter(p -> canStepOn(animals[p.x()][p.y()])).collect(Collectors.toList());
                if (adjacent.isEmpty()) {
                    return;
                }
                int share = foodLevel / BIRTH_DIVISION;
                foodLevel -= share;
                Point point = adjacent.get(random.nextInt(adjacent.size()));
                if (animals[point.x()][point.y()] != null) {
                    animals[point.x()][point.y()].die();
                }
                animals[point.x()][point.y()] = getChild(share);
            }
        }

        protected abstract Animal getChild(int share);

        protected boolean canStepOn(Animal a) {
            return a == null;
        }

        protected abstract boolean canConsume(Animal p);

        @Override
        public int size() {
            return foodLevel;
        }
    }

    private class Fox extends Predator {

        public Fox() {
            this(12);
        }
        public Fox(int initialFoodLevel) {
            super(initialFoodLevel);
            FOOD_DECREASE = 3;
            MAX_AGE = 40;
            BIRTH_FOOD_LEVEL = 40;
            BIRTH_AGE = 20;
            BIRTH_DIVISION = 3;
        }

        @Override
        protected Animal getChild(int share) {
            return new Fox(share);
        }

        @Override
        protected boolean canConsume(Animal a) {
            return a instanceof Rabbit;
        }


    }

    private class Wolf extends Predator {

        public Wolf() {
            this(100);
        }

        public Wolf(int foodLevel) {
            super(foodLevel);
            FOOD_DECREASE = 5;
            MAX_AGE = 500;
            BIRTH_FOOD_LEVEL = 250;
            BIRTH_AGE = 25;
            BIRTH_DIVISION = 4;
        }

        @Override
        protected Animal getChild(int share) {
            return new Wolf(share);
        }

        @Override
        protected boolean canConsume(Animal p) {
            return p instanceof Rabbit || p instanceof Fox;
        }


    }
    private class Bear extends Predator {

        public Bear() {
            this(100);
        }

        public Bear(int foodLevel) {
            super(foodLevel);
            FOOD_DECREASE = 2;
            MAX_AGE = 500;
            BIRTH_FOOD_LEVEL = 150;
            BIRTH_AGE = 35;
            BIRTH_DIVISION = 4;
        }

        @Override
        protected Animal getChild(int share) {
            return new Bear(share);
        }

        @Override
        protected boolean canConsume(Animal p) {
            return p instanceof Fox;
        }

        @Override
        protected boolean canStepOn(Animal a) {
            return a == null || a instanceof Rabbit;
        }
    }


    private class RabbitGameField implements Field {

        private Map<Integer, Color> cache = new HashMap<>();

        @Override
        public int width() {
            return xmax;
        }

        @Override
        public int height() {
            return ymax;
        }

        public static final int DEFAULT = 0;
        public static final int WOLFES_ONLY = 1;
        public static final int ANIMALS_ONLY = 2;
        public static final int PREDATORS_ONLY = 3;
        public static final int GRASS_ONLY = 4;
        int type = GRASS_ONLY;


        @Override
        public Color at(int x, int y) {
            if (grass[x][y] == -10) {
                //fire
                return Color.ORANGE.darker().darker();
            }
            switch (type) {
                case GRASS_ONLY: {
                    int grassValue = grass[x][y];
                    if (grassValue == -10) {
                        return Color.ORANGE;
                    } else if (grassValue <= 0) {
                        return Color.YELLOW;
                    } else {
                        if (!cache.containsKey(grassValue)) {
                            cache.put(grassValue, new Color(0, 256 - (10 * grassValue), 0));
                        }
                        return cache.get(grassValue);
                    }
                }
                case WOLFES_ONLY:
                    if (animals[x][y] instanceof Wolf) {
                        return Color.BLACK;
                    } else {
                        int grassValue = grass[x][y];
                        if (grassValue == -10) {
                            return Color.ORANGE;
                        } else if (grassValue <= 0) {
                            return Color.YELLOW;
                        } else {
                            if (!cache.containsKey(grassValue)) {
                                cache.put(grassValue, new Color(0, 256 - (10 * grassValue), 0));
                            }
                            return cache.get(grassValue);
                        }
                    }
                case ANIMALS_ONLY:
                    if (animals[x][y] == null) {
                        return Color.GRAY;
                    }
                    if (animals[x][y] instanceof Rabbit) {
                        return Color.BLUE;
                    } else if (animals[x][y] instanceof Fox) {
                        return Color.BLACK;
                    } else if (animals[x][y] instanceof Wolf) {
                        return Color.ORANGE;
                    } else {
                        return Color.RED;
                    }
                case PREDATORS_ONLY:
                    if (animals[x][y] == null || animals[x][y] instanceof Rabbit) {
                        return Color.GRAY;
                    } else if (animals[x][y] instanceof Fox) {
                        return Color.BLACK;
                    } else if (animals[x][y] instanceof Wolf) {
                        return Color.ORANGE;
                    } else {
                        return Color.RED;
                    }
            }


            if (animals[x][y] == null) {
                int grassValue = grass[x][y];
                if (grassValue == -10) {
                    return Color.ORANGE;
                } else if (grassValue <= 0) {
                    return Color.YELLOW;
                } else {
                    if (!cache.containsKey(grassValue)) {
                        cache.put(grassValue, new Color(0, 256 - (10 * grassValue), 0));
                    }
                    return cache.get(grassValue);
                }
            }
            if (animals[x][y] instanceof Rabbit) {
                return Color.BLUE;
            } else if (animals[x][y] instanceof Fox){
                return Color.BLACK;
            } else if (animals[x][y] instanceof Wolf){
                return Color.ORANGE;
            } else {
                return Color.RED;
            }
        }

        @Override
        public String info() {
            return info;
        }
    }

}
