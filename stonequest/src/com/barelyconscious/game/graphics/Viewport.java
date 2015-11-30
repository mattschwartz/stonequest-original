/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Viewport.java
 * Author:            Matt Schwartz
 * Date Created:      01.22.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.graphics.gui.BetterComponent;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Viewport extends Canvas {

    public int width;
    public int height;
    private final int SCALE = 1;
    protected int[] pixels;
    private boolean showUI = true;
    private final List<BetterComponent> components = new CopyOnWriteArrayList<BetterComponent>();
    private BufferedImage view;
    
    public Viewport() {
    }

    /**
     * Creates the int array of pixels that represents the BufferedImage which
     * is drawn to the screen during rendering.
     */
    private void createRaster() {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    } // createRaster

    /**
     * Resizes the screen with the supplied new width and height.
     *
     * @param newWidth The new width of the Viewport
     * @param newHeight The new height of the Viewport
     */
    @Override
    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        createRaster();
    } // resize

    public void toggleUI() {
        showUI = !showUI;
    } // toggleUI

    /**
     * Sets the current pixel at {x,y} to the color supplied by the call.
     *
     * @param color The new color of the pixel
     * @param x The x location of the pixel to be set
     * @param y The y location of the pixel to be set
     */
    public void setPixel(int color, int x, int y) {
        if (y < 0 || x + y * width >= pixels.length || x >= width) {
            return;
        } // if

        pixels[x + y * width] = color;
    } // setPixel

    /**
     * Returns the color at the supplied location.
     *
     * @param x The x location of the color to get
     * @param y The y location of the color to get
     * @return The color of the pixel at the supplied location
     */
    public int getPixel(int x, int y) {
        return pixels[x + y * width];
    } // getPixel

    /**
     * Resets the pixels on the screen to the BG color.
     */
    public void clear() {
        if (pixels == null) return;
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        } // for
    } // clear

    /**
     * This method is used for retrieving the Graphics2D object associated with
     * the view so drawing can be done to it.
     *
     * @return Returns the Graphics2D object associated with this view.
     */
    @Override
    public Graphics2D getGraphics() {
        return view == null ? null : view.createGraphics();
    } // getGraphics

    public void addComponent(BetterComponent c) {
        components.add(c);
    } // addComponent

    public boolean removeComponent(BetterComponent c) {
        if (c == null) {
            return false;
        } // if

        return components.remove(c);
    } // removeComponent
    
    public void removeComponents() {
        components.clear();
    } // removeComponents
    
    public void setComponentsEnabled(boolean enabled) {
        for (BetterComponent c : components) {
            c.setEnabled(enabled);
        } // for
    } // setComponentsEnabled
    
    /**
     * This method is called when the Viewport is to be drawn to the screen.
     */
    public void render() {
        clear();
        renderView();
    } // render

    protected void renderView() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        } // if
        
        if (showUI) {
            renderComponents();
        } // if
        
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(view, 0, 0, width * SCALE, height * SCALE, null);

        g.dispose();
        bs.show();
    } // renderView

    protected void renderComponents() {
        for (BetterComponent c : components) {
            if (c.shouldRemove()) {
                components.remove(c);
                continue;
            } // if

            c.render();
        } // for
    } // Components
} // Viewport
