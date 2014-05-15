/* *****************************************************************************
 * Project:           stonequest
 * File Name:         MainMenu.java
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
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.Label;

public class MainMenu extends Menu {

    private Widget rootWidget;
    private Label titleLabel;
    private Button newPlayerButton;
    private Button loadPlayerButton;
    private Button optionsButton;
    private Button exitGameButton;

    public MainMenu() {
        createWidgets();
        createRootWidget();
        registerHandlers();
    }

    @Override
    protected final void createWidgets() {
        titleLabel = new Label("StoneQuest");
        newPlayerButton = new Button("New Player");
        loadPlayerButton = new Button("Load Player");
        optionsButton = new Button("Options");
        exitGameButton = new Button("Exit Game");

        GUIHelper.setSize(titleLabel, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(titleLabel, 0.0f, 0.0f, 0.0f, 0.0f);
        
        GUIHelper.setSize(newPlayerButton, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(newPlayerButton, 0.5f, 0.5f, -75.0f, -80.0f);

        GUIHelper.setSize(loadPlayerButton, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(loadPlayerButton, 0.5f, 0.5f, -75.0f, -40.0f);

        GUIHelper.setSize(optionsButton, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(optionsButton, 0.5f, 0.5f, -75.0f, 0.0f);

        GUIHelper.setSize(exitGameButton, 0.0f, 0.0f, 150.0f, 40.0f);
        GUIHelper.setPosition(exitGameButton, 0.5f, 0.5f, -75.0f, 40.0f);
    }

    @Override
    protected final void createRootWidget() {
        rootWidget = new Widget();

        rootWidget.add(newPlayerButton);
        rootWidget.add(loadPlayerButton);
        rootWidget.add(optionsButton);
        rootWidget.add(exitGameButton);
    }

    @Override
    protected final void registerHandlers() {
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
    public final void show() {
        guiHandler.setRootWidget(rootWidget);
    }

    @Override
    public final void hide() {
        guiHandler.setRootWidget(null);
    }

    private void newPlayerEvent() {
        guiHandler.newPlayerMenu.show();
    }

    private void loadPlayerEvent() {

    }

    private void optionsEvent() {

    }

    private void exitGameEvent() {
        guiHandler.quitGame();
    }

} // MainMenu
