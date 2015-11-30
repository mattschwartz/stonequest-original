/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         PlayerObject.java
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

import com.barelyconscious.game.player.Player;
import java.awt.Point;

public class PlayerObject extends GameObject {

    private Player player;
    
    public PlayerObject(Player player, int x, int y) {
        this.player = player;
    } // constructor
    
    public PlayerObject(Player player, Point position) {
        this.player = player;
        this.position = position;
    } // constructor
    
    @Override
    public void spawnObject() {
    } // spawnObject
    
    @Override
    public void tick(UpdateEvent args) {
    } // tick
} // PlayerObject
