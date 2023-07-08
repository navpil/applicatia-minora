package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Configuration;
import ua.lviv.navpil.sim.points.Field;
import ua.lviv.navpil.sim.points.Point;
import ua.lviv.navpil.sim.points.PointUtil;
import ua.lviv.navpil.sim.points.SimpleColorField;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * At the beginning some lakes and trees are put onto board (number can be changed manually in the constructor)
 * Each step some amount of trees can be planted (change the value in the getNewTreesCount method)
 * and a match is thrown.
 * If a match hits a tree it burns it and all trees around.
 */
public class BurnTreesSimulation implements Game {

    public static boolean ADD_LAKES = true;
    public static int LAKE_PERCENTAGE = 15;
    private final SimpleColorField field;

    public static void main(String[] args) {
        RunSimulation.startSimulation(
                new BurnTreesSimulation(20, 20),
                "Burn Trees", 20, new GameRunner.Speeds(100, 10)
        );
    }

    private final Configuration configuration = new Configuration(4, true);

    private final Random random = new Random();
    private final int[][] trees;
    private final int xsize;
    private final int ysize;
    private int treeCounter = 0;
    private int stepsCount = 0;
    private double runningAverage;

    public BurnTreesSimulation(int xsize, int ysize) {
        this.xsize = xsize;
        this.ysize = ysize;
        trees = new int[xsize][];
        for (int i = 0; i < trees.length; i++) {
            trees[i] = new int[ysize];
        }
        if (ADD_LAKES) {
            putLakes((int)Math.round(xsize * ysize * 1.0 * LAKE_PERCENTAGE / 100));
        }
        int treesNumber = (xsize * ysize) / 3;
        for (int i = 0; i < treesNumber; i++) {
            plantTree(random.nextInt(this.xsize), random.nextInt(this.ysize));
        }

        field = new SimpleColorField(trees);
    }

    private void putLakes(int amount) {
        while(amount > 0) {
            int x = random.nextInt(this.xsize);
            int y = random.nextInt(this.ysize);
            int lakeSize = Math.min(random.nextInt(10) + 5, amount);
            putLakeAt(new Point(x, y), lakeSize);
            amount -= lakeSize;
        }
    }

    private int putLakeAt(Point p, int size) {
        if (size == 0) {
            return 0;
        }
        trees[p.x()][p.y()] = GamePanel.BLUE;
        size--;
        Set<Point> adjacent = getAdjacent(p);
        for (Point point : adjacent) {
            if (random.nextBoolean()) {
                size = putLakeAt(point, size);
            }
        }
        return size;
    }

    private void plantTree(int x, int y) {
        if (trees[x][y] != GamePanel.GREEN && trees[x][y] != GamePanel.BLUE) {
            trees[x][y] = GamePanel.GREEN;
            treeCounter++;
        }
    }

    @Override
    public void step(Runnable callback) {
        stepsCount++;
        //Plant a tree
        int newTreesCount = getNewTreesCount();
        for (int i = 0; i < newTreesCount; i++) {
            int x = random.nextInt(xsize);
            int y = random.nextInt(ysize);
            plantTree(x, y);
        }

        //Throw a match
        int x = random.nextInt(xsize);
        int y = random.nextInt(ysize);
        //Check if thrown on a tree
        if (trees[x][y] == GamePanel.GREEN) {
            //burn the tree
            trees[x][y] = GamePanel.RED;
            //notify that state have changed
            callback.run();

            //burn all surroundings
            Point point = new Point(x, y);
            HashSet<Point> allBurned = new HashSet<>();
            allBurned.add(point);
            Set<Point> adjacent = getAdjacent(point);
            while (!adjacent.isEmpty()) {
                HashSet<Point> temp = new HashSet<>(adjacent);
                adjacent.clear();
                for (Point p : temp) {
                    if (trees[p.x()][p.y()] == GamePanel.GREEN) {
                        allBurned.add(p);
                        trees[p.x()][p.y()] = GamePanel.RED;
                        adjacent.addAll(getAdjacent(p));
                    }
                }
                callback.run();
            }
            treeCounter -= allBurned.size();
            for (Point burnedPoint : allBurned) {
                trees[burnedPoint.x()][burnedPoint.y()] = GamePanel.BLACK;
            }
        } else {
            if (trees[x][y] != GamePanel.BLUE) {
                trees[x][y] = GamePanel.YELLOW;

            }

            callback.run();
        }

        //cutoff for the first 100 steps so simulation is already "warm"
        if (stepsCount > 100) {
            runningAverage = ((runningAverage * (stepsCount - 101)) + treeCounter) / (stepsCount - 100);
            System.out.println("On average a forest has " + runningAverage + " trees");
        }
    }

    private int getNewTreesCount() {
        return 10;
//        return (int)Math.max(1, Math.round(treeCounter * 0.01));
    }

    private Set<Point> getAdjacent(Point p) {
        return PointUtil.findAdjacent(p, configuration, xsize, ysize);
    }

    @Override
    public Field getBoxes() {
        return field;
    }

}
