/* *****************************************************************************
 * Project:           stonequest
 * File Name:         InGameOptionsMenuState.java
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
import com.barelyconscious.world.World;
import de.matthiasmann.twl.Button;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class InGameOptionsMenuState extends MenuState {

    private Button continueButton;
    private Button exitToMenuButton;
    private Button exitGameButton;

    public InGameOptionsMenuState(ClientBase<GameData> client, State state) {
        super(client, state);
    }

    @Override
    protected void createWidgets() {
        continueButton = new Button("Continue Game");
        exitToMenuButton = new Button("Quit to Main Menu");
        exitGameButton = new Button("Exit Game");
    }

    @Override
    protected void addWidgets() {
        root.add(continueButton);
        root.add(exitToMenuButton);
        root.add(exitGameButton);
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(continueButton, 0.0f, 0.0f, 150.0f, 25.0f);
        GUIHelper.setPosition(continueButton, 0.0f, 0.5f, 75.0f, -45.0f);

        GUIHelper.setSize(exitToMenuButton, 0.0f, 0.0f, 150.0f, 25.0f);
        GUIHelper.setPosition(exitToMenuButton, 0.0f, 0.5f, 75.0f, -15.0f);

        GUIHelper.setSize(exitGameButton, 0.0f, 0.0f, 150.0f, 25.0f);
        GUIHelper.setPosition(exitGameButton, 0.0f, 0.5f, 75.0f, 15.0f);
    }

    @Override
    protected void registerEvents() {
        continueButton.addCallback(new Runnable() {

            @Override
            public void run() {
                continueEvent();
            }
        });
        exitToMenuButton.addCallback(new Runnable() {

            @Override
            public void run() {
                exitToMenuEvent();
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
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        Color oldColor = g.getColor();
        g.setColor(new Color(41f / 256f, 48f / 256f, 61f / 256f, 0.6f));
        g.fillRect(75, 0, 150, container.getHeight());
        g.setColor(oldColor);
    }

    private void continueEvent() {
        getClient().enterState(State.WORLD_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
    }

    private void exitToMenuEvent() {
        World.getInstance().exitWorld();
        ((SavingGameMenuState) getClient().getState(State.SAVING_GAME_MENU_STATE.getValue())).setReturnState(State.MAIN_MENU_STATE.getValue());
        getClient().enterState(State.SAVING_GAME_MENU_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
    }

    private void exitGameEvent() {
        getClient().enterState(State.SAVING_GAME_MENU_STATE.getValue());
    }

    @Override
    public void keyPressed(int key, char c) {
        if (key == Keyboard.KEY_ESCAPE) {
            getClient().enterState(State.WORLD_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
        }
    }

} // InGameOptionsMenuState
