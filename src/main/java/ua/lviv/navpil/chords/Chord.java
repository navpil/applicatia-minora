package ua.lviv.navpil.chords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chord {

    private final Note[] notes;

    public Chord(Note ... notes){
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Chord{" +
                "notes=" + (notes == null ? null : Arrays.asList(notes)) +
                '}';
    }

    public List<Rational> intervals() {
        ArrayList<Rational> intervals = new ArrayList<Rational>();
        for(int i = 1; i < notes.length; i++) {
            intervals.add(notes[i].rational().divide(notes[i - 1].rational()));
        }
        return intervals;
    }
}
