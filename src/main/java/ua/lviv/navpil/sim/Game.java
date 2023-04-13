package ua.lviv.navpil.sim;

public interface Game {

    void step(Runnable r);

    int[][] getBoxes();
}
