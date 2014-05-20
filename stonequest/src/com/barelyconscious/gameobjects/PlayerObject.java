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
package com.barelyconscious.gameobjects;

import com.barelyconscious.entities.player.Player;
import com.barelyconscious.input.KeyMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class PlayerObject extends GameObject {

    private float x;
    private float y;
    private float walkSpeed = 0.5f;
    private final Player player;
    private Animation currentAnimation;
    private Animation animationWalkUp;
    private Animation animationWalkDown;
    private Animation animationWalkLeft;
    private Animation animationWalkRight;
    private Animation animationIdle;

    public PlayerObject(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    } // constructor

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void spawnObject() {
    } // spawnObject

    @Override
    public void update(UpdateEvent args) {
        boolean idle = true;
        Input input = args.gc.getInput();
        
        if (input.isKeyDown(KeyMap.playerMoveUp)) {
            y += walkSpeed * args.delta;
            currentAnimation = animationWalkUp;
            idle = false;
        }
        if (input.isKeyDown(KeyMap.playerMoveDown)) {
            y -= walkSpeed * args.delta;
            currentAnimation = animationWalkDown;
            idle = false;
        }
        if (input.isKeyDown(KeyMap.playerMoveLeft)) {
            x -= walkSpeed * args.delta;
            currentAnimation = animationWalkLeft;
            idle = false;
        }
        if (input.isKeyDown(KeyMap.playerMoveRight)) {
            x += walkSpeed * args.delta;
            currentAnimation = animationWalkRight;
            idle = false;
        }
        if (idle) {
            currentAnimation = animationIdle;
        }

//        currentAnimation.update(args.delta);
    } // update

    @Override
    public void render(UpdateEvent args) {
        Graphics g = args.g;
        
        g.drawString("Player position:\nx: " + x + "\ny: " + y, 10, 25);
//        currentAnimation.draw(x, y);
    }

} // PlayerObject
