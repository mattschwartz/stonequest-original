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
import com.barelyconscious.game.Screen;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UIElement {
    
    // Used for drawing text area borders
    public static UIElement textAreaBorderTopLeftCorner = new UIElement("/gfx/gui/components/textArea/borderTopLeft.png");
    public static UIElement textAreaBorderTopRightCorner = new UIElement("/gfx/gui/components/textArea/borderTopRight.png");
    public static UIElement textAreaBorderbottomLeftCorner = new UIElement("/gfx/gui/components/textArea/borderBottomLeft.png");
    public static UIElement textAreaBorderBottomRightCorner = new UIElement("/gfx/gui/components/textArea/borderBottomRight.png");
    public static UIElement textAreaBorderTopRepeat = new UIElement("/gfx/gui/components/textArea/borderTopRepeat.png");
    public static UIElement textAreaBorderBottomRepeat = new UIElement("/gfx/gui/components/textArea/borderBottomRepeat.png");
    public static UIElement textAreaBorderLeftRepeat = new UIElement("/gfx/gui/components/textArea/borderLeftRepeat.png");
    public static UIElement textAreaBorderRightRepeat = new UIElement("/gfx/gui/components/textArea/borderRightRepeat.png");
    
    // Used for drawing button borders
    public static UIElement buttonBorderLeft = new UIElement("/gfx/gui/components/button/borderLeft.png");
    public static UIElement buttonBorderRight = new UIElement("/gfx/gui/components/button/borderRight.png");
    public static UIElement buttonBorderRepeat = new UIElement("/gfx/gui/components/button/borderRepeat.png");
    
    // Buttons for accessing windows and closing them
    public static final UIElement INTERFACE_WINDOW_CLOSE_BUTTON = new UIElement("/gfx/gui/components/button/closeWindowButton.png");
    public static final UIElement UPGRADE_ITEM_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/upgradeItem/upgradeItemButton.png");
    public static final UIElement INVENTORY_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/inventory/inventoryButton.png");
    public static final UIElement CHARACTER_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/character/characterButton.png");
    public static final UIElement BREWING_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/brewing/brewingButton.png");
    public static final UIElement JOURNAL_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/journal/journalButton.png");
    public static final UIElement SALVAGE_WINDOW_BUTTON = new UIElement("/gfx/gui/components/windows/salvage/salvageButton.png");
    
    /**
     * The RGB color value of the transparent color 255,0,255 which is used to provide transparency when drawing the
     * UIElement.
     */
    private final int TRANSPARENT_COLOR = new Color(255, 0, 255).getRGB(); // 255,0,255
    /**
     * The flattened 2D containing pixel data of the UIElement.
     */
    private int[] pixels;
    /**
     * The width of the image.
     */
    private int width;
    /**
     * The height of the image.
     */
    private int height;

    /**
     * Opens the file containing the image representing the UIElement, loading its data into the pixels array, reporting any
     * errors
     *
     * @param imageFile the name of the file location of the UIElement
     */
    public UIElement(String imageFile) {
        System.err.print(" [NOTIFY] Loading image \"" + trim(imageFile) + "\"...");
        BufferedImage img = loadImage(imageFile);
        width = img.getWidth();
        height = img.getHeight();
        pixels = img.getRGB(0, 0, width, height, null, 0, width);
        System.err.println("done.");
    } // constructor

    /**
     * Loads an image from file location iconFile into a BufferedImage which it then returns
     *
     * @param iconFile the UIElement file location
     * @return the loaded-in image as a BufferedImage
     */
    public static BufferedImage loadImage(String iconFile) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(Game.class.getResourceAsStream(iconFile));
        } catch (IOException ex) {
            System.err.println("\n [ERR] Failed to load image (" + iconFile + "): " + ex);
            System.exit(-1);
        }

        return img;
    } // constructor
    
    public int getWidth() {
        return width;
    } // getWidth
    
    public int getHeight() {
        return height;
    }
    
    private static String trim(String string) {
        String parts[] = string.split("/");
        
        return parts[parts.length - 1];
    } // trim

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void render(Screen screen, int xStart, int yStart) {
        int pix;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                pix = pixels[x + y * width];
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if

                screen.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // render

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderGrayscale(Screen screen, int xStart, int yStart) {
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
                } // if

                if (r > g && r > b) {
                    g = b = r;
                } else if (g > r && g > b) {
                    r = b = g;
                } else if (b > r && b > g) {
                    r = g = b;
                }

                pix = (r << 16) + (g << 8) + b;

                screen.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // renderGrayscale

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderHighlighted(Screen screen, int xStart, int yStart) {
        int pix;
        int r, g, b, mask = 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if
                
                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (int) (r * 1.35);
                g = (int) (g * 1.25);
                b = (int) (b * 1.25);

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;

                screen.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // renderHighlighted

    /**
     * Draw the UIElement to the screen at xStart,yStart
     *
     * @param screen the screen which the UIElement is drawn to
     * @param xStart the starting x coordinate of the UIElement
     * @param yStart the starting y coordinate of the UIElement
     */
    public void renderShaded(Screen screen, int xStart, int yStart) {
        int pix;
        int r, g, b, mask = 0xFF;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];

                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if
                
                r = (pix >> 16) & mask;
                g = (pix >> 8) & mask;
                b = pix & mask;

                r = (int) (r * 0.75);
                g = (int) (g * 0.75);
                b = (int) (b * 0.75);

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;

                screen.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // renderShaded

    public void renderScaled(Screen scr, int xStart, int yStart, int scale) {
        int pix;
        int mask = 0xff;
        int r, g, b;
        int raster[][] = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = pixels[x + y * width];

//                if (pix == TRANSPARENT_COLOR) {
//                    continue;
//                } // if

                raster[x][y] = pix;
            } // for
        } // for

        scr.scale(xStart, yStart, raster, scale);
    } // render
} // UIElement
