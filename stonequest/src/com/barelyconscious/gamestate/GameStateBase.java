/* *****************************************************************************
 * Project:           stonequest
 * File Name:         GameStateBase.java
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

import org.newdawn.slick.state.GameState;

public abstract class GameStateBase<T> implements GameState {

    private final ClientBase<T> client;
    private final States state;

    public GameStateBase(ClientBase<T> client, States gameState) {
        this.client = client;
        this.state = gameState;
    }

    protected ClientBase<T> getClient() {
        return client;
    }

    @Override
    public int getID() {
        return state.getValue();
    }

    @Override
    public boolean isAcceptingInput() {
        return client.getCurrentState() == this;
    }

} // GameStateBase
