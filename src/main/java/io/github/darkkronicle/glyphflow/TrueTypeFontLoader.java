package io.github.darkkronicle.glyphflow;

import com.google.typography.font.sfntly.FontFactory;
import io.github.darkkronicle.glyphflow.api.Font;
import io.github.darkkronicle.glyphflow.api.FontException;
import io.github.darkkronicle.glyphflow.api.FontLoader;
import io.github.darkkronicle.glyphflow.api.FontType;
import io.github.darkkronicle.glyphflow.fonts.TTFFont;

import java.io.*;

public class TrueTypeFontLoader implements FontLoader {

    private final static TrueTypeFontLoader INSTANCE = new TrueTypeFontLoader();

    private TrueTypeFontLoader() {}


    public static TrueTypeFontLoader getInstance() {
        return INSTANCE;
    }

    @Override
    public Font getFont(File file) throws FontException {
        String[] endings = file.getName().split("\\.");
        if (endings.length == 1) {
            throw new FontException("Filename does not have an ending!");
        }
        FontType type = FontType.parse(endings[endings.length - 1]);
        if (type == null) {
            throw new FontException("Ending: " + endings[endings.length - 1] + " is invalid!");
        }
        try (FileInputStream stream = new FileInputStream(file)) {
            return getFont(stream);
        } catch (IOException e) {
            throw new FontException(e);
        }
    }

    @Override
    public Font getFont(InputStream stream) throws FontException {
        try {
            return TTFFont.fromTTF(FontFactory.getInstance().loadFonts(stream)[0]);
        } catch (IOException e) {
            throw new FontException(e);
        }
    }

}
