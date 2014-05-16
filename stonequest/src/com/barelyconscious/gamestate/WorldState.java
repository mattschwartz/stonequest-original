/* *****************************************************************************
 * Project:           stonequest
 * File Name:         WorldState.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gamestate;

import com.barelyconscious.gameobjects.ObjectManager;
import com.barelyconscious.gameobjects.UpdateEvent;
import com.barelyconscious.input.KeyMap;
import com.barelyconscious.input.KeyboardArgs;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldState extends GameStateBase<GameData> {
    
    public WorldState(ClientBase<GameData> client, State state) {
        super(client, state);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("Welcome to the world.", 50, 50);
        UpdateEvent args = new UpdateEvent(gc, getClient(), g);
        ObjectManager.getInstance().render(args);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        UpdateEvent args = new UpdateEvent(gc, getClient(), delta);
        ObjectManager.getInstance().update(args);
    }

    @Override
    public void keyPressed(int key, char c) {
        KeyboardArgs args = new KeyboardArgs(getClient().getContainer(), getClient(), key, c);
        
        KeyMap.invoke(args);
    }
    
} // WorldState
