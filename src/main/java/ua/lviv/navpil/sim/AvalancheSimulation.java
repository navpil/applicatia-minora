package ua.lviv.navpil.sim;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Each step a sand is thrown on some square.
 * If there are more than three sands, they are distributed around, creating an avalanche
 */
public class AvalancheSimulation implements Game {

    public static void main(String[] args) {
        RunSimulation.startSimulation(new AvalancheSimulation(20, 20),
                "Avalanche simulation");
    }

    private final Random random = new Random();
    private final int[][] sandbox;
    private final int xsize;
    private final int ysize;

    public AvalancheSimulation(int xsize, int ysize) {
        this.xsize = xsize;
        this.ysize = ysize;
        sandbox = new int[xsize][];
        for (int i = 0; i < sandbox.length; i++) {
            sandbox[i] = new int[ysize];
        }

        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                int amountOfSand = random.nextInt(4) + 1;
                for (int k = 0; k < amountOfSand; k++) {
                    dropSand(i, j);
                }
            }
        }
    }

    private void dropSand(int x, int y) {
        sandbox[x][y]++;
    }

    @Override
    public void step(Runnable callback) {
        //Drop a sand
        int x = random.nextInt(xsize);
        int y = random.nextInt(ysize);
        dropSand(x, y);

        //Start avalanch?
        if (sandbox[x][y] >= 4) {
            callback.run();

            Set<Point> allAffected = new HashSet<>();
            Point point = new Point(x, y);

            sandbox[point.x][point.y] -= 4;
            Set<Point> adjacentToPoint = point.adjacent();
            for (Point p : adjacentToPoint) {
                sandbox[p.x][p.y] += 1;
            }
            allAffected.addAll(adjacentToPoint);

            while (!allAffected.isEmpty()) {
                HashSet<Point> temp = new HashSet<>(allAffected);
                allAffected.clear();
                for (Point p : temp) {
                    if (sandbox[p.x][p.y] >= 4) {

                        sandbox[p.x][p.y] -= 4;
                        adjacentToPoint = p.adjacent();
                        for (Point adj : adjacentToPoint) {
                            sandbox[adj.x][adj.y] += 1;
                        }
                        allAffected.addAll(adjacentToPoint);

                    }
                }
                callback.run();
            }
        } else {
            callback.run();
        }
    }

    @Override
    public int[][] getBoxes() {
        return sandbox;
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
                adjacent.add(new Point(x-1, y));
            }
            if (x < xsize-1) {
                adjacent.add(new Point(x+1, y));
            }
            if (y > 0) {
                adjacent.add(new Point(x, y-1));
            }
            if (y < ysize-1) {
                adjacent.add(new Point(x, y+1));
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
