/* *****************************************************************************
 * Project:           stonequest
 * File Name:         GameObject.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.util.ConsoleWriter;
import com.barelyconscious.world.World;
import java.awt.Rectangle;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class GameObject {

    protected float x;
    protected float y;
    private String filepath;
    protected int frameTime;
    protected boolean removeOnUpdate = false;
    protected Rectangle boundingBox;
    protected float renderX;
    protected float renderY;
    protected SpriteSheet itemFrames;
    protected Animation itemAnimation;
    
    public GameObject() {
    }
    
    public GameObject(String filepath, float x, float y) {
        this.filepath = filepath;
        this.x = x * World.TILE_WIDTH;
        this.y = y * World.TILE_HEIGHT;
    }

    public void spawnObject() {
        try {
            itemFrames = new SpriteSheet(new Image(filepath), 32, 32);
            itemAnimation = new Animation(itemFrames, frameTime);
            itemAnimation.setPingPong(true);
            
            boundingBox = new Rectangle((int) x, (int) y, itemAnimation.getWidth(), itemAnimation.getHeight());
        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to load resource: " + ex);
        }
    } // spawnObject

    public void render(UpdateEvent args) {
    } // render

    public void update(UpdateEvent args) {
        renderX = args.worldShiftX + x;
        renderY = args.worldShiftY + y;
    } // update
    
    public boolean intersects(Rectangle boundingBox) {
        return false;
    }
    
    public Rectangle getBoundingBox() {
        return new Rectangle(boundingBox);
    }
    
    public void setBoundingBox(Rectangle box) {
        boundingBox = box;
    }
    
    protected void remove() {
        removeOnUpdate = true;
    }

    public boolean shouldRemove() {
        return removeOnUpdate;
    } // shouldRemove

} // GameObject
