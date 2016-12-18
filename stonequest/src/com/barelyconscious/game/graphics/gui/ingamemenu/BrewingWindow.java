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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Cauldron;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.WindowManager;

public class BrewingWindow extends Window implements ButtonAction {

    private final int MIX_BUTTON_OFFS_X = 26;
    private final int MIX_BUTTON_OFFS_Y = 206;
    private final int MIX_BUTTON_WIDTH = 106;
    private final int MIX_BUTTON_HEIGHT = 24;
    private final int NUM_INGREDIENT_SLOTS = 4;
    private final int INGREDIENT_OFFS_X = 31;
    private final int INGREDIENT_OFFS_Y = 97;
    private final int INGREDIENT_STEP_X = 49;
    private final int INGREDIENT_STEP_Y = 50;
    private final int RESULT_OFFS_X = 145;
    private final int RESULT_OFFS_Y = 121;
    private int animationY;
    private Cauldron cauldron;
    private BrewingSlotArea[] ingredientSlots = new BrewingSlotArea[NUM_INGREDIENT_SLOTS];
    private BrewingSlotArea resultSlot;
    private static final UIElement BREWING_WINDOW_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/windows/brewing/background.png");
    protected InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private Button brewPotionButton;

    public BrewingWindow() {
        setWidth(BREWING_WINDOW_BACKGROUND.getWidth());
        setHeight(BREWING_WINDOW_BACKGROUND.getHeight());

        createButtons();
        createItemSlots();
        resizeItemSlots();

        super.setRegion(getX() + MIX_BUTTON_OFFS_X, getY() + MIX_BUTTON_OFFS_Y, getWidth(), getHeight());
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    }
    
    private void createButtons() {
        closeWindowButton = new CloseWindowButton(this, WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON);

        brewPotionButton = new Button("Mix", getY() + MIX_BUTTON_OFFS_X, getY() + MIX_BUTTON_OFFS_Y, MIX_BUTTON_WIDTH, MIX_BUTTON_HEIGHT, true);
        brewPotionButton.setCallbackFunction(this);
    }

    /**
     * Resize elements as necessary when the application is resized.
     *
     */
    public void resize() {
        setX((SceneService.INSTANCE.getWidth() - BREWING_WINDOW_BACKGROUND.getWidth()) / 2);
        setY((WindowManager.INTERFACE_ARTWORK_WINDOW.getY() - BREWING_WINDOW_BACKGROUND.getHeight()) / 2);

        windowButton.setX(WindowManager.INTERFACE_ARTWORK_WINDOW.getBrewingButtonOffsX());
        windowButton.setY(WindowManager.INTERFACE_ARTWORK_WINDOW.getBrewingButtonOffsY());

        closeWindowButton.setX(getX() + BREWING_WINDOW_BACKGROUND.getWidth() - WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 19);
        closeWindowButton.setY(getY() + 10);

        brewPotionButton.setX(getX() + MIX_BUTTON_OFFS_X);
        brewPotionButton.setY(getY() + MIX_BUTTON_OFFS_Y);
        resizeItemSlots();

        super.setRegion(getX(), getY(), getWidth(), getHeight());
    }

    private void setComponentsEnabled(boolean enabled) {
        for (int i = 0; i < NUM_INGREDIENT_SLOTS; i++) {
            ingredientSlots[i].setEnabled(enabled);
        }
        
        resultSlot.setEnabled(enabled);
    }

    private void createItemSlots() {
        for (int i = 0; i < NUM_INGREDIENT_SLOTS; i++) {
            ingredientSlots[i] = new BrewingSlotArea(cauldron, i);
        }
        resultSlot = new BrewingSlotArea(cauldron, 5);
    }

    private void resizeItemSlots() {
        int xOffs = getX() + INGREDIENT_OFFS_X;
        int yOffs = getY() + INGREDIENT_OFFS_Y;

        ingredientSlots[0].resize(xOffs, yOffs);
        ingredientSlots[1].resize(xOffs + INGREDIENT_STEP_X, yOffs);
        ingredientSlots[2].resize(xOffs, yOffs + INGREDIENT_STEP_Y);
        ingredientSlots[3].resize(xOffs + INGREDIENT_STEP_X, yOffs + INGREDIENT_STEP_Y);

        resultSlot.resize(getX() + RESULT_OFFS_X, getY() + RESULT_OFFS_Y);
    }

    @Override
    public void show() {
        super.show();
        animationY = 0 - BREWING_WINDOW_BACKGROUND.getHeight();
        closeWindowButton.setEnabled(true);
        brewPotionButton.setEnabled(true);
        setComponentsEnabled(true);
        super.addMouseListener(Interactable.Z_BACKGROUND);
    }

    @Override
    public final void hide() {
        super.hide();
        closeWindowButton.setEnabled(false);
        brewPotionButton.setEnabled(false);
        setComponentsEnabled(false);
        super.removeMouseListener();
        
        ingredientSlots[0].onHide();
        ingredientSlots[1].onHide();
        ingredientSlots[2].onHide();
        ingredientSlots[3].onHide();
        resultSlot.onHide();
    }

    @Override
    public void action(Button buttonPressed) {
        Item item;
        
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            }
        }
        else {
            item = cauldron.brew();
            resultSlot.setItem(item);
        }
    }

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            WindowManager.INSTANCE.clearTooltipText();
            return;
        }

        if (caller == windowButton) {
            if (isVisible()) {
                WindowManager.INSTANCE.setTooltipText("Click to close\nthe Brewing\nWindow");
            }
            else {
                WindowManager.INSTANCE.setTooltipText("Click to open\nthe Brewing\nWindow");
            }
        }
        else if (caller == closeWindowButton) {
            WindowManager.INSTANCE.setTooltipText("Click to close\nthe Brewing\nWindow");
        }
        else if (caller == brewPotionButton) {
            WindowManager.INSTANCE.setTooltipText("Click to mix the\ningredients into\na potion");
        }
    }

    @Override
    public void render() {
        windowButton.render();

        if (!isVisible()) {
            return;
        }

        animationY = Math.min(animationY + (int) (SceneService.INSTANCE.getHeight() * FALL_RATE), getY());

        BREWING_WINDOW_BACKGROUND.render(getX(), animationY);

        if (animationY == getY()) {
            closeWindowButton.render();
            brewPotionButton.render();
            renderItemSlots();
        }
    }

    private void renderItemSlots() {
        for (int i = 0; i < NUM_INGREDIENT_SLOTS; i++) {
            ingredientSlots[i].render();
        }

        resultSlot.render();
    }
}
