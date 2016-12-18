/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      EntityHelper.java
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

public class EntityHelper {

    public static final int PHYSICAL_DAMAGE_TYPE = 0;
    public static final int MAGIC_DAMAGE_TYPE = 1;
    public static final int TOXIN_DAMAGE_TYPE = 2;
    public static final int CURSE_DAMAGE_TYPE = 3;

    public static String damageTypeToString(int damageType) {
        switch (damageType) {
            case PHYSICAL_DAMAGE_TYPE:
                return "Physical";
            case MAGIC_DAMAGE_TYPE:
                return "Magic";
            case TOXIN_DAMAGE_TYPE:
                return "Infection";
            case CURSE_DAMAGE_TYPE:
                return "Infliction";
            default:
                return "???";
        }
    }
}
