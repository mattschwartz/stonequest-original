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

public class LoadingMenuState extends MenuState {

    private ProgressBar progressBar;
    private DialogLayout tipPanel;
    private Panel titlePanel;
    private Label tipLabel;

    public LoadingMenuState(ClientBase<GameData> client, State state) {
        super(client, state);
    }

    @Override
    protected void createWidgets() {
        progressBar = new ProgressBar();
        tipPanel = new DialogLayout();

        tipPanel = new DialogLayout();
        tipPanel.setTheme("panel");

        tipLabel = new Label("Tips yo");

        tipPanel.setHorizontalGroup(tipPanel.createParallelGroup(tipLabel));
        tipPanel.setVerticalGroup(tipPanel.createParallelGroup(tipLabel));
    }

    @Override
    protected void addWidgets() {
        root.add(progressBar);
        root.add(tipPanel);
    }

    @Override
    protected void registerEvents() {
    }

    @Override
    protected void layoutRootPane() {
        GUIHelper.setSize(progressBar, 0.5f, 0.0f, 0.0f, 40.0f);
        GUIHelper.setPosition(progressBar, 0.25f, 1.0f, 0.0f, -40.0f);

        GUIHelper.setSize(tipPanel, 0.5f, 0.0f, 0.0f, 40.0f);
        GUIHelper.setPosition(tipPanel, 0.25f, 0.0f, 0.0f, 0.0f);
    }

} // LoadingMenuState
