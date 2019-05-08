/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         MainMenu.java
 * Author:            Matt Schwartz
 * Date Created:      01.22.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.gui.BetterButton;
import com.barelyconscious.game.graphics.gui.ButtonCallback;
import com.barelyconscious.game.graphics.gui.Label;
import com.barelyconscious.game.services.SceneService;

public class MainMenu extends Menu {

    private Label titleLabel;
    private BetterButton newPlayerButton;
    private BetterButton loadPlayerButton;
    private BetterButton optionsButton;
    private BetterButton quitGameButton;

    public MainMenu() {
        createWidgets();
        hide();
    }

    private void createWidgets() {
        ButtonCallback newPlayerAction;
        ButtonCallback loadPlayerAction;
        ButtonCallback optionsAction;
        ButtonCallback quitGameAction;

        newPlayerAction = new ButtonCallback() {
            @Override
            public void action(BetterButton buttonPressed) {
                newPlayerEvent();
            }

            @Override
            public void hoverOverAction(BetterButton caller) {
            }
        };
        loadPlayerAction = new ButtonCallback() {
            @Override
            public void action(BetterButton buttonPressed) {
                loadPlayerEvent();
            }

            @Override
            public void hoverOverAction(BetterButton caller) {
            }
        };
        optionsAction = new ButtonCallback() {
            @Override
            public void action(BetterButton buttonPressed) {
                optionsEvent();
            }

            @Override
            public void hoverOverAction(BetterButton caller) {
            }
        };
        quitGameAction = new ButtonCallback() {
            @Override
            public void action(BetterButton buttonPressed) {
                quitGameEvent();
            }

            @Override
            public void hoverOverAction(BetterButton caller) {
            }
        };
        
        titleLabel = new Label("StoneQuest", 150, 25);
        newPlayerButton = new BetterButton(newPlayerAction, "New Player");
        loadPlayerButton = new BetterButton(loadPlayerAction, "Load Player");
        optionsButton = new BetterButton(optionsAction, "Options");
        quitGameButton = new BetterButton(quitGameAction, "Quit Game");
        
        addComponent(titleLabel);
        addComponent(newPlayerButton);
        addComponent(loadPlayerButton);
        addComponent(optionsButton);
        addComponent(quitGameButton);
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        super.resize(newWidth, newHeight);
        titleLabel.setPosition(0.5f, -75, 0.5f, -85.0f);
        
        newPlayerButton.setPosition(0.5f, -75, 0.5f, -50.0f);
        newPlayerButton.setSize(0.0f, 150.0f, 0.0f, 25.0f);
        
        loadPlayerButton.setPosition(0.5f, -75, 0.5f, -25.0f);
        loadPlayerButton.setSize(0.0f, 150.0f, 0.0f, 25.0f);
        
        optionsButton.setPosition(0.5f, -75, 0.5f, 0.0f);
        optionsButton.setSize(0.0f, 150.0f, 0.0f, 25.0f);
        
        quitGameButton.setPosition(0.5f, -75, 0.5f, 25.0f);
        quitGameButton.setSize(0.0f, 150.0f, 0.0f, 25.0f);
    }

    private void newPlayerEvent() {
        SceneService.INSTANCE.setMenu(SceneService.newPlayerMenu);
    }

    private void loadPlayerEvent() {
    }
    
    private void optionsEvent() {
    }

    private void quitGameEvent() {
        Game.stop();
    }
}
