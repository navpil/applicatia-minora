package ua.lviv.navpil.sim.points;

import java.awt.*;

public interface Field {

    enum Shape {
        OVAL, SQUARE
    }

    int width();

    int height();

    Color at(int x, int y);

    default String info() {
        return "---";
    }

}
