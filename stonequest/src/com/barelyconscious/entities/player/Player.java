/* *****************************************************************************
   * Project:           stonequest
   * File Name:         Player.java
   * Author:            Matt Schwartz
   * Date Created:      05.11.2014 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
   *                    this file.  You are not allowed to take credit for code
   *                    that was not written fully by yourself, or to remove 
   *                    credit from code that was not written fully by yourself.  
   *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.entities.player;

import com.barelyconscious.entities.Entity;

public class Player extends Entity {
    
    private String name;
    private Cauldron cauldron;
    
    public Player(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
} // Player
