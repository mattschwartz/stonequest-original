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

import java.awt.Rectangle;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected boolean removeOnUpdate = false;
    protected Rectangle boundingBox;

    public GameObject() {
    } // constructor

    public void spawnObject() {
    } // spawnObject

    public void render(UpdateEvent args) {
    } // render

    public void update(UpdateEvent args) {
    } // update
    
    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    public void setBoundingBox(Rectangle box) {
        boundingBox = box;
    }

    public boolean shouldRemove() {
        return removeOnUpdate;
    } // shouldRemove

} // GameObject
