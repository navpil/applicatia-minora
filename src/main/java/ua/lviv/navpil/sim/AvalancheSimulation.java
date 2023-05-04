package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Configuration;
import ua.lviv.navpil.sim.points.Field;
import ua.lviv.navpil.sim.points.LegacyColorsField;
import ua.lviv.navpil.sim.points.Point;
import ua.lviv.navpil.sim.points.PointUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Each step a sand is thrown on some square.
 * If there are more than three sands, they are distributed around, creating an avalanche
 */
public class AvalancheSimulation implements Game {

    private final LegacyColorsField field;

    public static void main(String[] args) {
        boolean useLegacyColors = true;
        RunSimulation.startSimulation(new AvalancheSimulation(20, 20),
                "Avalanche simulation");
    }

    private final Random random = new Random();
    
    private final Configuration configuration = new Configuration(4, false);
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
                int amountOfSand = random.nextInt(3) + 1;
                for (int k = 0; k < amountOfSand; k++) {
                    dropSand(i, j);
                }
            }
        }

        field = new LegacyColorsField(sandbox);
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

            Point point = new Point(x, y);

            sandbox[point.x()][point.y()] -= 4;
            Set<Point> adjacentToPoint = getAdjacent(point);
            for (Point p : adjacentToPoint) {
                sandbox[p.x()][p.y()] += 1;
            }
            Set<Point> allAffected = new HashSet<>(adjacentToPoint);

            while (!allAffected.isEmpty()) {
                HashSet<Point> temp = new HashSet<>(allAffected);
                allAffected.clear();
                for (Point p : temp) {
                    if (sandbox[p.x()][p.y()] >= 4) {

                        sandbox[p.x()][p.y()] -= 4;
                        adjacentToPoint = getAdjacent(p);
                        for (Point adj : adjacentToPoint) {
                            sandbox[adj.x()][adj.y()] += 1;
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

    private Set<Point> getAdjacent(Point p) {
        return PointUtil.findAdjacent(p, configuration, xsize, ysize);
    }

    @Override
    public Field getBoxes() {
        return field;
    }

}
