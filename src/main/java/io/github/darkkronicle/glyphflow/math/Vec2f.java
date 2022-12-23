package io.github.darkkronicle.glyphflow.math;

import lombok.Value;

@Value
public class Vec2f {

    float x;
    float y;

    public Vec2f add(Vec2f f) {
        return new Vec2f(x + f.x, y + f.y);
    }

    public float dot(Vec2f other) {
        return x * other.x + y * other.y;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public double cross(Vec2f other) {
        return x * other.y - y * other.x;
    }

    public Vec2f divide(float value) {
        return new Vec2f(x / value, y / value);
    }

    public Vec2f multiply(float value) {
        return new Vec2f(x * value, y * value);
    }

    public Vec2f normalize() {
        return divide(magnitude());
    }

    public Vec2f subtract(Vec2f f) {
        return new Vec2f(x - f.x, y - f.y);
    }

    public Vec2f subtract(Vec2i start) {
        return new Vec2f(x - start.getX(), y - start.getY());
    }

    public Vec2f add(Vec2i vec) {
        return new Vec2f(vec.getX() + x, vec.getY() + y);
    }
}
