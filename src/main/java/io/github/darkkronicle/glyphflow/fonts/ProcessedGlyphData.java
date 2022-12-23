package io.github.darkkronicle.glyphflow.fonts;

import io.github.darkkronicle.glyphflow.math.Contour;
import io.github.darkkronicle.glyphflow.math.ParametricFunction;

import java.util.ArrayList;
import java.util.List;

public class ProcessedGlyphData {

    private final List<List<Contour>> edges;
    private final GlyphData glyph;

    protected ProcessedGlyphData(List<List<Contour>> edges, GlyphData glyph) {
        this.edges = edges;
        this.glyph = glyph;
    }

    public static ProcessedGlyphData from(GlyphData glyph) {
        List<List<Contour>> contours = new ArrayList<>();

        List<Contour> current = new ArrayList<>();
        for (Contour c : glyph.getContours()) {
            for (int i = 0; i < c.getFunctions().length; i++) {
                ParametricFunction now = c.getFunctions()[i];
                ParametricFunction next = c.getFunctions()[i % c.getFunctions().length];
                if (now.isRadianBelow(next, Math.PI / 3)) {
                    // Is a corner

                } else {
                    // Not a corner

                }
            }
        }
        return new ProcessedGlyphData(contours, glyph);
    }


}
