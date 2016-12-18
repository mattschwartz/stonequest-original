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

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.util.TextLogHelper;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class JustifiedTextArea extends TextArea {

    private final Color leftJustifiedTextColor = new Color(TextLogHelper.TEXTLOG_DEFAULT_COLOR);
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
    }

    @Override
    public void clearText() {
        leftJustifiedText.clear();
        rightJustifiedText.clear();
        currentLine = 0;
        super.scrollBar.setLineCounts(0, 0);
    }

    @Override
    public void resize(int startX, int startY) {
        super.resize(startX, startY);

        this.textOffsX = startX + MARGIN;
        this.textOffsY = startY + 12 + MARGIN;
    }
    
    public void setLine(int line, String leftText, String rightText) {
        if (line >= leftJustifiedText.size()) {
            return;
        }

        leftJustifiedText.set(line, leftText);
        rightJustifiedText.set(line, rightText);
    }

    public void appendLine(String leftText, String rightText) {
        leftJustifiedText.add(leftText);
        rightJustifiedText.add(rightText);
        currentLine++;
        startingLineOffset = 0;//currentLine - rows;
        super.scrollBar.setLineCounts(currentLine, startingLineOffset);
    }

    @Override
    protected void renderText() {
        int line = 0;
        int rightJustifiedTextOffsX;

        if (startingLineOffset + rows > leftJustifiedText.size()) {
            startingLineOffset = leftJustifiedText.size() - rows;
            scrollBar.setTopLine(startingLineOffset);
        }

        if (startingLineOffset < 0) {
            startingLineOffset = 0;
        }

        for (int i = startingLineOffset; i < leftJustifiedText.size(); i++) {
            if (!leftJustifiedText.get(i).equals("\n")) {
                FontService.drawFont(leftJustifiedText.get(i), leftJustifiedTextColor, null, textOffsX, textOffsY + line * FontService.characterHeight);
                rightJustifiedTextOffsX = x + width - FontService.getStringWidth(rightJustifiedText.get(i)) - MARGIN - 4;
                FontService.drawFont(rightJustifiedText.get(i), rightJustifiedTextColor, null, rightJustifiedTextOffsX, textOffsY + line * FontService.characterHeight);
            }
            
            line++;

            if (line >= super.rows) {
                break;
            }
        }
    }
}
