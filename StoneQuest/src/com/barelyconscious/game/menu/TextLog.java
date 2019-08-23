/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        TextLog.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove
 *                   credit from code that was not written fully by yourself.
 *                   Please email schwamat@gmail.com for issues or concerns.
 * File description: This file provides methods for writing formatted messages 
 *                   to the information text frame on the main screen.  New 
 *                   messages appear on the bottom line of the screen and push
 *                   older messages up.
 **************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.ScreenElement;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyMap;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.gui.IRenderable;
import com.barelyconscious.gui.IWidget;

public class TextLog extends Interactable
    implements Menu, IWidget, IRenderable {

    private int xOffs;
    private int yOffs = 0;
    private int currentLineView = 0;
    private int topLine = 0;
    private int menuLineWidth;
    private int menuLineHeight;
    private int maxHistory;
    private long time_last_touched;
    private final int NEW_MESSAGE_LINE = 0;
    private ScreenElement[][] textLogBuffer;

    /**
     * Create a new TextLog (only one should be created during runtime.
     *
     * @param w the width of each line
     * @param h the max displayable height of the buffer
     * @param max the max number of lines of history to be kept
     */
    public TextLog(int w, int h, int max) {
        menuLineWidth = w;
        menuLineHeight = h;
        maxHistory = max;
        textLogBuffer = new ScreenElement[menuLineWidth][maxHistory];
    } // constructor

    @Override
    public void resize(int width, int height) {
        menuLineWidth = (int) ((width * 0.55) / Font.CHAR_WIDTH);
        menuLineHeight = 20;

//        xOffs = width - getPixelWidth() - 2;
        xOffs = 2;
        yOffs = height - Font.CHAR_HEIGHT - 2;
        textLogBuffer = new ScreenElement[menuLineWidth][maxHistory];
    } // resize

    @Override
    public int getPixelWidth() {
        return menuLineWidth * Font.CHAR_WIDTH;
    } // getPixelWidth

    @Override
    public int getPixelHeight() {
        return menuLineHeight * Font.CHAR_HEIGHT;
    } // getPixelHeight

    @Override
    public int getOffsX() {
        return xOffs;
    } // getPixelWidth

    @Override
    public int getOffsY() {
        return yOffs;
    } // getPixelHeight

    @Override
    public void moveUp() {
        time_last_touched = System.currentTimeMillis();

        if (currentLineView >= maxHistory - 20 || currentLineView >= (topLine - menuLineHeight)) {
            return;
        } // if
        currentLineView++;
    } // moveUp

    @Override
    public void moveDown() {
        time_last_touched = System.currentTimeMillis();

        // Will eventually move down in the text log if possible using page down
        if (currentLineView <= 0) {
            return;
        } // if

        currentLineView--;
    } // moveDown

    @Override
    public void select() {
        // Maybe used to clear chat?
    } // select

    @Override
    public void keyPressed(int key) {
        int scrollUp = KeyMap.getKeyCode(KeyMap.Key.TEXT_LOG_SCROLL_UP);
        int scrollDown = KeyMap.getKeyCode(KeyMap.Key.TEXT_LOG_SCROLL_DOWN);

        if (key == scrollUp) {
            moveUp();
        } // if
        else if (key == scrollDown) {
            moveDown();
        } // else if
    } // keyPressed

    /**
     * Writes a message to the buffer based on a given Item
     *
     * @param item the Item looted
     */
    public void writeLootMessage(Item item) {
        int xPos = 0;
        String messagePart1 = "You find ";
        String messagePart2 = item.getStackSize() + " ";
        String messagePart3 = "" + item.getDisplayName();

        clearLine();

        // Message 1/3
        xPos = writePlainLine(messagePart1, xPos, NEW_MESSAGE_LINE);

        // Message 2/3
        if (item.getStackSize() > 1) {
            xPos = writePlainLine(messagePart2, xPos, NEW_MESSAGE_LINE);
        } // if 

        // Message 3/3
        if (item.getDisplayName().equals("gold")) {
            messagePart3 += " coin";

            if (item.getStackSize() > 1) {
                messagePart3 += "s";
            } // if
        } // if


        for (char c : messagePart3.toLowerCase().toCharArray()) {
            textLogBuffer[xPos++][NEW_MESSAGE_LINE] =
                    new ScreenElement(c, true, item.getRarityColor());
        } // for

        writePlainLine(".", xPos, NEW_MESSAGE_LINE);

        writeToFrame();
    } // writeLootMessage

    /**
     * May be decommissioned.
     *
     * @param newWidth
     * @param newHeight
     */
    public void resizeLogBuffer(int newWidth, int newHeight) {
        ScreenElement[][] temp = textLogBuffer;
        textLogBuffer = new ScreenElement[newWidth][newHeight];

        for (int x = 0; x < menuLineWidth; x++) {
            for (int y = 0; y < menuLineHeight; y++) {
                textLogBuffer[x][y] = temp[x][y];
            } // for
        } // for

        menuLineWidth = newWidth;
        menuLineHeight = newHeight;
    }

    /**
     * Write a formatted string to the text buffer. If digitColor is null,
     * digits will have the same color as the rest of the msg.
     *
     * @param msg the message to be printed
     * @param digitColor the Color to paint the numericals
     * @param elements Optional. Colors entities and bolds them to make them
     * more obvious in the log.
     */
    public void writeFormattedString(String msg, int digitColor, LineElement... elements) {
        int xPos = 0;
        boolean elementFound;
        char eChar;
        char lineChar;
        int charColor;

        // Wrap words that are longer than 1 line length
        if (msg.length() > menuLineWidth) {
            for (int i = 0; i < (int) Math.ceil(msg.length() / (menuLineWidth * 1.0)); i++) {
                int lineEnd = msg.length() > menuLineWidth * (i + 1) ? menuLineWidth * (i + 1) : msg.length();
                writeFormattedString(msg.substring(menuLineWidth * i, lineEnd), digitColor, elements);
            } // for-i

            return;
        } // if

        clearLine();

        /* Writes attack message to the information text log buffer, formatting
         numerical characters to the preset global constants */
        for (char c : msg.toCharArray()) {
            charColor = Common.FONT_DEFAULT_RGB;

            if (c >= '0' && c <= '9') {
                charColor = digitColor;
            } // if

            textLogBuffer[xPos++][NEW_MESSAGE_LINE] = new ScreenElement(c, false, charColor);
        } // for

        /* Searches through attackMessage to locate all entities, givin as
         a parameter, within the message and formatting them according
         to the preset global constants
         Entities are characters, including the player, that exist in
         the world and interact with one another */
        if (elements != null) {

            // Iterate through the list of entities
            for (int x = 0; x < elements.length; x++) {

                // Step through the attackMessage
                for (int i = 0; i < msg.length(); i++) {
                    elementFound = true;

                    // Locate x position of entities[x] start in attackMessage
                    for (int j = 0; j < elements[x].getElementMessage().length(); j++) {
                        eChar = (elements[x].getElementMessage().toLowerCase()).charAt(j);
                        lineChar = (msg.toLowerCase()).charAt(i + j);

                        if (eChar != lineChar) {
                            elementFound = false;
                            break;
                        } // if

                    } // for-entities[x]

                    if (elementFound) {
                        for (int ePos = i; ePos < i + elements[x].getElementMessage().length(); ePos++) {
                            textLogBuffer[ePos][NEW_MESSAGE_LINE].setColor(elements[x].getColorAsRGB());
                            textLogBuffer[ePos][NEW_MESSAGE_LINE].setBold(elements[x].getBold());
                        } // for-ePos
                        break;
                    } // if

                } // for-attackMessage
            } // for-entities

        } // if entities

        // Update information log on Screen
        writeToFrame();
    } // writeInfoLine

    /*  Formats and writes the info log to the Screen
     This function is not used outside of InformationLog */
    private void writeToFrame() {
        time_last_touched = System.currentTimeMillis();

        for (int lineNumber = 0; lineNumber < menuLineHeight; lineNumber++) {
            if (textLogBuffer[0][lineNumber] == null) {
                continue; // optimizing?
            }

            for (int xPos = 0; xPos < menuLineWidth; xPos++) {
                if (textLogBuffer[xPos][lineNumber] == null) {
                    break; // optimizing?
                } // if
            } // for-width
        } // for-height

        currentLineView = 0;
    } // writeToFrame

    /*  Clears the bottom line to prepare for writing a new one
     This function is not used outside of InformationLog */
    private void clearLine() {
        // Shift messages up in the log
        for (int y = maxHistory - 1; y > 0; y--) {
            for (int x = 0; x < menuLineWidth; x++) {
                textLogBuffer[x][y] = textLogBuffer[x][y - 1];
            } // for-x
        } // for-y

        topLine++;
        if (topLine > maxHistory) {
            topLine = maxHistory;
        }

        // Necessary?
        for (int x = 0; x < menuLineWidth; x++) {
            textLogBuffer[x][NEW_MESSAGE_LINE] = null;
        } // for
    } // clearLine

    /**
     * Write a simple line to the screen with no formatting. This method is only
     * used within TextLog.java.
     *
     * @param message the message to be written
     * @param xPos the x starting coordinate
     * @param yPos the y starting coordinate
     * @return
     */
    private int writePlainLine(String message, int xPos, int yPos) {
        for (char c : message.toCharArray()) {
            textLogBuffer[xPos++][yPos] =
                    new ScreenElement(c, false, Common.FONT_DEFAULT_RGB);
        } // for
        return xPos;
    } // writePlainLine
    
    /**
     * Writes the text log to the screen.
     *
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        double scrollBarSize;
        double scrollBarPosition;
        boolean minimizeScrollBar = false;

        screen.fillTransluscentRectangle(xOffs, yOffs - (menuLineHeight - 1) * Font.CHAR_HEIGHT - 2, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 2);
        screen.drawRectangle(Common.themeForegroundColor, xOffs, yOffs - (menuLineHeight - 1) * Font.CHAR_HEIGHT - 2, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 2);

        for (int x = 0; x < menuLineWidth; x++) {
            for (int y = currentLineView, y2 = 0; y < currentLineView + menuLineHeight; y++, y2++) {
                if (y >= maxHistory || textLogBuffer[x][y] == null) {
                    continue;
                } // if
                Font.drawFormattedMessage(screen, textLogBuffer[x][y], xOffs + (x * Font.CHAR_WIDTH), yOffs - (y2 * Font.CHAR_HEIGHT));
            } // for
        } // for

        if ((time_last_touched < (System.currentTimeMillis() - 2500))) {
            minimizeScrollBar = true;
        } // if

        if (topLine > menuLineHeight) {
            // Determine size of scroll bar
            scrollBarSize = (menuLineHeight * 1.0) / topLine;

            // Determine position of bar in the frame

            // How much empty space between top of log and top of scroll bar?
            // size of text log - size of scroll bar
            scrollBarPosition = getPixelHeight() - (int) (getPixelHeight() * scrollBarSize);

            // position bar so it will, at rest, be at the bottom
            // top of log + size of empty space
            scrollBarPosition = (yOffs - getPixelHeight()) + scrollBarPosition + Font.CHAR_HEIGHT;

            scrollBarPosition -= (getPixelHeight() - (int) (getPixelHeight() * scrollBarSize)) * (1 - ((((topLine - 20) - currentLineView) * 1.0) / (topLine - 20)));

            if (minimizeScrollBar) {
                screen.fillRectangle(Common.themeForegroundColor, xOffs + getPixelWidth() - 2 - 1, (int) scrollBarPosition, 2, (int) (getPixelHeight() * scrollBarSize) - 1);
            } // if
            else {
                screen.fillRectangle(Common.themeForegroundColor, xOffs + getPixelWidth() - 5 - 1, (int) scrollBarPosition, 5, (int) (getPixelHeight() * scrollBarSize) - 1);
            } // else
        } // if
        
//        System.err.println("starting");
//        new Thread(new Run()).start();
//        System.err.println("done.");
//        
//        System.exit(1);

//        System.err.println("waiting");
//        new Thread() {
//            @Override
//            public void run() {
//                System.err.println("hi");
//                int x = newfunc();
//                System.err.println("got value!");
//            }
//        }.start();
//        System.err.println("done.");
    } // render
} // TextLog

 class Run implements Runnable {

    @Override
    public void run() {
        System.err.println("test");
        
        while (true);
    }
    
}
