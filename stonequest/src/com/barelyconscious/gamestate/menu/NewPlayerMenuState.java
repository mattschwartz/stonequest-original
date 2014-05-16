/* *****************************************************************************
 * Project:           stonequest
 * File Name:         NewPlayerMenuState.java
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
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class NewPlayerMenuState extends MenuState {

    private DialogLayout playerNameText;
    private EditField playerNameEditField;
    private Button backButton;
    private Button startGameButton;

    public NewPlayerMenuState(ClientBase<GameData> client, State gameState) {
        super(client, gameState);
    }

    @Override
    protected void createWidgets() {
        playerNameText = new DialogLayout();
        playerNameText.setTheme("panel");
        playerNameEditField = new EditField();
        backButton = new Button("Back");
        startGameButton = new Button("Start Game");
        
        Label label1 = new Label("Player Name");
        
        playerNameText.setHorizontalGroup(playerNameText.createParallelGroup(label1));
        playerNameText.setVerticalGroup(playerNameText.createParallelGroup(label1));
        
        GUIHelper.setPosition(playerNameText, 2.0f, 2.0f, -150.0f, -40.0f);
        GUIHelper.setPosition(playerNameEditField, 2.0f, 2.0f, 0.0f, -40.0f);
        GUIHelper.setPosition(backButton, 2.0f, 2.0f, -150.0f, 0.0f);
        GUIHelper.setPosition(startGameButton, 2.0f, 2.0f, 0.0f, 0.0f);
    }

    @Override
    protected void addWidgets() {
        root.add(playerNameText);
        root.add(playerNameEditField);
        root.add(backButton);
        root.add(startGameButton);
    }

    @Override
    protected void registerEvents() {
        backButton.addCallback(new Runnable() {

            @Override
            public void run() {
                backEvent();
            }
        });
        startGameButton.addCallback(new Runnable() {

            @Override
            public void run() {
                startGameEvent();
            }
        });
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(playerNameText, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameText, 0.5f, 0.5f, -150.0f, -40.0f);

        GUIHelper.setSize(playerNameEditField, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameEditField, 0.5f, 0.5f, 0.0f, -40.0f);

        GUIHelper.setSize(backButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(backButton, 0.5f, 0.5f, -150.0f, 0.0f);

        GUIHelper.setSize(startGameButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(startGameButton, 0.5f, 0.5f, 0.0f, 0.0f);
    }
    
    private void startGameEvent() {
        getClient().enterState(State.LOADING_MENU_STATE.getValue());
    }

    private void backEvent() {
        getClient().enterState(State.MAIN_MENU_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
    }
} // NewPlayerMenuState
