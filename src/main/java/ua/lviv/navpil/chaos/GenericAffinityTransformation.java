package ua.lviv.navpil.chaos;

public class GenericAffinityTransformation implements AffinityTransformation {

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    public GenericAffinityTransformation(double a, double b, double c, double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public Point apply(Point point) {
        double x = point.getX();
        double y = point.getY();
        return new Point(x * a + b * y + e, x * c + d * y + f);
    }
}
