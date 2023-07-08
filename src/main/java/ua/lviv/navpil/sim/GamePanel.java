package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Field;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel {

    //These are reversed!!!
    public static final int WHITE = 0;
    public static final int BLACK = 0xffffff;

    public static final int YELLOW = 0xffff00;
    public static final int GREEN = 0x00ff00;
    public static final int BLUE = 0x0000ff;

    public static final int RED = 0xff0000;
    private PixelShape pixelShape;

    public enum PixelShape {
        CIRCLE, SQUARE
    }
    private final Game game;
    private final int step;
    public GamePanel(Game game, int step) {
        this(game, step, PixelShape.CIRCLE);
    }

    public GamePanel(Game game, int step, PixelShape pixelShape) {
        this.game = game;
        this.step = step;
        this.pixelShape = pixelShape;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Field boxes = game.getBoxes();
        for (int i = 0; i < boxes.width(); i++) {
            for (int j = 0; j < boxes.height(); j++) {
                g.setColor(boxes.at(i, j));
                if (Objects.requireNonNull(pixelShape) == PixelShape.CIRCLE) {
                    g.fillOval(step + (i * step), step + (j * step), step, step);
                } else {
                    g.fillRect(step + (i * step), step + (j * step), step, step);
                }
            }
        }
        g.setColor(Color.BLACK);
        String info = boxes.info();
        g.drawChars(info.toCharArray(), 0, info.length(), 0, ((boxes.height() + 1) * step) + 20);
    }

}
