package ua.lviv.navpil.sim;

import ua.lviv.navpil.sim.points.Field;

public interface Game {

    void step(Runnable r);

    Field getBoxes();
}
