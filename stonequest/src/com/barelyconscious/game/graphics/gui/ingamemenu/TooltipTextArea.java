/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        TooltipTextArea.java
 * Author:           Matt Schwartz
 * Date created:     08.31.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.gui.TextArea;
import com.barelyconscious.util.TextLogHelper;
import java.awt.Color;

public class TooltipTextArea extends TextArea {

    private int textOffsX;
    private int textOffsY;
    private int totalRows;
    private int usedRows;
    private String[] lines;
    private Color textColor = new Color(TextLogHelper.TEXTLOG_DEFAULT_COLOR);
    
    public TooltipTextArea(int startX, int startY, int width, int height, int rows) {
        super(startX, startY, width, height, false);
        this.textOffsX = startX + MARGIN;
        this.textOffsY = startY + 12 + MARGIN;
        this.totalRows = rows;
        lines = new String[rows];
        
        for (int i = 0; i < rows; i++) {
            lines[i] = "";
        } // for
    } // constructor
    
    @Override
    public void setText(String newText) {
        String[] textLines = newText.split("\n");
        usedRows = textLines.length;
        
        for (int i = 0; i < totalRows && i < textLines.length; i++) {
            lines[i] = textLines[i];
        } // for
    } // setText
    
    public void clearTooltipText() {
        for (int i = 0; i < totalRows; i++) {
            lines[i] = "";
        } // for
    } // clearTooltipText
    
    public void resize(int startX, int startY) {
        super.setX(startX);
        super.setY(startY);
        
        textOffsX = startX + MARGIN;
        textOffsY = startY + 12 + MARGIN;
    } // resize

    @Override
    protected void renderText() {
        int offsY = textOffsY + (height - MARGIN * 2 - usedRows * FontService.characterHeight) / 2;
        int offsX;
        
        for (int i = 0; i < totalRows; i++) {
            offsX = textOffsX + (width - MARGIN * 2 - FontService.getStringWidth(lines[i])) / 2;
            FontService.drawFont(lines[i], textColor, null, offsX, offsY + FontService.characterHeight * i);
        } // for
    } // renderText
} // TooltipTextArea
