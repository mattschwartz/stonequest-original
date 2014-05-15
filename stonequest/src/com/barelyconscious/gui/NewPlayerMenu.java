/* *****************************************************************************
 * Project:           stonequest
 * File Name:         NewPlayerMenu.java
 * Author:            Matt Schwartz
 * Date Created:      05.14.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gui;

import com.barelyconscious.util.GUIHelper;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;

public class NewPlayerMenu extends Menu {

    private Widget rootWidget;
    private Label playerNameLabel;
    private EditField playerNameEditField;
    private Button backButton;
    private Button startGameButton;
    
    public NewPlayerMenu() {
        createWidgets();
        createRootWidget();
        registerHandlers();
    }

    @Override
    protected final void createWidgets() {
        playerNameLabel = new Label("Player Name");
        playerNameEditField = new EditField();
        backButton = new Button("Back");
        startGameButton = new Button("Start Game");
        
        GUIHelper.setSize(playerNameLabel, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameLabel, 0.5f, 0.5f, -150.0f, -40.0f);
        
        GUIHelper.setSize(playerNameEditField, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(playerNameEditField, 0.5f, 0.5f, 0.0f, -40.0f);
        
        GUIHelper.setSize(backButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(backButton, 0.5f, 0.5f, -150.0f, 0.0f);
        
        GUIHelper.setSize(startGameButton, 0.0f, 0.0f, 139.0f, 40.0f);
        GUIHelper.setPosition(startGameButton, 0.5f, 0.5f, 0.0f, 0.0f);
    }

    @Override
    protected final void createRootWidget() {
        rootWidget = new Widget();
        
        rootWidget.add(playerNameLabel);
        rootWidget.add(playerNameEditField);
        rootWidget.add(backButton);
        rootWidget.add(startGameButton);
    }

    @Override
    protected final void registerHandlers() {
        backButton.addCallback(new Runnable() {

            @Override
            public void run() {
                backEvent();
            }
        });
    }
    
    @Override
    public void show() {
        guiHandler.setRootWidget(rootWidget);
    }

    @Override
    public void hide() {
    }
    
    private void backEvent() {
        guiHandler.mainMenu.show();
    }

} // NewPlayerMenu
