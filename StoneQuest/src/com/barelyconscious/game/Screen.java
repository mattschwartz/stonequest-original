/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        Screen.java
 * Author:           Matt Schwartz
 * Date created:     07.01.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Maintains the pixel array of the current screen image.  There
 may only be one Screen per instance of this game.  
 -           int width: the width of the image to be drawn to the screen.  This
 should also be the width of the game window
 -               int height: the height of the image to be drawn to the scren.  This
 should also be the height of the game window
 -         int[] pixels: the pixel data for the image
 - BufferedImage view: this is what is drawn to the screen.  Individual pixels 
 are changed in order to produce an actual game.
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.animation.Animation;
import com.barelyconscious.game.graphics.animation.Frame;
import com.barelyconscious.gui.IRenderable;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

public class Screen extends Canvas {

    private int width;
    private int height;
    private int[] pixels;
    private BufferedImage view;
    private boolean showUI = true;

    /**
     * Creates a new Screen (which extends Canvas) to draw an image from a flattened 2D array of pixels which can be
     * modified by calling the function setPixel(int x, int y). Only one Screen per runtime.
     *
     * @param w the width of the Screen in pixels
     * @param h the height of the Screen in pixels
     */
    public Screen(int w, int h) {
        resizeScreen(w, h);
        testanimation();
    } // constructor
    Animation anim1;

    public void testanimation() {

        Frame f1 = new Frame(50, 50, 125, "/anim/1.png");
        Frame f2 = new Frame(50, 50, 125, "/anim/2.png");
        Frame f3 = new Frame(50, 50, 125, "/anim/3.png");
        Frame f4 = new Frame(50, 50, 125, "/anim/4.png");
        Frame f5 = new Frame(50, 50, 125, "/anim/5.png");
        Frame f6 = new Frame(50, 50, 125, "/anim/6.png");
        Frame f7 = new Frame(50, 50, 125, "/anim/7.png");
        Frame f8 = new Frame(50, 50, 125, "/anim/8.png");
        Frame f9 = new Frame(50, 50, 125, "/anim/9.png");
        Frame f10 = new Frame(50, 50, 125, "/anim/10.png");
        Frame f11 = new Frame(50, 50, 125, "/anim/11.png");
        Frame f12 = new Frame(50, 50, 125, "/anim/12.png");
        Frame f13 = new Frame(50, 50, 125, "/anim/13.png");
        Frame f14 = new Frame(50, 50, 125, "/anim/14.png");
        Frame f15 = new Frame(50, 50, 125, "/anim/15.png");
        Frame f16 = new Frame(50, 50, 125, "/anim/16.png");
        Frame f17 = new Frame(50, 50, 125, "/anim/17.png");
        Frame f18 = new Frame(50, 50, 125, "/anim/18.png");
        Frame f19 = new Frame(50, 50, 125, "/anim/19.png");
        Frame f20 = new Frame(50, 50, 125, "/anim/20.png");
        Frame f21 = new Frame(50, 50, 125, "/anim/21.png");
        Frame f22 = new Frame(50, 50, 1000, "/anim/22.png");

        anim1 = new Animation(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22);
    }

    public void toggleUI() {
        showUI = !showUI;
    } // toggleUI

    /**
     * This function should be called every time the game window's sized is changed, otherwise a misfit view will be
     * drawn.
     *
     * @param w the new width of the screen
     * @param h the new height of the screen
     */
    public final void resizeScreen(int w, int h) {
        width = w;
        height = h;
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    } // resize

    /**
     *
     * @return the width of the screen in pixels
     */
    public int getScreenWidth() {
        return width;
    } // getPixelWidth

    /**
     *
     * @return the height of the screen in pixels
     */
    public int getScreenHeight() {
        return height;
    } // getPixelHeight

    /**
     * Sets the color of the pixel at x, y to pix.
     *
     * @param color the new color of the pixel
     * @param x the x coordinate of the pixel to be changed
     * @param y the y coordinate of the pixel to be changed
     */
    public void setPixel(int color, int x, int y) {
        try {
            pixels[x + y * width] = color;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("x: " + x + ", width: " + width);
            System.err.println("y: " + y + ", height: " + height);
            ex.printStackTrace();
            System.exit(1);
        }
    } // setPixel

