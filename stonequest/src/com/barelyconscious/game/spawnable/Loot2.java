/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Loot2.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Item;

public class Loot2 extends Sprite {

    public static final UIElement LOOT_ICON = new UIElement("/gfx/tiles/sprites/items/lootIcon.png");
    private Item item;
    private boolean pickupOnWalkOver;

    public Loot2(Item item) {
        super(item.getName(), Game.getCurrentPlayer().getX(), Game.getCurrentPlayer().getY(), false, LOOT_ICON);
        this.item = item;
        pickupOnWalkOver = false;
    } // constructor

    public Loot2(Item item, boolean pickupOnWalkOver) {
        super(item.getName(), Game.getCurrentPlayer().getX(), Game.getCurrentPlayer().getY(), false, LOOT_ICON);
        this.item = item;
        this.pickupOnWalkOver = pickupOnWalkOver;
    } // constructor

    public Loot2(Item item, int x, int y, boolean pickupOnWalkOver) {
        super(item.getName(), x, y, false, LOOT_ICON);
        this.item = item;
        this.pickupOnWalkOver = pickupOnWalkOver;
    } // constructor

    @Override
    public void remove() {
        System.out.println("You pick up " + item.getName());
        super.remove();
    } // remove

    @Override
    public void onWalkOver(Sprite interactee) {
        if (pickupOnWalkOver) {
            if (interactee instanceof Entity) {
                ((Entity) interactee).getInventory().addItem(item);
                remove();
            } // if
        } // if
        else {
            System.out.println("There is a " + item.getName() + " here.");
        } // else
    } // onWalkOver
} // Loot2
