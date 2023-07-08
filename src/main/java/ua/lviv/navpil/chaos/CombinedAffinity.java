package ua.lviv.navpil.chaos;

import java.util.ArrayList;

public class CombinedAffinity implements AffinityTransformation {

    private final ArrayList<AffinityTransformation> transformations;

    private CombinedAffinity(AffinityTransformation transformation) {
        transformations = new ArrayList<>();
        transformations.add(transformation);
    }

    public static CombinedAffinity create(AffinityTransformation transformation) {
        return new CombinedAffinity(transformation);
    }

    public CombinedAffinity combine(AffinityTransformation transformation) {
        transformations.add(transformation);
        return this;
    }
    public CombinedAffinity translation(double dx, double dy) {
        return combine(new GenericAffinityTransformation(1, 0, 0, 1, dx, dy));
    }
    public CombinedAffinity reflection(boolean x, boolean y) {
        return combine(new GenericAffinityTransformation(x ? -1 : 1, 0, 0, y ? -1 : 1, 0, 0));
    }

    public CombinedAffinity scale(double scale) {
        return scale(scale, scale);
    }

    public CombinedAffinity scale(double dx, double dy) {
        return combine(new GenericAffinityTransformation(dx, 0, 0, dy, 0, 0));
    }

    public CombinedAffinity rotateRadian(double a) {
        double sin = Math.sin(a);
        double cos = Math.cos(a);
        return combine(new GenericAffinityTransformation(cos, -sin, sin, cos, 0, 0));
    }

    public CombinedAffinity rotateAngle(int a) {
        return rotateRadian(a * Math.PI / 180);
    }

    public CombinedAffinity shear(double dx, double dy) {
        return combine(new GenericAffinityTransformation(1, dx, dy, 1, 0, 0));
    }
    @Override
    public Point apply(Point point) {
        Point result = point;
        for (AffinityTransformation transformation : transformations) {
            result = transformation.apply(result);
        }
        return result;
    }
}
