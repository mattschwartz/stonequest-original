/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        QuestButton.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import java.awt.Color;

public class QuestButton extends Button {

    private final int QUEST_NUMBER_FONT_SIZE = 30;
    private final Color QUEST_NUMBER_SELECTED_COLOR = Color.white;
    private final Color QUEST_NUMBER_ACTIVE_COLOR = new Color(137, 137, 137);
    private final Color QUEST_NUMBER_INACTIVE_COLOR = new Color(61, 61, 61);
    private static final UIElement QUEST_BUTTON_BACKGROUND = new UIElement("/gfx/gui/components/windows/journal/questSelectButton.png");
    private static final UIElement QUEST_BUTTON_SELECTED = new UIElement("/gfx/gui/components/windows/journal/questSelectButtonSelected.png");
    private boolean buttonSelected = false;

    public QuestButton(String questNumber, ButtonAction callback, int startX, int startY) {
        super(questNumber, startX, startY, false);
        callbackFunction = callback;
        super.width = QUEST_BUTTON_BACKGROUND.getWidth();
        super.height = QUEST_BUTTON_BACKGROUND.getHeight();
    } // constructor

    public void setSelected(boolean isSelected) {
        buttonSelected = isSelected;
    } // setSelected

    @Override
    public void render(Screen screen) {
        if (buttonSelected) {
            renderSelected(screen);
        } // if

        if (mouseInFocus || buttonSelected) {
            renderHighlighted(screen);
        } // if
        else {
            if (enabled) {
                QUEST_BUTTON_BACKGROUND.render(screen, x, y);
            } // if
            else {
                QUEST_BUTTON_BACKGROUND.renderGrayscale(screen, x, y);
            } // else
        } // else

        renderText(screen);
    } // render

    @Override
    protected void renderHighlighted(Screen screen) {
        QUEST_BUTTON_BACKGROUND.renderHighlighted(screen, x, y);
    } // renderHighlighted

    private void renderSelected(Screen screen) {
        QUEST_BUTTON_SELECTED.render(screen, x, y);
    } // renderSelected

    @Override
    protected void renderText(Screen screen) {
        int questNumberOffsX;
        int questNumberOffsY = y + (height) / 2 + 9;

        if (enabled) {
            if (buttonSelected) {
                questNumberOffsX = x + (width - Font.getStringWidth(screen, title, true, QUEST_NUMBER_FONT_SIZE)) / 2;
                Font.drawFont(screen, title, QUEST_NUMBER_SELECTED_COLOR, true, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
            } // if
            else {
                questNumberOffsX = x + (width - Font.getStringWidth(screen, title, false, QUEST_NUMBER_FONT_SIZE)) / 2;
                Font.drawFont(screen, title, QUEST_NUMBER_ACTIVE_COLOR, false, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
            } // else
        } // if
        else {
            questNumberOffsX = x + (width - Font.getStringWidth(screen, title, false, QUEST_NUMBER_FONT_SIZE)) / 2;
            Font.drawFont(screen, title, QUEST_NUMBER_INACTIVE_COLOR, false, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
        } // else
    } // renderText
} // QuestButton
