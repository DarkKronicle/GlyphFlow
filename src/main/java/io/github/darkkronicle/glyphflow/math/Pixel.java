package io.github.darkkronicle.glyphflow.math;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

import java.awt.*;

@Accessors(fluent = true)
public class Pixel {

    @Getter
    private final float r;
    @Getter
    private final float g;
    @Getter
    private final float b;
    @Getter
    private final float a;

    @Getter
    private final int rgb;

    public Pixel(float r, float g, float b, float a) {
        this.r = Math.max(0, Math.min(r, 1));
        this.g = Math.max(0, Math.min(g, 1));
        this.b = Math.max(0, Math.min(b, 1));
        this.a = Math.max(0, Math.min(a, 1));

        int value = (int) (this.a * 255);
        value = (value << 8) + (int) (this.r * 255);
        value = (value << 8) + (int) (this.g * 255);
        value = (value << 8) + (int) (this.b * 255);
        this.rgb = value;
    }



}
