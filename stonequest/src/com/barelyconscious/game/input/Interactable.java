/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Interactable.java
 * Author:           Matt Schwartz
 * Date created:     08.28.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Interactables are interface elements with which the user
 *                   interacts to perform some function. Interactables capture
 *                   mouse and/or keyboard input from the user for this purpose.
 **************************************************************************** */
package com.barelyconscious.game.input;

import com.barelyconscious.game.services.InputHandler;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Interactable {

    // Z levels
    public static final int Z_CURSOR_LEVEL = 0;
    public static final int Z_DIALOG_PANE_BUTTONS = 1;
    public static final int Z_DIALOG_PANE = 2;
    public static final int Z_BUTTON = 3;
    public static final int Z_TEXT_AREA = 4;
    public static final int Z_INTERFACE_WINDOW_BUTTON = 5;
    public static final int Z_BACKGROUND = 6;
    public static final int Z_WORLD = Integer.MAX_VALUE;
    // Button clicks
    public static final int MOUSE_LEFT_CLICK = MouseEvent.BUTTON1;
    public static final int MOUSE_MIDDLE_CLICK = MouseEvent.BUTTON2;
    public static final int MOUSE_RIGHT_CLICK = MouseEvent.BUTTON3;
    private boolean focus = false;
    private boolean mouseInFocus = false;
    private boolean mouseButtonDown = false;
    private Rectangle region;
    private boolean enabled;

    public Interactable() {
    }

    public Interactable(int xStart, int yStart, int width, int height) {
        region = new Rectangle(xStart, yStart, width, height);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        mouseInFocus = false;
        mouseButtonDown = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMouseInFocus() {
        return mouseInFocus;
    }

    public boolean isMouseButtonDown() {
        return mouseButtonDown;
    }

    public void addMouseListener(int zLevel) {
        InputHandler.INSTANCE.addMouseListener(this, zLevel);
        enabled = true;
    }

    public void removeMouseListener() {
        InputHandler.INSTANCE.removeMouseListener(this);
        enabled = false;
    }
    
    public void addKeyListener() {
        InputHandler.INSTANCE.addKeyListener(this);
    }
    
    public void removeKeyListener() {
        InputHandler.INSTANCE.removeKeyListener(this);
    }
    
    public void setFocus(boolean focus) {
        this.focus = focus;
    }
    
    public boolean hasFocus() {
        return focus;
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
    public void keyReleased(KeyEvent e) {
    }
    
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Changes the region of the interactable object.
     *
     * @param xStart the new x start location of the object
     * @param yStart the new y start location of the object
     * @param width the new width of the object
     * @param height the new height of the object
     */
    public void setRegion(int xStart, int yStart, int width, int height) {
        region = new Rectangle(xStart, yStart, width, height);
    }

    /**
     * Determines whether or not a given point (specified by its x and y
     * coordinates) lies within the region of the interactable object.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return true if the region contains the point
     */
    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void mouseEntered() {
        mouseInFocus = true;
    }

    public void mouseExited() {
        mouseInFocus = false;
        mouseButtonDown = false;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        focus = true;
    }

    public void mousePressed(MouseEvent e) {
        mouseButtonDown = true;
    }

    public void mouseReleased(MouseEvent e) {
        mouseButtonDown = false;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseWheelUp() {
    }

    public void mouseWheelDown() {
    }
}
