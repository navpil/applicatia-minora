package ua.lviv.navpil.dice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Die {
    private final int[] faces;

    public Die(int ... faces) {
        this.faces = faces;
    }

    public int[] getFaces() {
        return faces;
    }

    @Override
    public String toString() {
        return "Die{" +
                "faces=" + Arrays.toString(faces) +
                '}';
    }
}
