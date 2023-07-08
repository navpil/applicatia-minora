package ua.lviv.navpil.chaos;

public class AffinityTransformationFactory {

    public static AffinityTransformation translation(double dx, double dy) {
        return new GenericAffinityTransformation(1, 0, 0, 1, dx, dy);
    }
    public static AffinityTransformation reflection(boolean x, boolean y) {
        return new GenericAffinityTransformation(x ? -1 : 1, 0, 0, y ? -1 : 1, 0, 0);
    }

    public static AffinityTransformation scale(double scale) {
        return scale(scale, scale);
    }

    public static AffinityTransformation scale(double dx, double dy) {
        return new GenericAffinityTransformation(dx, 0, 0, dy, 0, 0);
    }

    public static AffinityTransformation rotateRadian(double a) {
        double sin = Math.sin(a);
        double cos = Math.cos(a);
        return new GenericAffinityTransformation(cos, -sin, sin, cos, 0, 0);
    }

    public static AffinityTransformation rotateAngle(int a) {
        return rotateRadian(a * Math.PI / 180);
    }

    public static AffinityTransformation shear(double dx, double dy) {
        return new GenericAffinityTransformation(1, dx, dy, 1, 0, 0);
    }
}
