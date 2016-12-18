/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        TextLog.java
 * Author:           Matt Schwartz
 * Date created:     08.30.2013
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
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.services.Service;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.util.EntityHelper;
import com.barelyconscious.util.LineElement;
import com.barelyconscious.util.TextLogHelper;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextLog extends TextArea implements Service {

    public static final TextLog INSTANCE = new TextLog();
    private final List<LineElement> textLog = new CopyOnWriteArrayList<LineElement>();

    private TextLog() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Only one TextLog can be created during runtime.");
        }
    }

    @Override
    public void start() {
        super.init(1, 1, 1, 1, true);
        append("\n\n\nWelcome to StoneQuest v0.7.0!\nPress ? to open the help menu.");
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    public void resize(int startX, int startY, int width, int height) {
        super.setWidth(width);
        super.setHeight(height);
        super.setX(startX);
        super.setY(startY);
    }

    @Override
    public void append(String text) {
        append(new LineElement(text));
    }

    public void appendDamageMessage(Entity assailant, Entity target, double dmg, int type) {
        int[] matchColors = new int[4];
        String msg;
        String[] matches = new String[4];

        if (assailant instanceof Player) {
            matches[0] = "You";
            matchColors[0] = TextLogHelper.TEXTLOG_ENTITY_LABEL_COLOR;
            matches[1] = target.getName();
            matchColors[1] = TextLogHelper.TEXTLOG_ENTITY_LABEL_COLOR;
            matches[2] = String.format("%.1f", dmg);
            matchColors[2] = TextLogHelper.TEXTLOG_DAMAGE_TAKEN_COLOR;
            matches[3] = EntityHelper.damageTypeToString(type);
            matchColors[3] = TextLogHelper.TEXTLOG_NULL_COLOR;

            msg = "You attack " + target.getName() + " for " + String.format("%.1f", dmg)
                    + " damage (" + EntityHelper.damageTypeToString(type) + ").";
        }
        else {
            matches[0] = assailant.getName();
            matchColors[0] = TextLogHelper.TEXTLOG_ENTITY_LABEL_COLOR;
            matches[1] = "you";
            matchColors[1] = TextLogHelper.TEXTLOG_ENTITY_LABEL_COLOR;
            matches[2] = String.format("%.1f", dmg);
            matchColors[2] = TextLogHelper.TEXTLOG_DAMAGE_TAKEN_COLOR;
            matches[3] = EntityHelper.damageTypeToString(type);
            matchColors[3] = TextLogHelper.TEXTLOG_NULL_COLOR;

            msg = assailant.getName() + " attacks you for " + String.format("%.1f", dmg)
                    + " damage (" + EntityHelper.damageTypeToString(type) + ").";
        }
        append(LineElement.parseString(msg, matches, TextLogHelper.TEXTLOG_DEFAULT_COLOR, matchColors));
    }
    
    public void appendDeathMessage(Entity deceased) {
        append(LineElement.parseString(deceased.getName() + " has been slain.", deceased.getName(), TextLogHelper.TEXTLOG_DEFAULT_COLOR, TextLogHelper.TEXTLOG_ENTITY_LABEL_COLOR));
    }

    public void append(LineElement message) {
        int lineCount;
        List<LineElement> split = message.split(columns);
        textLog.addAll(split);
        
        while (textLog.size() >= MAX_LINES) {
            textLog.remove(0);
        }

        lineCount = textLog.size();
        startingLineOffset = lineCount - rows;
        scrollBar.setLineCounts(lineCount, startingLineOffset);
    }

    public String toPlainText() {
        String str = "";

        for (LineElement line : textLog) {
            str += line + "\n";
        }

        return str;
    }

    @Override
    protected synchronized void renderText() {
        int textOffsX = x + textAreaBorderLeftRepeat.getWidth() + MARGIN;
        int textOffsY = y + textAreaBorderTopRepeat.getHeight() + MARGIN + FontService.characterHeight;
        int i = 0;
        List<LineElement> lines;

        if (startingLineOffset + rows > textLog.size()) {
            startingLineOffset = textLog.size() - rows;
            scrollBar.setTopLine(startingLineOffset);
        }

        if (startingLineOffset < 0) {
            startingLineOffset = 0;
        }

        // For scrolling
        lines = textLog.subList(startingLineOffset, textLog.size());

        for (LineElement line : lines) {
            FontService.drawFont(line, null, textOffsX, textOffsY + (i++) * FontService.characterHeight);
            if (i >= rows) {
                break;
            }
        }
    }
}
