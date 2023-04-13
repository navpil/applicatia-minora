package ua.lviv.navpil.sim;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static final int WHITE = 0;
    public static final int YELLOW = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    public static final int BLACK = 4;
    public static final int RED = 5;

    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] boxes = game.getBoxes();
        int step = 10;
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                g.setColor(selectColor(boxes[i][j]));
                g.fillOval(step + (i * step), step + (j * step), step, step);
//                g.fillRect(step + (i * step), step + (j * step), step, step);
            }
        }
    }

    private Color selectColor(int box) {
        switch (box) {
            case WHITE:
                return Color.WHITE;
            case YELLOW:
                return Color.YELLOW;
            case GREEN:
                return Color.GREEN;
            case BLUE:
                return Color.BLUE;
            case BLACK:
                return Color.BLACK;
            default:
                return Color.RED;
        }
    }

}
