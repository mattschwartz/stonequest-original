/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         EntityObject.java
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

import com.barelyconscious.entities.Entity;

public class EntityObject extends GameObject {
    
    protected Entity entity;
    
    public EntityObject(Entity entity, float x, float y) {
        this.entity = entity;
        this.x = x;
        this.y = y;
    } // constructor

    @Override
    public void spawnObject() {
    } // spawnObject

    @Override
    public void update(UpdateEvent args) {
    } // update
} // EntityObject
