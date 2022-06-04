package com.barelyconscious.game.entity.resources;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public enum FontResource {
    FONTIN_REGULAR("fonts/Fontin-Regular.ttf", Font.TRUETYPE_FONT),
    FONTIN_ITALIC("fonts/Fontin-Italic.ttf", Font.ITALIC),
    FONTIN_BOLD("fonts/Fontin-Bold.ttf", Font.BOLD),

    FONTIN_SMALLCAPS("fonts/Fontin-SmallCaps.ttf", Font.TRUETYPE_FONT);

    public final int defaultFontSize = 12;
    public final String filepath;
    /**
     * See {@link java.awt.Font.TRUETYPE_FONT}, etc.
     */
    public final int fontFormat;
}
