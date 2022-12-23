package io.github.darkkronicle.glyphflow.math;

import lombok.Value;

/**
 * Naming consistency, but this is just a line
 */
@Value
public class LinearBezier implements ParametricFunction {

    Vec2i start;
    Vec2i end;

    @Override
    public Vec2f getPoint(float t) {
        return start.multiply(1 - t).add(end.multiply(t));
    }

    @Override
    public Vec2f getDirection(float t) {
        return end.subtractF(start);
    }

    @Override
    public float closestT(Vec2f p) {
        Vec2f dis = end.subtractF(start);
        return Math.min(1, Math.max(0, p.subtract(start.toF()).dot(dis) / dis.dot(dis)));
    }

}
