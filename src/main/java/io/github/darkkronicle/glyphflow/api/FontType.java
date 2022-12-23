package io.github.darkkronicle.glyphflow.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public enum FontType {
    TTF("otf"),
    OTF("ttf");

    @Getter
    private final String fileEnding;


    @Nullable
    public static FontType parse(String fileEnding) {
        for (FontType t : values()) {
            if (t.fileEnding.equals(fileEnding)) {
                return t;
            }
        }
        return null;
    }

}
