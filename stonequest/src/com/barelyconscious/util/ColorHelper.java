/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      ColorHelper.java
 *   Author:         Matt Schwartz
 *   Date:           12.14.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    Contains constants that apply to colors when rendering the game.
 **************************************************************************** */
package com.barelyconscious.util;

import java.awt.Color;

public class ColorHelper {

    /**
     * When rendering a pixel array, those that match this mask should not be
     * rendered.
     */
    public static final int TRANSPARENCY_MASK = new Color(255, 0, 255).getRGB();
    /**
     * The RGB color value for world tile selections that are valid.
     */
    public static final Color TILE_SELECT_CAN_MOVE = new Color(255, 255, 0);
    /**
     * The RGB color value for world tile selections that are not valid.
     */
    public static final Color TILE_SELECT_CANNOT_MOVE = new Color(255, 0, 0);
    public static final Color TILE_SELECT_PLAYER = new Color(0, 255, 0);
    /**
     * The RGB color value of health remaining that appears beneath Entities in the world.
     */
    public static final int ENTITY_HEALTHBAR_COLOR = new Color(10, 250, 12).getRGB();
    /**
     * The RGB color value of damaged health that appears beneath Entities in the world.
     */
    public static final int ENTITY_HEALTHBAR_DAMAGED_COLOR = new Color(250, 55, 25).getRGB();
    /**
     * The RGB color value associated with items that have a common rarity.
     */
    public static final int ITEMRARITY_COMMON_COLOR = Color.yellow.getRGB();
    /**
     * The RGB color value associated with items that have a magic rarity.
     */
    public static final int ITEMRARITY_MAGIC_COLOR = new Color(137, 207, 240).getRGB();
    /**
     * The RGB color value associated with items that have a rare rarity.
     */
    public static final int ITEMRARITY_RARE_COLOR = Color.orange.getRGB();
    /**
     * The RGB color value associated with items that have a unique rarity.
     */
    public static final int ITEMRARITY_UNIQUE_COLOR = new Color(205, 133, 63).getRGB();
    /**
     * The color of gold in the player's inventory and when offered as a reward
     * from a quest.
     */
    public static final Color PLAYER_GOLD_TEXT_COLOR = new Color(207, 148, 20);
} // ColorHelper
