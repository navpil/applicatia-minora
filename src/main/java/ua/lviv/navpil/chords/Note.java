package ua.lviv.navpil.chords;

import ua.lviv.navpil.Rational;

import java.util.ArrayList;
import java.util.List;

public class Note {

    private final Rational rational;
    private String story;

    public Note() {
        this(1, 1, "DO");
    }

    public Note(String initial) {
        this(1, 1, initial);
    }

    public Note(int n, int d) {
        this(new Rational(n, d));
    }

    public Note(int n, int d, String story) {
        this(new Rational(n, d), story);
    }

    public Note(Rational rational) {
        this(rational, "");
    }

    public Note(Rational rational, String story) {
        this.rational = rational;
        this.story = story;
    }

    public Note up(Interval interval) {
        return new Note(rational.multiply(interval.getRational()), story + ";up by " + interval.name());
    }

    public Note down(Interval interval) {
        return new Note(rational.divide(interval.getRational()), story + ";down by " + interval.name());
    }

    @Override
    public String toString() {
        return "Note{" + rational +'}' + (story.isEmpty() ? "" : story);
    }

    public static void main(String [] args) {
//        initialInvestigation();
        reFailure();
    }

    private static void reFailure() {
        Note DO = new Note();
        Note RE = DO.down(Interval.QUART).up(Interval.QUINT);
//        Note FA = DO.up(QUART);
        Note LA = DO.up(Interval.TER).down(Interval.QUINT);

        Note RE_1 = LA.up(Interval.QUART);
        System.out.println("Which Re is correct?: " + RE + ", " + RE_1);

        System.out.println("This is normal RE quint: " + LA.up(Interval.OCTAVE).rational().divide(RE.rational()));
    }

    private static void initialInvestigation() {
        Note DO = new Note();
        Note MI = DO.up(Interval.TER);
        Note SOL = DO.up(Interval.QUINT);

        Note FA = DO.up(Interval.QUART);
        Note LA = MI.up(Interval.QUART);
        Note RE = SOL.down(Interval.QUART);
        Note TI = MI.up(Interval.QUINT);
        Octave normal = new Octave(DO, RE, MI, FA, SOL, LA, TI);
        System.out.println(normal);

        Chord all = new Chord(DO, RE, MI, FA, SOL, LA, TI, DO.up(Interval.OCTAVE));
        System.out.println("All intervals: " + all.intervals());


        Chord doMajor = new Chord(DO, MI, SOL);
        Chord reMinor = new Chord(RE, FA, LA);
        Chord miMinor = new Chord(MI, SOL, TI);
        Chord faMajor = new Chord(FA, LA, DO.up(Interval.OCTAVE));
        Chord solMajor = new Chord(SOL, TI, RE.up(Interval.OCTAVE));
        Chord laMinor = new Chord(LA, DO.up(Interval.OCTAVE), MI.up(Interval.OCTAVE));
        Chord tiMin = new Chord(TI, RE.up(Interval.OCTAVE), FA.up(Interval.OCTAVE));
        List<Chord> chords = new ArrayList<Chord>();
        chords.add(doMajor  );
        chords.add(reMinor  );
        chords.add(miMinor  );
        chords.add(faMajor  );
        chords.add(solMajor );
        chords.add(laMinor  );
        chords.add(tiMin    );
        for (Chord chord : chords) {
            System.out.println("Chord: " + chord.intervals());
        }


        System.out.println(solMajor.intervals());
        System.out.println(doMajor.intervals());

        System.out.println(solMajor);
        System.out.println(new Chord(SOL, SOL.up(Interval.TER), SOL.up(Interval.QUINT)));
    }

    private static final Rational half = new Rational(1, 2);
    private static final Rational one = new Rational(1, 1);
    private static Note firstOctave(Note note) {
        if(note.rational.lt(half))
            return firstOctave(note.down(Interval.OCTAVE));
        if(note.rational.gt(one))
            return firstOctave(note.up(Interval.OCTAVE));
        return note;

    }


    public Rational rational() {
        return rational;
    }
}
