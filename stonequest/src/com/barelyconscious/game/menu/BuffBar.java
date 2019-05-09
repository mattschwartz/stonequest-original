/* *****************************************************************************
 * File Name:         BuffBar.java
 * Author:            Matt Schwartz
 * Date Created:      03.10.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email schwamat@gmail.com for issues or concerns.
 * File Description:  In charge of drawing any active effects to the screen.
 **************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.ScreenElement;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.activeeffects.Buff;
import com.barelyconscious.game.player.activeeffects.Curse;
import com.barelyconscious.game.player.activeeffects.Debuff;
import com.barelyconscious.game.player.activeeffects.Poison;
import com.barelyconscious.game.player.activeeffects.PotionEffect;
import com.barelyconscious.game.player.activeeffects.StatPotionEffect;
import java.awt.Color;
import java.util.ArrayList;

public class BuffBar extends Interactable {

    private final int ACTIVE_EFFECT_POPOUT_WIDTH = 26;
    private final int ACTIVE_EFFECT_POPOUT_HEIGHT = 87;
    private final int ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH = 40;
    private final int ACTIVE_EFFECT_BACKGROUND_FRAME_HEIGHT = 55;
    private final int BUFF_BACKGROUND_FRAME_OFFS_Y = 30;
    private final int DEBUFF_BACKGROUND_FRAME_OFFS_Y = 9;
    private int buffOffsX;
    private int buffOffsY;
    private int debuffOffsX;
    private int debuffOffsY;
    private int buffPopoutOffsX;
    private int buffPopoutOffsY;
    private int debuffPopoutOffsX;
    private int debuffPopoutOffsY;
    private int xOffs;
    private int yOffs;
    private int selectedDebuff = -1;
    private int selectedBuff = -1;
    private final int BUFF_SCALE = 2;
    private final Player player;

    private final int YO_THERE_IS_THIS_VALUE_IDK_WHAT_IT_MEANS_BUT_ITS_20_NOW = 20;

    public BuffBar() {
        xOffs = 0;
        yOffs = 0;

        player = Game.player;
        xOffs = Game.miniMap.getOffsX();
        yOffs = Game.miniMap.getOffsY();
    } // constructor

    public void resize(int w, int h) {
        xOffs = Game.miniMap.getOffsX();
        yOffs = Game.miniMap.getOffsY();
        width = (YO_THERE_IS_THIS_VALUE_IDK_WHAT_IT_MEANS_BUT_ITS_20_NOW* BUFF_SCALE) * 10;
        height = Game.miniMap.getPixelHeight();

        buffPopoutOffsX = xOffs - ACTIVE_EFFECT_POPOUT_WIDTH;
        buffPopoutOffsY = yOffs + 6;

        debuffPopoutOffsX = buffPopoutOffsX;
        debuffPopoutOffsY = yOffs + 144;

        buffOffsX = buffPopoutOffsX - ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 1;
        buffOffsY = BUFF_BACKGROUND_FRAME_OFFS_Y;

        debuffOffsX = buffPopoutOffsX - ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 1;
        debuffOffsY = debuffPopoutOffsY + DEBUFF_BACKGROUND_FRAME_OFFS_Y;

        defineMouseZone(xOffs - width, yOffs, width, height);
        Game.mouseHandler.registerHoverableListener(this);
    } // resize

    @Override
    public void mouseMoved(int x, int y) {
        super.mouseMoved(x, y);
        x -= xOffs - ACTIVE_EFFECT_POPOUT_WIDTH;

        selectedBuff = selectedDebuff = -1;

        if (x > 0) {
            return;
        } // if

        x = Math.abs(x);

        if (y >= buffOffsY && y <= buffOffsY + ACTIVE_EFFECT_BACKGROUND_FRAME_HEIGHT) {
            selectedBuff = x / (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2);
        } // if
        else if (y >= debuffOffsY && y <= debuffOffsY + ACTIVE_EFFECT_BACKGROUND_FRAME_HEIGHT) {
            selectedDebuff = x / (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2);
        } // else if
    } // mouseMoved

    @Override
    public void mouseExited() {
        selectedBuff = selectedDebuff = -1;
    } // mouseExited

    public void render(Screen screen) {
        renderDebuffFrame(screen);
        renderBuffFrame(screen);
        renderTooltip(screen);
    } // render

    private void renderBuffFrame(Screen screen) {
        if (player.getNumBuffs() <= 0) {
            return;
        } // if

        renderBuffs(screen);
        UIElement.BUFF_BAR_POPOUT.render(screen, buffPopoutOffsX, buffPopoutOffsY);
    } // renderBuffFrame

    private void renderDebuffFrame(Screen screen) {
        if (player.getNumDebuffs() <= 0) {
            return;
        } // if

        renderDebuffs(screen);
        UIElement.DEBUFF_BAR_POPOUT.render(screen, debuffPopoutOffsX, debuffPopoutOffsY);
    } // renderDebuffFrame

    private void renderDebuffs(Screen screen) {
        int x;
        int y;
        int iconSize = YO_THERE_IS_THIS_VALUE_IDK_WHAT_IT_MEANS_BUT_ITS_20_NOW * BUFF_SCALE;
        String duration;

        for (int i = 0; i < player.getNumDebuffs(); i++) {
            Debuff debuff = player.getDebuffAt(i);

            x = debuffOffsX - (iconSize + 2) * i;
            y = debuffOffsY;
            duration = "" + debuff.getDuration();

            if (selectedDebuff == i) {
                UIElement.ACTIVE_EFFECT_BACKGROUND_FRAME.renderHighlighted(screen, x, y);
                debuff.getDebuffIcon().renderHighlighted(screen, x, y);
                Font.drawOutlinedMessage(screen, duration, Common.FONT_WHITE_RGB, false,
                        x + (iconSize - duration.length() * Font.CHAR_WIDTH) / 2,
                        y + iconSize + 4);
            } // if
            else {
                UIElement.ACTIVE_EFFECT_BACKGROUND_FRAME.render(screen, x, y);
                debuff.getDebuffIcon().render(screen, x, y);
                Font.drawOutlinedMessage(screen, duration, Common.themeForegroundColor, false,
                        x + (iconSize - duration.length() * Font.CHAR_WIDTH) / 2,
                        y + iconSize + 4);
            } // else
        } // for
    } // renderDebuffs

    private void renderBuffs(Screen screen) {
        int iconSize = YO_THERE_IS_THIS_VALUE_IDK_WHAT_IT_MEANS_BUT_ITS_20_NOW * BUFF_SCALE;
        String duration;
        Buff buff;

        for (int i = 0; i < player.getNumBuffs(); i++) {
            buff = player.getBuffAt(i);
            duration = "" + buff.getDurationInTicks();

            if (selectedBuff == i) {
                UIElement.ACTIVE_EFFECT_BACKGROUND_FRAME.renderHighlighted(screen, buffOffsX, buffOffsY);
                buff.getBuffIcon().renderHighlighted(screen, buffOffsX, buffOffsY);
                Font.drawOutlinedMessage(screen, duration, Common.FONT_WHITE_RGB, false,
                        buffOffsX + (iconSize - duration.length() * Font.CHAR_WIDTH) / 2,
                        buffOffsY + iconSize + 4);
            } // if
            else {
                UIElement.ACTIVE_EFFECT_BACKGROUND_FRAME.render(screen, buffOffsX, buffOffsY);
                buff.getBuffIcon().render(screen, buffOffsX, buffOffsY);
                Font.drawOutlinedMessage(screen, duration, Common.themeForegroundColor, false,
                        buffOffsX + (iconSize - duration.length() * Font.CHAR_WIDTH) / 2,
                        buffOffsY + iconSize + 4);
            } // else
        } // for
    } // renderBuffs

    private void renderTooltip(Screen screen) {
        int maxArrowOffsX;
        int tooltipOffsY;
        int arrowOffsX;
        int arrowOffsY;
        Debuff debuff;
        Buff buff;

        if ((Game.player.getDebuffAt(selectedDebuff) == null && Game.player.getBuffAt(selectedBuff) == null)) {
            return;
        } // if

        if ((debuff = Game.player.getDebuffAt(selectedDebuff)) != null) {
            maxArrowOffsX = debuffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedDebuff - 1) - 14;

            // Draw the drop down arrow
            arrowOffsX = Math.max(debuffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedDebuff) + 1, Math.min(mouseX, maxArrowOffsX));
            
            arrowOffsY = debuffOffsY + ACTIVE_EFFECT_BACKGROUND_FRAME_HEIGHT - 2;
            tooltipOffsY = arrowOffsY + 6;

            if (debuff instanceof Curse) {
                renderTooltipCurse(screen, tooltipOffsY);
            } // if
            else if (debuff instanceof Poison) {
                renderTooltipPoison(screen, tooltipOffsY);
            } // else
        } // if
        else if ( (buff = Game.player.getBuffAt(selectedBuff)) != null) {
            maxArrowOffsX = buffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedBuff - 1) - 14;

            // Draw the drop down arrow
            arrowOffsX = Math.max(buffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedBuff) + 1, Math.min(mouseX, maxArrowOffsX));
            
            arrowOffsY = buffOffsY + ACTIVE_EFFECT_BACKGROUND_FRAME_HEIGHT - 2;
            tooltipOffsY = arrowOffsY + 6;
            
            if (buff instanceof PotionEffect) {
                renderTooltipPotion(screen, tooltipOffsY);
            } // if
        } // else if
        else {
            return;
        } // else

        UIElement.DROP_DOWN_ARROW.render(screen, arrowOffsX, arrowOffsY);
    } // renderTooltip

    private void renderTooltipCurse(Screen screen, int tooltipOffsY) {
        int x; // temporary variable to reduce line length
        int y; // temporary variable to reduce line length
        int tooltipOffsX;
        int tooltipWidth;
        int tooltipHeight;
        String string; // temporary variable to reduce line length
        ArrayList<String> description = new ArrayList();
        Curse debuff = (Curse) Game.player.getDebuffAt(selectedDebuff);

        if (debuff == null) {
            System.err.println("You were null: " + selectedDebuff);
            System.exit(1);
        } // if

        // Name of the curse
        string = debuff.getDisplayName();
        description.add(string);

        tooltipWidth = string.length();

        // Flavor text for the description of the curse - always appears on all curses
        string = "A magical curse that";
        description.add(string);

        if (string.length() > tooltipWidth) {
            tooltipWidth = string.length();
        } // if

        string = "affects your attributes.";
        description.add(string);

        if (string.length() > tooltipWidth) {
            tooltipWidth = string.length();
        } // if

        tooltipWidth *= Font.CHAR_WIDTH;

        // Determine height of the tooltip; dependent on attributes, 3 lines of flavor text times height of each line
        tooltipHeight = (debuff.getNumAffectedAttributes() + 3) * (Font.CHAR_HEIGHT + 2);

        tooltipOffsX = debuffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedDebuff);

        // Make sure the tooltip does not extend past the frame - it will look displeasing
        while (tooltipOffsX + tooltipWidth > debuffOffsX + ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH) {
            tooltipOffsX--;
        } // while

        screen.fillRectangle(new Color(33, 33, 33).getRGB(), tooltipOffsX-1, tooltipOffsY-1, tooltipWidth+3, tooltipHeight+3);
        screen.drawRectangle(new Color(183, 183, 183).getRGB(), tooltipOffsX, tooltipOffsY, tooltipWidth, tooltipHeight);

        for (int i = 0; i < 3; i++) {
            string = description.get(i);
            x = tooltipOffsX + (tooltipWidth - string.length() * Font.CHAR_WIDTH) / 2;
            y = tooltipOffsY + 2;

            if (i != 0) {
                Font.drawMessage(screen, string, Common.CURSE_DURATION_TEXT_RGB, false, x, y + i * (Font.CHAR_HEIGHT + 2));
            } else {
                Font.drawMessage(screen, string, Common.FONT_WHITE_RGB, false, x, y + i * (Font.CHAR_HEIGHT + 2));
            }
        } // for

        for (int i = 0; i < debuff.getNumAffectedAttributes(); i++) {
            string = Player.idToString(debuff.getAffectedAttributeAt(i).getStatId());
            x = tooltipOffsX + 2;
            y = tooltipOffsY + 2 + (i + 3) * (Font.CHAR_HEIGHT + 2);

            Font.drawMessage(screen, string, Common.themeForegroundColor, false, x, y);

            string = "-" + (int) debuff.getAffectedAttributeAt(i).getAttributeModifier();
            x = tooltipOffsX + 2 + (tooltipWidth - string.length() * Font.CHAR_WIDTH) - 2;

            Font.drawMessage(screen, string, Common.FONT_DAMAGE_TEXT_RGB, false, x, y);
        } // for
    } // renderTooltipCurse

    private void renderTooltipPoison(Screen screen, int tooltipOffsY) {
        int tooltipWidth;
        int tooltipHeight;
        int tooltipOffsX;
        int x;
        int y;
        String title;
        String string;
        ArrayList<ScreenElement> elements = new ArrayList();
        Poison debuff = (Poison) Game.player.getDebuffAt(selectedDebuff);

        tooltipOffsX = debuffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedDebuff);

        // Add the name of the poison
        title = debuff.getDisplayName();
        tooltipWidth = 27 * Font.CHAR_WIDTH + 2;

        tooltipHeight = (4) * (Font.CHAR_HEIGHT + 2);

        // Make sure the tooltip does not extend past the frame - it will look displeasing
        while (tooltipOffsX + tooltipWidth > debuffOffsX + ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH) {
            tooltipOffsX--;
        } // while

        // Draw the actual tooltip
        screen.fillRectangle(new Color(33, 33, 33).getRGB(), tooltipOffsX-1, tooltipOffsY-1, tooltipWidth+3, tooltipHeight+3);
        screen.drawRectangle(new Color(183, 183, 183).getRGB(), tooltipOffsX, tooltipOffsY, tooltipWidth, tooltipHeight);

        // Write the name of the curse
        Font.drawMessage(screen, title, Common.FONT_WHITE_RGB, false, tooltipOffsX + (tooltipWidth - title.length() * Font.CHAR_WIDTH) / 2, tooltipOffsY + 2);

        // Write the affected stats and their values
        string = "A harsh toxin that inflicts";
        Font.drawMessage(screen, string, Common.POISON_DURATION_TEXT_RGB, false, tooltipOffsX + (tooltipWidth - string.length() * Font.CHAR_WIDTH) / 2, tooltipOffsY + 2 + Font.CHAR_HEIGHT);

        string = String.format("%.2f", debuff.getTickDamage()) + " damage as poison";

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (c >= '0' && c <= '9') {
                elements.add(new ScreenElement(c, true, Common.FONT_DAMAGE_TEXT_RGB));
            } // if
            else {
                elements.add(new ScreenElement(c, false, Common.POISON_DURATION_TEXT_RGB));
            } // else
        } // for

        x = tooltipOffsX + (tooltipWidth - string.length() * Font.CHAR_WIDTH) / 2;

        for (int i = 0; i < string.length(); i++) {
            Font.drawFormattedMessage(screen, elements.get(i), x + i * Font.CHAR_WIDTH, tooltipOffsY + 2 + Font.CHAR_HEIGHT * 2);
        } // for

        elements = new ArrayList();
        string = "every " + debuff.getDamageFrequency() + " turns";

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (c >= '0' && c <= '9') {
                elements.add(new ScreenElement(c, true, Common.FONT_DAMAGE_TEXT_RGB));
            } // if
            else {
                elements.add(new ScreenElement(c, false, Common.POISON_DURATION_TEXT_RGB));
            } // else
        } // for

        x = tooltipOffsX + (tooltipWidth - string.length() * Font.CHAR_WIDTH) / 2;

        for (int i = 0; i < string.length(); i++) {
            Font.drawFormattedMessage(screen, elements.get(i), x + i * Font.CHAR_WIDTH, tooltipOffsY + 2 + Font.CHAR_HEIGHT * 3);
        } // for
    } // renderTooltipPoison

    private void renderTooltipPotion(Screen screen, int tooltipOffsY) {
        int x;
        int y;
        int tooltipOffsX;
        int tooltipWidth;
        int tooltipHeight;
        String string;
        ArrayList<String> description = new ArrayList();

        ArrayList<ScreenElement> elements = new ArrayList();
        StatPotionEffect potionBuff = (StatPotionEffect) Game.player.getBuffAt(selectedBuff);

        if (potionBuff == null) {
            System.err.println("You're not supposssed to be heerrrrre.. " + selectedBuff);
            System.exit(1);
        }

        tooltipOffsX = buffOffsX - (ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH + 2) * (selectedDebuff);

        // Add the name of the potion
        string = potionBuff.getDisplayName();
        description.add(string);
        tooltipWidth = string.length();

        // A brief description of the potion
        string = "A thick, glowing fluid that";
        description.add(string);

        if (string.length() > tooltipWidth) {
            tooltipWidth = string.length();
        } // if

        string = "grants you additional power.";
        description.add(string);

        if (string.length() > tooltipWidth) {
            tooltipWidth = string.length();
        } // if

        tooltipWidth *= Font.CHAR_WIDTH;
        tooltipHeight = (potionBuff.getNumAffixes() + 3) * (Font.CHAR_HEIGHT + 2)+2;

        // Make sure the tooltip does not extend past the frame - it will look displeasing
        while (tooltipOffsX + tooltipWidth > debuffOffsX + ACTIVE_EFFECT_BACKGROUND_FRAME_WIDTH) {
            tooltipOffsX--;
        } // while

        // Draw the actual tooltip
        screen.fillRectangle(new Color(33, 33, 33).getRGB(), tooltipOffsX-1, tooltipOffsY-1, tooltipWidth+3, tooltipHeight+3);
        screen.drawRectangle(new Color(183, 183, 183).getRGB(), tooltipOffsX, tooltipOffsY, tooltipWidth, tooltipHeight);

        for (int i = 0; i < description.size(); i++) {
            x = tooltipOffsX + 2 + (tooltipWidth - description.get(i).length() * Font.CHAR_WIDTH) / 2;;
            y = tooltipOffsY + 2 + i * (Font.CHAR_HEIGHT + 2);

            if (i == 0) {
                Font.drawMessage(screen, description.get(i), Common.FONT_WHITE_RGB, false, x, y);
            } // if
            else {
                Font.drawMessage(screen, description.get(i), Common.POTION_DURATION_TEXT_RGB, false, x, y);
            } // else
        } // for

        for (int i = 0; i < potionBuff.getNumAffixes(); i++) {
            string = Player.idToString(potionBuff.getAffectedAttributeAt(i).getStatId());
            x = tooltipOffsX + 2;
            y = tooltipOffsY + 2 + (i + 3) * (Font.CHAR_HEIGHT + 2);

            Font.drawMessage(screen, string, Common.themeForegroundColor, false, x, y);

            string = "+" + (int) potionBuff.getAffectedAttributeAt(i).getAttributeModifier();
            x = tooltipOffsX + 2 + (tooltipWidth - string.length() * Font.CHAR_WIDTH) - 2;
            y = tooltipOffsY + 2 + (i + 3) * (Font.CHAR_HEIGHT + 2);

            Font.drawMessage(screen, string, Common.ENTITY_HEALTH_RGB, false, x, y);
        } // for
    } // renderTooltipPotion
} // BuffBar
