package ua.lviv.navpil.sim.points;

import ua.lviv.navpil.sim.GamePanel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleColorField implements Field {

    private final Map<Integer, Color> cache = new HashMap<>();
    private int[][] boxes;

    public SimpleColorField(int[][] boxes) {
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
        if (box == GamePanel.WHITE) {
            return Color.WHITE;
        } else if (box == GamePanel.BLACK) {
            return Color.BLACK;
        }
        if (!cache.containsKey(box)) {
            cache.put(box, new Color(box));
        }
        return cache.get(box);
    }

    public void changeBoxes(int[][] boxes) {
        this.boxes = boxes;
    }
}
