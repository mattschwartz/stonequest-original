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
import com.barelyconscious.util.ConsoleWriter;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class PlayerObject extends GameObject {

    private float x;
    private float y;
    private float walkSpeed = 0.3f;
    private final Player player;
    private SpriteSheet playerSheet;
    private Animation currentAnimation;
    private Animation animationWalkUp;
    private Animation animationWalkDown;
    private Animation animationWalkLeft;
    private Animation animationWalkRight;

    public PlayerObject(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
        
        loadAnimations();
    } // constructor

    private void loadAnimations() {
        Image[] images;

        try {
            playerSheet = new SpriteSheet("sprites/player.png", 32, 64);

            images = getRowImages(playerSheet, 0);
            animationWalkDown = new Animation(images, 100);

            images = getRowImages(playerSheet, 1);
            animationWalkUp = new Animation(images, 100);

            images = getRowImages(playerSheet, 2);
            animationWalkLeft = new Animation(images, 100);

            images = getRowImages(playerSheet, 3);
            animationWalkRight = new Animation(images, 100);
        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to load resource: " + ex);
        }

        currentAnimation = animationWalkDown;
    }

    public Image[] getRowImages(SpriteSheet sheet, int row) {
        Image[] images = new Image[sheet.getHorizontalCount()];

        for (int i = 0; i < images.length; i++) {
            images[i] = sheet.getSprite(i, row);
        }

        return images;
    }

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
        float oldX = x;
        float oldY = y;
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
        if (!idle) {
            currentAnimation.update(args.delta);
        }

        // if can't move here,
        // reset x and y
    } // update

    @Override
    public void render(UpdateEvent args) {
        currentAnimation.draw((Display.getWidth() - 32) / 2, (Display.getHeight() - 64) / 2);
    }

} // PlayerObject
