/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        CharacterWindow.java
 * Author:           Matt Schwartz
 * Date created:     08.31.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.graphics.gui.CloseWindowButton;
import com.barelyconscious.game.graphics.gui.InterfaceWindowButton;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.JustifiedTextArea;
import com.barelyconscious.game.graphics.gui.TextArea;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.spawnable.Entity;
import java.awt.Color;
import java.awt.Rectangle;

public class CharacterWindow extends Window implements ButtonAction {

    private static final UIElement CHARACTER_WINDOW_BACKGROUND = new UIElement("/gfx/gui/components/windows/character/background.png");
    private final int HITPOINTS_ATTRIBUTE_OFFS_X = 43;
    private final int HITPOINTS_ATTRIBUTE_OFFS_Y = 153;
    private final int STRENGTH_ATTRIBUTE_OFFS_X = 43;
    private final int STRENGTH_ATTRIBUTE_OFFS_Y = 192;
    private final int ACCURACY_ATTRIBUTE_OFFS_X = 43;
    private final int ACCURACY_ATTRIBUTE_OFFS_Y = 231;
    private final int DEFENSE_ATTRIBUTE_OFFS_X = 43;
    private final int DEFENSE_ATTRIBUTE_OFFS_Y = 270;
    private final int AGILITY_ATTRIBUTE_OFFS_X = 43;
    private final int AGILITY_ATTRIBUTE_OFFS_Y = 309;
    private final int FIRE_MAGIC_ATTRIBUTE_OFFS_X = 208;
    private final int FIRE_MAGIC_ATTRIBUTE_OFFS_Y = 153;
    private final int FROST_MAGIC_ATTRIBUTE_OFFS_X = 208;
    private final int FROST_MAGIC_ATTRIBUTE_OFFS_Y = 192;
    private final int HOLY_MAGIC_ATTRIBUTE_OFFS_X = 208;
    private final int HOLY_MAGIC_ATTRIBUTE_OFFS_Y = 231;
    private final int CHAOS_MAGIC_ATTRIBUTE_OFFS_X = 208;
    private final int CHAOS_MAGIC_ATTRIBUTE_OFFS_Y = 270;
    private final int FAITH_ATTRIBUTE_OFFS_X = 208;
    private final int FAITH_ATTRIBUTE_OFFS_Y = 309;
    private final int ATTRIBUTE_HOVER_AREA_WIDTH = 115;
    private final int ATTRIBUTE_HOVER_AREA_HEIGHT = 34;
    private final int ATTRIBUTE_TEXT_OFFS_X = 43;
    private final int ATTRIBUTE_TEXT_OFFS_Y = 4;
    private final int PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH = 70;
    private final int PLAYER_NAME_TEXT_OFFS_X = 46;
    private final int PLAYER_NAME_TEXT_OFFS_Y = 124;
    private final int PLAYER_NAME_TEXT_WIDTH = 274;
    private final int PLAYER_NAME_TEXT_HEIGHT = 28;
    private final int DETAILS_TITLE_TEXT_OFFS_X = PLAYER_NAME_TEXT_OFFS_X;
    private final int DETAILS_TITLE_TEXT_OFFS_Y = 373;
    private final int DETAILS_TITLE_TEXT_WIDTH = PLAYER_NAME_TEXT_WIDTH;
    private final int DETAILS_TITLE_TEXT_HEIGHT = PLAYER_NAME_TEXT_HEIGHT;
    /* The player can hover over an attribute to learn more about it, here the
     mouse hover over regions are defined as Rectangles */
    private Rectangle attributesMouseRegion;
    private Rectangle hitpointsAttributeMouseRegion;
    private Rectangle strengthAttributeMouseRegion;
    private Rectangle accuracyAttributeMouseRegion;
    private Rectangle defenseAttributeMouseRegion;
    private Rectangle agilityAttributeMouseRegion;
    private Rectangle fireMagicAttributeMouseRegion;
    private Rectangle frostMagicAttributeMouseRegion;
    private Rectangle holyMagicAttributeMouseRegion;
    private Rectangle chaosMagicAttributeMouseRegion;
    private Rectangle faithAttributeMouseRegion;
    private final Color PLAYER_NAME_TEXT_COLOR = new Color(Common.FONT_DEFAULT_RGB);
    private int animationY;
    private int attributeHoverOver = -1;
    private InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private JustifiedTextArea detailedStatsTextArea;
    private TextArea attributeInformationTextArea;
    private final Player player;

