package io.github.darkkronicle.glyphflow.fonts;

import com.google.typography.font.sfntly.table.core.HorizontalMetricsTable;
import com.google.typography.font.sfntly.table.truetype.*;


public class GlyphStorage {

    private final GlyphTable glyf;
    private final LocaTable loca;
    private final HorizontalMetricsTable hmtx;

    public GlyphStorage(LocaTable loca, GlyphTable glyf, HorizontalMetricsTable hmtx) {
        this.glyf = glyf;
        this.loca = loca;
        this.hmtx = hmtx;
    }

    public Glyph getGlyph(int glyphId) {
        int offset = loca.glyphOffset(glyphId);
        int length = loca.glyphLength(glyphId);
        return glyf.glyph(offset, length);
    }

    public GlyphData getGlyphData(int glyphId) {
        Glyph glyph = getGlyph(glyphId);
        int advance = hmtx.advanceWidth(glyphId);
        int leftSide = hmtx.leftSideBearing(glyphId);
        if (glyph instanceof SimpleGlyph simple) {
            return GlyphData.from(simple, advance, leftSide);
        } else if (glyph instanceof CompositeGlyph composite) {
        }
        return null;
    }

}
