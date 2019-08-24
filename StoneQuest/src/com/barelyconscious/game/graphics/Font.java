/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        Font.java
 * Author:           Matt Schwartz
 * Date created:     02.23.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove
 *                   credit from code that was not written fully by yourself.
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Draws characters defined in FONT_SHEET to the screen
 **************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Font {

    /**
     * List of characters in order, matching the font sheet.
     */
    private final static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]<>?!@#$%^&123456789.,;:\\/()*-_=+{}\"'~`0 "; // width = 22
    /**
     * The font sheet loaded in as a BufferedImage.
     */
    private final static BufferedImage img = UIElement.FONT_SHEET;
    /**
     * The flattened 2D pixel data array for the font sheet.
     */
    private final static int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
    /**
     * The color to skip when scanning each letter.
     */
    private final static int TRANSPARENT_COLOR = (Color.white).getRGB();
    /**
     * The width, in number of squares, of the font sheet.
     */
    private final static int SHEET_WIDTH = 32;
    /**
     * The width of each character.
     */
    public final static int CHAR_WIDTH = 8;
    /**
     * The height of each character.
     */
    public final static int CHAR_HEIGHT = 10;

    /**
     * Draw a formatted message to the screen starting at xStart, yStart; used
     * by the text log to draw lines that have dynamically colored and formatted
     * charaters.
     *
     * @param scr     the screen to draw to
     * @param element the
     * @param xStart  x starting coordinate
     * @param yStart  y starting coordinate
     */
    public static void drawFormattedMessage(Screen scr, ScreenElement element, int xStart, int yStart) {
        int pix;
        int xPos;
        int yPos;

        // Invalid request or Unrecognized character
        if (element == null || chars.indexOf(element.getElement()) == -1) {
            return;
        }

        xPos = (chars.indexOf(element.getElement()) % SHEET_WIDTH) * CHAR_WIDTH;
        yPos = (chars.indexOf(element.getElement()) / SHEET_WIDTH) * CHAR_HEIGHT;

        // Draw the character
        for (int x = xPos, xx = 0; x < (xPos + CHAR_WIDTH); x++, xx++) {
            for (int y = yPos, yy = 0; y < (yPos + CHAR_HEIGHT); y++, yy++) {
//                    if (msg[i].isBold()) {
//                        pix = pixels[x + (y + 2) * CHAR_WIDTH];
//                    } else {
                pix = pixels[x + y * img.getWidth()];
//                    }

                if (pix == TRANSPARENT_COLOR) {
                    if (element.getElement() == 'j') {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy + 1);
                    } else if (element.getElement() == 'g' || element.getElement() == 'p'
                        || element.getElement() == 'q'
                        || element.getElement() == 'y') {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy + 2);
                    } else {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy);
                    }
                }
            }
        }
    }

    /**
     * Draw a uniformly colored and formatted message to the screen
     *
     * @param scr    the screen to draw the message to
     * @param msg    the message to be drawn
     * @param color  the RGB color value of the message
     * @param isBold if true, the message will be drawn using a bolded character
     *               sheet
     * @param xStart the x coordinate where the message should start drawing
     * @param yStart the y coordinate where the message should start drawing
     */
    public static void drawMessage(
        final Screen scr,
        final String msg,
        final int color,
        final boolean isBold,
        final int xStart,
        final int yStart
    ) {
        if (msg == null || msg.isEmpty()) {
            return;
        }

        for (int i = 0; i < msg.length(); ++i) {
            final char charAt = msg.charAt(i);

            // If new line, draw everything else on a new line
            if (charAt == '\n') {
                drawMessage(scr, msg.substring(i + 1), color, isBold, xStart, yStart + CHAR_HEIGHT);
                return;
            }

            drawCharacterAt(i, scr, charAt, color, isBold, xStart, yStart);
        }
    }

    private static void drawCharacterAt(
        final int i,
        final Screen scr,
        final char charAt,
        final int color,
        final boolean isBold,
        final int xStart,
        final int yStart
    ) {
        // Unrecognized character
        if (chars.indexOf(charAt) == -1) {
            return;
        }

        int xPos = (chars.indexOf(charAt) % SHEET_WIDTH) * CHAR_WIDTH;
        int yPos = (chars.indexOf(charAt) / SHEET_WIDTH) * CHAR_HEIGHT;


        // Draw the character pixel by pixel
        for (int x = xPos, xx = 0; x < (xPos + CHAR_WIDTH); x++, xx++) {
            for (int y = yPos, yy = 0; y < (yPos + CHAR_HEIGHT); y++, yy++) {
                final int pix;

                if (isBold) {
                    pix = pixels[x + (y + 2) * CHAR_WIDTH];
                } else {
                    pix = pixels[x + y * img.getWidth()];
                }

                if (pix == TRANSPARENT_COLOR) {
                    if (charAt == 'j') {
                        scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy + 1);
                    } else if (charAt == 'g' || charAt == 'p' || charAt == 'q' || charAt == 'y' || charAt == ',') {
                        scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy + 2);
                    } else {
                        scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy);
                    }
                }
            }
        }
    }

    public static void drawOutlinedMessage(Screen scr, String msg, int color, boolean bold, int xStart, int yStart) {
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart - 1, yStart);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart + 1, yStart);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart, yStart - 1);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart, yStart + 1);
        drawMessage(scr, msg, color, bold, xStart, yStart);
    }
}
