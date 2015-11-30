/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Button.java
 * Author:           Matt Schwartz
 * Date created:     08.25.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import java.awt.Color;

public class Button extends Interactable implements Components {

    public static final int DEFAULT_WIDTH = 108;
    public static final int DEFAULT_HEIGHT = 24;
    public static final int MARGIN = 5;
    protected int x;
    protected int y;
    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;
    protected int titleOffsX;
    protected int titleOffsY;
    private int borderHighlightColor = new Color(137, 137, 137).getRGB();
    private int borderShadowColor = new Color(38, 38, 38).getRGB();
    protected boolean hasBorder = false;
    private boolean destroy = false;
    protected String title = "button";
    protected ButtonAction callbackFunction = null;

    /**
     * Creates a new button with a title and at coordinates, startX, startY and
     * automatic values for width and height.
     *
     * @param title the title of the button
     * @param startX the x starting location of the button
     * @param startY the y starting location of the button
     */
    public Button(String title, int startX, int startY, boolean hasBorder) {
        this(title, startX, startY, Font.getStringWidth(Game.screen, title) + MARGIN * 2, -1, hasBorder);
    } // constructor

    /**
     *
     * @param screen
     * @param title
     * @param startX
     * @param startY
     * @param hasBorder
     */
    public Button(ButtonAction callback, String title, int zLevel, int startX, int startY, boolean hasBorder) {
        this(title, zLevel, startX, startY, Font.getStringWidth(Game.screen, title) + MARGIN * 2, -1, hasBorder);
        callbackFunction = callback;
    } // constructor

    /**
     * Create a new button at the coordinates, (startX, startY) with the width
     * width and height height. None of these values should be below 0 and if
     * any are, they will be disregarded and set to 0.
     *
     * @param startX the starting x coordinate
     * @param startY the starting y coordinate
     * @param width the width of the button
     * @param height the height of the button
     */
    public Button(String title, int startX, int startY, int width, int height, boolean hasBorder) {
        this(title, Interactable.Z_BUTTON, startX, startY, width, height, hasBorder);
    } // constructor

    public Button(String title, int zLevel, int startX, int startY, int width, int height, boolean hasBorder) {
        x = Math.max(0, startX);
        y = Math.max(0, startY);

        if (height < 0) {
            height = this.height;
        } // if

        this.width = width;
        this.height = height;

        this.title = title;

        titleOffsX = x + MARGIN + (width - MARGIN * 2 - Font.getStringWidth(Game.screen, title)) / 2;
        titleOffsY = y + Font.NEW_CHAR_HEIGHT;

        this.hasBorder = hasBorder;
        super.setRegion(x, y, this.width, this.height);
        super.addMouseListener(zLevel);
    } // constructor

