/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Window.java
 * Author:           Matt Schwartz
 * Date created:     08.29.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.gui.Components;
import com.barelyconscious.game.input.Interactable;

public class Window extends Interactable implements Components {
    
    protected final double FALL_RATE = 0.4;
    protected int windowOffsX;
    protected int windowOffsY;
    protected int width;
    protected int height;
    protected boolean isVisible;

    public Window() {
    } // constructor

    public void toggleUI() {
        isVisible = !isVisible;

        if (isVisible) {
            show();
        } // if
        else {
            hide();
        } // else
    } // toggleUI

    public void show() {
        isVisible = true;
    } // show

    public void hide() {
        isVisible = false;
    } // hide

    @Override
    public int getX() {
        return windowOffsX;
    } // getX

    @Override
    public int getY() {
        return windowOffsY;
    } // getY

    @Override
    public void setX(int newX) {
        windowOffsX = Math.max(0, newX);
    } // setX

    @Override
    public void setY(int newY) {
        windowOffsY = Math.max(0, newY);
    } // setY

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    @Override
    public int getHeight() {
        return height;
    } // getHeight

    @Override
    public void dispose() {
    } // dispose

    /**
     * Renders the Window to the screen. This method does nothing and should be
     * overridden in subclasses which will tell the screen how to draw the
     * component.
     *
     * @param screen the screen to render the window to
     */
    @Override
    public void render(Screen screen) {
    } // render

    /**
     * Unused function for Window components.
     *
     * @return false always
     */
    @Override
    public boolean shouldRemove() {
        return false;
    } // shouldRemove
} // Window
