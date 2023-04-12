package ua.lviv.navpil.chords;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NoteTest {

    @Test
    public void reFailure() {
        Note DO = new Note();
        //Building RE (D) by going down by fourth and going up a fifth
        Note RE = DO.down(Interval.QUARTA).up(Interval.QUINTA);

        //Building LA (A) by going up by third and going down a fifth
        Note LA = DO.up(Interval.TERTIA).down(Interval.QUINTA);
        //RE (D) is up a quarter from LA (A)
        Note RE_1 = LA.up(Interval.QUARTA);

        Assertions.assertThat(RE).isEqualTo(RE_1);
    }

    @Test
    public void wrongReQuint() {
        Note DO = new Note();
        //Building RE (D) by going down by fourth and going up a fifth
        Note RE = DO.down(Interval.QUARTA).up(Interval.QUINTA);

        //Building LA (A) by going up by third and going down a fifth
        Note LA = DO.up(Interval.TERTIA).down(Interval.QUINTA);

        Rational reToLaQuinta = LA.up(Interval.OCTAVE).rational().divide(RE.rational());
        Assertions.assertThat(reToLaQuinta).isEqualTo(Interval.QUINTA.getRational());
    }

    //Does not really test anything, simply shows how to construct the just octave (according to me)
    // and how are the chords structured there
    @Test
    public void initialInvestigation() {
        Note DO = new Note();
        Note MI = DO.up(Interval.TERTIA);
        Note SOL = DO.up(Interval.QUINTA);

        Note FA = DO.up(Interval.QUARTA);
        Note LA = MI.up(Interval.QUARTA);
        Note RE = SOL.down(Interval.QUARTA);
        Note TI = MI.up(Interval.QUINTA);
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
        System.out.println(new Chord(SOL, SOL.up(Interval.TERTIA), SOL.up(Interval.QUINTA)));
    }


}