    public CharacterWindow() {
        player = null;
    }

    public CharacterWindow(int gameWidth, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        this.player = InterfaceDelegate.getInstance().getPlayer();
        width = CHARACTER_WINDOW_BACKGROUND.getWidth();
        height = CHARACTER_WINDOW_BACKGROUND.getHeight();

        windowOffsX = gameWidth - width;
        windowOffsY = artworkWindowOffsY - height;

        windowButton = new InterfaceWindowButton(windowButtonX, windowButtonY, this, UIElement.CHARACTER_WINDOW_BUTTON);
        closeWindowButton = new CloseWindowButton(gameWidth - UIElement.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 16, windowOffsY + 10, this, UIElement.INTERFACE_WINDOW_CLOSE_BUTTON);
        detailedStatsTextArea = new JustifiedTextArea(windowOffsX + 43, windowOffsY + 409, 280, 166);
        attributeInformationTextArea = new TextArea(windowOffsX + 43, windowOffsY + 409, 280, 166);

        setAttributeMouseRegions();

        super.setRegion(windowOffsX, windowOffsY, width, height);
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    } // constructor

    private void setAttributeMouseRegions() {
        attributesMouseRegion = new Rectangle(windowOffsX + 43, windowOffsY + 153, 280, 190);
        hitpointsAttributeMouseRegion = new Rectangle(windowOffsX + HITPOINTS_ATTRIBUTE_OFFS_X, windowOffsY + HITPOINTS_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        strengthAttributeMouseRegion = new Rectangle(windowOffsX + STRENGTH_ATTRIBUTE_OFFS_X, windowOffsY + STRENGTH_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        accuracyAttributeMouseRegion = new Rectangle(windowOffsX + ACCURACY_ATTRIBUTE_OFFS_X, windowOffsY + ACCURACY_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        defenseAttributeMouseRegion = new Rectangle(windowOffsX + DEFENSE_ATTRIBUTE_OFFS_X, windowOffsY + DEFENSE_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        agilityAttributeMouseRegion = new Rectangle(windowOffsX + AGILITY_ATTRIBUTE_OFFS_X, windowOffsY + AGILITY_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        fireMagicAttributeMouseRegion = new Rectangle(windowOffsX + FIRE_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + FIRE_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        frostMagicAttributeMouseRegion = new Rectangle(windowOffsX + FROST_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + FROST_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        holyMagicAttributeMouseRegion = new Rectangle(windowOffsX + HOLY_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + HOLY_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        chaosMagicAttributeMouseRegion = new Rectangle(windowOffsX + CHAOS_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + CHAOS_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        faithAttributeMouseRegion = new Rectangle(windowOffsX + FAITH_ATTRIBUTE_OFFS_X, windowOffsY + FAITH_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
    } // setAttributeMouseRegions

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
    public void resize(int gameWidth, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        windowOffsX = gameWidth - width;
        windowOffsY = artworkWindowOffsY - height;

        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        closeWindowButton.setX(gameWidth - UIElement.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 16);
        closeWindowButton.setY(windowOffsY + 10);

        detailedStatsTextArea.resize(windowOffsX + 43, windowOffsY + 409);
        attributeInformationTextArea.resize(windowOffsX + 43, windowOffsY + 409);

        super.setRegion(windowOffsX, windowOffsY, width, height);

        attributesMouseRegion = new Rectangle(windowOffsX + 43, windowOffsY + 153, 280, 190);
        hitpointsAttributeMouseRegion = new Rectangle(windowOffsX + HITPOINTS_ATTRIBUTE_OFFS_X, windowOffsY + HITPOINTS_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        strengthAttributeMouseRegion = new Rectangle(windowOffsX + STRENGTH_ATTRIBUTE_OFFS_X, windowOffsY + STRENGTH_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        accuracyAttributeMouseRegion = new Rectangle(windowOffsX + ACCURACY_ATTRIBUTE_OFFS_X, windowOffsY + ACCURACY_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        defenseAttributeMouseRegion = new Rectangle(windowOffsX + DEFENSE_ATTRIBUTE_OFFS_X, windowOffsY + DEFENSE_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        agilityAttributeMouseRegion = new Rectangle(windowOffsX + AGILITY_ATTRIBUTE_OFFS_X, windowOffsY + AGILITY_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        fireMagicAttributeMouseRegion = new Rectangle(windowOffsX + FIRE_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + FIRE_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        frostMagicAttributeMouseRegion = new Rectangle(windowOffsX + FROST_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + FROST_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        holyMagicAttributeMouseRegion = new Rectangle(windowOffsX + HOLY_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + HOLY_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        chaosMagicAttributeMouseRegion = new Rectangle(windowOffsX + CHAOS_MAGIC_ATTRIBUTE_OFFS_X, windowOffsY + CHAOS_MAGIC_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
        faithAttributeMouseRegion = new Rectangle(windowOffsX + FAITH_ATTRIBUTE_OFFS_X, windowOffsY + FAITH_ATTRIBUTE_OFFS_Y, ATTRIBUTE_HOVER_AREA_WIDTH, ATTRIBUTE_HOVER_AREA_HEIGHT);
    } // resize

    private void setComponentsEnabled(boolean enabled) {
        detailedStatsTextArea.setEnabled(enabled);
        attributeInformationTextArea.setEnabled(enabled);
        closeWindowButton.setEnabled(enabled);
    } // setComponentsEnabled

    private void writeDetailedStats() {
        detailedStatsTextArea.clearText();
        detailedStatsTextArea.appendLine("Melee damage", String.format("%.1f-%.1f", player.getMinimumPhysicalDamage(), player.getMaximumPhysicalDamage()));
        detailedStatsTextArea.appendLine("Magic damage", String.format("%.1f-%.1f", player.getMinimumMagicDamage(), player.getMaximumMagicDamage()));
        detailedStatsTextArea.appendLine("Crit chance", String.format("%.1f%%", player.getBonusFromAccuracy()));
        detailedStatsTextArea.appendLine("Armor", String.format("%.1f%%", player.getBonusFromDefense()));
        detailedStatsTextArea.appendLine("Evasion", String.format("%.1f%%", player.getBonusFromEvasion()));
        detailedStatsTextArea.appendLine("Fire magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.FIRE_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.appendLine("Frost magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.FROST_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.appendLine("Holy magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.HOLY_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.appendLine("Chaos magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.CHAOS_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.appendLine("Faith bonus", "12%");
        detailedStatsTextArea.appendLine("\n", null);
        detailedStatsTextArea.appendLine("Current level", "" + player.getLevel());
        detailedStatsTextArea.appendLine("Current experience", Common.formatNumber(player.getCurrentExperience()));
        detailedStatsTextArea.appendLine("Next level in", Common.formatNumber(player.getRequiredExperience()));
    } // writeDetailedStats

    private void updateDetailedStats() {
        detailedStatsTextArea.setLine(0, "Melee damage", String.format("%.1f-%.1f", player.getMinimumMagicDamage(), player.getMaximumPhysicalDamage()));
        detailedStatsTextArea.setLine(1, "Magic damage", String.format("%.1f-%.1f", player.getMinimumMagicDamage(), player.getMaximumMagicDamage()));
        detailedStatsTextArea.setLine(2, "Crit chance", String.format("%.1f%%", player.getBonusFromAccuracy()));
        detailedStatsTextArea.setLine(3, "Armor", String.format("%.1f%%", player.getBonusFromDefense()));
        detailedStatsTextArea.setLine(4, "Evasion", String.format("%.1f%%", player.getBonusFromEvasion()));
        detailedStatsTextArea.setLine(5, "Fire magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.FIRE_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.setLine(6, "Frost magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.FROST_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.setLine(7, "Holy magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.HOLY_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.setLine(8, "Chaos magic bonus", String.format("%.1f%%", player.getBonusFromElement(Entity.CHAOS_MAGIC_ATTRIBUTE)));
        detailedStatsTextArea.setLine(9, "Faith bonus", "12%");
        detailedStatsTextArea.setLine(11, "Current level", "" + player.getLevel());
        detailedStatsTextArea.setLine(12, "Current experience", Common.formatNumber(player.getCurrentExperience()));
        detailedStatsTextArea.setLine(13, "Next level in", Common.formatNumber(player.getRequiredExperience()));
    } // updateDetailedStats

    private void writeAttributeInformation() {
        attributeInformationTextArea.clearText();

        switch (attributeHoverOver) {
            case Entity.HEALTH_ATTRIBUTE:
                attributeInformationTextArea.append("Health keeps you alive and having more of it allows you to take more hits before you die. Health becomes more effective the higher it is.");
                break;
            case Entity.STRENGTH_ATTRIBUTE:
                attributeInformationTextArea.append("Strength improves the physical damage done by your attacks by " + (int) (player.getAttribute(Entity.STRENGTH_ATTRIBUTE) * 2) + "% and increases your weapon damage by a direct amount.");
                break;
            case Entity.ACCURACY_ATTRIBUTE:
                attributeInformationTextArea.append("Accuracy improves your chance to strike critically for double damage with physical attacks and reduces the change your attacks will glance off of enemies.");
                break;
            case Entity.DEFENSE_ATTRIBUTE:
                attributeInformationTextArea.append("Defense reduces the amount of physical damage taken by enemies and sufficient defense will stun attacking opponents.");
                break;
            case Entity.EVASION_ATTRIBUTE:
                attributeInformationTextArea.append("Evasion increases your chance to completely avoid incoming physical attacks.");
                break;
            case Entity.FIRE_MAGIC_ATTRIBUTE:
                attributeInformationTextArea.append("Fire magic improves your fire-based damage abilities as well as helps defend you from incoming fire-based atatcks.");
                break;
            case Entity.FROST_MAGIC_ATTRIBUTE:
                attributeInformationTextArea.append("Frost magic improves your frost-based damage abilities as well as helps defend you from incoming frost-based atatcks.");
                break;
            case Entity.HOLY_MAGIC_ATTRIBUTE:
                attributeInformationTextArea.append("Holy magic improves your holy-based damage abilities as well as helps defend you from incoming holy-based atatcks.");
                break;
            case Entity.CHAOS_MAGIC_ATTRIBUTE:
                attributeInformationTextArea.append("Chaos magic improves your chaos-based damage abilities as well as helps defend you from incoming chaos-based atatcks.");
                break;
            case Entity.FAITH_ATTRIBUTE:
                attributeInformationTextArea.append("Faith improves your worth to the divine and helps you gain their favor when performing religious rituals.");
                break;
            default:
                System.err.println("Unaccounted for: " + attributeHoverOver);
        } // switch-case
    } // writeAttributeInformation

    @Override
    public void mouseMoved(int x, int y) {
        attributeHoverOver = -1;
        if (!attributesMouseRegion.contains(x, y)) {
            return;
        } // if

        // Is mouse hovering over the hitpoints attribute?
        if (hitpointsAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.HEALTH_ATTRIBUTE;
        } // if
        else if (strengthAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.STRENGTH_ATTRIBUTE;
        } // else if
        else if (accuracyAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.ACCURACY_ATTRIBUTE;
        } // else if
        else if (defenseAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.DEFENSE_ATTRIBUTE;
        } // else if
        else if (agilityAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.EVASION_ATTRIBUTE;
        } // else if
        else if (fireMagicAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.FIRE_MAGIC_ATTRIBUTE;
        } // else if
        else if (frostMagicAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.FROST_MAGIC_ATTRIBUTE;
        } // else if
        else if (holyMagicAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.HOLY_MAGIC_ATTRIBUTE;
        } // else if
        else if (chaosMagicAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.CHAOS_MAGIC_ATTRIBUTE;
        } // else if
        else if (faithAttributeMouseRegion.contains(x, y)) {
            attributeHoverOver = Entity.FAITH_ATTRIBUTE;
        } // else if
    } // mouseMoved

    @Override
    public void mouseDragged(int x, int y) {
        attributeHoverOver = -1;
        super.mouseDragged(x, y);
    } // mouseDragged

    @Override
    public void mouseExited() {
        attributeHoverOver = -1;
        super.mouseExited();
    } // mouseExited

    @Override
    public void show() {
        if (InterfaceDelegate.getInstance().inventoryWindow.isVisible) {
            InterfaceDelegate.getInstance().inventoryWindow.hide();
        } // if

        super.show();
        animationY = 0 - CHARACTER_WINDOW_BACKGROUND.getHeight();
        setComponentsEnabled(true);
        setEnabled(true);
        writeDetailedStats();
    } // show

    @Override
    public final void hide() {
        super.hide();
        setComponentsEnabled(false);
        setEnabled(false);
        attributeHoverOver = -1;
    } // hide

    @Override
    public void action(Button buttonPressed) {
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            } // if
        } // if
    } // action

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            InterfaceDelegate.getInstance().clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible) {
                InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Character\nWindow");
            } // if
            else {
                InterfaceDelegate.getInstance().setTooltipText("Click to open\nthe Character\nWindow");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Character\nWindow");
        } // else if
    } // hoverOverAction

    @Override
    public void render(Screen screen) {
        windowButton.render(screen);

        if (!isVisible) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (screen.getVisibleHeight() * FALL_RATE), windowOffsY);

        CHARACTER_WINDOW_BACKGROUND.render(screen, windowOffsX, animationY);

        if (animationY == windowOffsY) {
            renderSubtitleText(screen);
            renderPlayerAttributes(screen);
            closeWindowButton.render(screen);

            if (attributeHoverOver >= 0) {
                writeAttributeInformation();
                attributeInformationTextArea.render(screen);
            } // if
            else {
                updateDetailedStats();
                detailedStatsTextArea.render(screen);
            } // else
        } // if
    } // render

    /**
     * Draw the player's attributes to the screen on top of the background.
     *
     * @param screen the screen to render to
     */
    private void renderPlayerAttributes(Screen screen) {
        int textOffsX = windowOffsX + ATTRIBUTE_TEXT_OFFS_X;
        int textOffsY = windowOffsY + ATTRIBUTE_TEXT_OFFS_Y + 17;
        String attributeValue;

        attributeValue = "" + (int) player.getAttribute(Entity.HEALTH_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + HITPOINTS_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + HITPOINTS_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.STRENGTH_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + STRENGTH_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + STRENGTH_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.ACCURACY_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + ACCURACY_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + ACCURACY_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.DEFENSE_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + DEFENSE_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + DEFENSE_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.EVASION_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + AGILITY_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + AGILITY_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.FIRE_MAGIC_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + FIRE_MAGIC_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + FIRE_MAGIC_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.FROST_MAGIC_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + FROST_MAGIC_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + FROST_MAGIC_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.HOLY_MAGIC_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + HOLY_MAGIC_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + HOLY_MAGIC_ATTRIBUTE_OFFS_Y);

        attributeValue = "" + (int) player.getAttribute(Entity.CHAOS_MAGIC_ATTRIBUTE);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + CHAOS_MAGIC_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + CHAOS_MAGIC_ATTRIBUTE_OFFS_Y);

        attributeValue = "100";// + (int) player.getAttribute(Entity.FAITH);
        Font.drawFont(screen, attributeValue, PLAYER_NAME_TEXT_COLOR, null, textOffsX + FAITH_ATTRIBUTE_OFFS_X + (PLAYER_ATTRIBUTE_TEXT_AREA_WIDTH - Font.getStringWidth(screen, attributeValue)) / 2, textOffsY + FAITH_ATTRIBUTE_OFFS_Y);
    } // renderPlayerAttributes

    private void renderSubtitleText(Screen screen) {
        int textOffsX;
        int textOffsY;
        String attributeDetailsTitle;

        // Render the player's name
        textOffsX = windowOffsX + PLAYER_NAME_TEXT_OFFS_X + (PLAYER_NAME_TEXT_WIDTH - Font.getStringWidth(screen, player.getName())) / 2;
        textOffsY = windowOffsY + PLAYER_NAME_TEXT_OFFS_Y + (PLAYER_NAME_TEXT_HEIGHT - Font.NEW_CHAR_HEIGHT) / 2;
        Font.drawFont(screen, player.getName(), PLAYER_NAME_TEXT_COLOR, null, textOffsX, textOffsY);

        // Render the attribute details title
        if (attributeHoverOver >= 0) {
            attributeDetailsTitle = Entity.attributeIdToString(attributeHoverOver);
        } // if
        else {
            attributeDetailsTitle = "Details";
        } // else

        textOffsX = windowOffsX + DETAILS_TITLE_TEXT_OFFS_X + (DETAILS_TITLE_TEXT_WIDTH - Font.getStringWidth(screen, attributeDetailsTitle)) / 2;
        textOffsY = windowOffsY + DETAILS_TITLE_TEXT_OFFS_Y + (DETAILS_TITLE_TEXT_HEIGHT - Font.NEW_CHAR_HEIGHT) / 2;
        Font.drawFont(screen, attributeDetailsTitle, PLAYER_NAME_TEXT_COLOR, null, textOffsX, textOffsY);
    } // renderPlayerName
} // CharacterWindow
