package io.github.darkkronicle.glyphflow.fonts;

import com.google.typography.font.sfntly.Font;
import com.google.typography.font.sfntly.Tag;
import com.google.typography.font.sfntly.table.core.CMap;
import com.google.typography.font.sfntly.table.core.CMapTable;
import com.google.typography.font.sfntly.table.core.HorizontalMetricsTable;
import com.google.typography.font.sfntly.table.truetype.GlyphTable;
import com.google.typography.font.sfntly.table.truetype.LocaTable;
import io.github.darkkronicle.glyphflow.api.FontException;
import io.github.darkkronicle.glyphflow.math.Image;
import io.github.darkkronicle.glyphflow.math.Pixel;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

import java.io.IOException;
import java.util.Iterator;

public class TTFFont implements io.github.darkkronicle.glyphflow.api.Font {


    private final Font font;

    private final CMapTable cmapTable;
    private final GlyphTable glyphTable;
    private final GlyphStorage glyphData;
    private final LocaTable locaTable;
    private final HorizontalMetricsTable hmtxTable;

    private TTFFont(Font font) throws IOException {
        this.font = font;
        this.cmapTable = font.getTable(Tag.cmap);
        this.glyphTable = font.getTable(Tag.glyf);
        this.locaTable = font.getTable(Tag.loca);
        this.hmtxTable = font.getTable(Tag.hmtx);
        this.glyphData = new GlyphStorage(locaTable, glyphTable, hmtxTable);
    }

    private static int toUnicode(String string) {
        return Character.codePointAt(string, 0);
    }

    @Override
    public GlyphData getGlyph(String character) {
        int index = getCharacterIndex(character);
        if (index == CMapTable.NOTDEF) {
            return null;
        }
        return glyphData.getGlyphData(index);
    }

    @Override
    public Image sdf(int width, int height, GlyphData glyph) {
        Image image = new Image(width, height);
        int gWidth = glyph.getMaxX() - glyph.getMinX();
        int gHeight = glyph.getMaxY() - glyph.getMinY();
        float wFactor;
        float hFactor;
        if (gWidth > gHeight) {
            wFactor = (float) gWidth / (width);
            hFactor = wFactor * ((float) height / width);
        } else {
            hFactor = (float) gHeight / (height);
            wFactor = hFactor * ((float) width / height);
        }
        float trueWidth = width * wFactor;
        float trueHeight = width * hFactor;
        float xOffset = (trueWidth - gWidth) / 2f;
        float yOffset = (trueHeight - gHeight) / 2f;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 17 && y == 14) {
                    System.out.println("Bork");
                }
                double d = glyph.getDistance((x + .5f) * wFactor - xOffset + glyph.getMinX(), (y + .5f) * hFactor - yOffset + glyph.getMinY());
                float color = (float) (d / 500 + .5);
                image.set(x, y, new Pixel(color, color, color, 1));
            }
        }
        return image;

    }

    @Override
    public int getCharacterIndex(String character) {
        int unicode = toUnicode(character);
        for (Iterator<CMap> it = this.cmapTable.iterator(cmapId -> cmapId.platformId() == 0); it.hasNext(); ) {
            CMap map = it.next();
            // 0 	Version 1.0 semantics
            // 1 	Version 1.1 semantics
            // 2 	ISO 10646 1993 semantics (deprecated)
            // 3 	Unicode 2.0 or later semantics (BMP only)
            // 4 	Unicode 2.0 or later semantics (non-BMP characters allowed)
            // 5 	Unicode Variation Sequence
            int encoding = map.encodingId();
            if ((Character.isBmpCodePoint(unicode) && encoding == 3) || encoding == 4) {
                int index = map.glyphId(unicode);
                if (index != CMapTable.NOTDEF) {
                    return index;
                }
            }
        }
        return CMapTable.NOTDEF;
    }

    public static TTFFont fromTTF(com.google.typography.font.sfntly.Font font) throws FontException {
        try {
            return new TTFFont(font);
        } catch (IOException io) {
            throw new FontException(io);
        }
    }

}
