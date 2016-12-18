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

import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Item;

public class Loot extends Sprite {

    public static final UIElement LOOT_ICON = UIElement.createUIElement("/gfx/tiles/sprites/items/lootIcon.png");
    private Item item;
    private boolean pickupOnWalkOver;

    public Loot(Item item) {
        super(item.getName(), World.INSTANCE.getPlayer().getX(), World.INSTANCE.getPlayer().getY(), false, LOOT_ICON);
        this.item = item;
        pickupOnWalkOver = false;
    }

    public Loot(Item item, boolean pickupOnWalkOver) {
        super(item.getName(), World.INSTANCE.getPlayer().getX(), World.INSTANCE.getPlayer().getY(), false, LOOT_ICON);
        this.item = item;
        this.pickupOnWalkOver = pickupOnWalkOver;
    }

    public Loot(Item item, int x, int y, boolean pickupOnWalkOver) {
        super(item.getName(), x, y, false, LOOT_ICON);
        this.item = item;
        this.pickupOnWalkOver = pickupOnWalkOver;
    }

    @Override
    public void remove() {
        System.out.println("You pick up " + item.getName());
        super.remove();
    }

    @Override
    public void onWalkOver(Sprite interactee) {
        if (pickupOnWalkOver) {
            if (interactee instanceof Entity) {
                ((Entity) interactee).getInventory().addItem(item);
                remove();
            }
        }
        else {
            System.out.println("There is a " + item.getName() + " here.");
        }
    }
}
