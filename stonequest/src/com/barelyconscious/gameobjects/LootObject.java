/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         LootObject.java
 * Author:            Matt Schwartz
 * Date Created:      05.09.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.items.Item;

public class LootObject extends GameObject {

    private Item item;

    public LootObject(Item item, int x, int y) {
        this.item = item;
    } // constructor

    @Override
    public void spawnObject() {
    } // spawnObject

    @Override
    public void update(UpdateEvent args) {
    } // update

} // LootObject
