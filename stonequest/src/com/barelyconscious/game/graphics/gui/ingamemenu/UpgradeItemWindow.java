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
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.gui.ingamemenu.CloseWindowButton;
import com.barelyconscious.game.graphics.gui.ingamemenu.InterfaceWindowButton;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.ingamemenu.ItemSlotArea;
import com.barelyconscious.game.graphics.gui.JustifiedTextArea;
import com.barelyconscious.game.graphics.gui.ProgressBar;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Augment;
import com.barelyconscious.game.item.DivineFavor;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.WindowManager;

public class UpgradeItemWindow extends Window implements ButtonAction {

    private static final UIElement UPGRADE_ITEM_WINDOW_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/windows/upgradeItem/background.png");
    private final int ITEM_TO_UPGRADE_OFFS_X = 40;
    private final int ITEM_TO_UPGRADE_OFFS_Y = 127;
    private final int SALVAGE_OFFS_X = 40;
    private final int SALVAGE_OFFS_Y = 232;
    private final int ITEM_AUGMENT_OFFS_X = 40;
    private final int ITEM_AUGMENT_OFFS_Y = 328;
    private final int DIVINE_FAVOR_OFFS_X = 40;
    private final int DIVINE_FAVOR_OFFS_Y = 422;
    private final int ITEM_DESCRIPTION_WIDTH = 226;
    private final int ITEM_DESCRIPTION_HEIGHT = 69;
    private final int DIVINE_FAVOR_DESCRIPTION_WIDTH = 286;
    private final int DIVINE_FAVOR_DESCRIPTION_HEIGHT = 109;
    private final int SALVAGE_PROGRESS_BAR_WIDTH = 226;
    private int animationY;
    protected InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private Button applySalvageButton;
    private Button applyAugmentationButton;
    private Button removeAugmentationButton;
    private Button performRitualButton;
    private JustifiedTextArea itemDescriptionTextArea;
    private JustifiedTextArea divineFavorDescriptionTextArea;
    private ItemSlotArea itemToUpgradeSlot;
    private ItemSlotArea salvageSlot;
    private ItemSlotArea itemAugmentSlot;
    private ItemSlotArea divineFavorSlot;
    private ProgressBar itemSalvageProgressBar;

    public UpgradeItemWindow() {
        setWidth(UPGRADE_ITEM_WINDOW_BACKGROUND.getWidth());
        setHeight(UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight());

        applySalvageButton = new Button("Apply", Interactable.Z_BUTTON, getX() + 97, getY() + 258, 111, 24, true);
        applyAugmentationButton = new Button("Apply", Interactable.Z_BUTTON, getX() + 97, getY() + 325, 111, 24, true);
        removeAugmentationButton = new Button("Remove", Interactable.Z_BUTTON, getX() + 97, getY() + 354, 111, 24, true);
        performRitualButton = new Button("Perform Ritual", Interactable.Z_BUTTON, getX() + 97, getY() + 419, 159, 24, true);

        itemDescriptionTextArea = new JustifiedTextArea(getX() + 105, getY() + 116, ITEM_DESCRIPTION_WIDTH, ITEM_DESCRIPTION_HEIGHT);
        divineFavorDescriptionTextArea = new JustifiedTextArea(getX() + 37, getY() + 478, DIVINE_FAVOR_DESCRIPTION_WIDTH, DIVINE_FAVOR_DESCRIPTION_HEIGHT);

        itemSalvageProgressBar = new ProgressBar(getX() + 97, getY() + 229, SALVAGE_PROGRESS_BAR_WIDTH);

        // Disable all buttons when the window is not visible
        createButtons();
        setComponentsEnabled(false);
        createItemSlots();
        resizeItemSlots();

        // Set the callback functions for the buttons
        setCallbacks();

        super.setRegion(getX(), getY(), getWidth(), getHeight());
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    }
    
