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

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import java.awt.Color;

public class ScrollBar {

    public static final int SCROLLBAR_WIDTH = 4;
    protected int x;
    protected int y;
    protected int height;
    protected int viewableLines; // number of lines visible in the frame
    protected int topLineNumber; // line number at the very top of the frame
    protected int lineCount; // total number of lines in the frame
    private int backgroundColor = new Color(33, 33, 33).getRGB();
    private int backgroundColorHighlight = new Color(61, 61, 61).getRGB();
    private static UIElement barTop = new UIElement("/gfx/gui/components/scrollBar/barTop.png");
    private int scrollBarColor = new Color(84, 84, 84).getRGB();
    private int scrollBarHighlight = new Color(127, 127, 127).getRGB();
    private int scrollBarShadow = new Color(61, 61, 61).getRGB();
    private static UIElement barBottom = new UIElement("/gfx/gui/components/scrollBar/barBottom.png");

    public ScrollBar(int xStart, int yStart, int height, int lineHeight) {
        x = xStart;
        y = yStart;
        this.height = height;
        viewableLines = lineHeight;
        topLineNumber = 0;
    } // constructor

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

    public void render(Screen screen) {
        if (lineCount <= viewableLines) {
            return;
        }
        
        renderBackground(screen);
        renderScrollBar(screen, getScrollBarOffset(), getEmptySpace());
    } // render

    private void renderBackground(Screen screen) {
        // Render background
        screen.fillRectangle(backgroundColor, x, y, SCROLLBAR_WIDTH, height);
        screen.fillRectangle(backgroundColorHighlight, x + SCROLLBAR_WIDTH - 1, y, 1, height);
    } // renderBackground

    private void renderScrollBar(Screen screen, int yStart, int height) {
        // Render middle part of the scroll bar
        screen.fillRectangle(scrollBarColor, x + 1, yStart + barTop.getHeight(), 2, height - barTop.getHeight() - barBottom.getHeight());
        screen.fillRectangle(scrollBarHighlight, x, yStart + barTop.getHeight(), 1, height - barTop.getHeight() - barBottom.getHeight());
        screen.fillRectangle(scrollBarShadow, x + SCROLLBAR_WIDTH - 1, yStart + barTop.getHeight(), 1, height - barTop.getHeight() - barBottom.getHeight());

        // Render top of scroll bar
        barTop.render(screen, x, yStart);

        // Render bottom of scroll bar
        barBottom.render(screen, x, yStart + height - barBottom.getHeight());
    } // renderScrollBar
} // ScrollBar
