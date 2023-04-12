package ua.lviv.navpil.chords;

public enum Interval {
    OCTAVE(new Rational(1, 2)),
    /**
     * Fifth
     */
    QUINTA(new Rational(2, 3)),
    /**
     * Fourth
     */
    QUARTA(new Rational(3, 4)),
    /**
     * Major third
     */
    TERTIA(new Rational(4, 5));
    private final Rational rational;

    Interval(Rational rational) {
        this.rational = rational;
    }

    public Rational getRational() {
        return rational;
    }
}