    private void createButtons() {
        closeWindowButton = new CloseWindowButton(this, WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON);
    }

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
        // item slot areas
    }

    /**
     * Sets the callback functions for all of the buttons so this class so that
     * each button knows who to call when an action is performed.
     */
    private void setCallbacks() {
        applySalvageButton.setCallbackFunction(this);
        applyAugmentationButton.setCallbackFunction(this);
        removeAugmentationButton.setCallbackFunction(this);
        performRitualButton.setCallbackFunction(this);
    }

    /**
     * Resize elements as necessary when the application is resized.
     *
     * @param artworkWindowOffsX the new getX() position of the artwork
     * interface window
     * @param artworkWindowOffsY the new getY() position of the artwork
     * interface window
     * @param windowButtonX the new getX() position of the upgrade item
     * window's button
     * @param windowButtonY the new getY() position of the upgrade item
     * window's button
     */
    public void resize(int artworkWindowOffsX, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        setX(artworkWindowOffsX);
        setY(artworkWindowOffsY - UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight());

        /* Relocate (if necessary) the button in the interface which toggles the 
         * showing of the Upgrade Item window */
        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        // Relocate all other buttons as necessary
        resizeButtons(getX(), getY());
        resizeItemSlots();

        // Relocate text logs as necessary
        itemDescriptionTextArea.resize(getX() + 105, getY() + 116);
        divineFavorDescriptionTextArea.resize(getX() + 37, getY() + 478);

        itemSalvageProgressBar.resize(getX() + 97, getY() + 229);

        super.setRegion(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Given the new coordinates for the Upgrade Item window interface, all
     * inner buttons may need to be shifted accordingly so that the interface
     * maintains a complete image.
     *
     * @param windowOffsX the new getX() coordinate of the starting
     * location for the Upgrade Item window interface
     * @param windowOffsY the new getY() coordinate of the starting
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
    }

    private void createItemSlots() {
        itemToUpgradeSlot = new ItemSlotArea() {

            @Override
            public Item setItem(Item item) {
                itemSalvageProgressBar.max = item.getRequiredSalvage();
                itemSalvageProgressBar.current = item.getAppliedSalvage();
                return super.setItem(item);
            }

            @Override
            public Item removeItem() {
                itemSalvageProgressBar.max = 0;
                itemSalvageProgressBar.current = 0;
                itemSalvageProgressBar.increaseBy = 0;
                return super.removeItem();
            }

        };
        salvageSlot = new ItemSlotArea() {

            @Override
            public Item setItem(Item item) {
                itemSalvageProgressBar.increaseBy = (int) (Math.random() * 400);
                return super.setItem(item);
            }

            @Override
            public Item removeItem() {
                itemSalvageProgressBar.increaseBy = 0;
                return super.removeItem();
            }

        };
        itemAugmentSlot = new ItemSlotArea() {

            @Override
            public boolean itemGoesHere(Item item) {
                return item instanceof Augment;
            }

        };
        divineFavorSlot = new ItemSlotArea() {

            @Override
            public boolean itemGoesHere(Item item) {
                return item instanceof DivineFavor;
            }

        };
    }

    private void resizeItemSlots() {
        itemToUpgradeSlot.resize(getX() + ITEM_TO_UPGRADE_OFFS_X, getY() + ITEM_TO_UPGRADE_OFFS_Y);
        salvageSlot.resize(getX() + SALVAGE_OFFS_X, getY() + SALVAGE_OFFS_Y);
        itemAugmentSlot.resize(getX() + ITEM_AUGMENT_OFFS_X, getY() + ITEM_AUGMENT_OFFS_Y);
        divineFavorSlot.resize(getX() + DIVINE_FAVOR_OFFS_X, getY() + DIVINE_FAVOR_OFFS_Y);
    }

    @Override
    public void show() {
        if (WindowManager.JOURNAL_WINDOW.isVisible()) {
            WindowManager.JOURNAL_WINDOW.hide();
        }

        super.show();
        animationY = 0 - UPGRADE_ITEM_WINDOW_BACKGROUND.getHeight();
        setComponentsEnabled(true);
        setEnabled(true);
    }

    @Override
    public final void hide() {
        super.hide();
        setComponentsEnabled(false);
        setEnabled(false);
        itemToUpgradeSlot.onHide();
        salvageSlot.onHide();
    }

    @Override
    public void action(Button buttonPressed) {
        if (buttonPressed == closeWindowButton || buttonPressed == windowButton) {
            toggleUI();

            if (buttonPressed == windowButton) {
                hoverOverAction(buttonPressed);
            }
        }
        else if (buttonPressed == applySalvageButton) {
            System.err.println("Applying salvage to item!");
        }
        else if (buttonPressed == applyAugmentationButton) {
            System.err.println("Applying augmentation to item!");
        }
        else if (buttonPressed == removeAugmentationButton) {
            System.err.println("Removing augmentation from item!");
        }
        else if (buttonPressed == performRitualButton) {
            System.err.println("Performing ritual!");
        }
        else {
            System.err.println("Unkown button: " + buttonPressed);
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
                WindowManager.INSTANCE.setTooltipText("Click to close\nthe Upgrade\nItem Window");
            }
            else {
                WindowManager.INSTANCE.setTooltipText("Click to open\nthe Upgrade\nItem Window");
            }
        }
        else if (caller == closeWindowButton) {
            WindowManager.INSTANCE.setTooltipText("Click to close\nthe Upgrade\nItem Window");
        }
        else if (caller == applySalvageButton) {
            WindowManager.INSTANCE.setTooltipText("Adds selected\nsalvage to the\nitem, improving\nit");
        }
        else if (caller == applyAugmentationButton) {
            WindowManager.INSTANCE.setTooltipText("Applies an\naugmentation\nto the item");
        }
        else if (caller == removeAugmentationButton) {
            WindowManager.INSTANCE.setTooltipText("Removes the\naugmentation\nfrom the item");
        }
        else if (caller == performRitualButton) {
            WindowManager.INSTANCE.setTooltipText("Performs a\nreligious ritual\nin the hopes of\ngaining divine\nfavor");
        }
    }

    private void updateItemInfo() {
        Item item = itemToUpgradeSlot.getItem();

        itemDescriptionTextArea.clearText();
        if (item == null) {
            return;
        }

        itemDescriptionTextArea.appendLine("Item level", "" + item.getItemLevel());
        itemDescriptionTextArea.appendLine("Next level", "" + item.getRequiredSalvage());
        itemDescriptionTextArea.appendLine("Material", item.getMaterial());
    }

    private void updateDivineFavorInfo() {
        Item item = divineFavorSlot.getItem();

        divineFavorDescriptionTextArea.clearText();
        if (item == null) {
            return;
        }
    }

    @Override
    public void render() {
        windowButton.render();

        if (!isVisible()) {
            return;
        }

        animationY = Math.min(animationY + (int) (SceneService.INSTANCE.getHeight() * FALL_RATE), getY());

        UPGRADE_ITEM_WINDOW_BACKGROUND.render(getX(), animationY);

        if (animationY == getY()) {
            closeWindowButton.render();
            applySalvageButton.render();
            applyAugmentationButton.render();
            removeAugmentationButton.render();
            performRitualButton.render();

            updateItemInfo();
            itemDescriptionTextArea.render();

            updateDivineFavorInfo();
            divineFavorDescriptionTextArea.render();
            renderItemSlots();

            itemSalvageProgressBar.render();
        }
    }

    private void renderItemSlots() {
        itemToUpgradeSlot.render();
        salvageSlot.render();
        itemAugmentSlot.render();
        divineFavorSlot.render();
    }
}
