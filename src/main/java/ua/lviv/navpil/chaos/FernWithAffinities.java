package ua.lviv.navpil.chaos;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FernWithAffinities {

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setSize(1200, 600);
        frame.setTitle("Fern");

        XYPanel2 innerPanel = new XYPanel2();
        frame.add(innerPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FernCalculator calc = new FernCalculator(100, 100);

        for (int i = 0; i < 10000; i++) {
            calc.next();
//            innerPanel.
            innerPanel.redraw(calc.x(), calc.y(), calc.color);
//            frame.getContentPane().repaint();

//            Thread.sleep(1);
            SwingUtilities.invokeLater(() -> innerPanel.repaint());
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

        private final ProbabilityList<AffinityTransformation> functions = new ProbabilityList<>();
        private final ProbabilityList<Color> colors = new ProbabilityList<Color>().withDefault(Color.BLACK);
        private final Random r = new Random();

        private double dx = 0.5;
        private double dy = 0.5;
        private Color color = Color.BLACK;

        public FernCalculator(int x, int y) {
            super(x, y);
            /*
            ƒ1	0	0	0	0.16	0	0	0.01	Stem
            ƒ2	0.85	0.04	−0.04	0.85	0	1.60	0.85	Successively smaller leaflets
            ƒ3	0.20	−0.26	0.23	0.22	0	1.60	0.07	Largest left-hand leaflet
            ƒ4	−0.15	0.28	0.26	0.24	0	0.44	0.07	Largest right-hand leaflet
            */
//            functions.add(new FernFunction(0.85	,0.4,	-0.7,	0.85,	1.0,	1.60,	0.85));
//            functions.add(new FernFunction(0.20	,-0.26,	0.23,	0.22,	0,	1.60,	0.07));
//            functions.add(new FernFunction(0.85	,0.4,	-0.4,	0.85,	1.0,	1.60,	0.85));
//            functions.add(new FernFunction(0.20	,-0.26,	0.23,	0.22,	0,	1.60,	0.07));

//            functions.add(new FernFunction(0.20	,-0.26,	-0.23,	0.22,	0,	1.60,	0.07));
            //Original:
            populateShell();
        }

        private void populateShell() {
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(0.85))
                            .translation(1.0, 1.6)
                   ,0.85);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(0.5))
                            .combine(AffinityTransformationFactory.rotateAngle(15))
//                            .combine(AffinityTransformationFactory.reflection(true, false))
                            .combine(AffinityTransformationFactory.translation(0, 1.6))
                    ,0.07);
//            functions.add(new GenericAffinityTransformation(0.85	,0.4,	-0.4,	0.85,
//                    1.0,	1.60),	0.85);
//            functions.add(new GenericAffinityTransformation(0.20	,-0.26,	0.23,	0.22,
//                    0,	1.60),	0.07);
        }

        private void populateShell2() {
            functions.add(new GenericAffinityTransformation(0.85	,0.4,	-0.4,	0.85,
                    1.0,	1.60),	0.85);
            functions.add(new GenericAffinityTransformation(0.20	,-0.26,	0.23,	0.22,
                    0,	1.60),	0.07);
        }
        private void populateTree() {
            double leaveScale = 0.64;
            double dy1 = 2.6;
            functions.add(new GenericAffinityTransformation(0.01,	-0.01,	0,
                    0.46,	0,	0),	0.01);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(leaveScale, leaveScale))
                            .combine(AffinityTransformationFactory.rotateAngle(15))
                            .combine(AffinityTransformationFactory.translation(0, dy1))
                    ,0.07);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(leaveScale, leaveScale* 0.9))
                            .combine(AffinityTransformationFactory.rotateAngle(55))
                            .combine(AffinityTransformationFactory.translation(0, dy1))
                    ,0.07);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(leaveScale, leaveScale* 0.9))
                            .combine(AffinityTransformationFactory.rotateAngle(-35))
                            .combine(AffinityTransformationFactory.translation(0, dy1))
                    ,0.07);
        }
        private void populateFern() {
            double leaveScale = 0.3;

            functions.add(new GenericAffinityTransformation(0,	0,	0,	0.16,	0,	0),	0.01);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(0.85))
                            .combine(AffinityTransformationFactory.translation(0, 1.6))
                            .combine(AffinityTransformationFactory.rotateAngle(-2))
                    ,0.85);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(leaveScale))
                            .combine(AffinityTransformationFactory.rotateAngle(45))
                            .combine(AffinityTransformationFactory.translation(0, 1.6))
                    ,0.07);
            functions.add(CombinedAffinity
                            .create(AffinityTransformationFactory.scale(leaveScale))
                            .combine(AffinityTransformationFactory.reflection(false, true))
                            .combine(AffinityTransformationFactory.rotateAngle(135))
                            .combine(AffinityTransformationFactory.translation(0, 1.6))
                    ,0.07);

            colors.add(Color.RED, 0.01);
            colors.add(Color.GREEN, 0.85);
            colors.add(Color.BLUE, 0.07);
            colors.add(Color.BLACK, 0.07);
        }

        @Override
        public void next() {
            double v = r.nextDouble();
            color = colors.getForProbability(v);
            AffinityTransformation fernFunction = functions.getForProbability(v);

            Point transformed = fernFunction.apply(new Point(dx, dy));
            this.dx = transformed.getX();
            this.dy = transformed.getY();

            this.x = (120) + (int)(dx * 30);
            this.y = (460) - ((120) + (int)(dy * 30));
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
//            g.drawOval(x, y, 2, 2);
            g.drawLine(x, y, x, y);
        }
    }
    public static final class XYPanel2 extends JPanel {

        private final Set<Point> pointSet = Collections.newSetFromMap(new ConcurrentHashMap<>());

        public void redraw(int x, int y) {
            redraw(x, y, Color.BLACK);
        }
        public void redraw(int x, int y, Color color) {
            pointSet.add(new Point(x, y, color));
        }
        int counter = 0;

        protected void paintComponent(Graphics g) {
            System.out.println(counter++);
            for (Point point : pointSet) {
                g.setColor(point.color);
                drawPoint(g, point.x, point.y);
            }
            //this call would remove previously painted dots, we don't want it.
//            super.paintComponent(g);
        }

        private void drawPoint(Graphics g, int x, int y) {
//            g.drawOval(x, y, 2, 2);
            g.drawLine(x, y, x, y);
        }

        private static final class Point {
            private final int x;
            private final int y;
            private final Color color;

            private Point(int x, int y, Color color) {
                this.x = x;
                this.y = y;
                this.color = color;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Point point = (Point) o;
                return x == point.x && y == point.y && Objects.equals(color, point.color);
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y, color);
            }
        }
    }
}
