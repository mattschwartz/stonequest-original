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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import java.awt.Color;

public class QuestButton extends Button {

    private final int QUEST_NUMBER_FONT_SIZE = 30;
    private final Color QUEST_NUMBER_SELECTED_COLOR = Color.white;
    private final Color QUEST_NUMBER_ACTIVE_COLOR = new Color(137, 137, 137);
    private final Color QUEST_NUMBER_INACTIVE_COLOR = new Color(61, 61, 61);
    private static final UIElement QUEST_BUTTON_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/windows/journal/questSelectButton.png");
    private static final UIElement QUEST_BUTTON_SELECTED = UIElement.createUIElement("/gfx/gui/components/windows/journal/questSelectButtonSelected.png");
    private boolean buttonSelected = false;

    public QuestButton(String questNumber, ButtonAction callback, int startX, int startY) {
        super(questNumber, startX, startY, false);
        callbackFunction = callback;
        super.width = QUEST_BUTTON_BACKGROUND.getWidth();
        super.height = QUEST_BUTTON_BACKGROUND.getHeight();
    }

    public void setSelected(boolean isSelected) {
        buttonSelected = isSelected;
    }

    @Override
    public void render() {
        if (buttonSelected) {
            renderSelected();
        }

        if (isMouseInFocus() || buttonSelected) {
            renderHighlighted();
        }
        else {
            if (isEnabled()) {
                QUEST_BUTTON_BACKGROUND.render(x, y);
            }
            else {
                QUEST_BUTTON_BACKGROUND.renderGrayscale(x, y);
            }
        }

        renderText();
    }

    @Override
    protected void renderHighlighted() {
        QUEST_BUTTON_BACKGROUND.renderHighlighted(x, y);
    }

    private void renderSelected() {
        QUEST_BUTTON_SELECTED.render(x, y);
    }

    @Override
    protected void renderText() {
        int questNumberOffsX;
        int questNumberOffsY = y + (height) / 2 + 9;

        if (isEnabled()) {
            if (buttonSelected) {
                questNumberOffsX = x + (width - FontService.getStringWidth(title, true, QUEST_NUMBER_FONT_SIZE)) / 2;
                FontService.drawFont(title, QUEST_NUMBER_SELECTED_COLOR, true, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
            }
            else {
                questNumberOffsX = x + (width - FontService.getStringWidth(title, false, QUEST_NUMBER_FONT_SIZE)) / 2;
                FontService.drawFont(title, QUEST_NUMBER_ACTIVE_COLOR, false, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
            }
        }
        else {
            questNumberOffsX = x + (width - FontService.getStringWidth(title, false, QUEST_NUMBER_FONT_SIZE)) / 2;
            FontService.drawFont(title, QUEST_NUMBER_INACTIVE_COLOR, false, QUEST_NUMBER_FONT_SIZE, questNumberOffsX, questNumberOffsY);
        }
    }
}
