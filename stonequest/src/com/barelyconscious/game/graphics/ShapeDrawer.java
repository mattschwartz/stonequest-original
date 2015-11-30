/* *****************************************************************************
 * File Name:         Map.java
 * Author:            Matt Schwartz
 * Date Created:      01.04.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Contains the data for the current map as a flattened 2D
 *                    integer array of tile ids.  The Screen calls renderBackground()
 *                    to draw the Tiles to the screen as well as perform lighting
 *                    for the map.  Each map is generated randomly based on some
 *                    seed so that the same random map can be generated repeatedly.
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.services.SceneService;
import java.awt.Color;
import java.awt.Graphics2D;

public class ShapeDrawer {

    public static void drawRectangle(Color c, int xStart, int yStart, int width, int height) {
        Graphics2D g = SceneService.INSTANCE.getCurrentGraphics();
        Color oldColor = g.getColor();
        g.setColor(c);
        g.drawRect(xStart, yStart, width, height);
        g.setColor(oldColor);
    } // drawRectangle

    public static void fillRectangle(Color c, int xStart, int yStart, int width, int height) {
        Graphics2D g = SceneService.INSTANCE.getCurrentGraphics();
        Color oldColor = g.getColor();
        g.setColor(c);
        g.fillRect(xStart, yStart, width, height);
        g.setColor(oldColor);
    } // drawRectangle

    public static void drawLine(Color c, int xStart, int yStart, int xEnd, int yEnd) {
        Graphics2D g = SceneService.INSTANCE.getCurrentGraphics();
        Color oldColor = g.getColor();
        g.setColor(c);
        g.drawLine(xStart, yStart, xEnd, yEnd);
        g.setColor(oldColor);
    } // drawRectangle

    public static void fillTransluscentRectangle(int xStart, int yStart, int w, int h) {
        int pix;
        int r, g, b;

        for (int x = xStart; x < xStart + w; x++) {
            for (int y = yStart; y < yStart + h; y++) {
                pix = SceneService.INSTANCE.getPixel(x, y);

                r = (pix >> 16) & 0xFF;
                g = (pix >> 8) & 0xFF;
                b = pix & 0xFF;

                r = (int) (r * 0.45);
                g = (int) (g * 0.45);
                b = (int) (b * 0.45);

//                b = b > 255 ? 255 : b;
                pix = (r << 16) + (g << 8) + b;
                SceneService.INSTANCE.setPixel(pix, x, y);
            } // for
        } // for
    } // fillTransluscentRectangle
}
