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

import TWLSlick.RootPane;
import com.barelyconscious.gameobjects.LightManager;
import com.barelyconscious.gameobjects.ObjectManager;
import com.barelyconscious.gameobjects.UpdateEvent;
import com.barelyconscious.input.KeyMap;
import com.barelyconscious.input.KeyboardArgs;
import com.barelyconscious.util.GUIHelper;
import com.barelyconscious.world.World;
import de.matthiasmann.twl.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class WorldState extends GameStateBase<GameData> {

    private RootPane root;
    private Button optionsMenuButton;

    public WorldState(ClientBase<GameData> client, State state) {
        super(client, state);
    }

    @Override
    protected RootPane createRootPane() {
        root = super.createRootPane();

        createWidgets();
        addWidgets();
        registerEvents();

        return root;
    }

    private void createWidgets() {
        optionsMenuButton = new Button("Options");
    }

    private void addWidgets() {
        root.add(optionsMenuButton);
    }

    private void registerEvents() {
        optionsMenuButton.addCallback(new Runnable() {

            @Override
            public void run() {
                optionsMenuEvent();
            }
        });
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(optionsMenuButton, 0.0f, 0.0f, 150.0f, 25.0f);
        GUIHelper.setPosition(optionsMenuButton, 1.0f, 0.0f, -150.0f, 0.0f);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        UpdateEvent args = new UpdateEvent(gc, getClient(), g);

        World.getInstance().render(args);
        ObjectManager.getInstance().render(args);
        Color oldColor = g.getColor();
        LightManager.getInstance().render(args);
        g.setColor(oldColor);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        UpdateEvent args = new UpdateEvent(gc, getClient(), delta);
        World.getInstance().update(args);
        ObjectManager.getInstance().update(args);
    }

    @Override
    public void keyPressed(int key, char c) {
        KeyboardArgs args = new KeyboardArgs(getClient().getContainer(), getClient(), key, c);

        KeyMap.invoke(args);
    }

    private void optionsMenuEvent() {
        getClient().enterState(State.IN_GAME_OPTIONS_MENU_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
    }

} // WorldState
