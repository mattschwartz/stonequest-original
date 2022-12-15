package com.barelyconscious.worlds.game.resources;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public enum FontResource {
    FONTIN_REGULAR(16f, "fonts/Fontin-Regular.ttf", Font.PLAIN),
    FONTIN_ITALIC(16f, "fonts/Fontin-Italic.ttf", Font.ITALIC),
    FONTIN_BOLD(16f, "fonts/Fontin-Bold.ttf", Font.BOLD),

    FONTIN_SMALLCAPS(16f, "fonts/Fontin-SmallCaps.ttf", Font.TRUETYPE_FONT);

    public final float defaultFontSize;
    public final String filepath;
    /**
     * See {@link java.awt.Font.TRUETYPE_FONT}, etc.
     */
    public final int fontFormat;
}
