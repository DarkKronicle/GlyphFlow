package io.github.darkkronicle.glyphflow.math;


import lombok.Value;

import java.util.List;

@Value
public class QuadradicBezier implements ParametricFunction {

    Vec2i start;
    Vec2i mid;
    Vec2i end;

    @Override
    public Vec2f getPoint(float t) {
        return new Vec2f(compute(start.getX(), mid.getX(), end.getX(), t), compute(start.getY(), mid.getY(), end.getY(), t));
    }

    private static float compute(int p0, int p1, int p2, float t) {
        return p0 + 2 * t * (p1 - p0) + t * t * (p2 - 2 * p1 + p0);
    }

    @Override
    public Vec2f getDirection(float t) {
        return new Vec2f(d(start.getX(), mid.getX(), end.getX(), t), d(start.getY(), mid.getY(), end.getY(), t));
    }

    @Override
    public float closestT(Vec2f point) {
        Vec2f p = point.subtract(start);
        Vec2f p1 = mid.subtractF(start);
        Vec2f p2 = end.subtract(mid.multiply(2)).add(start).toF();
        Complex[] nums = PolynomialRootFinderJenkinsTraub.findRoots(
                p2.dot(p2), 3 * p1.dot(p2), (2 * p1.dot(p1) - p2.dot(p)), -p1.dot(p)
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

    private static float d(int p0, int p1, int p2, float t) {
        return 2 * t * (p2 - 2 * p1 + p0) + 2 * (p1 - p0);
    }
}
