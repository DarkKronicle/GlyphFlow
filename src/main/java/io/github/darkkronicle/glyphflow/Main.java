package io.github.darkkronicle.glyphflow;


import io.github.darkkronicle.glyphflow.api.Font;
import io.github.darkkronicle.glyphflow.api.FontException;
import io.github.darkkronicle.glyphflow.api.FontLoader;
import io.github.darkkronicle.glyphflow.fonts.GlyphData;
import io.github.darkkronicle.glyphflow.math.*;
import io.github.darkkronicle.glyphflow.math.Image;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public class Main extends JPanel {

    private GlyphData glyph;

    public static void main(String[] args) throws FontException, IOException {
        InputStream stream = ClassLoader.getSystemResourceAsStream("Inter-Regular.ttf");
        Font font = FontLoader.getInstance().getFont(stream);
        GlyphData data = font.getGlyph("B");
        Main main = new Main(data);
//        main.run();

        int length = 251;
        Image image = font.sdf(length, length, data);
        ImageIO.write(image.toBufferedImage(), "png", new File("output.png"));
    }

    public void run() {
        JFrame f = new JFrame("Glyph Tester");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topLevel = new JPanel();
        topLevel.setLayout(new BorderLayout());
        topLevel.add(this, BorderLayout.CENTER);

        f.setContentPane(topLevel);

        f.pack();
        f.setLocationRelativeTo(null);  // Centers window
        f.setVisible(true);

    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(500, 500);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(500, 500);
    }


    @Override
    public Dimension getPreferredSize() {
        return getMaximumSize();
    }

    @Override
    public void paint(Graphics g) {
        for (Contour c : glyph.getContours()) {
            for (ParametricFunction f : c.getFunctions()) {
                Vec2f lastPoint = f.getPoint(0);
                for (float t = 0.1f; t <= 1; t += 0.05) {
                    Vec2f point = f.getPoint(t);
                    g.drawLine((int) (lastPoint.getX() / 10), (int) (500 - lastPoint.getY() / 10), (int) (point.getX() / 10), (int) (500 - point.getY() / 10));
                    lastPoint = point;
                }
            }
        }
    }

}