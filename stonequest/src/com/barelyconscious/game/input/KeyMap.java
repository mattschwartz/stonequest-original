/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        KeyMap.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use = reuse = and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself = or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: Contains the default keymapping for the game.  
 **************************************************************************** */

package com.barelyconscious.game.input;

import java.awt.event.KeyEvent;

public abstract class KeyMap {
    // Left  = H
    // Right = L
    // Up    = K
    // Down  = J
    public static int PLAYER_SKIP_TURN;
    public static int PLAYER_MOVE_UP;
    public static int PLAYER_MOVE_DOWN;
    public static int PLAYER_MOVE_LEFT;
    public static int PLAYER_MOVE_RIGHT;
    public static int MENU_MOVE_UP;
    public static int MENU_MOVE_DOWN;
    public static int PLAYER_MOVE_LEFT_ALT;
    public static int PLAYER_MOVE_RIGHT_ALT;
    public static int TEXT_LOG_SCROLL_UP;
    public static int TEXT_LOG_SCROLL_DOWN;
    public static int MENU_CLEAR_FOCUS;
    public static int MENU_SELECT;
    public static int OPEN_HELP_MENU;
    public static int OPEN_INVENTORY;
    public static int OPEN_SKILL_TAB;
    public static int PICKUP_ITEM;
    public static int DROP_ITEM;
    public static int USE_ITEM;
    public static int EXAMINE_ITEM;
    public static int SALVAGE_ITEM;
    
    public static final int CLASSIC_MAPPING = 0;
    public static final int MODERN_MAPPING = 1;
    
    /** 
     * Mapping similar to the classic Rogue.
     */
    private static void setClassicRogueMapping() {
        PLAYER_SKIP_TURN = KeyEvent.VK_SPACE;
        PLAYER_MOVE_UP = KeyEvent.VK_K;
        PLAYER_MOVE_DOWN = KeyEvent.VK_J;
        PLAYER_MOVE_LEFT = KeyEvent.VK_H;
        PLAYER_MOVE_RIGHT = KeyEvent.VK_L;
        MENU_MOVE_UP = KeyEvent.VK_UP;
        MENU_MOVE_DOWN = KeyEvent.VK_DOWN;
        PLAYER_MOVE_LEFT_ALT = KeyEvent.VK_LEFT;
        PLAYER_MOVE_RIGHT_ALT = KeyEvent.VK_RIGHT;
        TEXT_LOG_SCROLL_UP = KeyEvent.VK_PAGE_UP;
        TEXT_LOG_SCROLL_DOWN = KeyEvent.VK_PAGE_DOWN;
        MENU_CLEAR_FOCUS = KeyEvent.VK_ESCAPE;
        MENU_SELECT = KeyEvent.VK_ENTER;
        OPEN_HELP_MENU = '?';
        OPEN_INVENTORY = KeyEvent.VK_I;
        OPEN_SKILL_TAB = KeyEvent.VK_S;
        PICKUP_ITEM = KeyEvent.VK_COMMA;
        DROP_ITEM = KeyEvent.VK_D;
        USE_ITEM = KeyEvent.VK_E;
        EXAMINE_ITEM = KeyEvent.VK_X;
        SALVAGE_ITEM = KeyEvent.VK_S;
    } // setClassicRogueMapping
    
    /**
     * Move with WASD instead of HJKL.
     */
    private static void setModernMapping() {
        PLAYER_SKIP_TURN = KeyEvent.VK_SPACE;
        PLAYER_MOVE_UP = KeyEvent.VK_W;
        PLAYER_MOVE_DOWN = KeyEvent.VK_S;
        PLAYER_MOVE_LEFT = KeyEvent.VK_A;
        PLAYER_MOVE_RIGHT = KeyEvent.VK_D;
        MENU_MOVE_UP = KeyEvent.VK_UP;
        MENU_MOVE_DOWN = KeyEvent.VK_DOWN;
        PLAYER_MOVE_LEFT_ALT = KeyEvent.VK_LEFT;
        PLAYER_MOVE_RIGHT_ALT = KeyEvent.VK_RIGHT;
        TEXT_LOG_SCROLL_UP = KeyEvent.VK_PAGE_UP;
        TEXT_LOG_SCROLL_DOWN = KeyEvent.VK_PAGE_DOWN;
        MENU_CLEAR_FOCUS = KeyEvent.VK_ESCAPE;
        MENU_SELECT = KeyEvent.VK_ENTER;
        OPEN_HELP_MENU = '?';
        OPEN_INVENTORY = KeyEvent.VK_I;
        OPEN_SKILL_TAB = KeyEvent.VK_K;
        PICKUP_ITEM = KeyEvent.VK_P;
        DROP_ITEM = KeyEvent.VK_D;
        USE_ITEM = KeyEvent.VK_E;
        EXAMINE_ITEM = KeyEvent.VK_X;
        SALVAGE_ITEM = KeyEvent.VK_S;
    } // setModernMapping
    
    /**
     * Change the entire key map to map.
     * @param map 
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
     * Change key key to newBind.
     * @param key
     * @param newBind 
     */
    public static void changeKeyBind(int key, int newBind) {
        
    } // changeKeyBind
} // KeyMap