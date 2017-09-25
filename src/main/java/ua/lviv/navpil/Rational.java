package ua.lviv.navpil;

public class Rational {
    private int nominator;
    private int denominator;

    public static void main(String [] args) {
        Rational half = new Rational(3, 6);
        System.out.println(half);
        Rational threeFourt = new Rational(3, 4);
        System.out.println(half.multiply(threeFourt));
        System.out.println(half.gt(threeFourt));
    }

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
    public String toString() {
        return "" + nominator +
                "/" + denominator;
    }
}
