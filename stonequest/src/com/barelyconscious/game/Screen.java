/* *****************************************************************************
 * Project:         Roguelike2.0 
 * File name:       Screen.java 
 * Author:          Matt Schwartz 
 * Date created:    07.01.2012 
 * Redistribution:  You are free to use, reuse, and edit any
 *                  of the text in this file. You are not allowed to take credit for code that
 *                  was not written fully by yourself, or to remove credit from code that was not
 *                  written fully by yourself. Please email stonequest.bcgames@gmail.com for
 *                  issues or concerns. 
 * File description:Maintains the pixel array of the current screen image. 
 *                  There may only be one Screen per instance of this game.
 *     - int width: the width of the image to be drawn to the screen. This should
 *                  also be the width of the game window 
 *    - int height: the height of the image to be drawn to the scren. This should 
 *                  also be the height of the game window 
 *  - int[] pixels: the pixel data for the image 
 * - BufferedImage view: this is what is drawn to the screen. Individual pixels 
 *                  are changed in order to produce an actual game.
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.file.FileHandler;
import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.gui.Component;
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;

public class Screen extends Canvas {
//    
//    public static final Screen INSTANCE = new Screen();
//
//    /* Variables for z clipping */
//    public static final int Z_BACKGROUND = 0;
//    public static final int Z_FOREGROUND = 1;
//    public static final int Z_ALWAYS_ON_TOP = 2;
//    /**
//     * The scale of the game. This value is not used and will probably be
//     * removed
//     */
//    public static final int SCALE = 1;
//    public int width;
//    private int height;
//    public int[] pixels;
//    private BufferedImage view;
//    private boolean showUI = true;
//    private List<Component> componentsBackground = new ArrayList<Component>();
//    private List<Component> componentsForeground = new ArrayList<Component>();
//    private List<Component> componentsAlwaysOnTop = new ArrayList<Component>();
//
//    /**
//     * Creates a new Screen (which extends Canvas) to draw an image from a
//     * flattened 2D array of pixels which can be modified by calling the
//     * function setPixel(int x, int y). Only one Screen per runtime.
//     *
//     * @param w the width of the Screen in pixels
//     * @param h the height of the Screen in pixels
//     */
//    private Screen() {
//        if (INSTANCE != null) {
//            throw new IllegalStateException("Only one screen per runtime.");
//        }
//    }
//
//    public void toggleUI() {
//        showUI = !showUI;
//    }
//
//    public void saveScreenshot() {
//        String hour, minute, second, day, month, year;
//        String date;
//        File file = FileHandler.INSTANCE.getScreenshotDir();
//        Robot robot;
//        Rectangle bounds = new Rectangle();
//        BufferedImage capture;
//        Calendar cal = Calendar.getInstance();
//
//        hour = cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + cal.get(Calendar.HOUR_OF_DAY) : "" + cal.get(Calendar.HOUR_OF_DAY);
//        minute = cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : "" + cal.get(Calendar.MINUTE);
//        second = cal.get(Calendar.SECOND) < 10 ? "0" + cal.get(Calendar.SECOND) : "" + cal.get(Calendar.SECOND);
//        month = (cal.get(Calendar.MONTH) + 1) < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : "" + (cal.get(Calendar.MONTH) + 1);
//        day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : "" + cal.get(Calendar.DAY_OF_MONTH);
//        year = "" + cal.get(Calendar.YEAR);
//
//        date = day + month + year + "_" + hour + minute + second;
//
//        file = new File(file.getAbsolutePath() + FileHandler.delimiter + "screenshot" + date + ".png");
//
//        System.err.println(" [NOTIFY] Saving screenshot at '" + file.getAbsolutePath() + "'.");
//
//        try {
//            robot = new Robot();
//            bounds.setBounds(getLocationOnScreen().x, getLocationOnScreen().y, getWidth(), getHeight());
//            capture = robot.createScreenCapture(bounds);
//            ImageIO.write(capture, "png", file);
//        } catch (IOException ex) {
//            System.err.println("Error: " + ex);
//        } catch (AWTException ex) {
//            System.err.println("Error: " + ex);
//        }
//    }
//
//    /**
//     * This function should be called every time the game window's sized is
//     * changed, otherwise a misfit view will be drawn.
//     *
//     * @param w the new width of the screen
//     * @param h the new height of the screen
//     */
//    public final void resizeScreen(int w, int h) {
//        width = w;
//        height = h;
//        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
//    }
//
//    /**
//     *
//     * @return the width of the screen in pixels
//     */
//    public int getVisibleWidth() {
//        return getWidth();
//    }
//
//    /**
//     *
//     * @return the height of the screen in pixels
//     */
//    public int getVisibleHeight() {
//        return getHeight();
//    }
//
//    /**
//     * Sets the color of the pixel at x, y to pix.
//     *
//     * @param color the new color of the pixel
//     * @param x the x coordinate of the pixel to be changed
//     * @param y the y coordinate of the pixel to be changed
//     */
//    public void setPixel(int color, int x, int y) {
//        if (y < 0 || x + y * width >= pixels.length || x >= width) {
//            return;
//        }
//
//        pixels[x + y * width] = color;
//    }
//
//    /**
//     *
//     * @param x x coordinate of the pixel
//     * @param y y coordinate of the pixel
//     * @return the color of the pixel located at
//     * @x,
//     * @y
//     */
//    public int getPixel(int x, int y) {
//        return pixels[x + y * width];
//    }
//
//    /**
//     * Draw a single pixel width rectangular box starting at xStart,yStart with
//     * size w*h. This function is available to use when Menus draw things to the
//     * screen.
//     *
//     * @param color the RGB color of the rectangle
//     * @param xStart the left-most side of the rectangle
//     * @param yStart the upper-most side of the rectangle
//     * @param w the width of the rectangle to be drawn, in pixels
//     * @param h the height of the rectangle to be drawn, in pixels
//     */
//    public void drawRectangle(int color, int xStart, int yStart, int w, int h) {
//        drawRectangle(new Color(color), xStart, yStart, w, h);
//    }
//
//    /**
//     * Draw a single pixel width rectangular box starting at xStart,yStart with
//     * size w*h. This function is available to use when Menus draw things to the
//     * screen.
//     *
//     * @param color the RGB color of the rectangle
//     * @param xStart the left-most side of the rectangle
//     * @param yStart the upper-most side of the rectangle
//     * @param w the width of the rectangle to be drawn, in pixels
//     * @param h the height of the rectangle to be drawn, in pixels
//     */
//    public void drawRectangle(Color color, int xStart, int yStart, int w, int h) {
//        Graphics2D g = getGraphics();
//        g.setColor(color);
//        g.drawRect(xStart, yStart, w - 1, h - 1);
//        g.dispose();
//    }
//
//    public void fillTransluscentRectangle(int xStart, int yStart, int w, int h) {
//        int pix;
//        int r, g, b;
//
//        for (int x = xStart; x < xStart + w; x++) {
//            for (int y = yStart; y < yStart + h; y++) {
//                pix = pixels[x + y * width];
//
//                r = (pix >> 16) & 0xFF;
//                g = (pix >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
//                b = pix & 0xFF;
//
//                r = (int) (r * 0.45);
//                g = (int) (g * 0.45);
//                b = (int) (b * 0.45);
//
////                b = b > 255 ? 255 : b;
//                pix = (r << 16) + (g << 8) + b;
//                pixels[x + y * width] = pix;
//            }
//        }
//    }
//
//    public void fillTransluscentRectangle__2_electricboogaloo(int mask, int xStart, int yStart, int w, int h) {
//        int pix;
//        int r, g, b;
//        int mask_r, mask_g, mask_b;
//        double r_div, g_div, b_div;
//        r_div = g_div = b_div = 0.45;
//
//        mask_r = (mask >> 16) & 0xFF;
//        mask_g = (mask >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
//        mask_b = mask & 0xFF;
//
////        if (mask_r > mask_g && mask_r > mask_b) {
////            r_div = 2;
////        }
////
////        else if (mask_g > mask_r && mask_g > mask_b) {
////            g_div = 2;
////        }
////
////        else if (mask_b > mask_r && mask_b > mask_g) {
////            b_div = 2;
////        }
//        for (int x = xStart; x < xStart + w; x++) {
//            for (int y = yStart; y < yStart + h; y++) {
//                pix = pixels[x + y * width];
//
//                r = (pix >> 16) & 0xFF;
//                g = (pix >> 8) & 0xFF;//(Common.themeForegroundColor >> 8) & 0xFF;
//                b = pix & 0xFF;
//
////                r = (int)((r) * r_div);
////                g = (int)((g) * g_div);
////                b = (int)((b) * b_div);
//                r += Math.abs((int) ((mask_r - r) / 2));
//                g += Math.abs((int) ((mask_g - g) / 2));
//                b += Math.abs((int) ((mask_b - b) / 2));
//
//                r = r > 255 ? 255 : r;
//                g = g > 255 ? 255 : g;
//                b = b > 255 ? 255 : b;
//
////                b = b > 255 ? 255 : b;
//                pix = (r << 16) + (g << 8) + b;
//                pixels[x + y * width] = pix;
//            }
//        }
//    }
//
//    /**
//     * Draws a line from [
//     *
//     * @xStart,
//     * @yStart] to [
//     * @xEnd,
//     * @yEnd] using Brensenham's line drawing algorithm.
//     * @param color the color of the line
//     * @param xStart the x start coordinate of the line
//     * @param yStart the y start coordinate of the line
//     * @param xEnd the x end coordinate of the line
//     * @param yEnd the y end coordinate of the line
//     */
//    public void drawLine(int color, int xStart, int yStart, int xEnd, int yEnd) {
//        Graphics2D g = getGraphics();
//        g.setColor(new Color(color));
//        g.drawLine(xStart, yStart, xEnd, yEnd);
//        g.dispose();
//    }
//
//    /**
//     * Draw a filled rectangular box starting at
//     *
//     * @xStart,
//     * @yStart with size
//     * @w
//     * @h. This function is available to use when Menus draw things to the
//     * screen.
//     * @param pix
//     * @param xStart
//     * @param yStart
//     * @param w
//     * @param h
//     */
//    public void fillRectangle(int pix, int xStart, int yStart, int w, int h) {
//        Graphics2D g = getGraphics();
//        g.setColor(new Color(pix));
//        g.fillRect(xStart, yStart, w, h);
//        g.dispose();
//    }
//
//    /**
//     * Draw a filled rectangular box starting at
//     *
//     * @xStart,
//     * @yStart with size
//     * @w
//     * @h. This function is available to use when Menus draw things to the
//     * screen.
//     * @param color
//     * @param xStart
//     * @param yStart
//     * @param w
//     * @param h
//     */
//    public void fillRectangle(Color color, int xStart, int yStart, int w, int h) {
//        Graphics2D g = getGraphics();
//        g.setColor(color);
//        g.fillRect(xStart, yStart, w, h);
//        g.dispose();
//    }
//
//    /**
//     * Scale an image with data stored in 2D array,
//     *
//     * @pixToScale by
//     * @factor starting at
//     * @xStart,
//     * @yStart.
//     * @param xStart the x coordinate of the starting location of the scale
//     * image
//     * @param yStart the y coordinate of the starting location of the scale
//     * image
//     * @param pixToScale the 2D array of pixels of the image
//     * @param factor
//     */
//    public void scale(int xStart, int yStart, int[][] pixToScale, int factor) {
//        for (int x = 0; x < pixToScale.length; x++) {
//            for (int y = 0; y < pixToScale[x].length; y++) {
//                if (pixToScale[x][y] == new Color(255, 0, 255).getRGB()) {
//                    continue;
//                }
//
//                fillRectangle(pixToScale[x][y], (x * factor) + xStart, (y * factor) + yStart, factor, factor);
//            }
//        }
//    }
//
//    /**
//     * Resets the pixels on the screen to the BG color.
//     */
//    public void clear() {
//        for (int i = 0; i < pixels.length; i++) {
//            pixels[i] = Color.black.getRGB();
//        }
//    }
//
//    public void addBackgroundComponent(Component c) {
//        componentsBackground.add(c);
//    }
//
//    public void addForegroundComponent(Component c) {
//        componentsForeground.add(c);
//    }
//
//    public void addAlwaysOnTopComponent(Component c) {
//        componentsAlwaysOnTop.add(c);
//    }
//
//    @Override
//    public Graphics2D getGraphics() {
//        return view.createGraphics();
//    }
//
//    /**
//     * Draws everything to the screen that needs to be drawn. Some of the code
//     * used here is credited to Markus Persson (aka Notch) from his Ludum Dare
//     * 22 entry, Minicraft, which is available for download and review on the
//     * Ludum Dare website.
//     */
//    public void render() {
//        BufferStrategy bs = getBufferStrategy();
//        if (bs == null) {
//            createBufferStrategy(3);
//            requestFocus();
//            return;
//        }
//
////        animation();
//        renderComponents();
//
////        FontService.drawFont(this, "Frame time: " + (Game.frametime / 1000000) + "ms", Color.white, false, 0, FontService.characterHeight);
////        FontService.drawFont(this, "fps: " + Game.frames2, Color.white, false, 0, FontService.characterHeight * 2);
////        FontService.drawFont(this, "window size: " + width + " x " + height, Color.white, false, 0, FontService.characterHeight * 3);
//
//        Graphics g = bs.getDrawGraphics();
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        g.drawImage(view, 0, 0, width * SCALE, height * SCALE, null);
//
//        g.dispose();
//        bs.show();
//    }
//
//    private void renderComponents() {
//        Component component;
//
//        for (int i = 0; i < componentsBackground.size(); i++) {
//            component = componentsBackground.get(i);
//
//            if (component.shouldRemove()) {
//
//                componentsBackground.remove(component);
//                i--;
//                continue;
//            }
//            else {
//                component.render(this);
//            }
//        }
//
//        for (int i = 0; i < componentsForeground.size(); i++) {
//            component = componentsForeground.get(i);
//
//            if (component.shouldRemove()) {
//                componentsForeground.remove(component);
//                i--;
//                continue;
//            }
//            else {
//                component.render(this);
//            }
//        }
//
//        for (int i = 0; i < componentsAlwaysOnTop.size(); i++) {
//            component = componentsAlwaysOnTop.get(i);
//
//            if (component.shouldRemove()) {
//                componentsAlwaysOnTop.remove(component);
//                i--;
//                continue;
//            }
//            else {
//                component.render(this);
//            }
//        }
//    }
}
