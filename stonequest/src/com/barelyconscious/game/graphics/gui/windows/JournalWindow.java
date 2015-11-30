/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        JournalWindow.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.gui.CloseWindowButton;
import com.barelyconscious.game.graphics.gui.InterfaceWindowButton;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.QuestButton;
import com.barelyconscious.game.graphics.gui.TextArea;
import com.barelyconscious.game.input.Interactable;
import java.awt.Color;

public class JournalWindow extends Window implements ButtonAction {

    private static final UIElement JOURNAL_WINDOW_BACKGROUND = new UIElement("/gfx/gui/components/windows/journal/background.png");
    // Quest button values
    private final int QUEST_NUMBER_BUTTON_OFFS_X = 57;
    private final int QUEST_NUMBER_BUTTON_OFFS_Y = 85;
    private final int QUEST_DESCRIPTION_OFFS_X = 28;
    private final int QUEST_DESCRIPTION_OFFS_Y = 252;
    private final int QUEST_DESCRIPTION_WIDTH = 309;
    private final int QUEST_DESCRIPTION_HEIGHT = 192;
    private final int MAX_NUM_QUESTS = 10;
    private final int QUEST_TITLE_TEXT_OFFS_X = 30;
    private final int QUEST_TITLE_TEXT_OFFS_Y = 215;
    private final int QUEST_TITLE_WIDTH = 305;
    // Quest item reward values
    private final int MAX_QUEST_REWARD_ITEMS = 5;
    private final int QUEST_REWARD_ITEM_OFFS_X = 31;
    private final int QUEST_REWARD_ITEM_OFFS_Y = 506;
    private final int QUEST_REWARD_ITEM_STEP_X = 65;
    // Gold reward values
    private final int QUEST_REWARD_GOLD_OFFS_X = 228;
    private final int QUEST_REWARD_GOLD_OFFS_Y = 569;
    private final int QUEST_REWARD_GOLD_WIDTH = 82;
    private int numQuests = 6;
    private int selectedQuest = 0;
    private int animationY;
    private InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private TextArea questDescriptionTextArea;
    // Quest buttons
    private QuestButton[] questButtons;
    
    public JournalWindow() {
    }

    public JournalWindow(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        width = JOURNAL_WINDOW_BACKGROUND.getWidth();
        height = JOURNAL_WINDOW_BACKGROUND.getHeight();

        windowOffsX = artworkWindowOffsX;
        windowOffsY = artworkWindowOffsY - JOURNAL_WINDOW_BACKGROUND.getHeight();

        windowButton = new InterfaceWindowButton(windowButtonX, windowButtonY, this, UIElement.JOURNAL_WINDOW_BUTTON);
        closeWindowButton = new CloseWindowButton(windowOffsX + 16, windowOffsY + 10, this, UIElement.INTERFACE_WINDOW_CLOSE_BUTTON);

        questDescriptionTextArea = new TextArea(windowOffsX + QUEST_DESCRIPTION_OFFS_X, windowOffsY + QUEST_DESCRIPTION_OFFS_Y, QUEST_DESCRIPTION_WIDTH, QUEST_DESCRIPTION_HEIGHT);

        createQuestButtons();

        resizeQuestButtons();

        // Disable all buttons when the window is not visible
        setComponentsEnabled(false);

        // Set the callback functions for the buttons
        setCallbacks();

        super.setRegion(windowOffsX, windowOffsY, width, height);
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();

        // debug
        questDescriptionTextArea.setText("Somewhere along this road, you will find an entrance to the mountain. It's been decades since another like yourself has wandered these parts and even longer since one has found this passage.\n\nYou know they say it's hidden? Tough to find though it might be, I believe you can find the way, adventurer.");
    } // constructor

    private void createQuestButtons() {
        questButtons = new QuestButton[MAX_NUM_QUESTS];

        questButtons[0] = new QuestButton("1", this, -1, -1);
        questButtons[1] = new QuestButton("2", this, -1, -1);
        questButtons[2] = new QuestButton("3", this, -1, -1);
        questButtons[3] = new QuestButton("4", this, -1, -1);
        questButtons[4] = new QuestButton("5", this, -1, -1);
        questButtons[5] = new QuestButton("6", this, -1, -1);
        questButtons[6] = new QuestButton("7", this, -1, -1);
        questButtons[7] = new QuestButton("8", this, -1, -1);
        questButtons[8] = new QuestButton("9", this, -1, -1);
        questButtons[9] = new QuestButton("10", this, -1, -1);
    } // createQuestButtons

