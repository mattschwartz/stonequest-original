/* *****************************************************************************
 * Project:           stonequest
 * File Name:         World.java
 * Author:            Matt Schwartz
 * Date Created:      05.16.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.world;

import com.barelyconscious.entities.player.Player;
import com.barelyconscious.gameobjects.ObjectManager;
import com.barelyconscious.gameobjects.PlayerObject;
import com.barelyconscious.gameobjects.UpdateEvent;
import com.barelyconscious.pcg.ZoneFactory;

public class World {

    private static final World INSTANCE = new World();

    private Player player;
    private PlayerObject playerObject;
    private Zone currentZone;
    private final ZoneFactory zoneFactory;

    private World() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }

        zoneFactory = ZoneFactory.getInstance();
    }

    public static World getInstance() {
        return INSTANCE;
    }
    
    public void setPlayer(Player player, boolean newPlayer) {
        this.player = player;
        playerObject = new PlayerObject(player, 0, 0);

        if (newPlayer) {
            loadZone(zoneFactory.getIntroductionZone());
        }
    }
    
    public void loadZone(Zone zone) {
        currentZone = zone;
    }

    public void spawnCurrentPlayer() {
        ObjectManager.getInstance().spawnObject(playerObject);
    }

    public void render(UpdateEvent args) {
        currentZone.render(args);
    }

    public void update(UpdateEvent args) {
        currentZone.shift(-playerObject.getX(), playerObject.getY());
        currentZone.update(args);
    }

} // World
