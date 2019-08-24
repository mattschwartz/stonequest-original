/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        AttributesMenu.java
 * Author:           Matt Schwartz
 * Date created:     04.21.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or 
 *                   concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.gui.IRenderable;
import com.barelyconscious.gui.IWidget;

import java.awt.Color;

public class AttributesMenu extends Interactable
    implements IWidget, IRenderable {

    private final int ATTRIBUTES_FRAME_WIDTH = 185;
    private final int ATTRIBUTES_FRAME_HEIGHT = 164;
    private final int TITLE_TEXT_OFFS_X = 42;
    private final int TITLE_TEXT_OFFS_Y = 0;
    private final int TITLE_BAR_WIDTH = 98;
    private final int TITLE_BAR_HEIGHT = 20;
    private final int STAT_TEXT_COL_1_OFFS_X = 33;
    private final int STAT_TEXT_COL_1_OFFS_Y = 22;
    private final int STAT_TEXT_COL_2_OFFS_X = 115;
    private final int STAT_TEXT_COL_2_OFFS_Y = STAT_TEXT_COL_1_OFFS_Y;
    private final int STAT_TEXT_WIDTH = 57;
    private final int STAT_TEXT_HEIGHT = 22;
    private final int STAT_ICON_WIDTH = 23;
    private final int DETAILS_TEXT_OFFS_X = 52;
    private final int DETAILS_TEXT_OFFS_Y = 142;
    private final int DETAILS_TEXT_BAR_WIDTH = 78;
    private final int DETAILS_TEXT_BAR_HEIGHT = 22;
    private final int DETAILS_PAGE_TEXT_OFFS_X = 11;
    private final int DETAILS_PAGE_TEXT_OFFS_Y = 23;
    private final int DETAILS_PAGE_TEXT_WIDTH = 280;
    private final int DETAILS_PAGE_HEIGHT = 123;
    private final int DETAILS_PAGE_OFFS_Y = 13;
    private final int DETAILS_PAGE_FRAME_OFFS_X = 302 + 2;
    private final int DETAILS_PAGE_FRAME_HEIGHT = 157;
    private int xOffs;
    private int yOffs;
    private int selectedStat = -1;
    private boolean highlightDetailsButton = false;
    private boolean showDetailsPage = false;

    private final MiniMap miniMap;

    public AttributesMenu(final MiniMap miniMap) {
        this.miniMap = miniMap;
    }

    @Override
    public void resize(int width, int height) {
        xOffs = width - ATTRIBUTES_FRAME_WIDTH;
        yOffs = miniMap.getOffsY() + miniMap.getPixelHeight() + Common.TILE_SIZE;
        defineMouseZone(xOffs, yOffs, ATTRIBUTES_FRAME_WIDTH, ATTRIBUTES_FRAME_HEIGHT);
    } // resize

    /**
     * Function is called from the game's MouseHandler when the user's mouse enters the frame; this function checks to
     * see which relevant areas the cursor is located.
     *
     * @param x the x location of the mouse cursor
     * @param y the y location of the mouse cursor
     */
    @Override
    public void mouseMoved(int x, int y) {
        x -= xOffs;
        y -= yOffs;
        selectedStat = -1;
        highlightDetailsButton = false;

        // If cursor is hovering over a stat in the left column
        if (x >= STAT_TEXT_COL_1_OFFS_X - STAT_ICON_WIDTH && x <= STAT_TEXT_COL_1_OFFS_X + STAT_TEXT_WIDTH
                && y >= STAT_TEXT_COL_1_OFFS_Y && y < STAT_TEXT_COL_1_OFFS_Y + (STAT_TEXT_HEIGHT + 2) * 5) {
            selectedStat = (y - STAT_TEXT_COL_1_OFFS_Y) / (STAT_TEXT_HEIGHT + 2);
        } // if
        // If cursor is hovering over a stat in the right column
        else if (x >= STAT_TEXT_COL_2_OFFS_X - STAT_ICON_WIDTH && x <= STAT_TEXT_COL_2_OFFS_X + STAT_TEXT_WIDTH
                && y >= STAT_TEXT_COL_2_OFFS_Y && y < STAT_TEXT_COL_2_OFFS_Y + (STAT_TEXT_HEIGHT + 2) * 5) {
            selectedStat = (y - STAT_TEXT_COL_2_OFFS_Y) / (STAT_TEXT_HEIGHT + 2) + 5;
        } // else if

        // If cursor is hovering over the "Details" button
        if (x >= DETAILS_TEXT_OFFS_X && x <= (DETAILS_TEXT_OFFS_X + DETAILS_TEXT_BAR_WIDTH)
                && y >= DETAILS_TEXT_OFFS_Y && y <= DETAILS_TEXT_OFFS_Y + DETAILS_TEXT_BAR_HEIGHT) {
            highlightDetailsButton = true;
        } // if
    } // mouseMoved

    /**
     * Called from the game's MouseHandler when the user clicks a button within the attributes frame; can level up the
     * player's skills or show the details page of advanced stats.
     *
     * @param button which button the user pressed - left or right
     * @param x the x location of the mouse click
     * @param y the y location of the mouse click
     */
    @Override
    public void mouseClicked(int button, int x, int y) {
        if (selectedStat >= 0) {
            Game.player.addPointToAttribute(selectedStat);
        } // if

        if (highlightDetailsButton) {
            showDetailsPage = !showDetailsPage;
        } // if
    } // mouseClicked

    /**
     * Clears variables used for mouse location so that highlighted areas aren't when the mouse isn't in the attributes
     * frame.
     */
    @Override
    public void mouseExited() {
        selectedStat = -1;
        highlightDetailsButton = false;
    } // mouseExited

    /**
     * Render elements of the attributes frame.
     *
     * @param screen the screen to draw to
     */
    public void render(Screen screen) {
        UIElement.ATTRIBUTES_FRAME.render(screen, xOffs, yOffs);

        if (highlightDetailsButton) {
            UIElement.ATTRIBUTES_HIGHLIGHTED_DETAILS_BUTTON.renderHighlighted(screen, xOffs + DETAILS_TEXT_OFFS_X, yOffs
                    + DETAILS_TEXT_OFFS_Y);
        } // if

        // Render title text
        renderTitleText(screen);

        // Render stats text
        renderStatsText(screen);

        // Render unspent attribute points
        renderUnspentPoints(screen);

        // Render details page
        renderDetailsPage(screen);
    } // render

    /**
     * Render static text on the attributes frame.
     *
     * @param screen the screen to draw to
     */
    private void renderTitleText(Screen screen) {
        int x;
        int y;
        String string;

        string = "Attributes";
        x = xOffs + TITLE_TEXT_OFFS_X + (TITLE_BAR_WIDTH - string.length() * Font.CHAR_WIDTH) / 2;
        y = yOffs + TITLE_TEXT_OFFS_Y + (DETAILS_TEXT_BAR_HEIGHT - Font.CHAR_HEIGHT) / 2;

        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x, y);

        string = "Details";

        if (highlightDetailsButton) {
            x = xOffs + DETAILS_TEXT_OFFS_X + (DETAILS_TEXT_BAR_WIDTH - string.length() * Font.CHAR_WIDTH) / 2;
            y = yOffs + DETAILS_TEXT_OFFS_Y + (DETAILS_TEXT_BAR_HEIGHT - Font.CHAR_HEIGHT) / 2;

            Font.drawMessage(screen, string, Common.FONT_WHITE_RGB, false, x, y);
        } // if
        else {
            x = xOffs + DETAILS_TEXT_OFFS_X + (DETAILS_TEXT_BAR_WIDTH - string.length() * Font.CHAR_WIDTH) / 2;
            y = yOffs + DETAILS_TEXT_OFFS_Y + (DETAILS_TEXT_BAR_HEIGHT - Font.CHAR_HEIGHT) / 2;

            Font.drawMessage(screen, string, Common.themeForegroundColor, false, x, y);
        } // else
    } // renderTitleText

    /**
     * Render stat levels for the player.
     *
     * @param screen the screen to draw to
     */
    private void renderStatsText(Screen screen) {
        int x;
        int y;
        String string;

        // Stats in the left coloumn
        for (int i = 0; i < 5; i++) {
            string = (int) Game.player.getAttribute(i + 10) + "/" + (int) Game.player.getAttribute(i);
            x = xOffs + STAT_TEXT_COL_1_OFFS_X;
            y = yOffs + STAT_TEXT_COL_1_OFFS_Y + i
                    * (STAT_TEXT_HEIGHT + 2);

            if (i == selectedStat) {
                UIElement.ATTRIBUTES_HIGHLIGHTED_STAT.renderHighlighted(screen, x, y);

                Font.drawOutlinedMessage(screen, string, Common.FONT_WHITE_RGB, false, x + (STAT_TEXT_WIDTH
                        - string.length() * Font.CHAR_WIDTH) / 2, y + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);

                if (Game.player.canAddPointToAttribute(selectedStat)) {
                    UIElement.SKILL_UP_ICON.render(screen, x - STAT_ICON_WIDTH + 1, y + 1);
                } // if
            } // if
            else {
                Font.drawOutlinedMessage(screen, string, Common.FONT_DEFAULT_RGB, false, xOffs + STAT_TEXT_COL_1_OFFS_X
                        + (STAT_TEXT_WIDTH - string.length() * Font.CHAR_WIDTH) / 2, y
                        + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);
            } // else
        } // for

        // Stats in the right column
        for (int i = 5; i < 9; i++) {
            string = (int) Game.player.getAttribute(i + 10) + "/" + (int) Game.player.getAttribute(i);
            x = xOffs + STAT_TEXT_COL_2_OFFS_X;
            y = yOffs + STAT_TEXT_COL_2_OFFS_Y;

            if (i == selectedStat) {
                UIElement.ATTRIBUTES_HIGHLIGHTED_STAT.renderHighlighted(screen, x, y + (i - 5)
                        * (STAT_TEXT_HEIGHT + 2));

                Font.drawOutlinedMessage(screen, string, Common.FONT_WHITE_RGB, false, x + (STAT_TEXT_WIDTH
                        - string.length() * Font.CHAR_WIDTH) / 2, y + (i - 5) * (STAT_TEXT_HEIGHT + 2)
                        + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);

                if (Game.player.canAddPointToAttribute(selectedStat)) {
                    UIElement.SKILL_UP_ICON.render(screen, x - STAT_ICON_WIDTH + 1, y + (i - 5) * (STAT_TEXT_HEIGHT
                            + 2) + 1);
                } // if
            } // if
            else {
                Font.drawOutlinedMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x + (STAT_TEXT_WIDTH
                        - string.length() * Font.CHAR_WIDTH) / 2, y + (i - 5) * (STAT_TEXT_HEIGHT + 2)
                        + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);
            } // else
        } // for

        // Bonus to "all magic" 
        string = "" + (int) Game.player.getAttribute(Player.PLUS_ALL_MAGIC_BONUS);
        x = xOffs + STAT_TEXT_COL_2_OFFS_X;
        y = yOffs + STAT_TEXT_COL_2_OFFS_Y + 4 * (STAT_TEXT_HEIGHT + 2);

        if (selectedStat == Player.PLUS_ALL_MAGIC_BONUS) {
            UIElement.ATTRIBUTES_HIGHLIGHTED_STAT.renderHighlighted(screen, x, y);
            Font.drawOutlinedMessage(screen, string, Common.FONT_WHITE_RGB, false, x + (STAT_TEXT_WIDTH
                    - string.length() * Font.CHAR_WIDTH) / 2, y + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);

            if (Game.player.canAddPointToAttribute(selectedStat)) {
                UIElement.SKILL_UP_ICON.render(screen, x - STAT_ICON_WIDTH + 1, y + 1);
            } // if
        } // if
        else {
            Font.drawOutlinedMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x + (STAT_TEXT_WIDTH
                    - string.length() * Font.CHAR_WIDTH) / 2, y + (STAT_TEXT_HEIGHT - Font.CHAR_HEIGHT) / 2);
        } // else
    } // renderStatsText

    /**
     * Renders beneath the attributes frame how many points the player has left to spend, if any and which stat the
     * cursor is hovering over, currently.
     *
     * @param screen the screen to draw to
     */
    private void renderUnspentPoints(Screen screen) {
        int x;
        int y = yOffs + ATTRIBUTES_FRAME_HEIGHT + 15;
        int infoY;
        int borderColor = new Color(183, 183, 183).getRGB();
        int backgroundColor = new Color(33, 33, 33).getRGB();
        int points = Game.player.getUnspentAttributePoints();
        String string = "";

        // Show displayName of the stat that the player is hovering over
        if (selectedStat >= 0) {
            // If the player has attributes to spend, add text to inform the player of that in the message
            if (points > 0) {
                string = "click to add a point to ";
            } // if

            // Get the displayName of the stat
            string += "" + Player.idToString(selectedStat);

            x = xOffs + ATTRIBUTES_FRAME_WIDTH - string.length() * Font.CHAR_WIDTH - 1;
            infoY = y;

            if (points > 0) {
                infoY += 22;
            } // if

            // Render background bar
            screen.drawRectangle(borderColor, x - 6, infoY - 1, string.length() * Font.CHAR_WIDTH + 4,
                    Font.CHAR_HEIGHT + 2);
            screen.fillRectangle(backgroundColor, x - 4, infoY - 3, string.length() * Font.CHAR_WIDTH + 4,
                    Font.CHAR_HEIGHT + 6);
            screen.drawRectangle(borderColor, x - 4, infoY - 3, string.length() * Font.CHAR_WIDTH + 4,
                    Font.CHAR_HEIGHT + 6);
            screen.fillRectangle(backgroundColor, x - 5, infoY, string.length() * Font.CHAR_WIDTH + 4,
                    Font.CHAR_HEIGHT + 1);

            Font.drawMessage(screen, string, Common.FONT_WHITE_RGB, false, x, infoY);
        } // if

        if (Game.player.getUnspentAttributePoints() <= 0) {
            return;
        } // if

        string = Game.player.getUnspentAttributePoints() + " unspent attribute points";
        x = xOffs + ATTRIBUTES_FRAME_WIDTH - string.length() * Font.CHAR_WIDTH - 1;

        // Render background bar
        screen.drawRectangle(borderColor, x - 6, y - 1, string.length() * Font.CHAR_WIDTH + 4, Font.CHAR_HEIGHT + 2);
        screen.fillRectangle(backgroundColor, x - 4, y - 3, string.length() * Font.CHAR_WIDTH + 4, Font.CHAR_HEIGHT + 6);
        screen.drawRectangle(borderColor, x - 4, y - 3, string.length() * Font.CHAR_WIDTH + 4, Font.CHAR_HEIGHT + 6);
        screen.fillRectangle(backgroundColor, x - 5, y, string.length() * Font.CHAR_WIDTH + 4, Font.CHAR_HEIGHT + 1);

        Font.drawMessage(screen, string, Common.FONT_WHITE_RGB, false, x, y);
    } // renderUnspentPoints

    /**
     * Renders a details page, displaying advanced statistics about the player when the user requests it and draws
     * nothing otherwise.
     *
     * @param screen the screen to draw to
     */
    private void renderDetailsPage(Screen screen) {
        int x;
        int y;
        String string;

        if (!showDetailsPage) {
            return;
        } // if

        x = xOffs - DETAILS_PAGE_FRAME_OFFS_X;
        y = yOffs;

        UIElement.ATTRIBUTES_DETAILS_PAGE_VISIBLE.render(screen, x, y);

        x += DETAILS_PAGE_TEXT_OFFS_X + 2;
        y += DETAILS_PAGE_TEXT_OFFS_Y + 4;

        string = "Physical damage";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 0);
        string = String.format("%.1f", Game.player.getMinPhysicalDamage()) + "-" + String.format("%.1f",
                Game.player.getMaxPhysicalDamage());
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 0);

        string = "Magic damage";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 1);
        string = String.format("%.1f", Game.player.getMinMagicDamage()) + "-" + String.format("%.1f",
                Game.player.getMaxMagicDamage());
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 1);

        string = "Crit chance";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 2);
        string = String.format("%.2f", Game.player.getCritChance()) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 2);

        string = "Physical damage reduction";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 3);
        string = String.format("%.2f", Game.player.getPhysicalDamageReduction()) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 3);

        string = "Evasion";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 4);
        string = String.format("%.2f", Game.player.getEvadeChance()) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 4);

        string = "Fire magic bonus";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 5);
        string = String.format("%.2f", Game.player.getBonusToElement(Player.FIRE_MAGIC_BONUS)) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 5);

        string = "Frost magic bonus";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 6);
        string = String.format("%.2f", Game.player.getBonusToElement(Player.FROST_MAGIC_BONUS)) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 6);

        string = "Chaos magic bonus";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 7);
        string = String.format("%.2f", Game.player.getBonusToElement(Player.CHAOS_MAGIC_BONUS)) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 7);

        string = "Holy magic bonus";
        Font.drawMessage(screen, string, Common.FONT_DEFAULT_RGB, false, x, y + (Font.CHAR_HEIGHT + 2) * 8);
        string = String.format("%.2f", Game.player.getBonusToElement(Player.HOLY_MAGIC_BONUS)) + "%";
        Font.drawMessage(screen, string, Common.themeForegroundColor, false, x + DETAILS_PAGE_TEXT_WIDTH
                - (string.length() * Font.CHAR_WIDTH) - 1, y + (Font.CHAR_HEIGHT + 2) * 8);
    } // renderDetailsPage
} // AttributesMenu
