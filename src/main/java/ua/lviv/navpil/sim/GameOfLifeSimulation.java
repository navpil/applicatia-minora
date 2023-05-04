package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Configuration;
import ua.lviv.navpil.sim.points.Point;
import ua.lviv.navpil.sim.points.PointUtil;

import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GameOfLifeSimulation implements Game {

    public static void main(String[] args) {
        RunSimulation.startSimulation(new GameOfLifeSimulation(200, 100),
                "Conway's game of Life");
    }

    private final Random random = new Random();

    private final Configuration configuration = new Configuration(8, true);
    private int[][] sandbox;
    private final int xsize;
    private final int ysize;

    public GameOfLifeSimulation(int xsize, int ysize) {
        this.xsize = xsize;
        this.ysize = ysize;
        sandbox = createSandbox();
        withEveryPoint(p -> {
            if (random.nextBoolean()) {
                sandbox[p.x()][p.y()] = GamePanel.BLACK;
            }
        });
    }

    private int[][] createSandbox() {
        final int[][] sandbox;
        sandbox = new int[xsize][];
        for (int i = 0; i < sandbox.length; i++) {
            sandbox[i] = new int[ysize];
        }
        return sandbox;
    }


    @Override
    public void step(Runnable ignore) {

        int[][] newBox = createSandbox();

        withEveryPoint((Point p) -> {
            Set<Point> adjacent = getAdjacent(p);
            int alivePoints = adjacent.stream().filter(this::isAlive).collect(Collectors.toSet()).size();
            /*
             * Any live cell with two or three live neighbours survives.
             * Any dead cell with three live neighbours becomes a live cell.
             * All other live cells die in the next generation. Similarly, all other dead cells stay dead.
             */
            if (isAlive(p)) {
                if (alivePoints == 2 || alivePoints == 3
//                        || alivePoints == 6
                ) {
                    newBox[p.x()][p.y()] = GamePanel.BLACK;
                }
            } else {
                if (alivePoints == 3) {
                    newBox[p.x()][p.y()] = GamePanel.BLACK;
                }
            }
        });

        sandbox = newBox;

    }

    private boolean isAlive(Point p) {
        return sandbox[p.x()][p.y()] == GamePanel.BLACK;
    }

    private void withEveryPoint(Consumer<Point> pointConsumer) {
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                pointConsumer.accept(new Point(i, j));
            }
        }
    }

    private Set<Point> getAdjacent(Point p) {
        return PointUtil.findAdjacent(p, configuration, xsize, ysize);
    }

    @Override
    public int[][] getBoxes() {
        return sandbox;
    }

}
