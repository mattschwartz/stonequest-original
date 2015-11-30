/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Font.java
 * Author:           Matt Schwartz
 * Date created:     02.23.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Screen;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Font {
    /**
     * List of characters in order, matching fontspace.
     */
    private final static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]<>?!@#$%^&123456789.,;:\\/()*-_=+{}\"'~`0 "; // width = 22
    private final static BufferedImage img = Icon.FONT_SHEET;
    private final static int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
    
    private final static int WHITE_RGB = (Color.white).getRGB();
    
    private final static int SHEET_WIDTH = 32;
    public final static int CHAR_WIDTH = 8;
    public final static int CHAR_HEIGHT = 10;
    
    /**
     * Draw a message to the screen starting at xStart, yStart
     * @param scr the screen to draw to
     * @param msg the message to be drawn
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

                if (pix == WHITE_RGB) {
                        if (element.getElement() == 'g' || element.getElement() == 'j' 
                                || element.getElement() == 'p' || element.getElement() == 'q' 
                                || element.getElement() == 'y') {
                            scr.setPixel(element.getColor(), xStart + xx, yStart + yy + 2);
                        } else {
                            scr.setPixel(element.getColor(), xStart + xx, yStart + yy);
                        } // else
                } // if
            } // for
        } // for
    } // drawMessage
    
    public static void drawMessage(Screen scr, String msg, int color, boolean bold, int xStart, int yStart) {
        int pix;
        int xPos;
        int yPos;
        
        if (msg == null) {
            return;
        }
        
        for (int i = 0; i < msg.length(); i++) {
            xPos = (chars.indexOf(msg.charAt(i)) % SHEET_WIDTH) * CHAR_WIDTH;
            yPos = (chars.indexOf(msg.charAt(i)) / SHEET_WIDTH) * CHAR_HEIGHT;
            
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
                    
                    if (pix == WHITE_RGB) {
                        if (msg.charAt(i) == 'g' || msg.charAt(i) == 'j' || msg.charAt(i) == 'p' || msg.charAt(i) == 'q' || msg.charAt(i) == 'y') {
                            scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy + 2);
                        } else {
                            scr.setPixel(color, xStart + xx + i * CHAR_WIDTH, yStart + yy);
                        } // else
                    } // if
                } // for
            } // for
        } // for
    } // drawMessage
} // Font