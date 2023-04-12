package ua.lviv.navpil.chords;

import java.util.Objects;

public class Rational {
    private int nominator;
    private int denominator;

    public Rational(int nom, int denom) {
        this.nominator = nom;
        this.denominator = denom;
        normalize();
    }

    public Rational multiply(Rational other) {
        return new Rational(nominator * other.nominator, denominator * other.denominator);
    }

    public Rational divide(Rational other) {
        return new Rational(nominator * other.denominator, denominator * other.nominator);
    }

    private void normalize() {
        int cdn = cdn(nominator, denominator);
        nominator = nominator / cdn;
        denominator = denominator / cdn;
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }

    public boolean lt(Rational other) {
        return nominator * other.denominator < other.nominator * denominator;
    }

    public boolean gt(Rational other) {
        return nominator * other.denominator > other.nominator * denominator;
    }

    private int cdn(int a, int b){
        if(b == 0)
            return a;
        return cdn(b, a % b);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational rational = (Rational) o;
        return nominator == rational.nominator && denominator == rational.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominator, denominator);
    }

    @Override
    public String toString() {
        return "" + nominator +
                "/" + denominator;
    }
}
