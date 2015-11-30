/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         World.java
 * Author:            Matt Schwartz
 * Date Created:      05.09.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.world;

import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.player.Player;

public class World {

    public static final World INSTANCE = new World();

    private Player player;
    private Map currentMap;

    private World() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been instantiated.");
        } // if
    } // constructor

    public void setCurrentPlayer(Player player) {
        this.player = player;
    } // setCurrentPlayer
} // World
