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

import com.barelyconscious.game.Game;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.Service;
import com.barelyconscious.util.CharacterElement;
import com.barelyconscious.util.ConsoleWriter;
import com.barelyconscious.util.LineElement;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class FontService implements Service {

    public final static FontService INSTANCE = new FontService();
    public static int characterHeight;
    public static int characterWidth;
    // test
    public static BufferedImage stringImage;
    public static Graphics2D g;
    public static java.awt.Font exocetFont;
    public static int[] stringImagePix;
    
    private FontService() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been instantiated.");
        }
    }
    
    /**
     * Creates the necessary fonts for the application to be packaged and run on
     * computers that may or may not have the necessary font packages and sets
     * other values necessary at runtime. Should be called once per runtime.
     */
    @Override
    public void start() {
        try {
            ConsoleWriter.writeStr("Loading font \"Exocet.ttf\"");

            // TODO - remove reference to Game.class
            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Game.class.getResourceAsStream("/fonts/Exocet.ttf"));
            exocetFont = font.deriveFont(13f);

            characterHeight = SceneService.INSTANCE.getCurrentGraphics().getFontMetrics(exocetFont).getHeight();
            characterWidth = SceneService.INSTANCE.getCurrentGraphics().getFontMetrics(exocetFont).getMaxAdvance();
        } catch (FontFormatException ex) {
            ConsoleWriter.writeError("Error loading font \"Exocet.ttf\": " + ex);
        } catch (IOException ex) {
            ConsoleWriter.writeError("Error loading font \"Exocet.ttf\": " + ex);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        stop();
        start();
    }
    
    /**
     * Gets the width of the message once it is drawn to the screen
     *
     * @param msg the message to be drawn to the screen
     * @return
     */
    public static int getStringWidth(String msg) {
        return SceneService.INSTANCE.getCurrentGraphics().getFontMetrics(exocetFont).stringWidth(msg);
    }

    /**
     * Gets the width of the message once it is drawn to the screen
     *
     * @param msg the message to be drawn to the screen
     * @param bold if true, the font used will be bold
     * @param fontSize the point size of the font to be used
     * @return
     */
    public static int getStringWidth(String msg, boolean bold, float fontSize) {
        java.awt.Font newFont = exocetFont.deriveFont(fontSize);

        if (bold) {
            newFont = newFont.deriveFont(java.awt.Font.BOLD);
        }

        return SceneService.INSTANCE.getCurrentGraphics().getFontMetrics(newFont).stringWidth(msg);
    }

    /**
     * Returns the maximum width as an integer based on a given list of strings.
     *
     * @param strings the list of strings from which to determine a maximum
     * width
     * @return the max width in pixels
     */
    public static int getMaxStringWidth(List<String> strings) {
        int stringLength;
        int maxLength = Integer.MIN_VALUE;

        for (String str : strings) {
            stringLength = getStringWidth(str);

            if (stringLength > maxLength) {
                maxLength = stringLength;
            }
        }

        return maxLength;
    }

    /**
     * Draws a string to the screen at the supplied coordinates.
     *
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
    public static void drawFont(String msg, Color col, java.awt.Font font, int xStart, int yStart) {
        String[] parts;

        g = SceneService.INSTANCE.getCurrentGraphics();

        if (font == null) {
            g.setFont(exocetFont);
        } else {
            g.setFont(font);
        }

        if (msg.contains("\n")) {
            parts = msg.split("\n");

            for (int i = 0; i < parts.length; i++) {
                drawFont(parts[i], col, font, xStart, yStart + (g.getFontMetrics().getHeight() * i));
            }
        }

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(col);
        g.drawString(msg, xStart, yStart);
        g.dispose();
    }

    public static void drawFont(String msg, Color col, boolean bold, float fontSize, int xStart, int yStart) {
        java.awt.Font newFont = exocetFont.deriveFont(fontSize);

        if (bold) {
            drawFont(msg, col, newFont.deriveFont(java.awt.Font.BOLD), xStart, yStart);
        }
        else {
            drawFont(msg, col, newFont, xStart, yStart);
        }
    }

    /**
     * Draws a string to the screen at the supplied coordinates.
     *
     * @param msg the message to be written to the screen
     * @param col the color of the message as an integer
     * @param xStart the starting x coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the width
     * of the screen
     * @param yStart the starting y coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the height
     * of the screen
     */
    public static void drawFont(String msg, int col, int xStart, int yStart) {
        drawFont(msg, new Color(col), null, xStart, yStart);
    }

    /**
     * Draws a string to the screen at the supplied coordinates.
     *
     * @param msg the message to be written to the screen
     * @param col the color of the message as an integer
     * @param xStart the starting x coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the width
     * of the screen
     * @param yStart the starting y coordinate where the message will be drawn,
     * this value should be greater than or equal to 0 but less than the height
     * of the screen
     */
    public static void drawFont(String msg, Color col, int xStart, int yStart) {
        drawFont(msg, col, null, xStart, yStart);
    }

    /**
     * Draws a string to the screen at the supplied coordinates using the
     * default font.
     *
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
    public static void drawFont(String msg, Color col, boolean bold, int xStart, int yStart) {
        java.awt.Font newFont = exocetFont.deriveFont(java.awt.Font.BOLD);

        if (bold) {
            drawFont(msg, col, newFont, xStart, yStart);
        } else {
            drawFont(msg, col, exocetFont, xStart, yStart);
        }
    }

    public static void drawFont(LineElement line, java.awt.Font font, int xStart, int yStart) {
        g = SceneService.INSTANCE.getCurrentGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (font == null) {
            g.setFont(exocetFont);
        }
        else {
            g.setFont(font);
        }

        for (CharacterElement c : line.line) {
            g.setColor(new Color(c.color));
            g.drawString("" + c, xStart, yStart);
            xStart += getStringWidth("" + c);
        }

        g.dispose();
    }
}
