package ua.lviv.navpil.sim.points;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class LegacyColorsField implements Field {

    private static final java.util.List<Color> LEGACY_COLORS = Collections.unmodifiableList(
            Arrays.asList(Color.WHITE, Color.YELLOW, Color.RED, Color.BLUE, Color.BLACK)
    );
    private final int[][] boxes;

    public LegacyColorsField(int[][] boxes) {
        this.boxes = boxes;
    }

    @Override
    public int width() {
        return boxes.length;
    }

    @Override
    public int height() {
        return boxes[0].length;
    }

    @Override
    public Color at(int x, int y) {
        int box = boxes[x][y];
        return box < LEGACY_COLORS.size() ? LEGACY_COLORS.get(box) : Color.BLACK;
    }
}
