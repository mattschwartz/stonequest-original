/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      TextLogHelper.java
 *   Author:         Matt Schwartz
 *   Date:           12.14.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    
 **************************************************************************** */
package com.barelyconscious.util;

import java.awt.Color;

public class TextLogHelper {

    /**
     * The RGB color value for loot labels for the text log.
     */
    public static final int TEXTLOG_LOOT_COLOR = new Color(137, 207, 240).getRGB();
    // The following color constants are used in the text log
    /**
     * The default RGB color value of text for the text log.
     */
    public static final int TEXTLOG_DEFAULT_COLOR = Color.lightGray.getRGB();
    /**
     * The RGB color value for entity labels for the text log.
     */
    public static final int TEXTLOG_ENTITY_LABEL_COLOR = new Color(255, 255, 150).getRGB();
    /**
     * Null RGB color value for use in the text log. This value is used when
     * digits in the text log should not be colored differently.
     */
    public static final int TEXTLOG_NULL_COLOR = new Color(69, 69, 69).getRGB();
    /**
     * The RGB color of the text that displays the remaining duration associated
     * with curses.
     */
    public static final int CURSE_DURATION_TEXT_RGB = new Color(138, 43, 226).getRGB();
    /**
     * The RGB color value for Toxin labels for the text log.
     */
    public static final int TEXTLOG_TOXIN_COLOR = new Color(15, 175, 6).getRGB();
    /**
     * The RGB color value for curses labels for the text log.
     */
    public static final int TEXTLOG_CURSE_COLOR = new Color(15, 175, 6).getRGB();
    /**
     * The RGB color of the text that displays the remaining duration associated
     * with potions.
     */
    public static final int TEXTLOG_POTION_COLOR = new Color(79, 105, 198).getRGB();
    /**
     * The RGB color value of damage text for the text log.
     */
    public static final int TEXTLOG_DAMAGE_TAKEN_COLOR = new Color(255, 3, 62).getRGB();
    /**
     * The RGB color value for health gain labels for the text log.
     */
    public static final int TEXTLOG_HEALTH_GAINED_COLOR = new Color(0, 255, 0).getRGB();
} // TextLogHelper