    private void resizeQuestButtons() {
        int i = 0;
        int questButtonStepX = 50;
        int questButtonStepY = 50;

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                questButtons[i].setX(windowOffsX + QUEST_NUMBER_BUTTON_OFFS_X + questButtonStepX * x);
                questButtons[i].setY(windowOffsY + QUEST_NUMBER_BUTTON_OFFS_Y + questButtonStepY * y);
                i++;
            } // for
        } // for
    } // setUpQuestButtons

    /**
     * Disable or enable all buttons associated with the upgrade item window
     * that should not be interactable with whenever the frame is hidden.
     *
     * @param enabled if true, all buttons will be enabled
     */
    private void setComponentsEnabled(boolean enabled) {
        closeWindowButton.setEnabled(enabled);

        for (int i = 0; i < MAX_NUM_QUESTS; i++) {
            if (i < numQuests) {
                questButtons[i].setEnabled(enabled);
            } // if
            else {
                questButtons[i].setEnabled(false);
            } // else
        } // for
        
        questDescriptionTextArea.setEnabled(enabled);

        action(questButtons[selectedQuest]);
    } // setAllButtonsEnabled

    /**
     * Sets the callback functions for all of the buttons so this class so that
     * each button knows who to call when an action is performed.
     */
    private void setCallbacks() {
    } // setCallbacks

    /**
     * Resize elements as necessary when the application is resized.
     *
     * @param artworkWindowOffsX the new windowOffsX position of the artwork
     * interface window
     * @param artworkWindowOffsY the new windowOffsY position of the artwork
     * interface window
     * @param windowButtonX the new windowOffsX position of the upgrade item
     * window's button
     * @param windowButtonY the new windowOffsY position of the upgrade item
     * window's button
     */
    public void resize(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        windowOffsX = artworkWindowOffsX;
        windowOffsY = artworkWindowOffsY - JOURNAL_WINDOW_BACKGROUND.getHeight();

        /* Relocate (if necessary) the button in the interface which toggles the 
         * showing of the Upgrade Item window */
        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        // Relocate all other buttons as necessary
        resizeButtons(windowOffsX, windowOffsY);

        // Relocate text logs as necessary
        questDescriptionTextArea.resize(windowOffsX + QUEST_DESCRIPTION_OFFS_X, windowOffsY + QUEST_DESCRIPTION_OFFS_Y);

        // Relocate quest select buttons
        resizeQuestButtons();

        super.setRegion(windowOffsX, windowOffsY, width, height);
    } // resize

    /**
     * Given the new coordinates for the Upgrade Item window interface, all
     * inner buttons may need to be shifted accordingly so that the interface
     * maintains a complete image.
     *
     * @param windowOffsX the new windowOffsX coordinate of the starting
     * location for the Upgrade Item window interface
     * @param windowOffsY the new windowOffsY coordinate of the starting
     * location for the Upgrade Item window interface
     */
    private void resizeButtons(int windowOffsX, int windowOffsY) {
        closeWindowButton.setX(windowOffsX + 16);
        closeWindowButton.setY(windowOffsY + 10);
    } // resizeButtons

    @Override
    public void show() {
        if (InterfaceDelegate.getInstance().upgradeItemWindow.isVisible) {
            InterfaceDelegate.getInstance().upgradeItemWindow.hide();
        } // if

        super.show();
        animationY = 0 - JOURNAL_WINDOW_BACKGROUND.getHeight();
        setComponentsEnabled(true);
        setEnabled(true);
        super.addMouseListener(Interactable.Z_BACKGROUND);
    }

    @Override
    public final void hide() {
        super.hide();
        setComponentsEnabled(false);
        setEnabled(false);
        super.removeMouseListener();
    } // hide

    @Override
    public void action(Button buttonPressed) {
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            } // if
        } // if
        else {
            for (int i = 0; i < MAX_NUM_QUESTS; i++) {
                if (buttonPressed == questButtons[i]) {
                    selectedQuest = i;
                    questButtons[i].setSelected(true);
                } // if
                else {
                    questButtons[i].setSelected(false);
                } // else
            } // for
        } // else
    } // action

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            InterfaceDelegate.getInstance().clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible) {
                InterfaceDelegate.getInstance().setTooltipText("Click to close\nyour Journal");
            } // if
            else {
                InterfaceDelegate.getInstance().setTooltipText("Click to view\nyour Journal");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            InterfaceDelegate.getInstance().setTooltipText("Click to close\nyour Journal");
        } // else if
    } // hoverOverAction

    @Override
    public void render(Screen screen) {
        windowButton.render(screen);

        if (!isVisible) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (screen.getVisibleHeight() * FALL_RATE), windowOffsY);

        JOURNAL_WINDOW_BACKGROUND.render(screen, windowOffsX, animationY);

        if (animationY == windowOffsY) {
            closeWindowButton.render(screen);
            questDescriptionTextArea.render(screen);

            for (int i = 0; i < MAX_NUM_QUESTS; i++) {
                questButtons[i].render(screen);
            } // for
            
            renderText(screen);
        } // if
    } // render
    
    public void renderText(Screen screen) {
        // temp until quest system is in place
        String text = "Search for a Hidden Passage";
        int textOffsX;
        int textOffsY;
        
        textOffsX = windowOffsX + QUEST_TITLE_TEXT_OFFS_X + (QUEST_TITLE_WIDTH - Font.getStringWidth(screen, text, true, 13)) / 2;
        textOffsY = windowOffsY + QUEST_TITLE_TEXT_OFFS_Y + 18;
        Font.drawFont(screen, text, Color.white, true, 13, textOffsX, textOffsY);
        
        text = "2,180";
        textOffsX = windowOffsX + QUEST_REWARD_GOLD_OFFS_X + QUEST_REWARD_GOLD_WIDTH - Font.getStringWidth(screen, text);
        textOffsY = windowOffsY + QUEST_REWARD_GOLD_OFFS_Y + 15;
        Font.drawFont(screen, text, Common.GOLD_TEXT_COLOR, false, textOffsX, textOffsY);
    } // renderText
} // JournalWindow
