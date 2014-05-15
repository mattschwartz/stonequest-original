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

import TWLSlick.RootPane;
import com.barelyconscious.gamestate.ClientBase;
import com.barelyconscious.gamestate.GameData;
import com.barelyconscious.gamestate.GameStateBase;
import com.barelyconscious.gamestate.State;
import com.barelyconscious.util.GUIHelper;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class NewPlayerMenuState extends GameStateBase<GameData> {

    private RootPane root;
    private Label playerNameLabel;
    private EditField playerNameEditField;
    private Button backButton;
    private Button startGameButton;

    public NewPlayerMenuState(ClientBase<GameData> client, State gameState) {
        super(client, gameState);
    }

    @Override
    protected RootPane createRootPane() {
        root = super.createRootPane();

        createWidgets();
        registerEvents();

        return root;
    }

    private void createWidgets() {
        playerNameLabel = new Label("Player Name");
        playerNameEditField = new EditField();
        backButton = new Button("Back");
        startGameButton = new Button("Start Game");

        root.add(playerNameLabel);
        root.add(playerNameEditField);
        root.add(backButton);
        root.add(startGameButton);
        
        GUIHelper.setPosition(playerNameLabel, 2.0f, 2.0f, -150.0f, -40.0f);
        GUIHelper.setPosition(playerNameEditField, 2.0f, 2.0f, 0.0f, -40.0f);
        GUIHelper.setPosition(backButton, 2.0f, 2.0f, -150.0f, 0.0f);
        GUIHelper.setPosition(startGameButton, 2.0f, 2.0f, 0.0f, 0.0f);
    }

    private void registerEvents() {
        backButton.addCallback(new Runnable() {

            @Override
            public void run() {
                backEvent();
            }
        });
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(playerNameLabel, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameLabel, 0.5f, 0.5f, -150.0f, -40.0f);

        GUIHelper.setSize(playerNameEditField, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameEditField, 0.5f, 0.5f, 0.0f, -40.0f);

        GUIHelper.setSize(backButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(backButton, 0.5f, 0.5f, -150.0f, 0.0f);

        GUIHelper.setSize(startGameButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(startGameButton, 0.5f, 0.5f, 0.0f, 0.0f);
    }

    private void backEvent() {
        getClient().enterState(State.MAIN_MENU_STATE.getValue(), new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
    }
} // NewPlayerMenuState
