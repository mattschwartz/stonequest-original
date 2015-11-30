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

package com.barelyconscious.game.gameobjects;

import com.barelyconscious.game.spawnable.Entity;
import java.awt.Point;

public class EntityObject extends GameObject {
    
    protected Entity entity;
    
    public EntityObject(Entity entity, int x, int y) {
        this.entity = entity;
        super.position.x = x;
        super.position.y = y;
    } // constructor
    
    public EntityObject(Entity entity, Point position) {
        this.entity = entity;
        super.position = position;
    } // constructor

    @Override
    public void spawnObject() {
    } // spawnObject

    @Override
    public void tick(UpdateEvent args) {
    } // tick
} // EntityObject
