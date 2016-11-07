package ua.csia.dmp;

public enum Interval {
    OCTAVE(new Rational(1, 2)), QUINT(new Rational(2, 3)), QUART(new Rational(3, 4)), TER(new Rational(4, 5));
    private final Rational rational;

    Interval(Rational rational) {
        this.rational = rational;
    }

    public Rational getRational() {
        return rational;
    }
}
