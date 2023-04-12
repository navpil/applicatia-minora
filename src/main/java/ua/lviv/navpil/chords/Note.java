package ua.lviv.navpil.chords;

import java.util.Objects;

public class Note {

    private final Rational rational;
    private String name;

    public Note() {
        this(1, 1, "DO");
    }

    public Note(String name) {
        this(1, 1, name);
    }

    public Note(int n, int d) {
        this(new Rational(n, d));
    }

    public Note(int n, int d, String name) {
        this(new Rational(n, d), name);
    }

    public Note(Rational rational) {
        this(rational, "");
    }

    public Note(Rational rational, String name) {
        this.rational = rational;
        this.name = name;
    }

    public Note up(Interval interval) {
        return new Note(rational.multiply(interval.getRational()), name + ";up by " + interval.name());
    }

    public Note down(Interval interval) {
        return new Note(rational.divide(interval.getRational()), name + ";down by " + interval.name());
    }

    @Override
    public String toString() {
        return "Note{" + rational +'}' + (name.isEmpty() ? "" : name);
    }

    private static final Rational half = new Rational(1, 2);
    private static final Rational one = new Rational(1, 1);

    private static Note firstOctave(Note note) {
        if(note.rational().lt(half))
            return firstOctave(note.down(Interval.OCTAVE));
        if(note.rational().gt(one))
            return firstOctave(note.up(Interval.OCTAVE));
        return note;

    }

    public Rational rational() {
        return rational;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(rational, note.rational);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rational);
    }
}
