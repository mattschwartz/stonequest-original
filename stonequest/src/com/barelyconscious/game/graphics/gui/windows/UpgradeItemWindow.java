/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        UpgradeItemWindow.java
 * Author:           Matt Schwartz
 * Date created:     08.29.2013
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
import com.barelyconscious.game.graphics.gui.JustifiedTextArea;
import com.barelyconscious.game.input.Interactable;

public class UpgradeItemWindow extends Window implements ButtonAction {

    private static final UIElement UPGRADE_ITEM_WINDOW_BACKGROUND = new UIElement("/gfx/gui/components/windows/upgradeItem/background.png");
    public final int ITEM_DESCRIPTION_WIDTH = 226;
    public final int ITEM_DESCRIPTION_HEIGHT = 69;
    public final int DIVINE_FAVOR_DESCRIPTION_WIDTH = 286;
    public final int DIVINE_FAVOR_DESCRIPTION_HEIGHT = 109;
    private int animationY;
    private InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private Button applySalvageButton;
    private Button applyAugmentationButton;
    private Button removeAugmentationButton;
    private Button performRitualButton;
    private JustifiedTextArea itemDescriptionTextArea;
    private JustifiedTextArea divineFavorDescriptionTextArea;
    
    public UpgradeItemWindow() {
    }
    
    public UpgradeItemWindow(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        width = UPGRADE_ITEM_WINDOW_BACKGROUND.getWidth();
        height = UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight();

        windowOffsX = artworkWindowOffsX;
        windowOffsY = artworkWindowOffsY - UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight();

        windowButton = new InterfaceWindowButton(windowButtonX, windowButtonY, this, UIElement.UPGRADE_ITEM_WINDOW_BUTTON);
        closeWindowButton = new CloseWindowButton(windowOffsX + 16, windowOffsY + 10, this, UIElement.INTERFACE_WINDOW_CLOSE_BUTTON);
        
        applySalvageButton = new Button("Apply", Interactable.Z_BUTTON, windowOffsX + 97, windowOffsY + 258, 111, 24, true);
        applyAugmentationButton = new Button("Apply", Interactable.Z_BUTTON, windowOffsX + 97, windowOffsY + 325, 111, 24, true);
        removeAugmentationButton = new Button("Remove", Interactable.Z_BUTTON, windowOffsX + 97, windowOffsY + 354, 111, 24, true);
        performRitualButton = new Button("Perform Ritual", Interactable.Z_BUTTON, windowOffsX + 97, windowOffsY + 419, 159, 24, true);

        itemDescriptionTextArea = new JustifiedTextArea(windowOffsX + 105, windowOffsY + 116, ITEM_DESCRIPTION_WIDTH, ITEM_DESCRIPTION_HEIGHT);
        divineFavorDescriptionTextArea = new JustifiedTextArea(windowOffsX + 37, windowOffsY + 478, DIVINE_FAVOR_DESCRIPTION_WIDTH, DIVINE_FAVOR_DESCRIPTION_HEIGHT);

        // Disable all buttons when the window is not visible
        setComponentsEnabled(false);

        // Set the callback functions for the buttons
        setCallbacks();

        super.setRegion(windowOffsX, windowOffsY, width, height);
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    } // constructor

    /**
     * Disable or enable all buttons associated with the upgrade item window
     * that should not be interactable with whenever the frame is hidden.
     *
     * @param enabled if true, all buttons will be enabled
     */
    private void setComponentsEnabled(boolean enabled) {
        closeWindowButton.setEnabled(enabled);
        applySalvageButton.setEnabled(enabled);
        applyAugmentationButton.setEnabled(enabled);
        removeAugmentationButton.setEnabled(enabled);
        performRitualButton.setEnabled(enabled);
        itemDescriptionTextArea.setEnabled(enabled);
        divineFavorDescriptionTextArea.setEnabled(enabled);
    } // setComponentsEnabled

    /**
     * Sets the callback functions for all of the buttons so this class so that
     * each button knows who to call when an action is performed.
     */
    private void setCallbacks() {
        applySalvageButton.setCallbackFunction(this);
        applyAugmentationButton.setCallbackFunction(this);
        removeAugmentationButton.setCallbackFunction(this);
        performRitualButton.setCallbackFunction(this);
    } // setCallbacks

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
    public void resize(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        windowOffsX = artworkWindowOffsX;
        windowOffsY = artworkWindowOffsY - UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight();

        /* Relocate (if necessary) the button in the interface which toggles the 
         * showing of the Upgrade Item window */
        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        // Relocate all other buttons as necessary
        resizeButtons(windowOffsX, windowOffsY);

        // Relocate text logs as necessary
        itemDescriptionTextArea.resize(windowOffsX + 105, windowOffsY + 116);
        divineFavorDescriptionTextArea.resize(windowOffsX + 37, windowOffsY + 478);

        super.setRegion(windowOffsX, windowOffsY, width, height);
    } // resize

