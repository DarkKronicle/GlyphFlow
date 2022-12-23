package io.github.darkkronicle.glyphflow.fonts;

import com.google.typography.font.sfntly.table.truetype.SimpleGlyph;
import io.github.darkkronicle.glyphflow.math.*;
import lombok.Value;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.shape.CubicBezierCurve;

import java.util.ArrayList;
import java.util.List;


@Value
public class GlyphData {

    Contour[] contours;
    int minX;
    int maxX;
    int minY;
    int maxY;
    int advance;
    int leftSideBearing;

    public final static GeometryFactory FACTORY = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING));

    public static GlyphData from(SimpleGlyph glyph, int advance, int bearing) {
        List<Contour> contours = new ArrayList<>();
        for (int i = 0; i < glyph.numberOfContours(); i++) {
            List<ParametricFunction> functions = new ArrayList<>();
            int num = glyph.numberOfPoints(i);
            Vec2i start = new Vec2i(glyph.xCoordinate(i, 0), glyph.yCoordinate(i, 0));
            Vec2i last = start;
            List<Vec2i> middle = new ArrayList<>();
            for (int c = 1; c < num + 1; c++) {
                int x = glyph.xCoordinate(i, c % num);
                int y = glyph.yCoordinate(i, c % num);
                if (glyph.onCurve(i, c % num)) {
                    last = new Vec2i(x, y);
                    if (middle.isEmpty()) {
                        functions.add(new LinearBezier(start, start = last));
                    } else if (middle.size() == 1) {
                        functions.add(new QuadradicBezier(start, middle.get(0), start = last));
                    } else {
                        middle.add(0, start);
                        middle.add(last);
                        for (int j = 1; j < middle.size() - 1; j += 2) {
                            functions.add(new QuadradicBezier(
                                    middle.get(j - 1),
                                    middle.get(j),
                                    middle.get(j + 1))
                            );
                        }
                        start = last;
//                        functions.add(new QuadradicBezier(start, middle.get(0), last));
                    }
                    middle.clear();
                } else {
                    last = new Vec2i(x, y);
                    if (middle.size() >= 1) {
                        middle.add(middle.get(middle.size() - 1).midpoint(last));
                    }
                    middle.add(last);
                }
            }
            contours.add(new Contour(functions.toArray(new ParametricFunction[0])));
        }
        return new GlyphData(
                contours.toArray(new Contour[0]),
                glyph.xMin(),
                glyph.xMax(),
                glyph.yMin(),
                glyph.yMax(),
                advance,
                bearing
        );
    }

    public double getDistance(float x, float y) {
        double min = Double.MAX_VALUE;
        Vec2f p = new Vec2f(x, y);
        ParametricFunction last = null;
        float lastT = 0;
        for (Contour c : contours) {
            for (ParametricFunction f : c.getFunctions()) {
                float t = f.closestT(p);
                if (t > 1) {
                    // Invalid something
                    continue;
                }
                double d = f.signedDistance(p, t);
                double change = Math.abs(Math.abs(min) - Math.abs(d));
                if (last != null && change <= 0.001) {
                    if (f.orthogonality(t, p) > last.orthogonality(lastT, p)) {
                        min = d;
                        last = f;
                        lastT = t;
                    }
                } else if (Math.abs(d) <= Math.abs(min)) {
                    min = d;
                    last = f;
                    lastT = t;
                }
            }
        }
        return min;

    }

}
