/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        Interactable.java
 * Author:           Matt Schwartz
 * Date created:     04.20.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: In an effort to centralize input and user-focus within the
 *                   Game, this function was created.  Controls focus variables
 *                   for subclasses as well as functions for handling mouse
 *                   actions.
 **************************************************************************** */
package com.barelyconscious.game.input;

import com.barelyconscious.game.input.KeyMap.Key;

public class Interactable {

    protected int xStart;
    protected int yStart;
    protected int width;
    protected int height;
    protected int mouseX;
    protected int mouseY;
    protected long mouseLastMoved;
    protected Key focusKey;
    private boolean hasFocus;
    
    private boolean entered;

    public Interactable() {
        xStart = -1;
        yStart = -1;
        width = -1;
        height = -1;
        focusKey = null;
    } // empty constructor

    public Interactable(int xStart, int yStart, int width, int height) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
    } // constructor

    public void defineMouseZone(int xStart, int yStart, int width, int height) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        this.height = height;
    } // defineMouseZone

    public void defineFocusKey(Key focusKey) {
        this.focusKey = focusKey;
    } // defineFocusKey

    public boolean focusKeyPressed(int key) {
        return focusKey != null && key == KeyMap.getKeyCode(focusKey);
    } // focusKeyPressed

    // Sets this object as active to be interacted with
    public void setActive() {
        hasFocus = true;
    } // setActive

    // Removes focus from the object
    public void clearFocus() {
        hasFocus = false;
    } // clearFocus

    // Returns true if the object has focus
    public boolean isActive() {
        return hasFocus;
    } // isActive

    // Mouse input functions
    public void enableMouse() {
    } // enableMouse

    public void disableMouse() {
    } // disableMouse

    /**
     * Allows the child Interactable (i.e., PopupMenu) to interact with its parent caller, whichever class that may be.
     *
     * @param ret the integer value that the child Interactable wishes to convey to its parent Interactable
     */
    public void parentCallback(int ret) {
    } // parentCallback

    // returns true if the mouse is positioned with the Interactable
    public boolean mouseInFocus(int x, int y) {
        return (entered = (x >= 0 && y >= 0) && ((x >= xStart && x <= xStart + width)
                && (y >= yStart && y <= yStart + height)));
    } // mouseInFocus

    public void mouseExited() {
    } // mouseExited

    /**
     * Tracks the location of the mouse cursor.
     *
     * @param x the x coordinate of the mouse
     * @param y the y coordinate of the mouse
     */
    public void mouseMoved(int x, int y) {
        mouseX = x;
        mouseY = y;
        mouseLastMoved = System.currentTimeMillis();
    } // mouseMoved

    // when a mouse button is clicked, this is called
    public void mouseClicked(int button, int x, int y) {
        mouseX = x;
        mouseY = y;
        mouseLastMoved = System.currentTimeMillis();
    } // mouseClicked

    // when the user scrolls the mouse wheel, this is called
    public void mouseWheelMoved(int direction) {
    } // mouseWheelMoved

    public void keyPressed(int key) {
    } // keyPressed
} // Interactable
