/* *****************************************************************************
 * File Name:         UIElement.java
 * Author:            Matt Schwartz
 * Date Created:      01.03.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Similar to the Tile.java class, but these images serve
 a different purpose as icons.  And then a font sheet.
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.util.ConsoleWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class UIElement {

    private static java.util.Map<String, UIElement> loadedAssets = new HashMap<String, UIElement>();
    /**
     * The RGB color value of the transparent color 255,0,255 which is used to
     * provide transparency when drawing the UIElement.
     */
    private static final int TRANSPARENT_COLOR = new Color(255, 0, 255).getRGB(); // 255,0,255
    /**
     * The flattened 2D containing pixel data of the UIElement.
     */
    private final int[] pixels;
    /**
     * The width of the image.
     */
    private final int width;
    /**
     * The height of the image.
     */
    private final int height;

    /**
     * Opens the file containing the image representing the UIElement, loading
     * its data into the pixels array, reporting any errors
     *
     * @param imageFile the name of the file location of the UIElement
     */
    private UIElement(String imageFile) {
        BufferedImage img = loadImage(imageFile);
        width = img.getWidth();
        height = img.getHeight();
        pixels = img.getRGB(0, 0, width, height, null, 0, width);
    }

    public UIElement(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public static UIElement createUIElement(String imageFile) {
        UIElement newElement;
        if (loadedAssets.containsKey(imageFile)) {
            return loadedAssets.get(imageFile);
        }

        newElement = new UIElement(imageFile);
        loadedAssets.put(imageFile, newElement);
        return newElement;
    }

    /**
     * Loads an image from file location iconFile into a BufferedImage which it
     * then returns
     *
     * @param iconFile the UIElement file location
     * @return the loaded-in image as a BufferedImage
     */
    public static BufferedImage loadImage(String iconFile) {
        ConsoleWriter.writeStr(" [NOTIFY] Loading image \"" + trim(iconFile) + "\"...");
        BufferedImage img;

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(iconFile));
        } catch (IOException ex) {
            ConsoleWriter.writeError("Failed to load image (" + iconFile + "): " + ex);
            return null;
        }
        ConsoleWriter.writeStr("done.");

        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static String trim(String string) {
        String parts[] = string.split("/");

        return parts[parts.length - 1];
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void render(int xStart, int yStart) {
        int pix;
        int yLoc;

        for (int y = 0; y < height; y++) {
            yLoc = y * width;
            for (int x = 0; x < width; x++) {
                pix = pixels[yLoc++];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                }

                SceneService.INSTANCE.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderGrayscale(int xStart, int yStart) {
        int pix;
        int r, g, b, mask = 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];
                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                }

                g = b = r = Math.max(r, Math.max(g, b));

                pix = (r << 16) + (g << 8) + b;

                SceneService.INSTANCE.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderHighlighted(int xStart, int yStart) {
        int pix;
        int yLoc;
        int r, g, b, mask = 0xFF;

        for (int y = 0; y < height; y++) {
            yLoc = y * width;
            for (int x = 0; x < width; x++) {
                pix = pixels[yLoc++];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                }

                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (r * 5) >> 2;
                g = (g * 5) >> 2;
                b = (b * 5) >> 2;

                r = Math.min(255, r);
                g = Math.min(255, g);
                b = Math.min(255, b);

                pix = (r << 16) + (g << 8) + b;

                SceneService.INSTANCE.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderShaded(int xStart, int yStart) {
        int pix;
        int yLoc;
        int r, g, b, mask = 0xFF;

        for (int y = 0; y < height; y++) {
            yLoc = y * width;
            for (int x = 0; x < width; x++) {
                pix = pixels[yLoc++];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                }

                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (r * 3) >> 2;
                g = (g * 3) >> 2;
                b = (b * 3) >> 2;

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;

                SceneService.INSTANCE.setPixel(pix, xStart + x, yStart + y);
            }
        }
    }
}
