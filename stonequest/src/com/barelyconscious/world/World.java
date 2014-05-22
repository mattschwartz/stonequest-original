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
import com.barelyconscious.gameobjects.GameObject;
import com.barelyconscious.gameobjects.LightManager;
import com.barelyconscious.gameobjects.ObjectManager;
import com.barelyconscious.gameobjects.PlayerObject;
import com.barelyconscious.gameobjects.UpdateEvent;
import com.barelyconscious.pcg.ZoneFactory;
import com.barelyconscious.util.Pair;
import java.awt.Rectangle;
import org.lwjgl.opengl.Display;

public class World {

    private static final World INSTANCE = new World();
    public static float TILE_WIDTH = 32;
    public static float TILE_HEIGHT = 32;

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
    
    public Rectangle getPlayerBoundingBox() {
        return playerObject.getBoundingBox();
    }

    public void spawnCurrentPlayer() {
        ObjectManager.getInstance().spawnObject(playerObject);
    }

    public void exitWorld() {
        ObjectManager.getInstance().removeAllObjects();
        LightManager.getInstance().removeAllLights();
    }

    public void render(UpdateEvent args) {
        currentZone.render(args);
    }

    public Pair<Float, Float> getShift() {
        float xShift = Display.getWidth() - playerObject.getBoundingBox().width;
        float yShift = Display.getHeight() - playerObject.getBoundingBox().height;

        xShift /= 2;
        yShift /= 2;

        xShift -= playerObject.getX();
        yShift -= playerObject.getY();

        return new Pair<>(xShift, yShift);
    }

    public void update(UpdateEvent args) {
        Pair<Float, Float> shift = getShift();

        currentZone.shift(shift.first, shift.second);
        currentZone.update(args);
    }

    public boolean canMove(GameObject object) {
        return ObjectManager.getInstance().canMove(object) && currentZone.canMove(object.getBoundingBox());
    }

} // World
