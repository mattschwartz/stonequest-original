/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Screen.java
 * Author:           Matt Schwartz
 * Date created:     07.01.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Canvas {
    private int width;
    private int height;
    private int[] pixels;
    
    private BufferedImage view;
    
    public Screen(int w, int h) {
        resizeScreen(w, h);
    } // constructor
    
    public final void resizeScreen(int w, int h) {
        width = w;
        height = h;
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    } // resize
    
    /**
     * Returns the width of the screen in number of Tiles.  Multiply this value
     * by TILE_SIZE (getTileSize()) to get the actual width of the Screen.
     * @return 
     */
    public int getScreenWidth() {
        return width;
    } // getWidth
    
    /**
     * Returns the height of the screen in number of Tiles.  Multiply this value
     * by TILE_SIZE (getTileSize()) to get the actual height of the Screen.
     * @return 
     */
    public int getScreenHeight() {
        return height;
    } // getHeight
    
    /**
     * Sets the color of the pixel at x, y to pix.
     * @param pix
     * @param x
     * @param y 
     */
    public void setPixel(int pix, int x, int y) {
        pixels[x + y * width] = pix;
    } // setPixel
    
    /**
     * Draw a single pixel width rectangular box starting at xStart,yStart with 
     * size w*h.  This function is available to use when Menus draw things to 
     * the screen.
     * @param pix
     * @param xStart
     * @param yStart
     * @param w
     * @param h 
     */
    public void drawRectangle(int pix, int xStart, int yStart, int w, int h) {
        // Draw horizontal sides of box
        for (int x = xStart; x <= (xStart + w); x++) {
            pixels[x + yStart * width] = pix;
            pixels[x + (yStart + h) * width] = pix;
        } // for
        
        // Draw vertical sides of box
        for (int y = yStart; y <= (yStart + h); y++) {
            pixels[xStart + y * width] = pix;
            pixels[(xStart + w) + y * width] = pix;
        } // for
    } // drawRectangle
    
    /**
     * Draw a single-pixel width line.
     * @param pix the pixel to fill the line with
     * @param xStart x starting position of the line
     * @param yStart y starting postition of the line
     * @param length length of the line
     */
    public void drawLine(int pix, int xStart, int yStart, int length) {
        for (int x = xStart; x < xStart + length; x++) {
            pixels[x + yStart * width] = pix;
        } // for
    } // drawLine
    
    /**
     * Draw a solid rectangle starting at xStart, yStart with size w*h
     * @param pix
     * @param xStart
     * @param yStart
     * @param w
     * @param h 
     */
    public void fillRectangle(int pix, int xStart, int yStart, int w, int h) {
        for (int x = xStart; x < (xStart + w); x++) {
            for (int y = yStart; y < (yStart + h); y++) {
                if ( (x + y * width) >= pixels.length) {
                    continue;
                } // if
                
                pixels[x + y * width] = pix;
            } // for
        } // for
    } // fillRectangle
    
    /**
     * Resets the screen to the BG color.
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = Common.THEME_BG_COLOR_RGB;
        } // for
    } // clear
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        } // if
        
        Game.map.renderBackground(this);
        Game.world.renderSprites(this);
        
        Game.buffBar.render(this);
        
        renderInterface();
        
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.drawImage(view, 0, 0, width * Common.SCALE, height * Common.SCALE, null);
        g.dispose();
        bs.show();
    } // renderSprites

    /**
     * Render all visible Menus to the screen on top of the background.
     */
    private void renderInterface() {
        Game.invenMenu.render(this);
        Game.attributesMenu.render(this);
        Game.textLog.render(this);
    } // renderMenus
} // Screen