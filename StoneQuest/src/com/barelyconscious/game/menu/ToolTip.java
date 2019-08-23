/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ToolTip.java
 * Author:           Matt Schwartz
 * Date created:     04.20.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.menu;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Food;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.player.AttributeMod;

public class ToolTip {

    private final MiniMap miniMap;

    public ToolTip(final MiniMap miniMap) {
        this.miniMap = miniMap;
    }

    public void render(Screen screen, int xStart, int yStart, boolean centered, String... message) {
        int height = (message.length) * Font.CHAR_HEIGHT;
        int width = 0;

        for (String string : message) {
            if (string.length() > width) {
                width = string.length();
            } // if
        } // for

        width *= Font.CHAR_WIDTH;

        if (centered) {
            xStart -= width;
        }

        screen.fillTransluscentRectangle(xStart - 1, yStart - 1, width + 2, height + 2);
//        screen.drawRectangle(Common.themeForegroundColor, xStart, yStart, width, height);
        screen.fillRectangle(Common.themeForegroundColor, xStart, yStart, width, Font.CHAR_HEIGHT + 1);
        Font.drawMessage(screen, message[0], Common.FONT_WHITE_RGB, false, xStart + (width - message[0].length() * Font.CHAR_WIDTH) / 2, yStart);

        for (int i = 1; i < message.length; i++) {
            Font.drawMessage(screen, message[i], Common.FONT_WHITE_RGB, false, xStart + (width - message[i].length() * Font.CHAR_WIDTH) / 2, yStart + i * Font.CHAR_HEIGHT);
        } // for
    } // render

    public void renderItem(Screen screen, Item item, int xStart, int yStart, boolean centered) {
        int height;
        int width = item.getDisplayName().length();
        String message[] = new String[item.getNumAffixes() + 2];
        AttributeMod mod;

        message[0] = item.getDisplayName();

        if (item instanceof Weapon) {
            message[1] = ((Weapon) item).weaponTypeToString(((Weapon) item).getWeaponType());
        } // if
        else if (item instanceof Armor) {
            message[1] = Armor.armorTypeToString(((Armor) item).getArmorType());
        } // else if
        else if (item instanceof Potion) {
            message[1] = "Lasts for " + ((Potion) item).getEffects().getDurationInTicks() + " turns";
        } // else if
        else if (item instanceof Projectile) {
            message[1] = "projectile";
        } // else if
        else if (item instanceof Food) {
            message[1] = "food";
        } // else if
        else if (item instanceof Scroll) {
            message[1] = "scroll";
        } // else if
        else {
            message[1] = "junk";
        } // else

        height = message.length * (Font.CHAR_HEIGHT + 1);

        for (int i = 2; i < item.getNumAffixes() + 2; i++) {
            mod = item.getAffixAt(i - 2);
            if (item instanceof Scroll) {
                message[i] = "+???";
            } // if
            else {
                message[i] = mod.getAttributeModifier() > 0 ? "+" : "-";
                message[i] += (int) mod.getAttributeModifier() + " " + mod;
            } // else
        } // for

        for (String string : message) {
            if (string.length() > width) {
                width = string.length();
            } // if
        } // for

        width *= Font.CHAR_WIDTH;

        if (centered) {
            xStart -= width / 2;
        } // if

        yStart += Common.TILE_SIZE + 1;

        while ((xStart + width) > (Game.screen.getWidth())) {
            xStart--;
        }

//        screen.fillRectangle(Common.ITEM_RARITY_UNIQUE_RGB, xStart, yStart, width, Font.CHAR_HEIGHT+1);
        screen.fillTransluscentRectangle(xStart, yStart, width, height);
        screen.fillRectangle(Common.themeForegroundColor, xStart, yStart, width, (Font.CHAR_HEIGHT + 1) * 2);
//        screen.drawRectangle(Common.ITEM_RARITY_UNIQUE_RGB, xStart, yStart, width, (Font.CHAR_HEIGHT + 1) * 2);

        Font.drawMessage(screen, message[0], item.getRarityColor(), true, xStart + (width - message[0].length() * Font.CHAR_WIDTH) / 2, yStart);

        for (int i = 1; i < message.length; i++) {
            Font.drawMessage(screen, message[i], Common.FONT_WHITE_RGB, false, xStart + (width - message[i].length() * Font.CHAR_WIDTH) / 2, yStart + i * (Font.CHAR_HEIGHT + 1));
        } // for
    } // renderItem

    public void renderItem(Screen screen, Item item) {
        int xStart = Game.getGameWidth() - Font.CHAR_WIDTH * 2 - miniMap.getPixelWidth() + 1;
        int yStart = miniMap.getPixelHeight();
        renderItem(screen, item, xStart, yStart, false);
    } // renderItem

    public void render(Screen screen, String... message) {
        int xStart = Game.getGameWidth() - Font.CHAR_WIDTH * 2 - miniMap.getPixelWidth() + 1;
        int yStart = miniMap.getPixelHeight() + Font.CHAR_HEIGHT * 2 + 1;
        render(screen, xStart, yStart, false, message);
    } // renderItem
} // ToolTip
