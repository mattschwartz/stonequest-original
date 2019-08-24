/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        KeyMap.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use = reuse = and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself = or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Keeps track of the each adjustable keybind for the player.
                     Keybinds determine how the game should react when its 
                     corresponding key is pressed on the keyboard.  Two default
                     maps are available for quickloading in functions below.
 **************************************************************************** */

package com.barelyconscious.game.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public abstract class KeyMap {
    /** Integer constants as an enumerator for the various actions necessary to
        perform when a key is pressed. */
    public static enum Key {
        PLAYER_SKIP_TURN,
        PLAYER_MOVE_UP,
        PLAYER_MOVE_DOWN,
        PLAYER_MOVE_LEFT,
        PLAYER_MOVE_RIGHT,
        MENU_MOVE_UP,
        MENU_MOVE_DOWN,
        PLAYER_MOVE_LEFT_ALT,
        PLAYER_MOVE_RIGHT_ALT,
        PLAYER_MOVE_UP_ALT,
        PLAYER_MOVE_DOWN_ALT,
        TEXT_LOG_SCROLL_UP,
        TEXT_LOG_SCROLL_DOWN,
        CLEAR_FOCUS,
        MENU_SELECT,
        OPEN_HELP_MENU,
        OPEN_INVENTORY,
        OPEN_SKILL_TAB,
        PICKUP_ITEM,
        DROP_ITEM,
        USE_ITEM,
        EXAMINE_ITEM,
        SALVAGE_ITEM,
        ZONE_INFO
    }; // Key
    
    private static Map keyBinds = new HashMap<Integer, Integer>();
    
    public static final int CLASSIC_MAPPING = 0;
    public static final int MODERN_MAPPING = 1;
    
    /** 
     * Mapping similar to the classic Rogue.
     */
    private static void setClassicRogueMapping() {
        keyBinds.put(Key.PLAYER_SKIP_TURN, KeyEvent.VK_SPACE);
        keyBinds.put(Key.PLAYER_MOVE_UP, KeyEvent.VK_K);
        keyBinds.put(Key.PLAYER_MOVE_DOWN, KeyEvent.VK_J);
        keyBinds.put(Key.PLAYER_MOVE_LEFT, KeyEvent.VK_H);
        keyBinds.put(Key.PLAYER_MOVE_RIGHT, KeyEvent.VK_L);
        keyBinds.put(Key.MENU_MOVE_UP, KeyEvent.VK_UP);
        keyBinds.put(Key.MENU_MOVE_DOWN, KeyEvent.VK_DOWN);
        keyBinds.put(Key.PLAYER_MOVE_LEFT_ALT, KeyEvent.VK_LEFT);
        keyBinds.put(Key.PLAYER_MOVE_RIGHT_ALT, KeyEvent.VK_RIGHT);
        keyBinds.put(Key.PLAYER_MOVE_UP_ALT, KeyEvent.VK_UP);
        keyBinds.put(Key.PLAYER_MOVE_DOWN_ALT, KeyEvent.VK_DOWN);
        keyBinds.put(Key.TEXT_LOG_SCROLL_UP, KeyEvent.VK_PAGE_UP);
        keyBinds.put(Key.TEXT_LOG_SCROLL_DOWN, KeyEvent.VK_PAGE_DOWN);
        keyBinds.put(Key.CLEAR_FOCUS, KeyEvent.VK_ESCAPE);
        keyBinds.put(Key.MENU_SELECT, KeyEvent.VK_ENTER);
        keyBinds.put(Key.OPEN_HELP_MENU, (int)'?');
        keyBinds.put(Key.OPEN_INVENTORY, KeyEvent.VK_I);
        keyBinds.put(Key.OPEN_SKILL_TAB, KeyEvent.VK_S);
        keyBinds.put(Key.PICKUP_ITEM, KeyEvent.VK_COMMA);
        keyBinds.put(Key.DROP_ITEM, KeyEvent.VK_D);
        keyBinds.put(Key.USE_ITEM, KeyEvent.VK_E);
        keyBinds.put(Key.EXAMINE_ITEM, KeyEvent.VK_X);
        keyBinds.put(Key.SALVAGE_ITEM, KeyEvent.VK_S);
        keyBinds.put(Key.ZONE_INFO, KeyEvent.VK_TAB);
    } // setClassicRogueMapping
    
    /**
     * Move with WASD instead of HJKL.
     */
    private static void setModernMapping() {
        keyBinds.put(Key.PLAYER_SKIP_TURN, KeyEvent.VK_SPACE);
        keyBinds.put(Key.PLAYER_MOVE_UP, KeyEvent.VK_W);
        keyBinds.put(Key.PLAYER_MOVE_DOWN, KeyEvent.VK_S);
        keyBinds.put(Key.PLAYER_MOVE_LEFT, KeyEvent.VK_A);
        keyBinds.put(Key.PLAYER_MOVE_RIGHT, KeyEvent.VK_D);
        keyBinds.put(Key.MENU_MOVE_UP, KeyEvent.VK_UP);
        keyBinds.put(Key.MENU_MOVE_DOWN, KeyEvent.VK_DOWN);
        keyBinds.put(Key.PLAYER_MOVE_LEFT_ALT, KeyEvent.VK_LEFT);
        keyBinds.put(Key.PLAYER_MOVE_RIGHT_ALT, KeyEvent.VK_RIGHT);
        keyBinds.put(Key.PLAYER_MOVE_UP_ALT, KeyEvent.VK_UP);
        keyBinds.put(Key.PLAYER_MOVE_DOWN_ALT, KeyEvent.VK_DOWN);
        keyBinds.put(Key.TEXT_LOG_SCROLL_UP, KeyEvent.VK_PAGE_UP);
        keyBinds.put(Key.TEXT_LOG_SCROLL_DOWN, KeyEvent.VK_PAGE_DOWN);
        keyBinds.put(Key.CLEAR_FOCUS, KeyEvent.VK_ESCAPE);
        keyBinds.put(Key.MENU_SELECT, KeyEvent.VK_ENTER);
        keyBinds.put(Key.OPEN_HELP_MENU, (int)'?');
        keyBinds.put(Key.OPEN_INVENTORY, KeyEvent.VK_I);
        keyBinds.put(Key.OPEN_SKILL_TAB, KeyEvent.VK_K);
        keyBinds.put(Key.PICKUP_ITEM, KeyEvent.VK_P);
        keyBinds.put(Key.DROP_ITEM, KeyEvent.VK_D);
        keyBinds.put(Key.USE_ITEM, KeyEvent.VK_E);
        keyBinds.put(Key.EXAMINE_ITEM, KeyEvent.VK_X);
        keyBinds.put(Key.SALVAGE_ITEM, KeyEvent.VK_S);
        keyBinds.put(Key.ZONE_INFO, KeyEvent.VK_TAB);
    } // setModernMapping
    
    public static boolean isFocusKey(int key) {
        return (key == getKeyCode(Key.OPEN_INVENTORY)) || (key == getKeyCode(Key.OPEN_SKILL_TAB));
    } // isFocusKey
    
    /**
     * Sets the key gameMap to one of the preset maps defined as final integers
     * @param map the new preset gameMap
     */
    public static void changeMapping(int map) {
        if (map == MODERN_MAPPING) {
            setModernMapping();
        } // if
        
        else if (map == CLASSIC_MAPPING) {
            setClassicRogueMapping();
        } // if
    } // changeMapping
    
    /**
     * Returns what key the action is bound to
     * @param action the key action 
     * @return the keyboard key that the action is bound to
     */
    public static int getKeyCode(Key action) {
        return Integer.parseInt(keyBinds.get(action).toString());
    } // getKeyCode
    
    /**
     * Changes one key value pair to a new key value pair for a key binding; does
     * nothing if the given key value is not in the keymap
     * @param key the key binding to change
     * @param newBind the new value for the key binding
     */
    public static void changeKeyBind(int key, int newBind) {
        if (keybindExists(key)) {
            keyBinds.put(key, newBind);
        } // if
    } // changeKeyBind
    
    private static boolean keybindExists(int key) {
        for (Key k : Key.values()) {
            if (k.name().equals(key)) {
                return true;
            } // if
        } // for
        return false;
    } // keybindExists
} // KeyMap
