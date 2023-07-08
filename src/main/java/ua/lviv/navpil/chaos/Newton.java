package ua.lviv.navpil.chaos;

import ua.lviv.navpil.sim.Game;
import ua.lviv.navpil.sim.GamePanel;
import ua.lviv.navpil.sim.points.Field;
import ua.lviv.navpil.sim.points.LegacyColorsField;

import javax.swing.*;

public class Newton {

    public static void main(String[] args) {

        int size = 800;
        int halfsize = size / 2;

        int[][] ints = new int[size][];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = new int[size];
        }

        double coef = 0.000002;
        double dx = 0;//-80;
        double dx2 = 0;//-150;
        double dy = 0;
        for (int i = -halfsize; i < halfsize; i++) {
            for (int j = -halfsize; j < halfsize; j++) {
                INum prev = new INum((i * 1.0 + dx)/ coef + dx2, (j * 1.0 + dy)/ coef);
                for (int k = 0; k < 100; k++) {
                    prev = prev.next();
                }
                if (Math.abs(prev.real - 1) < 0.01) {
                    ints[size -(i+ halfsize) - 1][j+ halfsize] = 1;
                } else if (prev.imaginary < 0) {
                    ints[size -(i+ halfsize) - 1][j+ halfsize] = 2;
                } else {
                    ints[size -(i+ halfsize) - 1][j+ halfsize] = 3;
                }
//                if (Math.abs(prev.real - 1) < 0.01) {
//                    ints[j+100][200-(i+100) - 1] = 1;
//                } else if (prev.imaginary < 0) {
//                    ints[j+100][200-(i+100) - 1] = 2;
//                } else {
//                    ints[j+100][200-(i+100) - 1] = 3;
//                }
            }
        }

        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setTitle("Newton");

        JPanel innerPanel = new GamePanel(new Game() {
            @Override
            public void step(Runnable r) {
                //do nothing
            }

            @Override
            public Field getBoxes() {
                return new LegacyColorsField(ints);
            }
        }, 1, GamePanel.PixelShape.SQUARE);
        frame.add(innerPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

//        innerPanel.repaint();

//        for (int i = 0; i < ints.length; i++) {
//            System.out.println(Arrays.toString(ints[i]));
//        }

    }


    public static final class INum {
        private final double real;
        private final double imaginary;

        public INum(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public INum next() {
            //x - ((x^3 - 1)/(3*x^2))
            return this.minus(this.multiply(this).multiply(this).minus(new INum(1, 0))
                    .divide(this.multiply(this).multiply(new INum(3, 0))));
        }


        public INum multiply(INum inum) {
            double nReal = real*inum.real - imaginary*inum.imaginary;
            double nImaginary = real*inum.imaginary + imaginary*inum.real;
            return new INum(nReal, nImaginary);
        }

        public INum add(INum inum) {
            return new INum(real + inum.real, imaginary + inum.imaginary);
        }
        public INum minus(INum inum) {
            return new INum(real - inum.real, imaginary - inum.imaginary);
        }

        public INum divide(INum iNum) {
            INum upperPart = multiply(new INum(iNum.real, -iNum.imaginary));
            double lowerPart = iNum.real*iNum.real + iNum.imaginary*iNum.imaginary;
            return new INum(upperPart.real / lowerPart, upperPart.imaginary / lowerPart);
        }

        @Override
        public String toString() {
            return "INum{" +
                    "real=" + real +
                    ", imaginary=" + imaginary +
                    '}';
        }
    }
}
