/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        TextLog.java
 * Author:           Matt Schwartz
 * Date created:     07.03.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove
                     credit from code that was not written fully by yourself.
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: This file provides methods for writing formatted messages 
                     to the information text frame on the main screen.  New 
                     messages appear on the bottom line of the screen and push
                     older messages up.
 **************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.ScreenElement;
import com.barelyconscious.game.item.Item;
import java.awt.Color;

public class TextLog implements Menu {
    private int xOffs;
    private int yOffs = 150;
    
    private int currentLineView = 0;
    private int topLine = 0;
    private int menuLineWidth;
    private int menuLineHeight;
    private int maxHistory;
    
    private final int NEW_MESSAGE_LINE = 0;

    private ScreenElement[][] textLogBuffer;
    
    /**
     * Create a new TextLog (only one should be created during runtime.
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
    public void resizeMenu(int width, int height) {
        menuLineWidth = (Game.attributesMenu.getOffsX() - Font.CHAR_WIDTH * 2) / Font.CHAR_WIDTH;
        menuLineHeight = 20;
        xOffs = Font.CHAR_WIDTH;
        yOffs = Game.attributesMenu.getOffsY() + (menuLineHeight - 1) * Font.CHAR_HEIGHT + 2;//859;
        textLogBuffer = new ScreenElement[menuLineWidth][maxHistory];
    } // resizeMenu
    
    @Override
    public int getWidth() {
        return menuLineWidth;
    } // getWidth
    
    @Override
    public int getHeight() {
        return menuLineHeight;
    } // getHeight
    
    @Override
    public int getOffsX() {
        return xOffs;
    } // getWidth
    
    @Override
    public int getOffsY() {
        return yOffs;
    } // getHeight

    @Override
    public void moveUp() {
        if (currentLineView >= maxHistory || currentLineView >= (topLine - menuLineHeight) ) {
            return;
        } // if
        currentLineView++;
    } // moveUp

    @Override
    public void moveDown() {
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
    public boolean isActive() {
        return true;
    } // isActive

    @Override
    public void setActive() {
    } // setActive

    @Override
    public void clearFocus() {
    } // clearFocus
    
    /**
     * Writes a message to the buffer based on a given Item
     * @param item the Item looted
     */
    public void writeLootMessage(Item item) {
        int xPos = 0;
        String messagePart1 = "You find ";
        String messagePart2 = item.getStackSize() + " ";
        String messagePart3 = "" + item;

        clearLine();

        // Message 1/3
        xPos = writePlainLine(messagePart1, xPos, NEW_MESSAGE_LINE);
        
        // Message 2/3
        if (item.getStackSize() > 1) {
            xPos = writePlainLine(messagePart2, xPos, NEW_MESSAGE_LINE);
        } // if 
       
        // Message 3/3
        if (item.getInternalName().equals("gold")) {
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
     * Write a formatted string to the text buffer.  If digitColor is null, digits
     * will have the same color as the rest of the msg.
     * @param msg the message to be printed
     * @param digitColor the Color to paint the numericals
     * @param elements Optional.  Colors entities and bolds them to make them
     * more obvious in the log.
     */
    public void writeFormattedString(String msg, Color digitColor, LineElement... elements) {
        int xPos = 0;
        boolean elementFound;
        char eChar;
        char lineChar;
        int charColor;
        
        // Wrap words that are longer than 1 line length
        if (msg.length() > menuLineWidth) {
            for (int i = 0; i < (int)Math.ceil(msg.length() / (menuLineWidth * 1.0)); i++) {
                int lineEnd = msg.length() > menuLineWidth * (i + 1) ? menuLineWidth * (i + 1) : msg.length();
                writeFormattedString(msg.substring(menuLineWidth * i, lineEnd), digitColor, elements);
            } // for-i
            
            return;
        } // if

        clearLine();

        /* Writes attack message to the information text log buffer, formatting
            numerical characters to the preset global constants */
        for (char c : msg.toCharArray()) {
            charColor = (Common.INFO_FRAME_DEFAULT_MSG_COLOR).getRGB();
            
            if (digitColor != null && c >= '0' && c <= '9') {
                charColor = digitColor.getRGB();
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
                    for (int j = 0; j < elements[x].getElement().length(); j++) {
                        eChar  = (elements[x].getElement().toLowerCase()).charAt(j);
                        lineChar = (msg.toLowerCase()).charAt(i + j);

                        if (eChar != lineChar) {
                            elementFound = false;
                            break;
                        } // if

                    } // for-entities[x]

                    if (elementFound) {
                        for (int ePos = i; ePos < i + elements[x].getElement().length(); ePos++) {
                            textLogBuffer[ePos][NEW_MESSAGE_LINE].setColor(elements[x].getColor());
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
        
        // Necessary?
        for (int x = 0; x < menuLineWidth; x++) {
            textLogBuffer[x][NEW_MESSAGE_LINE] = null;
        } // for
    } // clearLine
    
    /**
     * Write a simple line to the screen with no formatting.  This method is only
     * used within TextLog.java.
     * @param message the message to be written
     * @param xPos the x starting coordinate
     * @param yPos the y starting coordinate
     * @return 
     */
    private int writePlainLine(String message, int xPos, int yPos) {
        for (char c : message.toCharArray()) {
            textLogBuffer[xPos++][yPos] =
                    new ScreenElement(c, false, Common.INFO_FRAME_DEFAULT_MSG_COLOR.getRGB());
        } // for
        return xPos;
    } // writePlainLine

    /**
     * Writes the text log to the screen.
     * @param screen 
     */
    @Override
    public void render(Screen screen) {
        screen.fillRectangle(Common.THEME_BG_COLOR_RGB, xOffs, yOffs - (menuLineHeight - 1) * Font.CHAR_HEIGHT - 2, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 2);
        screen.drawRectangle(Common.THEME_FG_COLOR_RGB, xOffs, yOffs - (menuLineHeight - 1) * Font.CHAR_HEIGHT - 2, menuLineWidth * Font.CHAR_WIDTH, menuLineHeight * Font.CHAR_HEIGHT + 2);
        
        for (int x = 0; x < menuLineWidth; x++) {
            for (int y = currentLineView, y2 = 0; y < currentLineView + menuLineHeight; y++, y2++) {
                if (y >= maxHistory || textLogBuffer[x][y] == null) {
                    continue;
                } // if
                Font.drawFormattedMessage(screen, textLogBuffer[x][y], xOffs + (x * Font.CHAR_WIDTH), yOffs - (y2 * Font.CHAR_HEIGHT));
            } // for
        } // for
    } // render
} // TextLog