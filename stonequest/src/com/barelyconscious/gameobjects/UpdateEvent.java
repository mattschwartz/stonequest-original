/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         UpdateEvent.java
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

import com.barelyconscious.gamestate.ClientBase;
import com.barelyconscious.util.Pair;
import com.barelyconscious.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class UpdateEvent {

    public GameContainer gc;
    public ClientBase client;
    public Graphics g;
    public int delta;
    public float mouseX;
    public float mouseY;
    public float mouseInWorldX;
    public float mouseInWorldY;
    public float worldShiftX;
    public float worldShiftY;

    public UpdateEvent() {
        findMousePosition();
    }

    public UpdateEvent(GameContainer container, ClientBase client, int delta) {
        this.gc = container;
        this.client = client;
        this.delta = delta;
        findMousePosition();
    }

    public UpdateEvent(GameContainer container, ClientBase client, Graphics g) {
        this.gc = container;
        this.client = client;
        this.g = g;
        findMousePosition();
    }

    private void findMousePosition() {
        Pair<Float, Float> shift = World.getInstance().getShift();
        
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();

        worldShiftX = shift.first;
        worldShiftY = shift.second;
        mouseInWorldX = mouseX - worldShiftX;
        mouseInWorldY = mouseY - worldShiftY;
    }

} // UpdateEvent
