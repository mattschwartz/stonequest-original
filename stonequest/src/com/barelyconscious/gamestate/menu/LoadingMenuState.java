/* *****************************************************************************
 * Project:           stonequest
 * File Name:         LoadingMenuState.java
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
import de.matthiasmann.twl.ColumnLayout.Panel;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ProgressBar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingMenuState extends MenuState {

    private boolean complete;
    private List<String> log;
    private ProgressBar progressBar;
    private DialogLayout tipPanel;
    private Panel titlePanel;
    private Label tipLabel;

    public LoadingMenuState(ClientBase<GameData> client, State state) {
        super(client, state);
        log = new ArrayList<>();
    }

    @Override
    protected void createWidgets() {
        progressBar = new ProgressBar();
        progressBar.setTheme("progressBar");
        tipPanel = new DialogLayout();

        tipPanel = new DialogLayout();
        tipPanel.setTheme("panel");

        tipLabel = new Label("Did you know these are tips, yo?");

        tipPanel.setHorizontalGroup(tipPanel.createParallelGroup(tipLabel));
        tipPanel.setVerticalGroup(tipPanel.createParallelGroup(tipLabel));
    }

    @Override
    protected void addWidgets() {
        root.add(progressBar);
        root.add(tipPanel);
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(progressBar, 0.5f, 0.0f, 0.0f, 40.0f);
        GUIHelper.setPosition(progressBar, 0.25f, 1.0f, 0.0f, -40.0f);

        GUIHelper.setSize(tipPanel, 0.5f, 0.0f, 0.0f, 40.0f);
        GUIHelper.setPosition(tipPanel, 0.25f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container, game, g);

        String loadMessage = "Loading resources... \n";

        for (String str : log) {
            loadMessage += str + "\n";
        }

        g.setColor(Color.black);
        g.drawString(loadMessage, 5, 151);
        g.drawString(loadMessage, 5, 149);
        g.drawString(loadMessage, 6, 150);
        g.drawString(loadMessage, 4, 150);
        g.setColor(Color.white);
        g.drawString(loadMessage, 5, 150);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        String logMessage = "";
        
        if (complete) {
            getClient().enterState(State.WORLD_STATE.getValue(), new FadeOutTransition(Color.black, 175), new FadeInTransition(Color.black, 175));
            return;
        }
        
        if (LoadingList.get().getRemainingResources() > 0) {
            try {
                DeferredResource nextResource = LoadingList.get().getNext();
                logMessage += "Loading " + nextResource.getDescription() + "... ";
                nextResource.load();
                logMessage += "done.";
            } catch (IOException ex) {
                logMessage += "FAILED: " + ex;
            }
        } else {
            logMessage += "Complete.";
            complete = true;
        }
        
        log.add(logMessage);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        complete = false;
        log.clear();
    }

} // LoadingMenuState