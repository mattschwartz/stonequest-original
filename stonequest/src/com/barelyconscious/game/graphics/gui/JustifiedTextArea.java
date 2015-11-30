/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        JustifiedTextArea.java
 * Author:           Matt Schwartz
 * Date created:     08.31.2013
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
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class JustifiedTextArea extends TextArea {

    private final Color leftJustifiedTextColor = new Color(Common.FONT_DEFAULT_RGB);
    private final Color rightJustifiedTextColor = new Color(72, 191, 72);
    private int textOffsX;
    private int textOffsY;
    private int currentLine;
    private List<String> leftJustifiedText;
    private List<String> rightJustifiedText;

    public JustifiedTextArea(int startX, int startY, int width, int height) {
        super(startX, startY, width, height, true);
        this.textOffsX = startX + MARGIN;
        this.textOffsY = startY + 12 + MARGIN;
        super.rows = 10;
        currentLine = 0;

        leftJustifiedText = new ArrayList<String>();
        rightJustifiedText = new ArrayList<String>();
    } // constructor

    @Override
    public void clearText() {
        leftJustifiedText.clear();
        rightJustifiedText.clear();
        currentLine = 0;
        super.scrollBar.setLineCounts(0, 0);
    } // clearText

    @Override
    public void resize(int startX, int startY) {
        super.resize(startX, startY);

        this.textOffsX = startX + MARGIN;
        this.textOffsY = startY + 12 + MARGIN;
    } // resize
    
    public void setLine(int line, String leftText, String rightText) {
        if (line >= leftJustifiedText.size()) {
            return;
        } // if

        leftJustifiedText.set(line, leftText);
        rightJustifiedText.set(line, rightText);
    } // setLine

    public void appendLine(String leftText, String rightText) {
        leftJustifiedText.add(leftText);
        rightJustifiedText.add(rightText);
        currentLine++;
        startingLineOffset = 0;//currentLine - rows;
        super.scrollBar.setLineCounts(currentLine, startingLineOffset);
    } // appendLine

    @Override
    protected void renderText(Screen screen) {
        int line = 0;
        int rightJustifiedTextOffsX;

        if (startingLineOffset + rows > leftJustifiedText.size()) {
            startingLineOffset = leftJustifiedText.size() - rows;
            scrollBar.setTopLine(startingLineOffset);
        } // if

        if (startingLineOffset < 0) {
            startingLineOffset = 0;
        } // if

        for (int i = startingLineOffset; i < leftJustifiedText.size(); i++) {
            if (!leftJustifiedText.get(i).equals("\n")) {
                Font.drawFont(screen, leftJustifiedText.get(i), leftJustifiedTextColor, null, textOffsX, textOffsY + line * Font.NEW_CHAR_HEIGHT);
                rightJustifiedTextOffsX = x + width - Font.getStringWidth(screen, rightJustifiedText.get(i)) - MARGIN - 4;
                Font.drawFont(screen, rightJustifiedText.get(i), rightJustifiedTextColor, null, rightJustifiedTextOffsX, textOffsY + line * Font.NEW_CHAR_HEIGHT);
            } // if
            
            line++;

            if (line >= super.rows) {
                break;
            } // if
        } // for
    } // renderText
} // JustifiedTextArea
