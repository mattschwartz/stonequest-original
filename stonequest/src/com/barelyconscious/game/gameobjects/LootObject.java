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
package com.barelyconscious.game.gameobjects;

import com.barelyconscious.game.item.Item;
import java.awt.Point;

public class LootObject extends GameObject {

    private Item item;

    public LootObject(Item item, int x, int y) {
        this.item = item;
        super.position.x = x;
        super.position.y = y;
    } // constructor

    public LootObject(Item item, Point position) {
        this.item = item;
        this.position = position;
    } // constructor

    @Override
    public void spawnObject() {
    } // spawnObject

    @Override
    public void tick(UpdateEvent args) {
    } // tick
} // LootObject