    /**
     *
     * @param x x coordinate of the pixel
     * @param y y coordinate of the pixel
     * @return the color of the pixel located at
     * @x,
     * @y
     */
    public int getPixel(int x, int y) {
        return pixels[x + y * width];
    } // getPixel

    /**
     * Draw a single pixel width rectangular box starting at xStart,yStart with size w*h. This function is available to
     * use when Menus draw things to the screen.
     *
     * @param color the RGB color of the rectangle
     * @param xStart the left-most side of the rectangle
     * @param yStart the upper-most side of the rectangle
     * @param w the width of the rectangle to be drawn, in pixels
     * @param h the height of the rectangle to be drawn, in pixels
     */
    public void drawRectangle(int color, int xStart, int yStart, int w, int h) {
        // Draw horizontal sides of box
        for (int x = xStart; x <= (xStart + w); x++) {
            pixels[x + yStart * width] = color;
            pixels[x + (yStart + h) * width] = color;
        } // for

        // Draw vertical sides of box
        for (int y = yStart; y <= (yStart + h); y++) {
            pixels[xStart + y * width] = color;
            pixels[(xStart + w) + y * width] = color;
        } // for
    } // drawRectangle

    public void fillTransluscentRectangle(int xStart, int yStart, int w, int h) {
        int pix;
        int r, g, b;

        for (int x = xStart; x < xStart + w; x++) {
            for (int y = yStart; y < yStart + h; y++) {
                pix = pixels[x + y * width];

                r = (pix >> 16) & 0xFF;
                g = (pix >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
                b = pix & 0xFF;

                r = (int) (r * 0.45);
                g = (int) (g * 0.45);
                b = (int) (b * 0.45);

//                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;
                pixels[x + y * width] = pix;
            } // for
        } // for
    } // fillTransluscentRectangle

    public void fillTransluscentRectangle__2_electricboogaloo(int mask, int xStart, int yStart, int w, int h) {
        int pix;
        int r, g, b;
        int mask_r, mask_g, mask_b;
        double r_div, g_div, b_div;
        r_div = g_div = b_div = 0.45;

        mask_r = (mask >> 16) & 0xFF;
        mask_g = (mask >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
        mask_b = mask & 0xFF;

//        if (mask_r > mask_g && mask_r > mask_b) {
//            r_div = 2;
//        }
//
//        else if (mask_g > mask_r && mask_g > mask_b) {
//            g_div = 2;
//        }
//
//        else if (mask_b > mask_r && mask_b > mask_g) {
//            b_div = 2;
//        }

        for (int x = xStart; x < xStart + w; x++) {
            for (int y = yStart; y < yStart + h; y++) {
                pix = pixels[x + y * width];

                r = (pix >> 16) & 0xFF;
                g = (pix >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
                b = pix & 0xFF;

//                r = (int)((r) * r_div);
//                g = (int)((g) * g_div);
//                b = (int)((b) * b_div);

                r += Math.abs((int) ((mask_r - r) / 2));
                g += Math.abs((int) ((mask_g - g) / 2));
                b += Math.abs((int) ((mask_b - b) / 2));

                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;

//                b = b > 255 ? 255 : b;

                pix = (r << 16) + (g << 8) + b;
                pixels[x + y * width] = pix;
            } // for
        } // for
    } // fillTransluscentRectangle

    /**
     * Draws a line from [
     *
     * @xStart,
     * @yStart] to [
     * @xEnd,
     * @yEnd] using Brensenham's line drawing algorithm.
     * @param color the color of the line
     * @param xStart the x start coordinate of the line
     * @param yStart the y start coordinate of the line
     * @param xEnd the x end coordinate of the line
     * @param yEnd the y end coordinate of the line
     */
    public void drawLine(int color, int xStart, int yStart, int xEnd, int yEnd) {
        /*
         * pseudocode from wikipedia
         * function line(x0, x1, y0, y1)
         int deltax := x1 - x0
         int deltay := y1 - y0
         real error := 0
         real deltaerr := abs (deltay / deltax)    // Assume deltax != 0 (line is not vertical),
         // note that this division needs to be done in a way that preserves the fractional part
         int y := y0
         for x from x0 to x1
         plot(x,y)
         error := error + deltaerr
         if error ≥ 0.5 then
         y := y + 1
         error := error - 1.0
         */

        int deltaX = xEnd - xStart;
        int deltaY = yEnd - yStart;
        double error = 0;

        double deltaErr = Math.abs(deltaY * 1.0 / deltaX);
        int y = yStart;

        for (int x = xStart; x < xEnd; x++) {
            setPixel(color, x, y);

            error = error + deltaErr;

            if (error >= 0.5) {
                y = y + 1;
                error = error - 1.0;
            }
        }

    } // drawLine

    /**
     * Draw a filled rectangular box starting at
     *
     * @xStart,
     * @yStart with size
     * @w
     * @h. This function is available to use when Menus draw things to the screen.
     * @param pix
     * @param xStart
     * @param yStart
     * @param w
     * @param h
     */
    public void fillRectangle(int pix, int xStart, int yStart, int w, int h) {
        for (int x = xStart; x < (xStart + w); x++) {
            for (int y = yStart; y < (yStart + h); y++) {
                if ((x + y * width) >= pixels.length) {
                    continue;
                } // if

                pixels[x + y * width] = pix;
            } // for
        } // for
    } // fillRectangle

    /**
     * Scale an image with data stored in 2D array,
     *
     * @pixToScale by
     * @factor starting at
     * @xStart,
     * @yStart.
     * @param xStart the x coordinate of the starting location of the scale image
     * @param yStart the y coordinate of the starting location of the scale image
     * @param pixToScale the 2D array of pixels of the image
     * @param factor
     */
    public void scale(int xStart, int yStart, int[][] pixToScale, int factor) {
        for (int x = 0; x < pixToScale.length; x++) {
            for (int y = 0; y < pixToScale[x].length; y++) {
                if (pixToScale[x][y] == new Color(255, 0, 255).getRGB()) {
                    continue;
                } // if

                fillRectangle(pixToScale[x][y], (x * factor) + xStart, (y * factor) + yStart, factor, factor);
            } // for
        } // for
    } // scale

    /**
     * Resets the pixels on the screen to the BG color.
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = Common.THEME_BG_COLOR_RGB;
        } // for
    } // clear

    /**
     * Draws everything to the screen that needs to be drawn. Some of the code used here is credited to Markus Persson
     * (aka Notch) from his Ludum Dare 22 entry, Minicraft, which is available for download and review on the Ludum Dare
     * website.
     */
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        } // if

        Game.gameMap.renderBackground(this);
        Game.world.render(this);

        if (showUI) {
            renderInterface();

            Font.drawMessage(this, "Frame time: " + (Game.frametime / 1000000) + "ms", Color.white.getRGB(), false, Font.CHAR_WIDTH * 65, Font.CHAR_HEIGHT * 47);
            Font.drawMessage(this, "fps: " + Game.frames2, Color.white.getRGB(), false, Font.CHAR_WIDTH * 65, Font.CHAR_HEIGHT * 48);
        } // if

//        animation();

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(view, 0, 0, width * Common.SCALE, height * Common.SCALE, null);
        g.dispose();
        bs.show();
    } // renderSprites


    private final List<IRenderable> renderables = new ArrayList<>();
    public void addRenderable(final IRenderable renderable) {
        renderables.add(renderable);
    }

    /**
     * Render all visible Menus to the screen on top of the background.
     */
    private void renderInterface() {
        Game.world.renderPortraits(this);

        renderables.forEach(t -> t.render(this));

        Game.world.renderZoneInfo(this);
    } // renderInterface

    private void animation() {
        if (anim1 != null) {
            anim1.render(this);
        }
    }
} // Screen
