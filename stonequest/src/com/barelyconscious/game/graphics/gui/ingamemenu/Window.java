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
 * File description: Windows are GUI elements that comprise of multiple types of
 *                   generic elements to perform a specific function. Windows are
 *                   meant to serve as a GUI implementation of a player function
 *                   like viewing the player's attributes or inventory.
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.gui.Component;
import com.barelyconscious.game.input.Interactable;

public class Window extends Interactable implements Component {

    public final double FALL_RATE = 0.4;
    private int windowOffsX;
    private int windowOffsY;
    private int width;
    private int height;
    private boolean isVisible;

    public Window() {
    }

    public void toggleUI() {
        isVisible = !isVisible;

        if (isVisible) {
            show();
        }
        else {
            hide();
        }
    }

    public void show() {
        isVisible = true;
    }

    public void hide() {
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    @Override
    public int getX() {
        return windowOffsX;
    }

    @Override
    public int getY() {
        return windowOffsY;
    }

    @Override
    public void setX(int newX) {
        windowOffsX = Math.max(0, newX);
    }

    @Override
    public void setY(int newY) {
        windowOffsY = Math.max(0, newY);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * Renders the Window to the screen. This method does nothing and should be
     * overridden in subclasses which will tell the screen how to draw the
     * component.
     *
     */
    @Override
    public void render() {
    }

    /**
     * Unused function for Window components.
     *
     * @return false always
     */
    @Override
    public boolean shouldRemove() {
        return false;
    }

    /**
     * Unused function for Window components.
     */
    @Override
    public void dispose() {
    }
}
