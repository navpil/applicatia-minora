package ua.lviv.navpil.chaos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Fern {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setSize(1200, 600);
        frame.setTitle("Fern");

        XYPanel innerPanel = new XYPanel();
        frame.add(innerPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FernCalculator calc = new FernCalculator(100, 100);

        for (int i = 0; i < 10000; i++) {
            calc.next();
//            innerPanel.
            innerPanel.redraw(calc.x(), calc.y(), calc.color);
//            frame.getContentPane().repaint();

            SwingUtilities.invokeLater(() -> innerPanel.repaint());
            Thread.sleep(1);
        }

    }

    public static abstract class XYCalculator {

        protected int x;
        protected int y;

        public XYCalculator(int x, int y) {
            this.x = x;
            this.y = y;
        }
        abstract public void next();

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }
    }

    public static class FernCalculator extends XYCalculator {

        private final java.util.List<FernFunction> functions = new ArrayList<>();
        private final Random r = new Random();
        private final int xsize;
        private final int ysize;

        private double dx = 0.5;
        private double dy = 0.5;
        private Color color = Color.BLACK;

        public FernCalculator(int x, int y) {
            super(x, y);
            this.xsize = x;
            this.ysize = y;
            /*
            ƒ1	0	0	0	0.16	0	0	0.01	Stem
            ƒ2	0.85	0.04	−0.04	0.85	0	1.60	0.85	Successively smaller leaflets
            ƒ3	0.20	−0.26	0.23	0.22	0	1.60	0.07	Largest left-hand leaflet
            ƒ4	−0.15	0.28	0.26	0.24	0	0.44	0.07	Largest right-hand leaflet
            */
//            functions.add(new FernFunction(0.85	,0.4,	-0.7,	0.85,	1.0,	1.60,	0.85));
//            functions.add(new FernFunction(0.20	,-0.26,	0.23,	0.22,	0,	1.60,	0.07));
            functions.add(new FernFunction(0.85	,0.4,	-0.4,	0.85,	1.0,	1.60,	0.85));
            functions.add(new FernFunction(0.20	,-0.26,	0.23,	0.22,	0,	1.60,	0.07));

//            functions.add(new FernFunction(0.20	,-0.26,	-0.23,	0.22,	0,	1.60,	0.07));
            //Original:
//            functions.add(new FernFunction(0,	0,	0,	0.16,	0,	0,	0.01));
//            functions.add(new FernFunction(0.85	,0.04,	-0.04,	0.85,	0,	1.60,	0.85));
//            functions.add(new FernFunction(0.20	,-0.26,	0.23,	0.22,	0,	1.60,	0.07));
//            functions.add(new FernFunction(-0.15,	0.28,	0.26,	0.24,	0,	0.44,	0.07));
        }

        @Override
        public void next() {
            FernFunction fernFunction = getFunction();

            double newDx = fernFunction.nextX(dx, dy);
            double newDy = fernFunction.nextY(dx, dy);

            this.dx = newDx;
            this.dy = newDy;

            this.x = (120) + (int)(dx * 30);
            this.y = (460) - ((120) + (int)(dy * 30));
        }

        private FernFunction getFunction() {
            double v = r.nextDouble();
            double runningTotal = 0;
            this.color = Color.RED;
            for (FernFunction function : functions) {
                if (runningTotal + function.probability >= v) {
                    return function;
                } else {
                    runningTotal += function.probability;
                }
                color = Color.BLACK;
            }
            return functions.get(functions.size() - 1);
        }

        private static class FernFunction {
            private final double a;
            private final double b;
            private final double c;
            private final double d;
            private final double e;
            private final double f;

            private final double probability;

            private FernFunction(double a, double b, double c, double d, double e, double f, double probability) {
                this.a = a;
                this.b = b;
                this.c = c;
                this.d = d;
                this.e = e;
                this.f = f;
                this.probability = probability;
            }

            /*

ƒ3	0.20	−0.26	0.23	0.22	0	1.60	0.07	Largest left-hand leaflet

xn + 1 = 0.2 xn − 0.26 yn
yn + 1 = 0.23 xn + 0.22 yn + 1.6.
             */

            public double nextX(double x, double y) {
                return x * a + b * y + e;
            }

            public double nextY(double x, double y) {
                return x * c + d * y + f;
            }
        }
    }

    public static final class XYPanel extends JPanel {
        private int x;
        private int y;
        private Color color;

        public void redraw(int x, int y) {
            redraw(x, y, Color.BLACK);
        }
        public void redraw(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
        int counter = 0;

        protected void paintComponent(Graphics g) {
            System.out.println(counter++);
            //this call would remove previously painted dots, we don't want it.
//            super.paintComponent(g);
            g.setColor(color);
            drawPoint(g, x, y);
        }

        private void drawPoint(Graphics g, int x, int y) {
            g.drawOval(x, y, 2, 2);
//            g.drawLine(x, y, x, y);
        }
    }
}
