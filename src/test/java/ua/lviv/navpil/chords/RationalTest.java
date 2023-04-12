package ua.lviv.navpil.chords;

import org.junit.Test;
public class RationalTest {

    @Test
    public void testRationalsAreNormalized() {
        Rational half = new Rational(3, 6);

        assert half.equals(new Rational(1, 2));
    }
    @Test
    public void testMultiplicationWorks() {
        Rational half = new Rational(1, 2);
        Rational threeFourth = new Rational(3, 4);

        assert half.multiply(threeFourth).equals(new Rational(3, 8));
    }
    @Test
    public void testInequalityWorks() {
        Rational half = new Rational(3, 6);
        Rational threeFourt = new Rational(3, 4);

        assert !half.gt(threeFourt);
        assert half.lt(threeFourt);
    }

}