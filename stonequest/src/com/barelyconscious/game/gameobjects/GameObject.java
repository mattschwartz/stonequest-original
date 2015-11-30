/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         GameObject.java
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

import java.awt.Point;

public class GameObject {
    protected boolean removeOnUpdate = false;
    protected Point position;
    
    public GameObject() {
    } // constructor
    
    public void spawnObject() {
    } // spawnObject
    
    public void tick(UpdateEvent args) {
    } // tick
    
    public boolean shouldRemove() {
        return removeOnUpdate;
    } // shouldRemove
} // GameObject
