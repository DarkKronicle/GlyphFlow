package io.github.darkkronicle.glyphflow.math;

import lombok.Value;

@Value
public class Vec2i {

    int x;
    int y;

    public Vec2i add(Vec2i p2) {
        return add(this, p2);
    }

    public Vec2i midpoint(Vec2i p2) {
        return new Vec2i((int) ((x + p2.x) * .5), (int) ((y + p2.y) * .5));
    }

    public static Vec2i add(Vec2i p1, Vec2i p2) {
        return new Vec2i(p1.x + p2.x, p1.y + p2.y);
    }

    public Vec2f toF() {
        return new Vec2f(x, y);
    }

    public Vec2f multiply(float v) {
        return new Vec2f(x * v, y * v);
    }

    public Vec2f subtractF(Vec2i vec) {
        return new Vec2f(x - vec.x, y - vec.y);
    }

    public Vec2i subtract(Vec2i vec) {
        return new Vec2i(x - vec.x, y - vec.y);
    }

    public Vec2i multiply(int v) {
        return new Vec2i(x * v, y * v);
    }
}
