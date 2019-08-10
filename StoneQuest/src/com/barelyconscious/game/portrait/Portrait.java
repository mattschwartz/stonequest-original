/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Portrait.java
 * Author:           Matt Schwartz
 * Date created:     04.18.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A Portrait is a graphical representation of some Entity,
 *                   such as the player or monsters.  The user will have the 
 *                   option of displaying:
 *                 - His or her portrait
 *                 - A list of all entities that he or she can see or has seen
 *                 - A list of all elites in the area (elites that haven't been
 *                   seen will have a ? as their portrait, and their health
 *                   levels unkown; elites that have been seen will be displayed
 *                   just like regular entities)
 *                   
 *                   Or any combination of the three.
 **************************************************************************** */
package com.barelyconscious.game.portrait;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.spawnable.Entity;

public class Portrait {

    // Portrait element variables
    private final int UF_PLAYER_HEALTHBAR_WIDTH = 205;
    private final int UF_PLAYER_HEALTHBAR_HEIGHT = 18;
    private final int UF_PLAYER_HEALTHBAR_OFFS_X = 82;
    private final int UF_PLAYER_HEALTHBAR_OFFS_Y = 7;
    private final int UF_PLAYER_NAME_OFFS_X = 82;
    private final int UF_PLAYER_NAME_OFFS_Y = 26;
    private final int UF_PLAYER_NAMEBAR_WIDTH = 171;
    private final int UF_PLAYER_NAMEBAR_HEIGHT = 18;
    private final int UF_PLAYER_LEVEL_OFFS_X = 257;
    private final int UF_PLAYER_LEVEL_OFFS_Y = UF_PLAYER_NAME_OFFS_Y;
    private final int UF_PLAYER_LEVEL_BAR_WIDTH = 30;
    private final int UF_PLAYER_LEVEL_BAR_HEIGHT = UF_PLAYER_NAMEBAR_HEIGHT;
    private final int UF_PLAYER_FRAME_WIDTH = 291;
    private final int UF_PLAYER_FRAME_HEIGHT = 62;
    private final int UF_PLAYER_ICON_OFFS_X = 8;
    private final int UF_PLAYER_ICON_OFFS_Y = 8;
    private final UIElement UF_PLAYER_HEALTHBAR = UIElement.UNITFRAME_PLAYER_HEALTHBAR;
    // Entity variables
    private final int UF_ENTITY_FRAME_WIDTH = 238;
    private final int UF_ENTITY_FRAME_HEIGHT = 51;
    private final int UF_ENTITY_HEALTHBAR_WIDTH = 236;
    private final int UF_ENTITY_HEALTHBAR_HEIGHT = 15;
    private final int UF_ENTITY_HEALTHBAR_OFFS_X = 2;
    private final int UF_ENTITY_HEALTHBAR_OFFS_Y = 36;
    private final int UF_ENTITY_NAMEBAR_OFFS_X = 36;
    private final int UF_ENTITY_NAMEBAR_OFFS_Y = 16;
    private final int UF_ENTITY_NAMEBAR_WIDTH = 171;
    private final int UF_ENTITY_NAMEBAR_HEIGHT = 18;
    private final int UF_ENTITY_ICON_OFFS_X = 7;
    private final int UF_ENTITY_ICON_OFFS_Y = 7;
    private final int UF_ENTITY_LEVEL_BAR_WIDTH = 30;
    private final int UF_ENTITY_LEVEL_BAR_HEIGHT = UF_ENTITY_NAMEBAR_HEIGHT;
    private final int UF_ENTITY_LEVEL_OFFS_X = 208;
    private final int UF_ENTITY_LEVEL_OFFS_Y = UF_ENTITY_NAMEBAR_OFFS_Y;
    private final UIElement UF_ENTITY_HEALTHBAR = UIElement.UNITFRAME_ENTITY_HEALTHBAR;
    // Instance variables
    private int width;
    private int height;
    // Health bar
    private int healthBarWidth;
    private int healthBarHeight;
    private int healthBarOffsX;
    private int healthBarOffsY;
    private int healthPercentOffsX;
    private int healthPercentOffsY;
    // Name bar
    private int nameOffsX;
    private int nameOffsY;
    private int nameBarWidth;
    private int nameBarHeight;
    private int levelOffsX;
    private int levelOffsY;
    private int levelBarWidth;
    private int levelBarHeight;
    // UIElement location
    private int iconOffsX;
    private int iconOffsY;
    private int entityType;
    public final static int NON_ELITE = 0;
    public final static int PLAYER = 1;
    public static final int ELITE = 2;
    private int entityId;
    private Entity entity;
    private UIElement healthBar;

