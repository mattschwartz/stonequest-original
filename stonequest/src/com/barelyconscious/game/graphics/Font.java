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
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Font {

    /**
     * List of characters in order, matching the font sheet.
     */
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]<>?!@#$%^&123456789.,;:\\/()*-_=+{}\"'~`0 "; // width = 22
    /**
     * The color to skip when scanning each letter.
     */
    private static final int TRANSPARENT_COLOR = (Color.white).getRGB();
    /**
     * The width, in number of squares, of the font sheet.
     */
    private static final int SHEET_WIDTH = 32;
    /**
     * The width of each character.
     */
    public static int CHAR_WIDTH = 8;
    /**
     * The height of each character.
     */
    public static int CHAR_HEIGHT = 10;
    public static int NEW_CHAR_HEIGHT;
    public static int NEW_CHAR_WIDTH;
    // test
    public static BufferedImage stringImage;
    public static Graphics2D g;
    public static java.awt.Font exocetFont;
    public static int[] stringImagePix;

    /**
     * Creates the necessary fonts for the application to be packaged and run on
     * computers that may or may not have the necessary font packages and sets
     * other values necessary at runtime. Should be called once per runtime.
     */
    public static void init(Screen scr) {
        try {
            System.err.print(" [NOTIFY] Loading font \"Exocet.ttf\"...");

            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Game.class.getResourceAsStream("/fonts/Exocet.ttf"));
            exocetFont = font.deriveFont(13f);

            NEW_CHAR_HEIGHT = scr.getGraphics().getFontMetrics(exocetFont).getHeight();
            NEW_CHAR_WIDTH = scr.getGraphics().getFontMetrics(exocetFont).getMaxAdvance();

            System.err.println("done.");
        } catch (FontFormatException ex) {
            System.err.println("\n [ERROR] Error loading font \"Exocet.ttf\": " + ex);
        } catch (IOException ex) {
            System.err.println("\n [ERROR] Error loading font \"Exocet.ttf\": " + ex);
        }
    } // init

    /**
     * Gets the width of the message once it is drawn to the screen
     *
     * @param scr the screen the message would be rendered to
     * @param msg the message to be drawn to the screen
     * @return
     */
    public static int getStringWidth(Screen scr, String msg) {
        return scr.getGraphics().getFontMetrics(exocetFont).stringWidth(msg);
    } // getStringWidth

    /**
     * Gets the width of the message once it is drawn to the screen
     *
     * @param scr the screen the message would be rendered to
     * @param msg the message to be drawn to the screen
     * @return
     */
    /**
     * Gets the width of the message once it is drawn to the screen
     *
     * @param scr the screen the message would be rendered to
     * @param msg the message to be drawn to the screen
     * @param bold if true, the font used will be bold
     * @param fontSize the point size of the font to be used
     * @return 
     */
    public static int getStringWidth(Screen scr, String msg, boolean bold, float fontSize) {
        java.awt.Font newFont = exocetFont.deriveFont(fontSize);

        if (bold) {
            newFont = newFont.deriveFont(java.awt.Font.BOLD);
        } // if

        return scr.getGraphics().getFontMetrics(newFont).stringWidth(msg);
    } // getStringWidth

    /**
     * Returns the maximum width as an integer based on a given list of strings.
     *
     * @param scr the screen the messages would be rendered to
     * @param strings the list of strings from which to determine a maximum
     * width
     * @return the max width in pixels
     */
    public static int getMaxStringWidth(Screen scr, List<String> strings) {
        int stringLength;
        int maxLength = Integer.MIN_VALUE;

        for (String str : strings) {
            stringLength = getStringWidth(scr, str);

            if (stringLength > maxLength) {
                maxLength = stringLength;
            } // if
        } // for

        return maxLength;
    } // getMaxStringLength

    /**
     * Draws a string to the screen at the supplied coordinates.
     *
     * @param scr the screen to render to
     * @param msg the message to be written to the screen
     * @param col the color of the message as a Color object
     * @param font if null, the default font will be used
     * @param xStart the starting x coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the width
     * of the screen
     * @param yStart the starting y coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the height
     * of the screen
     */
    public static void drawFont(Screen scr, String msg, Color col, java.awt.Font font, int xStart, int yStart) {
        String[] parts;

        g = scr.getGraphics();

        if (font == null) {
            g.setFont(exocetFont);
        } else {
            g.setFont(font);
        }

        if (msg.contains("\n")) {
            parts = msg.split("\n");

            for (int i = 0; i < parts.length; i++) {
                drawFont(scr, parts[i], col, font, xStart, yStart + (g.getFontMetrics().getHeight() * i));
            } // for
        } // if

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(col);
        g.drawString(msg, xStart, yStart);
        g.dispose();
    } // drawFont

    public static void drawFont(Screen scr, String msg, Color col, boolean bold, float fontSize, int xStart, int yStart) {
        java.awt.Font newFont = exocetFont.deriveFont(fontSize);

        if (bold) {
            drawFont(scr, msg, col, newFont.deriveFont(java.awt.Font.BOLD), xStart, yStart);
        } // if
        else {
            drawFont(scr, msg, col, newFont, xStart, yStart);
        } // else
    } // drawFont

    /**
     * Draws a string to the screen at the supplied coordinates.
     *
     * @param scr the screen to render to
     * @param msg the message to be written to the screen
     * @param col the color of the message as an integer
     * @param font if null, the default font will be used
     * @param xStart the starting x coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the width
     * of the screen
     * @param yStart the starting y coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the height
     * of the screen
     */
    public static void drawFont(Screen scr, String msg, int col, int xStart, int yStart) {
        drawFont(scr, msg, new Color(col), null, xStart, yStart);
    } // drawFont

    /**
     * Draws a string to the screen at the supplied coordinates using the
     * default font.
     *
     * @param scr the screen to render to
     * @param msg the message to be written to the screen
     * @param col the color of the message as a Color object
     * @param bold if true, the message printed will be bolded
     * @param xStart the starting x coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the width
     * of the screen
     * @param yStart the starting y coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the height
     * of the screen
     */
    public static void drawFont(Screen scr, String msg, Color col, boolean bold, int xStart, int yStart) {
        java.awt.Font newFont = exocetFont.deriveFont(java.awt.Font.BOLD);
        
        if (bold) {
            drawFont(scr, msg, col, newFont, xStart, yStart);
        } else {
            drawFont(scr, msg, col, exocetFont, xStart, yStart);
        }
    } // drawFont
} // Font
