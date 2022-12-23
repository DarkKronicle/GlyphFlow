package io.github.darkkronicle.glyphflow.math;

import lombok.Getter;

import java.awt.image.BufferedImage;

public class Image {

    @Getter
    private final int width;

    @Getter
    private final int height;

    private final Pixel[][] pixels;


    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width][height];
    }


    public void set(int x, int y, Pixel p) {
        pixels[x][y] = p;
    }

    public Pixel get(int x, int y) {
        return pixels[x][y];
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, get(x, height - y - 1).rgb());
            }
        }
        return image;
    }


}
