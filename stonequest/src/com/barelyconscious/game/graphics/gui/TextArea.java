/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        TextArea.java
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
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import java.awt.Color;
import java.util.List;

public class TextArea extends Interactable implements Components {

    protected final int MARGIN = 5;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int backgroundColor = Color.black.getRGB();
    private int textColor = Common.FONT_DEFAULT_RGB;
    private boolean destroy;
    // For scrolling
    protected int startingLineOffset = 0;
    protected int rows;
    protected int columns;
    protected String pureText = "";
    protected ScrollBar scrollBar;

    public TextArea(int startX, int startY, int width, int height) {
        this(startX, startY, width, height, true);
    } // constructor

    public TextArea(int startX, int startY, int width, int height, boolean addMouseListener) {
        x = Math.max(0, startX);
        y = Math.max(0, startY);
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
        rows = (height - UIElement.textAreaBorderTopRepeat.getHeight() - MARGIN * 2) / Font.NEW_CHAR_HEIGHT;
        columns = (int) ((width - UIElement.textAreaBorderLeftRepeat.getWidth() - MARGIN * 2) / 8.25);

        scrollBar = new ScrollBar(x + width - ScrollBar.SCROLLBAR_WIDTH - UIElement.textAreaBorderRightRepeat.getWidth() - 1, y + UIElement.textAreaBorderTopRepeat.getHeight() + 1, height - UIElement.textAreaBorderTopRepeat.getHeight() * 2 - 2, rows);

        if (addMouseListener) {
            super.setRegion(x, y, this.width, this.height);
            super.addMouseListener(Interactable.Z_TEXT_AREA);
        } // if
    } // constructor
    
    public void resize(int startX, int startY) {
        setX(startX);
        setY(startY);
    } // resize

    public void setBackgroundColor(int newBackground) {
        backgroundColor = newBackground;
    } // setBackgroundColor

    public void setTextColor(int newTextColor) {
        textColor = newTextColor;
    } // setTextColor
    
    public void clearText() {
        pureText = "";
        startingLineOffset = 0;
        scrollBar.setLineCounts(0, 0);
    } // clearText

    public void setText(String text) {
        int lineCount;
        this.pureText = text;
        lineCount = Common.splitStringAlongWords(pureText, columns).size();
        startingLineOffset = lineCount - rows;
        scrollBar.setLineCounts(lineCount, startingLineOffset);
    } // setText

    public void append(String text) {
        int lineCount;
        this.pureText += text;
        lineCount = Common.splitStringAlongWords(pureText, columns).size();
        startingLineOffset = lineCount - rows;
        scrollBar.setLineCounts(lineCount, startingLineOffset);
    } // append

    @Override
    public void mouseWheelDown() {
        startingLineOffset++;
        scrollBar.setTopLine(startingLineOffset);
    } // scrollDown

    @Override
    public void mouseWheelUp() {
        startingLineOffset = Math.max(0, startingLineOffset - 1);
        scrollBar.setTopLine(startingLineOffset);
    } // scrollUp

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
        x = Math.max(0, newX);
        scrollBar.setX(x + width - ScrollBar.SCROLLBAR_WIDTH - UIElement.textAreaBorderRightRepeat.getWidth() - 1);
        setRegion(x, y, width, height);
    } // setX

    @Override
    public void setY(int newY) {
        y = Math.max(0, newY);
        scrollBar.setY(y + UIElement.textAreaBorderTopRepeat.getHeight() + 1);
        setRegion(x, y, width, height);
    } // setY

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    @Override
    public int getHeight() {
        return height;
    } // getHeight

    public void setWidth(int newWidth) {
        width = newWidth;
        setRegion(x, y, width, height);
    }

    public void setHeight(int newHeight) {
        height = newHeight;
        scrollBar.setHeight(height - UIElement.textAreaBorderTopRepeat.getHeight() * 2 - 2);
        setRegion(x, y, width, height);
    } // setHeight

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
        renderBorder(screen);
        renderText(screen);
        scrollBar.render(screen);
    } // render

    protected void renderBorder(Screen screen) {
        // Black background
        screen.fillRectangle(backgroundColor, x + UIElement.textAreaBorderLeftRepeat.getWidth(), y + UIElement.textAreaBorderTopRepeat.getHeight(), width - UIElement.textAreaBorderLeftRepeat.getWidth() * 2, height - UIElement.textAreaBorderTopRepeat.getHeight() * 2);

        // Render corners
        UIElement.textAreaBorderTopLeftCorner.render(screen, x, y);
        UIElement.textAreaBorderbottomLeftCorner.render(screen, x, y + height - UIElement.textAreaBorderbottomLeftCorner.getHeight());
        UIElement.textAreaBorderTopRightCorner.render(screen, x + width - UIElement.textAreaBorderTopRightCorner.getWidth(), y);
        UIElement.textAreaBorderBottomRightCorner.render(screen, x + width - UIElement.textAreaBorderTopRightCorner.getWidth(), y + height - UIElement.textAreaBorderbottomLeftCorner.getHeight());

        // Render edges
        // top and UIElement.bottom edges
        for (int i = x + UIElement.textAreaBorderTopLeftCorner.getWidth(); i < x + (width - UIElement.textAreaBorderTopRightCorner.getWidth()); i += UIElement.textAreaBorderTopRepeat.getWidth()) {
            UIElement.textAreaBorderTopRepeat.render(screen, i, y);
            UIElement.textAreaBorderBottomRepeat.render(screen, i, y + height - UIElement.textAreaBorderBottomRepeat.getHeight());
        } // for

        // left and right edges
        for (int i = y + UIElement.textAreaBorderTopLeftCorner.getHeight(); i < y + (height - UIElement.textAreaBorderbottomLeftCorner.getHeight()); i += UIElement.textAreaBorderLeftRepeat.getWidth()) {
            UIElement.textAreaBorderLeftRepeat.render(screen, x, i);
            UIElement.textAreaBorderRightRepeat.render(screen, x + width - UIElement.textAreaBorderRightRepeat.getWidth(), i);
        } // for
    } // renderBorder

    protected void renderText(Screen screen) {
        int textOffsX = x + UIElement.textAreaBorderLeftRepeat.getWidth() + MARGIN;
        int textOffsY = y + UIElement.textAreaBorderTopRepeat.getHeight() + MARGIN + Font.NEW_CHAR_HEIGHT;
        int line = 0;
        List<String> lines = Common.splitStringAlongWords(pureText, columns);

        if (startingLineOffset + rows > lines.size()) {
            startingLineOffset = lines.size() - rows;
            scrollBar.setTopLine(startingLineOffset);
        } // if

        if (startingLineOffset < 0) {
            startingLineOffset = 0;
        }

        // For scrolling
        lines = lines.subList(startingLineOffset, lines.size());

        for (String string : lines) {
            Font.drawFont(screen, string, textColor, textOffsX, textOffsY + (line++) * Font.NEW_CHAR_HEIGHT);

            if (line >= rows) {
                break;
            } // if
        } // for
    } // renderText
} // TextArea
