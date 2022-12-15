package com.barelyconscious.worlds.util;

import java.awt.Color;

public final class UColor {

    public static Color combine(final Color lhs, final Color rhs) {
        return new Color(
            lhs.getRed(),
            lhs.getGreen(),
            lhs.getBlue(),
            rhs.getAlpha()
        );
//        return new Color(
//            (int) (255 - Math.sqrt((255-lhs.getRed())^2 + (255-rhs.getRed())^2)/2),
//            (int) (255 - Math.sqrt((255-lhs.getGreen())^2 + (255-rhs.getGreen())^2)/2),
//            (int) (255 - Math.sqrt((255-lhs.getBlue())^2 + (255-rhs.getBlue())^2)/2),
//            (int) (255 - Math.sqrt((255-lhs.getAlpha())^2 + (255-rhs.getAlpha())^2)/2)
//        );
        /**
         *
         return new Color(
         (lhs.getRed() * rhs.getRed())/255,
         (lhs.getGreen() * rhs.getGreen())/255,
         (lhs.getBlue() * rhs.getBlue())/255,
         (lhs.getAlpha() * rhs.getAlpha())/255
         );
         */
    }

    public static Color toColor(final int colorAsPixel) {
        int alpha, red, green, blue;
        // TODO hopefully temporary because resizing kinda crashes the program otherwise
        alpha = (colorAsPixel >> 24) & 0xFF;
        red = (colorAsPixel >> 16) & 0xFF;
        green = (colorAsPixel >> 8) & 0xFF;
        blue = colorAsPixel & 0xFF;
        return new Color(red, green, blue, alpha);
    }

    private UColor() {}
}
