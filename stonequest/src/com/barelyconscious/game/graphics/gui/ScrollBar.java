/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ScrollBar.java
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

import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.Viewport;
import com.barelyconscious.game.services.SceneService;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ScrollBar {

    public static final String SCROLLBAR_PATH = "/gfx/gui/components/scrollBar/scrollBarBorder.png";
    public static final int SCROLLBAR_WIDTH = 4;
    protected int x;
    protected int y;
    protected int height;
    protected int viewableLines; // number of lines visible in the frame
    protected int topLineNumber; // line number at the very top of the frame
    protected int lineCount; // total number of lines in the frame
    private Color backgroundColor = new Color(33, 33, 33);
    private Color backgroundColorHighlight = new Color(61, 61, 61);
    private Color scrollBarColor = new Color(84, 84, 84);
    private Color scrollBarHighlight = new Color(127, 127, 127);
    private Color scrollBarShadow = new Color(61, 61, 61);
    private static UIElement barTop;
    private static UIElement barBottom;

    public ScrollBar(int xStart, int yStart, int height, int lineHeight) {
        loadBorder();
        x = xStart;
        y = yStart;
        this.height = height;
        viewableLines = lineHeight;
        topLineNumber = 0;
    } // constructor

    /**
     * Loads the border from disk by locating subimages within the larger image. This results in a single, large disk access instead of multiple,
     * smaller accesses
     */
    private void loadBorder() {
        int[] pixels;
        int subWidth = 4;
        int subHeight = 4;
        BufferedImage unparsedImage = UIElement.loadImage(SCROLLBAR_PATH);

        pixels = unparsedImage.getRGB(0, 0, subWidth, subHeight, null, 0, subWidth);
        barTop = new UIElement(pixels, subWidth, subHeight);
        pixels = unparsedImage.getRGB(subWidth, 0, subWidth, subHeight, null, 0, subWidth);
        barBottom = new UIElement(pixels, subWidth, subHeight);
    } // loadBorder

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    } // setLineCount

    public void setTopLine(int topLine) {
        topLineNumber = topLine;
    } // setTopLine

    public void setLineCounts(int newLineCount, int topLine) {
        lineCount = newLineCount;
        topLineNumber = topLine;
    } // setLineCounts

    /**
     * @return the empty space between the total height of the scroll bar and
     * the size of the scroll bar.
     */
    private int getEmptySpace() {
        if (lineCount <= viewableLines) {
            return height;
        } // if
        
        return Math.min(height, height - (int) (height * ((lineCount - viewableLines) * 1.0 / lineCount)));
    } // getEmptySpace
    
    private int getScrollBarOffset() {
        return y + Math.max(0, (int) ((topLineNumber * 1.0 / lineCount) * height));
    } // getScrollBarOffset
    
    public void setX(int newX) {
        x = Math.max(0, newX);
    } // setX
    
    public void setY(int newY) {
        y = Math.max(0, newY);
    } // setY
    
    public void setHeight(int newHeight) {
        height = newHeight;
    } // setHeight

    public void render() {
        if (lineCount <= viewableLines) {
            return;
        } // if
        
        renderBackground();
        renderScrollBar(getScrollBarOffset(), getEmptySpace());
    } // render

    private void renderBackground() {
        // Render background
        ShapeDrawer.fillRectangle(backgroundColor, x, y, SCROLLBAR_WIDTH, height);
        ShapeDrawer.fillRectangle(backgroundColorHighlight, x + SCROLLBAR_WIDTH - 1, y, 1, height);
    } // renderBackground

    private void renderScrollBar(int yStart, int height) {
        // Render middle part of the scroll bar
        ShapeDrawer.fillRectangle(scrollBarColor, x + 1, yStart + barTop.getHeight(), 2, height - barTop.getHeight() - barBottom.getHeight());
        ShapeDrawer.fillRectangle(scrollBarHighlight, x, yStart + barTop.getHeight(), 1, height - barTop.getHeight() - barBottom.getHeight());
        ShapeDrawer.fillRectangle(scrollBarShadow, x + SCROLLBAR_WIDTH - 1, yStart + barTop.getHeight(), 1, height - barTop.getHeight() - barBottom.getHeight());

        // Render top of scroll bar
        barTop.render(x, yStart);

        // Render bottom of scroll bar
        barBottom.render(x, yStart + height - barBottom.getHeight());
    } // renderScrollBar
} // ScrollBar
