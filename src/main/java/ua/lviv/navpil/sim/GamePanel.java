package ua.lviv.navpil.sim;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    //These are reversed!!!
    public static final int WHITE = 0;
    public static final int BLACK = 0xffffff;

    public static final int YELLOW = 0xffff00;
    public static final int GREEN = 0x00ff00;
    public static final int BLUE = 0x0000ff;

    public static final int RED = 0xff0000;

    public static final java.util.List<Color> LEGACY_COLORS = Collections.unmodifiableList(
            Arrays.asList(Color.WHITE, Color.YELLOW, Color.RED, Color.BLUE, Color.BLACK)
    );

    private final Map<Integer, Color> cache = new HashMap<>();

    private final Game game;
    private final int step;
    private final boolean useLegacyColors;

    public GamePanel(Game game, int step, boolean useLegacyColors) {
        this.game = game;
        this.step = step;
        this.useLegacyColors = useLegacyColors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] boxes = game.getBoxes();
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                g.setColor(selectColor(boxes[i][j]));
                g.fillOval(step + (i * step), step + (j * step), step, step);
//                g.fillRect(step + (i * step), step + (j * step), step, step);
            }
        }
    }

    private Color selectColor(int box) {
        if (useLegacyColors) {
            return box < LEGACY_COLORS.size() ? LEGACY_COLORS.get(box) : Color.BLACK;
        }
        if (box == WHITE) {
            return Color.WHITE;
        } else if (box == BLACK) {
            return Color.BLACK;
        }
        if (!cache.containsKey(box)) {
            cache.put(box, new Color(box));
        }
        return cache.get(box);
    }

}
