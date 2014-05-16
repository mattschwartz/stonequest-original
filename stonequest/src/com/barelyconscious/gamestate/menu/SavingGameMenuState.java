/* *****************************************************************************
 * Project:           stonequest
 * File Name:         SavingGameMenuState.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gamestate.menu;

import com.barelyconscious.gamestate.ClientBase;
import com.barelyconscious.gamestate.GameData;
import com.barelyconscious.gamestate.State;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SavingGameMenuState extends MenuState {

    private boolean done = false;
    private GameState returnState;

    public SavingGameMenuState(ClientBase<GameData> client, State state) {
        super(client, state);
    }
    
    public void setReturnState(int stateId) {
        returnState = getClient().getState(stateId);
    }

    public void setReturnState(GameState state) {
        returnState = state;
    }

    @Override
    protected void createWidgets() {
    }

    @Override
    protected void addWidgets() {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        done = false;
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        returnState = null;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        if (done) {
            if (returnState == null) {
                getClient().getContainer().exit();
            } else {
                getClient().enterState(returnState.getID(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
            }
        }
        
        done = true;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        String saveMessage = "Saving game...";
        
        super.render(container, game, g);
        
        g.setColor(Color.black);
        g.drawString(saveMessage, 5, 151);
        g.drawString(saveMessage, 5, 149);
        g.drawString(saveMessage, 4, 150);
        g.drawString(saveMessage, 6, 150);
        g.setColor(Color.white);
        g.drawString(saveMessage, 5, 150);
    }
    
} // SavingGameMenuState
