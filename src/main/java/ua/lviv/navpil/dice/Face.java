package ua.lviv.navpil.dice;

public class Face {
    private final int value;
    private final int count;

    public Face(int value) {
        this(value, 1);
    }
    public Face(int value, int count) {
        this.value = value;
        this.count = count;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }
}
