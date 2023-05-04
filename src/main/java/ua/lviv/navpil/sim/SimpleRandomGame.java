package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Field;
import ua.lviv.navpil.sim.points.LegacyColorsField;

import java.util.Random;

/**
 * Simply creates random pattern.
 * Not really a game nor a simulation.
 */
public class SimpleRandomGame implements Game {

    private final LegacyColorsField field;

    public static void main(String[] args) {
        RunSimulation.startSimulation(new SimpleRandomGame(20, 20),
                "Simple random game");
    }
    private final int xmax;

    private final int ymax;

    private final Random random = new Random();
    private final int[][] boxes;

    public SimpleRandomGame(int xmax, int ymax) {
        this.xmax = xmax;
        this.ymax = ymax;
        boxes = new int[xmax][];
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new int[ymax];
        }
        field = new LegacyColorsField(boxes);
    }

    @Override
    public Field getBoxes() {
        return field;
    }

    @Override
    public void step(Runnable r) {
        for (int i = 0; i < xmax; i++) {
            for (int j = 0; j < ymax; j++) {
                boxes[i][j] =random.nextInt(5);
            }
        }
    }
}
