package io.github.darkkronicle.glyphflow.math;

import lombok.Value;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.shape.CubicBezierCurve;

import java.util.List;

@Value
public class CubicBezier implements ParametricFunction {

    Vec2i start;
    Vec2i mid1;
    Vec2i mid2;
    Vec2i end;

    @Override
    public Vec2f getPoint(float t) {
        return new Vec2f(
                compute(start.getX(), mid1.getX(), mid2.getX(), end.getX(), t),
                compute(start.getY(), mid1.getY(), mid2.getY(), end.getY(), t)
        );
    }

    private static float compute(int p0, int p1, int p2, int p3, float t) {
        Geometry geo = new Geometry(new GeometryFactory();
        CubicBezierCurve.bezierCurve()
        return p0 + 3 * t * (p1 - p0) + 3 * t * t * (p2 - 2 * p1 + p0) + t * t * t * (p3 - 3 * p2 + 3 * p1 - p0);
    }

    private static float d(int p0, int p1, int p2, int p3, float t) {
        return 3 * (p3 - 3 * p2 + 3 * p1 - p1) + 6 * (p2 - 2 * p1 + p0) + 3 * (p1 - p0);
    }

    @Override
    public Vec2f getDirection(float t) {
        return new Vec2f(
                d(start.getX(), mid1.getX(), mid2.getX(), end.getX(), t), d(start.getY(), mid1.getY(), mid2.getY(), end.getY(), t));
    }

    @Override
    public float closestT(Vec2f point) {
        Vec2f p = point.subtract(start);
        Vec2f p1 = mid1.subtractF(start);
        Vec2f p2 = mid2.subtract(mid1.multiply(2)).add(start).toF();
        Vec2f p3 = end.subtract(mid2.multiply(3)).add(mid1.multiply(3)).subtract(start).toF();
        Complex[] nums = PolynomialRootFinderJenkinsTraub.findRoots(
                p3.dot(p3), 5 * p2.dot(p3), (4 * p1.dot(p3) + 6 * p2.dot(p2)), (9 * p1.dot(p3) - p2.dot(p)),
                (3 * p1.dot(p1) - 2 * p2.dot(p)), -p1.dot(p)
        );
        double t = 1;
        double min = getPoint(1).subtract(point).magnitude();
        double min0 = getPoint(0).subtract(point).magnitude();
        if (min0 < min) {
            min = min0;
            t = 0;
        }
        for (Complex c : nums) {
            double d = c.re();
            if (d < 0 || d > 1) {
                continue;
            }
            double distance = getPoint((float) d).subtract(point).magnitude();
            if (distance < min) {
                t = d;
                min = distance;
            }
        }
        return (float) Math.min(1, Math.max(0, t));
    }

}
