package io.github.darkkronicle.glyphflow.api;

import io.github.darkkronicle.glyphflow.TrueTypeFontLoader;

import java.io.File;
import java.io.InputStream;

public interface FontLoader {

    static FontLoader getInstance() {
        return TrueTypeFontLoader.getInstance();
    }

    io.github.darkkronicle.glyphflow.api.Font getFont(File path) throws io.github.darkkronicle.glyphflow.api.FontException;

    io.github.darkkronicle.glyphflow.api.Font getFont(InputStream stream) throws io.github.darkkronicle.glyphflow.api.FontException;

}
