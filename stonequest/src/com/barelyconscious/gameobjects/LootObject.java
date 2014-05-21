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
import com.barelyconscious.util.ConsoleWriter;
import com.barelyconscious.world.World;
import java.awt.Rectangle;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class LootObject extends GameObject {

    private Item item;
    private SpriteSheet itemFrames;
    private Animation itemAnimation;
    private float renderX;
    private float renderY;
    private boolean itemUnderPlayer;
    private boolean mouseHoverOver;

    public LootObject(Item item, float x, float y) {
        this.item = item;
        this.x = x;
        this.y = y;
    } // constructor

    @Override
    public void spawnObject() {
        try {
            itemFrames = new SpriteSheet(new Image("sprites/items/potionBottle.png"), 32, 32);
            itemAnimation = new Animation(itemFrames, 300);
            itemAnimation.setPingPong(true);
            
            boundingBox = new Rectangle((int) x, (int) y, itemAnimation.getWidth(), itemAnimation.getHeight());
        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to load resource: " + ex);
        }
    }

    @Override
    public void update(UpdateEvent args) {
        renderX = args.worldShiftX + x;
        renderY = args.worldShiftY + y;
        
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