    public void setCallbackFunction(ButtonAction callbackFunction) {
        this.callbackFunction = callbackFunction;
    } // setCallbackFunction

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);


        if (!enabled && callbackFunction != null) {
            callbackFunction.hoverOverAction(null);
        } // if
    } // setEnabled

    @Override
    public void mouseMoved(int x, int y) {
        if (!enabled) {
            return;
        } // if

        super.mouseMoved(x, y); //To change body of generated methods, choose Tools | Templates.
    } // mouseMoved

    @Override
    public void mouseClicked(int buttonClicked, int clickCount, int x, int y) {
        if (!enabled) {
            return;
        } // if

        super.mouseClicked(buttonClicked, clickCount, x, y);

        if (callbackFunction != null && buttonClicked == Interactable.MOUSE_LEFT_CLICK) {
            callbackFunction.action(this);
        } // if
    } // mouseClicked

    @Override
    public void mousePressed() {
        if (!enabled) {
            return;
        } // if

        super.mousePressed();
    } // mousePressed

    @Override
    public void mouseReleased() {
        if (!enabled) {
            return;
        } // if

        super.mouseReleased();
    } // mouseReleased

    @Override
    public void mouseEntered() {
        if (!enabled) {
            return;
        } // if

        if (callbackFunction != null) {
            callbackFunction.hoverOverAction(this);
        } // if

        super.mouseEntered();
//        Cursors.setCursor(Cursors.HAND_CURSOR);
    } // mouseEntered

    @Override
    public void mouseExited() {
        if (!enabled) {
            return;
        } // if

        if (callbackFunction != null) {
            callbackFunction.hoverOverAction(null);
        } // if

        super.mouseExited();
//        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    } // mouseExited

    @Override
    public int getX() {
        return x;
    } // getX

    @Override
    public int getY() {
        return y;
    } // getY

    @Override
    public void setX(int newX) {
        int titleWidth = titleOffsX - x;
        x = Math.max(0, newX);
        titleOffsX = x + titleWidth;

        super.setRegion(x, y, width, height);
    } // setX

    @Override
    public void setY(int newY) {
        y = Math.max(0, newY);
        titleOffsY = y + Font.NEW_CHAR_HEIGHT + 2;

        super.setRegion(x, y, width, height);
    } // setY

    public void setHeight(int newHeight) {
        height = newHeight;
        super.setRegion(x, y, this.width, this.height);
    } // setHeight

    @Override
    public int getHeight() {
        return height;
    } // getHeight

    public void setWidth(int newWidth) {
        width = newWidth;
        super.setRegion(x, y, this.width, this.height);
    } // setWidth

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    /**
     * This function is called when the DialogPane is no longer necessary and
     * should be removed as determined by the JRE.
     */
    @Override
    public void dispose() {
        destroy = true;
        removeMouseListener();
    } // dispose

    @Override
    public boolean shouldRemove() {
        return destroy;
    } // shouldDestroy

    @Override
    public void render(Screen screen) {
        int xOffs = x;
        int yOffs = y;

        if (mouseInFocus) {
            renderHighlighted(screen);
            return;
        } // if

        if (mouseButtonDown) {
            xOffs++;
            yOffs++;
        } // if

        UIElement.buttonBorderLeft.render(screen, xOffs, yOffs);

        for (int i = xOffs + UIElement.buttonBorderLeft.getWidth(); i < xOffs + (width - UIElement.buttonBorderRight.getWidth()); i += UIElement.buttonBorderRepeat.getWidth()) {
            UIElement.buttonBorderRepeat.render(screen, i, yOffs);
        } // for

        UIElement.buttonBorderRight.render(screen, xOffs + width - UIElement.buttonBorderRight.getWidth(), yOffs);

        renderText(screen);

        if (hasBorder) {
            renderBorder(screen);
        } // if
    } // render

    /**
     * If the user is hovering over the button, the border is highlighted to
     * help provide visual feedback to him/her.
     *
     * @param screen the rendering screen object
     */
    protected void renderHighlighted(Screen screen) {
        int xOffs = x;
        int yOffs = y;

        if (mouseButtonDown) {
            xOffs++;
            yOffs++;
        } // if

        UIElement.buttonBorderLeft.renderHighlighted(screen, xOffs, yOffs);

        for (int i = xOffs + UIElement.buttonBorderLeft.getWidth(); i < xOffs + (width - UIElement.buttonBorderRight.getWidth()); i += UIElement.buttonBorderRepeat.getWidth()) {
            UIElement.buttonBorderRepeat.renderHighlighted(screen, i, yOffs);
        } // for

        UIElement.buttonBorderRight.renderHighlighted(screen, xOffs + width - UIElement.buttonBorderRight.getWidth(), yOffs);

        if (title != null && !(title.equals(""))) {
            renderText(screen);
        } // if

        if (hasBorder) {
            renderBorder(screen);
        } // if
    } // renderHighlighted

    protected void renderText(Screen screen) {
        int offsX = titleOffsX;
        int offsY = titleOffsY;

        if (mouseButtonDown) {
            offsX++;
            offsY++;
            Font.drawFont(screen, title, Common.FONT_DEFAULT_RGB, offsX, offsY);
        } // if
        else {
            Font.drawFont(screen, title, Common.FONT_WHITE_RGB, offsX, offsY);
        } // else
    } // renderText

    private void renderBorder(Screen screen) {
        int xOffs = x;
        int yOffs = y;

        if (mouseButtonDown) {
            xOffs++;
            yOffs++;
            screen.drawLine(borderShadowColor, xOffs - 1, yOffs - 1, xOffs - 1, yOffs + height + 1);
            screen.drawLine(borderShadowColor, xOffs, yOffs - 1, xOffs + width, yOffs - 1);

            screen.drawLine(borderHighlightColor, xOffs + width, yOffs, xOffs + width, yOffs + height);
            screen.drawLine(borderHighlightColor, xOffs, yOffs + height, xOffs + width, yOffs + height);
        } // if
        else {
            screen.drawLine(borderHighlightColor, xOffs - 1, yOffs - 1, xOffs - 1, yOffs + height + 1);
            screen.drawLine(borderHighlightColor, xOffs, yOffs - 1, xOffs + width, yOffs - 1);

            screen.drawLine(borderShadowColor, xOffs + width, yOffs, xOffs + width, yOffs + height);
            screen.drawLine(borderShadowColor, xOffs, yOffs + height, xOffs + width, yOffs + height);
        } // else
    } // renderButton

    @Override
    public String toString() {
        return "[Button] Title: " + title;
    } // toString
} // Button
