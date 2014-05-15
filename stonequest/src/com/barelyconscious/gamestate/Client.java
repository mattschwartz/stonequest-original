/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Client.java
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

import com.barelyconscious.gamestate.menu.LoadingMenuState;
import com.barelyconscious.gamestate.menu.MainMenuState;
import com.barelyconscious.gamestate.menu.NewPlayerMenuState;
import java.net.URL;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Client extends ClientBase<GameData> {

    public Client(String name, GameData gameData) {
        super(name, gameData);
    }
    
    @Override
    protected URL getThemeURL() {
        return getClass().getResource("/theme/chutzpah.xml");
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new MainMenuState(this, State.MAIN_MENU_STATE));
        addState(new NewPlayerMenuState(this, State.NEW_PLAYER_MENU_STATE));
        addState(new WorldState(this, State.WORLD_STATE));
        addState(new LoadingMenuState(this, State.LOADING_MENU_STATE));
   }
    
} // Client
