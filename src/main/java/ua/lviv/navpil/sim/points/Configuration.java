package ua.lviv.navpil.sim.points;

public class Configuration {

    private final int surroundingPoints;
    private final boolean wrapAround;

    public Configuration(int surroundingPoints, boolean wrapAround) {
        this.surroundingPoints = surroundingPoints;
        this.wrapAround = wrapAround;
    }

    public int surroundingPoints() {
        return surroundingPoints;
    }
    public boolean wrapAround() {
        return wrapAround;
    }
}