    public Portrait(int entityId, Entity entity, int entityType) {
        this.entityId = entityId;
        this.entity = entity;
        this.entityType = entityType;

        if (entityType == PLAYER) {
            width = UF_PLAYER_FRAME_WIDTH;
            height = UF_PLAYER_FRAME_HEIGHT;
            healthBarWidth = UF_PLAYER_HEALTHBAR_WIDTH;
            healthBarHeight = UF_PLAYER_HEALTHBAR_HEIGHT;
            healthBarOffsX = UF_PLAYER_HEALTHBAR_OFFS_X;
            healthBarOffsY = UF_PLAYER_HEALTHBAR_OFFS_Y;
            healthPercentOffsX = UF_PLAYER_LEVEL_OFFS_X;
            healthPercentOffsY = UF_PLAYER_HEALTHBAR_OFFS_Y;
            nameOffsX = UF_PLAYER_NAME_OFFS_X;
            nameOffsY = UF_PLAYER_NAME_OFFS_Y;
            nameBarWidth = UF_PLAYER_NAMEBAR_WIDTH;
            nameBarHeight = UF_PLAYER_NAMEBAR_HEIGHT;
            levelOffsX = UF_PLAYER_LEVEL_OFFS_X;
            levelOffsY = UF_PLAYER_LEVEL_OFFS_Y;
            levelBarHeight = UF_PLAYER_LEVEL_BAR_HEIGHT;
            levelBarWidth = UF_PLAYER_LEVEL_BAR_WIDTH;
            iconOffsX = UF_PLAYER_ICON_OFFS_X;
            iconOffsY = UF_PLAYER_ICON_OFFS_Y;
            healthBar = UF_PLAYER_HEALTHBAR;
        } // if
        else if (entityType == ELITE) {
//            width = UF_ELITE_FRAME_WIDTH;
//            height = UF_ELITE_FRAME_HEIGHT;
//            healthBarWidth = UF_ELITE_HEALTHBAR_WIDTH;
//            healthBarHeight = UF_ELITE_HEALTHBAR_HEIGHT;
//            healthBarXOffs = UF_ELITE_HEALTHBAR_XOFFS;
//            healthBarYOffs = UF_ELITE_HEALTHBAR_YOFFS;
//            levelXOffs = healthBarXOffs + healthBarWidth;
        } // else if
        else {
            width = UF_ENTITY_FRAME_WIDTH;
            height = UF_ENTITY_FRAME_HEIGHT;
            healthBarWidth = UF_ENTITY_HEALTHBAR_WIDTH;
            healthBarHeight = UF_ENTITY_HEALTHBAR_HEIGHT;
            healthBarOffsX = UF_ENTITY_HEALTHBAR_OFFS_X;
            healthBarOffsY = UF_ENTITY_HEALTHBAR_OFFS_Y;
            nameOffsX = UF_ENTITY_NAMEBAR_OFFS_X;
            nameOffsY = UF_ENTITY_NAMEBAR_OFFS_Y;
            nameBarWidth = UF_ENTITY_NAMEBAR_WIDTH;
            nameBarHeight = UF_ENTITY_NAMEBAR_HEIGHT;
            levelBarWidth = UF_ENTITY_LEVEL_BAR_WIDTH;
            levelBarHeight = UF_ENTITY_LEVEL_BAR_HEIGHT;
            levelOffsX =UF_ENTITY_LEVEL_OFFS_X;
            levelOffsY =UF_ENTITY_LEVEL_OFFS_Y;
            iconOffsX = UF_ENTITY_ICON_OFFS_X;
            iconOffsY = UF_ENTITY_ICON_OFFS_Y;
            healthBar = UF_ENTITY_HEALTHBAR;
        } // else
    } // constructor

