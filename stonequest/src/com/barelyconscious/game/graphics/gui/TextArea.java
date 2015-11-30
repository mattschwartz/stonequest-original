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

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.Viewport;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.util.StringHelper;
import com.barelyconscious.util.TextLogHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class TextArea extends Interactable implements Component {

    public static final String TEXT_AREA_BORDER_PATH = "/gfx/gui/components/textArea/textAreaBorder.png";
    protected static final int MAX_LINES = 25;
    protected final int MARGIN = 5;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private Color backgroundColor = Color.black;
    private int textColor = TextLogHelper.TEXTLOG_DEFAULT_COLOR;
    private boolean destroy;
    // For scrolling
    protected int startingLineOffset = 0;
    protected int rows;
    protected int columns;
    protected String pureText = "";
    protected ScrollBar scrollBar;
    // Used for drawing text area borders
    public static UIElement textAreaBorderTopLeftCorner;
    public static UIElement textAreaBorderTopRightCorner;
    public static UIElement textAreaBorderBottomLeftCorner;
    public static UIElement textAreaBorderBottomRightCorner;
    public static UIElement textAreaBorderTopRepeat;
    public static UIElement textAreaBorderBottomRepeat;
    public static UIElement textAreaBorderLeftRepeat;
    public static UIElement textAreaBorderRightRepeat;
    
    public TextArea() {
    }

    public TextArea(int startX, int startY, int width, int height) {
        this(startX, startY, width, height, true);
    } // constructor

    public TextArea(int startX, int startY, int width, int height, boolean addMouseListener) {
        loadBorder();
        x = Math.max(0, startX);
        y = Math.max(0, startY);
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
        rows = (height - textAreaBorderTopRepeat.getHeight() - MARGIN * 2) / FontService.characterHeight;
        columns = (int) ((width - textAreaBorderLeftRepeat.getWidth() - MARGIN * 2) / 8.25);

        scrollBar = new ScrollBar(x + width - ScrollBar.SCROLLBAR_WIDTH - textAreaBorderRightRepeat.getWidth() - 1, y + textAreaBorderTopRepeat.getHeight() + 1, height - textAreaBorderTopRepeat.getHeight() * 2 - 2, rows);

        if (addMouseListener) {
            super.setRegion(x, y, this.width, this.height);
            super.addMouseListener(Interactable.Z_TEXT_AREA);
        } // if
    } // constructor
    
    public void init(int startX, int startY, int width, int height, boolean addMouseListener) {
        x = Math.max(0, startX);
        y = Math.max(0, startY);
        this.width = Math.max(0, width);
        this.height = Math.max(0, height);
        rows = (height - textAreaBorderTopRepeat.getHeight() - MARGIN * 2) / FontService.characterHeight;
        columns = (int) ((width - textAreaBorderLeftRepeat.getWidth() - MARGIN * 2) / 8.25);

        scrollBar = new ScrollBar(x + width - ScrollBar.SCROLLBAR_WIDTH - textAreaBorderRightRepeat.getWidth() - 1, y + textAreaBorderTopRepeat.getHeight() + 1, height - textAreaBorderTopRepeat.getHeight() * 2 - 2, rows);

        if (addMouseListener) {
            super.setRegion(x, y, this.width, this.height);
            super.addMouseListener(Interactable.Z_TEXT_AREA);
        } // if
    }

    /**
     * Loads the border from disk by locating subimages within the larger image. This results in a single, large disk access instead of multiple,
     * smaller accesses
     */
    private void loadBorder() {
        int[] pixels;
        int subWidth = 3;
        int subHeight = 3;
        BufferedImage unparsedImage = UIElement.loadImage(TEXT_AREA_BORDER_PATH);

        pixels = unparsedImage.getRGB(0, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderTopRepeat = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderTopRightCorner = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*2, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderBottomLeftCorner = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*3, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderBottomRepeat = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*4, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderBottomRightCorner = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*5, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderLeftRepeat = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*6, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderRightRepeat = new UIElement(pixels, subWidth, subHeight);
        
        pixels = unparsedImage.getRGB(subWidth*7, 0, subWidth, subHeight, null, 0, subWidth);
        textAreaBorderTopLeftCorner = new UIElement(pixels, subWidth, subHeight);
    } // loadBorder

    public void resize(int startX, int startY) {
        setX(startX);
        setY(startY);
    } // resize

    public void setBackgroundColor(Color newBackground) {
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
        lineCount = StringHelper.splitStringAlongWords(pureText, columns).size();
        startingLineOffset = lineCount - rows;
        scrollBar.setLineCounts(lineCount, startingLineOffset);
    } // setText

    public void append(String text) {
        int lineCount;
        this.pureText += text;
        lineCount = StringHelper.splitStringAlongWords(pureText, columns).size();
        startingLineOffset = lineCount - rows;
        scrollBar.setLineCounts(lineCount, startingLineOffset);
    } // append

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
            mouseWheelDown();
        } // if
        else if (e.getButton() == Interactable.MOUSE_RIGHT_CLICK) {
            mouseWheelUp();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getY() - 8 > y) {
            mouseWheelUp();
        }
        else if (e.getY() + 8 < y) {
            mouseWheelDown();
        }
    }
    
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
        scrollBar.setX(x + width - ScrollBar.SCROLLBAR_WIDTH - textAreaBorderRightRepeat.getWidth() - 1);
        setRegion(x, y, width, height);
    } // setX

    @Override
    public void setY(int newY) {
        y = Math.max(0, newY);
        scrollBar.setY(y + textAreaBorderTopRepeat.getHeight() + 1);
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
        scrollBar.setHeight(height - textAreaBorderTopRepeat.getHeight() * 2 - 2);
        setRegion(x, y, width, height);
    } // setHeight

    /**
     * This function is called when the container is no longer necessary and should be removed as determined by the JRE.
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
    public void render() {
        renderBorder();
        renderText();
        scrollBar.render();
    } // render

    protected void renderBorder() {
        // Black background
        ShapeDrawer.fillRectangle(backgroundColor, x + textAreaBorderLeftRepeat.getWidth(), y + textAreaBorderTopRepeat.getHeight(), width - textAreaBorderLeftRepeat.getWidth() * 2, height - textAreaBorderTopRepeat.getHeight() * 2);

        // Render corners
        textAreaBorderTopLeftCorner.render(x, y);
        textAreaBorderBottomLeftCorner.render(x, y + height - textAreaBorderBottomLeftCorner.getHeight());
        textAreaBorderTopRightCorner.render(x + width - textAreaBorderTopRightCorner.getWidth(), y);
        textAreaBorderBottomRightCorner.render(x + width - textAreaBorderTopRightCorner.getWidth(), y + height - textAreaBorderBottomLeftCorner.getHeight());

        // Render edges
        // top and UIElement.bottom edges
        for (int i = x + textAreaBorderTopLeftCorner.getWidth(); i < x + (width - textAreaBorderTopRightCorner.getWidth()); i += textAreaBorderTopRepeat.getWidth()) {
            textAreaBorderTopRepeat.render(i, y);
            textAreaBorderBottomRepeat.render(i, y + height - textAreaBorderBottomRepeat.getHeight());
        } // for

        // left and right edges
        for (int i = y + textAreaBorderTopLeftCorner.getHeight(); i < y + (height - textAreaBorderBottomLeftCorner.getHeight()); i += textAreaBorderLeftRepeat.getWidth()) {
            textAreaBorderLeftRepeat.render(x, i);
            textAreaBorderRightRepeat.render(x + width - textAreaBorderRightRepeat.getWidth(), i);
        } // for
    } // renderBorder

    protected void renderText() {
        int textOffsX = x + textAreaBorderLeftRepeat.getWidth() + MARGIN;
        int textOffsY = y + textAreaBorderTopRepeat.getHeight() + MARGIN + FontService.characterHeight;
        int line = 0;
        List<String> lines = StringHelper.splitStringAlongWords(pureText, columns);

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
            FontService.drawFont(string, textColor, textOffsX, textOffsY + (line++) * FontService.characterHeight);

            if (line >= rows) {
                break;
            } // if
        } // for
    } // renderText
} // TextArea
