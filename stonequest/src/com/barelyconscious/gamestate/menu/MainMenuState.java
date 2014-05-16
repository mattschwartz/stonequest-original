/* *****************************************************************************
 * Project:           stonequest
 * File Name:         MainMenuState.java
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
import com.barelyconscious.util.GUIHelper;
import de.matthiasmann.twl.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenuState extends MenuState {

    private Button newPlayerButton;
    private Button loadPlayerButton;
    private Button optionsButton;
    private Button exitGameButton;
    
    public MainMenuState(ClientBase<GameData> client, State state) {
        super(client, state);
    }
    
    @Override
    protected void createWidgets() {
        newPlayerButton = new Button("New Player");
        loadPlayerButton = new Button("Load Player");
        optionsButton = new Button("Options");
        exitGameButton = new Button("Exit Game");
        
        newPlayerButton.setTheme("button_right");
        loadPlayerButton.setTheme("button_right");
        optionsButton.setTheme("button_right");
        exitGameButton.setTheme("button_right");
    }

    @Override
    protected void addWidgets() {
        root.add(newPlayerButton);
        root.add(loadPlayerButton);
        root.add(optionsButton);
        root.add(exitGameButton);
    }
    
    @Override
    protected void registerEvents() {
        newPlayerButton.addCallback(new Runnable() {

            @Override
            public void run() {
                newPlayerEvent();
            }
        });
        loadPlayerButton.addCallback(new Runnable() {

            @Override
            public void run() {
                loadPlayerEvent();
            }
        });
        optionsButton.addCallback(new Runnable() {

            @Override
            public void run() {
                optionsEvent();
            }
        });
        exitGameButton.addCallback(new Runnable() {

            @Override
            public void run() {
                exitGameEvent();
            }
        });
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(newPlayerButton, 0.0f, 0.0f, 300.0f, 25.0f);
        GUIHelper.setPosition(newPlayerButton, 1.0f, 1.0f, -300.0f, -100.0f);

        GUIHelper.setSize(loadPlayerButton, 0.0f, 0.0f, 250.0f, 25.0f);
        GUIHelper.setPosition(loadPlayerButton, 1.0f, 1.0f, -250.0f, -75.0f);

        GUIHelper.setSize(optionsButton, 0.0f, 0.0f, 200.0f, 25.0f);
        GUIHelper.setPosition(optionsButton, 1.0f, 1.0f, -200.0f, -50.0f);

        GUIHelper.setSize(exitGameButton, 0.0f, 0.0f, 150.0f, 25.0f);
        GUIHelper.setPosition(exitGameButton, 1.0f, 1.0f, -150.0f, -25.0f);
    }

    private void newPlayerEvent() {
        getClient().enterState(State.NEW_PLAYER_MENU_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
    }

    private void loadPlayerEvent() {

    }

    private void optionsEvent() {

    }

    private void exitGameEvent() {
        getClient().enterState(State.SAVING_GAME_MENU_STATE.getValue());
    }

} // MainMenuState
