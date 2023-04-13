package ua.lviv.navpil.sim;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * At the beginning some lakes and trees are put onto board (number can be changed manually in the constructor)
 * Each step some amount of trees can be planted (change the value in the getNewTreesCount method)
 * and a match is thrown.
 * If a match hits a tree it burns it and all trees around.
 */
public class BurnTreesSimulation implements Game {

    public static void main(String[] args) {
        RunSimulation.startSimulation(new BurnTreesSimulation(20, 20), "Burn Trees");
    }

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
        putLakes(150);
        for (int i = 0; i < 50; i++) {
            plantTree(random.nextInt(this.xsize), random.nextInt(this.ysize));
        }
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
        trees[p.x][p.y] = GamePanel.BLUE;
        size--;
        Set<Point> adjacent = p.adjacent();
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
            Set<Point> adjacent = point.adjacent();
            while (!adjacent.isEmpty()) {
                HashSet<Point> temp = new HashSet<>(adjacent);
                adjacent.clear();
                for (Point p : temp) {
                    if (trees[p.x][p.y] == GamePanel.GREEN) {
                        allBurned.add(p);
                        trees[p.x][p.y] = GamePanel.RED;
                        adjacent.addAll(p.adjacent());
                    }
                }
                callback.run();
            }
            treeCounter -= allBurned.size();
            for (Point point1 : allBurned) {
                trees[point1.x][point1.y] = GamePanel.BLACK;
            }
        } else {
            callback.run();
        }

        //cutoff for the first 100 steps so simulation is already "warm"
        if (stepsCount > 100) {
            runningAverage = ((runningAverage * (stepsCount - 101)) + treeCounter) / (stepsCount - 100);
            System.out.println("On average a forest has " + runningAverage + " trees");
        }
    }

    private int getNewTreesCount() {
        return (int)Math.max(1, Math.round(treeCounter * 0.01));
    }

    @Override
    public int[][] getBoxes() {
        return trees;
    }

    private class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Set<Point> adjacent() {
            HashSet<Point> adjacent = new HashSet<>();
            if (x > 0) {
                adjacent.add(new Point(x - 1, y));
            }
            if (x < xsize - 1) {
                adjacent.add(new Point(x + 1, y));
            }
            if (y > 0) {
                adjacent.add(new Point(x, y - 1));
            }
            if (y < ysize - 1) {
                adjacent.add(new Point(x, y + 1));
            }
            return adjacent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
