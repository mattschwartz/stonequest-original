/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Font.java
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
     * @param scr the screen to draw to
     * @param element the
     * @param xStart x starting coordinate
     * @param yStart y starting coordinate
     */
    public static void drawFormattedMessage(Screen scr, ScreenElement element, int xStart, int yStart) {
        int pix;
        int xPos;
        int yPos;

        if (element == null) {
            return;
        } // if

        xPos = (chars.indexOf(element.getElement()) % SHEET_WIDTH) * CHAR_WIDTH;
        yPos = (chars.indexOf(element.getElement()) / SHEET_WIDTH) * CHAR_HEIGHT;

        // Unrecognized character
        if (xPos < 0 || yPos < 0) {
            return;
        } // if

        // Draw the character
        for (int x = xPos, xx = 0; x < (xPos + CHAR_WIDTH); x++, xx++) {
            for (int y = yPos, yy = 0; y < (yPos + CHAR_HEIGHT); y++, yy++) {
//                    if (msg[i].isBold()) {
//                        pix = pixels[x + (y + 2) * CHAR_WIDTH];
//                    } else {
                pix = pixels[x + y * img.getWidth()];
//                    } // else

                if (pix == TRANSPARENT_COLOR) {
                    if (element.getElement() == 'j') {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy + 1);
                    } // if
                    
                    else if (element.getElement() == 'g' || element.getElement() == 'p'
                            || element.getElement() == 'q'
                            || element.getElement() == 'y') {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy + 2);
                    } // else if
                    
                    else {
                        scr.setPixel(element.getColor(), xStart + xx, yStart + yy);
                    } // else
                } // if
            } // for
        } // for
    } // drawMessage

    /**
     * Draw a uniformly colored and formatted message to the screen
     *
     * @param scr the screen to draw the message to
     * @param msg the message to be drawn
     * @param color the RGB color value of the message
     * @param bold if true, the message will be drawn using a bolded character
     * sheet
     * @param xStart the x coordinate where the message should start drawing
     * @param yStart the y coordinate where the message should start drawing
     */
    public static void drawMessage(Screen scr, String msg, int color, boolean bold, int xStart, int yStart) {
        int pix;
        int xPos;
        int yPos;

        if (msg == null) {
            return;
        } // if

        for (int i = 0; i < msg.length(); i++) {
            xPos = (chars.indexOf(msg.charAt(i)) % SHEET_WIDTH) * CHAR_WIDTH;
            yPos = (chars.indexOf(msg.charAt(i)) / SHEET_WIDTH) * CHAR_HEIGHT;

            // new line
            if (msg.charAt(i) == '\n') {
                drawMessage(scr, msg.substring(i + 1), color, bold, xStart, yStart + CHAR_HEIGHT);
                return;
            } // if

            // Unrecognized character
            if (xPos < 0 || yPos < 0) {
                continue;
            } // if

            // Draw the character
            for (int x = xPos, xx = 0; x < (xPos + CHAR_WIDTH); x++, xx++) {
                for (int y = yPos, yy = 0; y < (yPos + CHAR_HEIGHT); y++, yy++) {
//                    if (bold) {
//                        pix = pixels[x + (y + 2) * CHAR_WIDTH];
//                    } else {
                    pix = pixels[x + y * img.getWidth()];
//                    } // else

                    if (pix == TRANSPARENT_COLOR) {
                        if (msg.charAt(i) == 'j') {
                            scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy + 1);
                        } // if
                        else if (msg.charAt(i) == 'g' || msg.charAt(i) == 'p' || msg.charAt(i) == 'q' || msg.charAt(i) == 'y' || msg.charAt(i) == ',') {
                            scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy + 2);
                        } // else if
                        else {
                            scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy);
                        } // else
                    } // if
                } // for
            } // for
        } // for
    } // drawMessage

    public static void drawOutlinedMessage(Screen scr, String msg, int color, boolean bold, int xStart, int yStart) {
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart - 1, yStart);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart + 1, yStart);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart, yStart - 1);
        drawMessage(scr, msg, Common.THEME_BG_COLOR_RGB, bold, xStart, yStart + 1);
        drawMessage(scr, msg, color, bold, xStart, yStart);
    }
} // Font