    public int getWidth() {
        return width;
    } // getWidth

    public int getHeight() {
        return height;
    } // getHeight

    public void render(Screen screen, int xStart, int yStart) {
        int scale = 1;

        if (entityType == PLAYER) {
            scale = 2;
            UIElement.UNITFRAME_PLAYER.render(screen, xStart, yStart);
        } // else if
        else if (entityType == ELITE) {
            UIElement.UNITFRAME_ELITE.render(screen, xStart, yStart);
        } // else if
        else {
            xStart += 15;
            UIElement.UNITFRAME_ENTITY.render(screen, xStart, yStart);
        } // else

        entity.getTile().renderScaled(screen, xStart + iconOffsX, yStart + iconOffsY, scale);

        renderDamageBar(screen, xStart, yStart);
        renderText(screen, xStart, yStart);
    } // render

    private void renderText(Screen screen, int xStart, int yStart) {
        String message;
        // Player name
        message = entity.getDisplayName();

        if (message.length() > 20) {
            message = message.substring(0, 17);
//            message += "...";
        } // if

        Font.drawOutlinedMessage(screen, message, Common.FONT_WHITE_RGB, false, xStart + nameOffsX
                /* added ... */ + (nameBarWidth - message.length() * Font.CHAR_WIDTH) / 2 /* ... added */, yStart + nameOffsY + (nameBarHeight - Font.CHAR_HEIGHT) / 2);

        // Player level
        message = "Lv" + entity.getLevel();
        Font.drawOutlinedMessage(screen, message, Common.FONT_WHITE_RGB, false, xStart + levelOffsX + (levelBarWidth - message.length()
                * Font.CHAR_WIDTH) / 2, yStart + levelOffsY + (levelBarHeight - Font.CHAR_HEIGHT) / 2);

//        message = "" + Math.max((int) ((entity.getHealthPoints() / (int) entity.getMaxHealth()) * 100), 0) + "%";
//        Font.drawOutlinedMessage(screen, message, Common.FONT_WHITE_RGB, false, xStart + healthPercentOffsX + (levelBarWidth - message.length() * Font.CHAR_WIDTH) / 2, yStart + healthPercentOffsY + (levelBarHeight - Font.CHAR_HEIGHT) / 2);

        // Player hitpoints
//        message = (int) entity.getHealthPoints() + "/" + (int) entity.getMaxHealth();
//
//        Font.drawOutlinedMessage(screen, message, Common.FONT_WHITE_RGB, false, xStart + levelXOffs - message.length()
//                * Font.CHAR_WIDTH, yStart + healthBarOffsY + (healthBarHeight - Font.CHAR_HEIGHT) / 2);
    } // renderText

    private void renderDamageBar(Screen screen, int xStart, int yStart) {
        double percentHealth = (int) entity.getCurrentHealth() * 1.0 / (int) entity.getMaxHealth();
        int damageBarLength = (int) (healthBarWidth * Math.min(1, 1 - percentHealth));

        healthBar.render(screen, xStart + healthBarOffsX, yStart + healthBarOffsY);
        screen.fillTransluscentRectangle(xStart + healthBarOffsX + healthBarWidth - damageBarLength, yStart + healthBarOffsY, damageBarLength, healthBarHeight);
    } // renderDamageBar
} // Portrait
