package io.github.darkkronicle.glyphflow.api;

import io.github.darkkronicle.glyphflow.fonts.GlyphData;
import io.github.darkkronicle.glyphflow.math.Image;

public interface Font {
    GlyphData getGlyph(String character);

    Image sdf(int width, int height, GlyphData glyph);

    int getCharacterIndex(String character);
}
