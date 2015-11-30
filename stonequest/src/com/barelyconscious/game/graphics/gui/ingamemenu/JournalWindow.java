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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.gui.ingamemenu.CloseWindowButton;
import com.barelyconscious.game.graphics.gui.ingamemenu.InterfaceWindowButton;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.ingamemenu.ItemSlotArea;
import com.barelyconscious.game.graphics.gui.ingamemenu.QuestButton;
import com.barelyconscious.game.graphics.gui.TextArea;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Journal;
import com.barelyconscious.game.player.JournalEntry;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.WindowManager;
import com.barelyconscious.util.ColorHelper;
import com.barelyconscious.util.StringHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class JournalWindow extends Window implements ButtonAction {

    private static final UIElement JOURNAL_WINDOW_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/windows/journal/background.png");
    private static final UIElement ITEM_REWARD_BORDER = UIElement.createUIElement("/gfx/gui/components/windows/journal/itemRewardBorder.png");
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
    private final int QUEST_REWARD_ITEM_OFFS_X = 31;
    private final int QUEST_REWARD_ITEM_OFFS_Y = 506;
    private final int QUEST_REWARD_ITEM_STEP_X = 64;
    // Gold reward values
    private final int QUEST_REWARD_GOLD_OFFS_X = 228;
    private final int QUEST_REWARD_GOLD_OFFS_Y = 569;
    private final int QUEST_REWARD_GOLD_WIDTH = 82;
    private int selectedQuest = -1;
    private int animationY;
    protected InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private TextArea questDescriptionTextArea;
    // Quest buttons
    private QuestButton[] questButtons;
    private ItemSlotArea[] itemRewards;

    public JournalWindow() {
        setWidth(JOURNAL_WINDOW_BACKGROUND.getWidth());
        setHeight(JOURNAL_WINDOW_BACKGROUND.getHeight());

        questDescriptionTextArea = new TextArea(getX() + QUEST_DESCRIPTION_OFFS_X, getY() + QUEST_DESCRIPTION_OFFS_Y, QUEST_DESCRIPTION_WIDTH, QUEST_DESCRIPTION_HEIGHT);
        itemRewards = new ItemSlotArea[5];
        
        createButtons();
        resizeQuestButtons();
        createItemRewardSlots();

        // Disable all buttons when the window is not visible
        setComponentsEnabled(false);

        super.setRegion(getX(), getY(), getWidth(), getHeight());
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    } // constructor

    private void createButtons() {
        closeWindowButton = new CloseWindowButton(this, WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON);
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
                questButtons[i].setX(getX() + QUEST_NUMBER_BUTTON_OFFS_X + questButtonStepX * x);
                questButtons[i].setY(getY() + QUEST_NUMBER_BUTTON_OFFS_Y + questButtonStepY * y);
                i++;
            } // for
        } // for
    } // setUpQuestButtons
    
    private void createItemRewardSlots() {
        for (int i = 0; i < 5; i++) {
            itemRewards[i] = new ItemSlotArea() {

                @Override
                public void mouseClicked(MouseEvent e) {
                } // mouseClicked
                
            };
        } // for
    } // createItemRewardSlots

    /**
     * Disable or enable all buttons associated with the upgrade item window
     * that should not be interactable with whenever the frame is hidden.
     *
     * @param enabled if true, all buttons will be enabled
     */
    private void setComponentsEnabled(boolean enabled) {
        closeWindowButton.setEnabled(enabled);

        for (int i = 0; i < MAX_NUM_QUESTS; i++) {
            if (i < World.INSTANCE.getPlayer().journal.getNumEntries()) {
                questButtons[i].setEnabled(enabled);
            } // if
            else {
                questButtons[i].setEnabled(false);
            } // else
        } // for
        
        for (int i = 0; i < 5; i++) {
            itemRewards[i].setEnabled(enabled);
        } // for

        questDescriptionTextArea.setEnabled(enabled);
    } // setAllButtonsEnabled

    /**
     * Resize elements as necessary when the application is resized.
     *
     * @param artworkWindowOffsX the new getX() position of the artwork
     * interface window
     * @param artworkWindowOffsY the new getY() position of the artwork
     * interface window
     * @param windowButtonX the new getX() position of the upgrade item
     * window's button
     * @param windowButtonY the new getY() position of the upgrade item
     * window's button
     */
    public void resize(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        setX(artworkWindowOffsX);
        setY(artworkWindowOffsY - JOURNAL_WINDOW_BACKGROUND.getHeight());

        /* Relocate (if necessary) the button in the interface which toggles the 
         * showing of the Upgrade Item window */
        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        // Relocate all other buttons as necessary
        resizeButtons(getX(), getY());

        // Relocate text logs as necessary
        questDescriptionTextArea.resize(getX() + QUEST_DESCRIPTION_OFFS_X, getY() + QUEST_DESCRIPTION_OFFS_Y);

        // Relocate quest select buttons
        resizeQuestButtons();

        super.setRegion(getX(), getY(), getWidth(), getHeight());
    } // resize

    /**
     * Given the new coordinates for the Upgrade Item window interface, all
     * inner buttons may need to be shifted accordingly so that the interface
     * maintains a complete image.
     *
     * @param getX() the new getX() coordinate of the starting
     * location for the Upgrade Item window interface
     * @param getY() the new getY() coordinate of the starting
     * location for the Upgrade Item window interface
     */
    private void resizeButtons(int windowOffsX, int windowOffsY) {
        closeWindowButton.setX(windowOffsX + 16);
        closeWindowButton.setY(windowOffsY + 10);
    } // resizeButtons

    @Override
    public void show() {
        if (WindowManager.UPGRADE_ITEM_WINDOW.isVisible()) {
            WindowManager.UPGRADE_ITEM_WINDOW.hide();
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
            WindowManager.INSTANCE.clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible()) {
                WindowManager.INSTANCE.setTooltipText("Click to close\nyour Journal");
            } // if
            else {
                WindowManager.INSTANCE.setTooltipText("Click to view\nyour Journal");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            WindowManager.INSTANCE.setTooltipText("Click to close\nyour Journal");
        } // else if
    } // hoverOverAction

    @Override
    public void render() {
        windowButton.render();

        if (!isVisible()) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (SceneService.INSTANCE.getHeight() * FALL_RATE), getY());

        JOURNAL_WINDOW_BACKGROUND.render(getX(), animationY);

        if (animationY == getY()) {
            closeWindowButton.render();
            questDescriptionTextArea.render();

            for (int i = 0; i < MAX_NUM_QUESTS; i++) {
                questButtons[i].render();
            } // for

            renderJournalEntry();
        } // if
    } // render

    private void renderJournalEntry() {
        int textOffsX;
        int textOffsY;
        String text;
        JournalEntry entry = World.INSTANCE.getPlayer().journal.getEntryAt(selectedQuest);

        if (entry == null) {
            questDescriptionTextArea.setText("No entry selected.");
            return;
        } // if

        text = entry.getTitle();
        textOffsX = getX() + QUEST_TITLE_TEXT_OFFS_X + (QUEST_TITLE_WIDTH - FontService.getStringWidth(text)) / 2;
        textOffsY = getY() + QUEST_TITLE_TEXT_OFFS_Y + 18;
        FontService.drawFont(text, Color.white, textOffsX, textOffsY);

        text = "" + StringHelper.formatNumber(entry.goldReward);
        textOffsX = getX() + QUEST_REWARD_GOLD_OFFS_X + QUEST_REWARD_GOLD_WIDTH - FontService.getStringWidth(text);
        textOffsY = getY() + QUEST_REWARD_GOLD_OFFS_Y + 15;
        FontService.drawFont(text, ColorHelper.PLAYER_GOLD_TEXT_COLOR, false, textOffsX, textOffsY);

        questDescriptionTextArea.setText(entry.getDescription());

        renderItemRewards(entry);
    } // renderJournalEntry

    private void renderItemRewards(JournalEntry entry) {
        int i = 0;
        int x, y;
        if (entry.rewards == null) {
            return;
        } // if
        
        x = getX() + QUEST_REWARD_ITEM_OFFS_X;
        y = getY() + QUEST_REWARD_ITEM_OFFS_Y;

        for (Item reward : entry.rewards) {
            ITEM_REWARD_BORDER.render(x - 4, y - 4);
            itemRewards[i].setEnabled(true);
            itemRewards[i].resize(x, y);
            itemRewards[i].setItem(reward);
            itemRewards[i].render();
            i++;
            x += QUEST_REWARD_ITEM_STEP_X;
        } // for
    } // renderItemRewards
} // JournalWindow
