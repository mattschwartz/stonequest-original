/* *****************************************************************************
 * Project:           stonequest
 * File Name:         ClientBase.java
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ClientBase<T> extends StateBasedGame {

    private T gameData;

    public ClientBase(String name, T gameData) {
        super(name);
        this.gameData = gameData;
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
    }

    public void setData(T gameData) {
        this.gameData = gameData;
    }

    public T getData() {
        return gameData;
    }

} // ClientBase
