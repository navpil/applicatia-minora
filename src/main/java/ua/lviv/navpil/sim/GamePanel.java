package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Field;

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

    private final Game game;
    private final int step;
    public GamePanel(Game game, int step) {
        this.game = game;
        this.step = step;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Field boxes = game.getBoxes();
        for (int i = 0; i < boxes.width(); i++) {
            for (int j = 0; j < boxes.height(); j++) {
                g.setColor(boxes.at(i, j));
                g.fillOval(step + (i * step), step + (j * step), step, step);
//                g.fillRect(step + (i * step), step + (j * step), step, step);
            }
        }
    }

}
