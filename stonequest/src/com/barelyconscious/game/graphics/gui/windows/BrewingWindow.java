/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        BrewingWindow.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.graphics.gui.CloseWindowButton;
import com.barelyconscious.game.graphics.gui.InterfaceWindowButton;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.input.Interactable;

public class BrewingWindow extends Window implements ButtonAction {

    private final int MIX_BUTTON_OFFS_X = 26;
    private final int MIX_BUTTON_OFFS_Y = 206;
    private final int MIX_BUTTON_WIDTH = 106;
    private final int MIX_BUTTON_HEIGHT = 24;
    private static final UIElement BREWING_WINDOW_BACKGROUND = new UIElement("/gfx/gui/components/windows/brewing/background.png");
    private int animationY;
    private InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private Button brewPotionButton;
    
    public BrewingWindow() {
    }
    
    public BrewingWindow(int gameWidth, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        width = BREWING_WINDOW_BACKGROUND.getWidth();
        height = BREWING_WINDOW_BACKGROUND.getHeight();

        windowOffsX = (gameWidth - BREWING_WINDOW_BACKGROUND.getWidth()) / 2;
        windowOffsY = (artworkWindowOffsY - BREWING_WINDOW_BACKGROUND.getHeight()) / 2;

        windowButton = new InterfaceWindowButton(windowButtonX, windowButtonY, this, UIElement.BREWING_WINDOW_BUTTON);
        closeWindowButton = new CloseWindowButton(windowOffsX + BREWING_WINDOW_BACKGROUND.getWidth() - UIElement.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 19, windowOffsY + 10, this, UIElement.INTERFACE_WINDOW_CLOSE_BUTTON);

        brewPotionButton = new Button("Mix", windowOffsX + MIX_BUTTON_OFFS_X, windowOffsY + MIX_BUTTON_OFFS_Y, MIX_BUTTON_WIDTH, MIX_BUTTON_HEIGHT, true);
        brewPotionButton.setCallbackFunction(this);

        super.setRegion(windowOffsX + MIX_BUTTON_OFFS_X, windowOffsY + MIX_BUTTON_OFFS_Y, width, height);
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    } // constructor

    /**
     * Resize elements as necessary when the application is resized.
     *
     * @param artworkWindowOffsX the new windowOffsX position of the artwork
     * interface window
     * @param artworkWindowOffsY the new windowOffsY position of the artwork
     * interface window
     * @param windowButtonX the new windowOffsX position of the upgrade item
     * window's button
     * @param windowButtonY the new windowOffsY position of the upgrade item
     * window's button
     */
    public void resize(int gameWidth, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        windowOffsX = (gameWidth - BREWING_WINDOW_BACKGROUND.getWidth()) / 2;
        windowOffsY = (artworkWindowOffsY - BREWING_WINDOW_BACKGROUND.getHeight()) / 2;

        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        closeWindowButton.setX(windowOffsX + BREWING_WINDOW_BACKGROUND.getWidth() - UIElement.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 19);
        closeWindowButton.setY(windowOffsY + 10);

        brewPotionButton.setX(windowOffsX + MIX_BUTTON_OFFS_X);
        brewPotionButton.setY(windowOffsY + MIX_BUTTON_OFFS_Y);

        super.setRegion(windowOffsX, windowOffsY, width, height);
    } // resize

    @Override
    public void show() {
        super.show();
        animationY = 0 - BREWING_WINDOW_BACKGROUND.getHeight();
        closeWindowButton.setEnabled(true);
        brewPotionButton.setEnabled(true);
        super.addMouseListener(Interactable.Z_BACKGROUND);
    } // show

    @Override
    public final void hide() {
        super.hide();
        closeWindowButton.setEnabled(false);
        brewPotionButton.setEnabled(false);
        super.removeMouseListener();
    } // hide

    @Override
    public void action(Button buttonPressed) {
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            } // if
        } // if
    } // action

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            InterfaceDelegate.getInstance().clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible) {
                InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Brewing\nWindow");
            } // if
            else {
                InterfaceDelegate.getInstance().setTooltipText("Click to open\nthe Brewing\nWindow");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Brewing\nWindow");
        } // else if
        else if (caller == brewPotionButton) {
            InterfaceDelegate.getInstance().setTooltipText("Click to mix the\ningredients into\na potion");
        } // else if
    } // hoverOverAction

    @Override
    public void render(Screen screen) {
        windowButton.render(screen);

        if (!isVisible) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (screen.getVisibleHeight() * FALL_RATE), windowOffsY);
        
        BREWING_WINDOW_BACKGROUND.render(screen, windowOffsX, animationY);

        if (animationY == windowOffsY) {
            closeWindowButton.render(screen);
            brewPotionButton.render(screen);
        } // if
    } // render
} // BrewingWindow
