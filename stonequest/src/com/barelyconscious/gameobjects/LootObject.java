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
import com.barelyconscious.util.Pair;
import com.barelyconscious.world.World;
import org.newdawn.slick.Image;

public class LootObject extends GameObject {

    private Item item;
    private Image image;

    public LootObject(Item item, float x, float y) {
        this.item = item;
        this.x = x;
        this.y = y;
    } // constructor

    @Override
    public void render(UpdateEvent args) {
        Pair<Float, Float> shift = World.getInstance().getShift();
        image.draw(shift.first + x, shift.second + y);
    }

} // LootObject
