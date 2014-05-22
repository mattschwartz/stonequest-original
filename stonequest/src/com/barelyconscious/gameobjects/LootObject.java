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

import com.barelyconscious.input.KeyMap;
import com.barelyconscious.items.Item;
import com.barelyconscious.world.World;
import java.awt.Rectangle;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class LootObject extends GameObject {

    private final Item item;
    private boolean itemUnderPlayer;
    private boolean mouseHoverOver;

    public LootObject(Item item, float x, float y) {
        super("sprites/items/potionBottle.png", x, y);
        this.item = item;
        frameTime = 300;
    } // constructor

    @Override
    public void update(UpdateEvent args) {
        super.update(args);

        itemUnderPlayer = underPlayer();
        mouseHoverOver = isMouseOnItem(args.mouseInWorldX, args.mouseInWorldY);
        itemAnimation.update(args.delta);

        if (itemUnderPlayer && Keyboard.isKeyDown(KeyMap.pickupItemKey)) {
            remove();
        }
    }

    @Override
    public void render(UpdateEvent args) {
        if (itemUnderPlayer) {
            args.g.drawString("[P] ick up item.", 0, 0);
        }

        itemAnimation.draw(renderX, renderY);

        if (mouseHoverOver) {
            Color oldColor = args.g.getColor();
            args.g.setColor(new Color(1.0f, 1.0f, 0.0f, 0.2f));
            args.g.fillRect(renderX, renderY, itemAnimation.getWidth(), itemAnimation.getHeight());
            args.g.setColor(oldColor);
            args.g.drawString(item.getName(), args.mouseX, args.mouseY - 25);
        }
    }

    private boolean isMouseOnItem(float mouseX, float mouseY) {
        return boundingBox.contains((int) mouseX, (int) mouseY);
    }

    private boolean underPlayer() {
        Rectangle playerBounds = World.getInstance().getPlayerBoundingBox();

        playerBounds.height = itemAnimation.getHeight();
        playerBounds.y += playerBounds.height;

        return boundingBox.intersects(playerBounds);
    }

} // LootObject