    /**
     * Given the new coordinates for the Upgrade Item window interface, all
     * inner buttons may need to be shifted accordingly so that the interface
     * maintains a complete image.
     *
     * @param windowOffsX the new windowOffsX coordinate of the starting
     * location for the Upgrade Item window interface
     * @param windowOffsY the new windowOffsY coordinate of the starting
     * location for the Upgrade Item window interface
     */
    private void resizeButtons(int windowOffsX, int windowOffsY) {
        closeWindowButton.setX(windowOffsX + 16);
        closeWindowButton.setY(windowOffsY + 10);

        applySalvageButton.setX(windowOffsX + 97);
        applySalvageButton.setY(windowOffsY + 258);

        applyAugmentationButton.setX(windowOffsX + 97);
        applyAugmentationButton.setY(windowOffsY + 325);

        removeAugmentationButton.setX(windowOffsX + 97);
        removeAugmentationButton.setY(windowOffsY + 354);

        performRitualButton.setX(windowOffsX + 97);
        performRitualButton.setY(windowOffsY + 419);
    } // resizeButtons

    @Override
    public void show() {
        if (InterfaceDelegate.getInstance().journalWindow.isVisible) {
            InterfaceDelegate.getInstance().journalWindow.hide();
        } // if

        super.show();
        animationY = 0 - UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight();
        setComponentsEnabled(true);
        setEnabled(true);
    } // show

    @Override
    public final void hide() {
        super.hide();
        setComponentsEnabled(false);
        setEnabled(false);
    } // hide

    @Override
    public void action(Button buttonPressed) {
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            } // if
        } // if
        else if (buttonPressed == applySalvageButton) {
            System.err.println("Applying salvage to item!");
        } // else if
        else if (buttonPressed == applyAugmentationButton) {
            System.err.println("Applying augmentation to item!");
        } // else if
        else if (buttonPressed == removeAugmentationButton) {
            System.err.println("Removing augmentation from item!");
        } // else if
        else if (buttonPressed == performRitualButton) {
            System.err.println("Performing ritual!");
        } // else if
        else {
            System.err.println("Unkown button: " + buttonPressed);
        } // else
    } // action

    @Override
    public void hoverOverAction(Button caller) {
        if (caller == null) {
            InterfaceDelegate.getInstance().clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible) {
                InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Upgrade\nItem Window");
            } // if
            else {
                InterfaceDelegate.getInstance().setTooltipText("Click to open\nthe Upgrade\nItem Window");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            InterfaceDelegate.getInstance().setTooltipText("Click to close\nthe Upgrade\nItem Window");
        } // else if
        else if (caller == applySalvageButton) {
            InterfaceDelegate.getInstance().setTooltipText("Adds selected\nsalvage to the\nitem, improving\nit");
        } // else if
        else if (caller == applyAugmentationButton) {
            InterfaceDelegate.getInstance().setTooltipText("Applys the\naugmentation\nto the selected\nitem");
        } // else if
        else if (caller == removeAugmentationButton) {
            InterfaceDelegate.getInstance().setTooltipText("Removes the\naugmentation\nto the selected\nitem");
        } // else if
        else if (caller == performRitualButton) {
            InterfaceDelegate.getInstance().setTooltipText("Performs a\nreligious ritual\nin the hopes of\ngaining divine\nfavor");
        }
    } // hoverOverAction

    @Override
    public void render(Screen screen) {
        windowButton.render(screen);

        if (!isVisible) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (screen.getVisibleHeight() * FALL_RATE), windowOffsY);

        UPGRADE_ITEM_WINDOW_BACKGROUND.render(screen, windowOffsX, animationY);

        if (animationY == windowOffsY) {
            closeWindowButton.render(screen);
            applySalvageButton.render(screen);
            applyAugmentationButton.render(screen);
            removeAugmentationButton.render(screen);
            performRitualButton.render(screen);

            itemDescriptionTextArea.render(screen);
            divineFavorDescriptionTextArea.render(screen);
        } // if
    } // render
} // UpgradeItemWindow
