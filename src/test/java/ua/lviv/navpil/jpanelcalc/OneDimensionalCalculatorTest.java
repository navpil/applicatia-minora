package ua.lviv.navpil.jpanelcalc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ua.lviv.navpil.jpanelcalc.OneDimensionalCalculator.calculate;

public class OneDimensionalCalculatorTest {


    @Test
    public void testEqualSizes() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        sizes.add(new OneDimensionalSize(0.2));
        sizes.add(new OneDimensionalSize(0.8));

        int[] calculate = calculate(sizes, 20);
        assert calculate[0] == 4 && calculate[1] == 16;

    }

    @Test
    public void testMax() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        sizes.add(new OneDimensionalSize(0.2));
        OneDimensionalSize hasMax = new OneDimensionalSize(0.8);
        hasMax.setMax(2);
        sizes.add(hasMax);

        int[] calculate = calculate(sizes, 20);
        assert calculate[0] == 18 && calculate[1] == 2;
    }

    @Test
    public void testMin() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        OneDimensionalSize hasMin = new OneDimensionalSize(0.2);
        hasMin.setMin(15);

        sizes.add(hasMin);
        sizes.add(new OneDimensionalSize(0.8));

        int[] calculate = calculate(sizes, 20);
        assert (calculate[0] == 15 && calculate[1] == 5);
    }

    @Test
    public void testMaxMin() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        OneDimensionalSize hasMax = new OneDimensionalSize(0.2);
        hasMax.setMax(3);
        sizes.add(hasMax);

        OneDimensionalSize hasMin = new OneDimensionalSize(0.8);
        hasMin.setMin(5);
        sizes.add(hasMin);

        int[] calculate = calculate(sizes, 20);
        assert (calculate[0] == 3 && calculate[1] == 17);

    }

    @Test
    public void testMaxMin2() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        OneDimensionalSize hasMax = new OneDimensionalSize(0.2);
        hasMax.setMax(3);
        sizes.add(hasMax);

        OneDimensionalSize hasMin = new OneDimensionalSize(0.8);
        hasMin.setMin(5);
        sizes.add(hasMin);

        int[] calculate = calculate(sizes, 6);
        assert (calculate[0] == 1 && calculate[1] == 5);

    }

    @Test
    public void testFixCase() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        sizes.add(new OneDimensionalSize(30));
        sizes.add(new OneDimensionalSize(0.8));

        int[] calculate = calculate(sizes, 20);
        assert (calculate[0] == 30 && calculate[1] == 0);
    }

    @Test
    public void testGeneralCase() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        sizes.add(new OneDimensionalSize(5));
        sizes.add(new OneDimensionalSize(0.5));
        sizes.add(new OneDimensionalSize(4));

        int[] calculate = calculate(sizes, 20);
        assert (calculate[0] == 5 && calculate[1] == 11 && calculate[2] == 4);
    }

    @Test
    public void testThreeSizes() {
        List<OneDimensionalSize> sizes = new ArrayList<>();
        sizes.add(new OneDimensionalSize(0.2));
        sizes.add(new OneDimensionalSize(0.4));
        sizes.add(new OneDimensionalSize(0.5));

        int[] calculate = calculate(sizes, 11);
        assert (calculate[0] == 2 && calculate[1] == 4 && calculate[2] == 5);

    }

}