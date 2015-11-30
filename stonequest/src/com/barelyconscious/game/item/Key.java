/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         Key.java
   * Author:            Matt Schwartz
   * Date Created:      03.17.2013 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  Keys are used to open locked Doodads such as chests and
                        doors.  
   ************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.spawnable.Entity;

public class Key extends Item {
    private static final UIElement KEY_ICON = UIElement.createUIElement("");
    private final int LOCK_ID;
    
    public Key(String name, int itemLevel, int sellValue, int lockId, Entity owner) {
        super(name, itemLevel, sellValue, 1, KEY_ICON, owner);
        LOCK_ID = lockId;
    } // constructor
    
    /**
     * 
     * @return the lock id that the key fits into
     */
    public int getLockId() {
        return LOCK_ID;
    } // getLockId

    /**
     * Keys do not stack with each other
     * @param item
     * @return 
     */
    @Override
    public int compareTo(Item item) {
        return -1;
    } // compareTo
} // Key
