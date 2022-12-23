package io.github.darkkronicle.glyphflow.math;

public interface ParametricFunction {

    Vec2f getPoint(float t);

    Vec2f getDirection(float t);

    float closestT(Vec2f p);

    default double normalizedCrossMagnitudeD(ParametricFunction o) {
        Vec2f da = getDirection(1);
        Vec2f db = o.getDirection(0);
        return Math.abs(da.normalize().cross(db.normalize()));
    }

    default boolean isRadianBelow(ParametricFunction o, double radians) {
        return sameDirection(o) && normalizedCrossMagnitudeD(o) <= Math.sin(radians);
    }

    default boolean sameDirection(ParametricFunction o) {
        return getDirection(1).dot(o.getDirection(0)) > 0;
    }

    default double orthogonality(float t, Vec2f p) {
        Vec2f da = getDirection(t).normalize();
        Vec2f line = p.subtract(getPoint(t)).normalize();
        return da.cross(line);
    }

    default double signedDistance(Vec2f point, float t) {
        Vec2f offset = getPoint(t).subtract(point);
        double cross = getDirection(t).cross(offset);
        return (cross > 0 ? 1 : -1) * offset.magnitude();
    }

}
