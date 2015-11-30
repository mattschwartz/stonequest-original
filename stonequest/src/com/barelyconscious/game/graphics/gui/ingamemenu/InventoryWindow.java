/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        InventoryWindow.java
 * Author:           Matt Schwartz
 * Date created:     08.31.2013
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
import com.barelyconscious.game.World;
import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Button;
import com.barelyconscious.game.graphics.gui.ButtonAction;
import com.barelyconscious.game.graphics.gui.ingamemenu.EquipmentSlotArea;
import com.barelyconscious.game.graphics.gui.ingamemenu.InventorySlotArea;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.game.services.WindowManager;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.util.ColorHelper;

public class InventoryWindow extends Window implements ButtonAction {

    private static final UIElement INVENTORY_WINDOW_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/windows/inventory/background.png");
    private final int EQUIPMENT_SLOT_HELMET_OFFS_X = 37;
    private final int EQUIPMENT_SLOT_HELMET_OFFS_Y = 140;
    private final int EQUIPMENT_SLOT_CHEST_OFFS_X = 37;
    private final int EQUIPMENT_SLOT_CHEST_OFFS_Y = 189;
    private final int EQUIPMENT_SLOT_GREAVES_OFFS_X = 37;
    private final int EQUIPMENT_SLOT_GREAVES_OFFS_Y = 238;
    private final int EQUIPMENT_SLOT_BELT_OFFS_X = 37;
    private final int EQUIPMENT_SLOT_BELT_OFFS_Y = 287;
    private final int EQUIPMENT_SLOT_NECKLACE_OFFS_X = 282;
    private final int EQUIPMENT_SLOT_NECKLACE_OFFS_Y = 140;
    private final int EQUIPMENT_SLOT_EARRING_OFFS_X = 282;
    private final int EQUIPMENT_SLOT_EARRING_OFFS_Y = 189;
    private final int EQUIPMENT_SLOT_RING_OFFS_X = 282;
    private final int EQUIPMENT_SLOT_RING_OFFS_Y = 238;
    private final int EQUIPMENT_SLOT_BOOTS_OFFS_X = 282;
    private final int EQUIPMENT_SLOT_BOOTS_OFFS_Y = 287;
    private final int EQUIPMENT_SLOT_MAIN_HAND_OFFS_X = 130;
    private final int EQUIPMENT_SLOT_MAIN_HAND_OFFS_Y = 287;
    private final int EQUIPMENT_SLOT_OFF_HAND_OFFS_X = 189;
    private final int EQUIPMENT_SLOT_OFF_HAND_OFFS_Y = 287;
    private final int NUM_EQUIPMENT_SLOTS = 10;
    // Inventory items
    private final int INVENTORY_SLOT_OFFS_X = 37;
    private final int INVENTORY_SLOT_OFFS_Y = 387;
    private final int INVENTORY_SLOT_STEP_X = 49;
    private final int INVENTORY_SLOT_STEP_Y = 49;
    private final int INVENTORY_SLOT_ROWS = 4;
    private final int INVENTORY_SLOT_COLS = 6;
    private final int NUM_INVENTORY_SLOTS = INVENTORY_SLOT_ROWS * INVENTORY_SLOT_COLS;
    private int goldTextOffsX = 130;
    private int goldTextOffsY = 345 + FontService.characterHeight;
    private int goldTextAreaWidth = 82;
    private int animationY;
    protected InterfaceWindowButton windowButton;
    private CloseWindowButton closeWindowButton;
    private InventorySlotArea[] itemSlots;
    private InventorySlotArea[] equipmentSlots;
    
    public InventoryWindow() {
        setWidth(INVENTORY_WINDOW_BACKGROUND.getWidth());
        setHeight(INVENTORY_WINDOW_BACKGROUND.getHeight());

        createButtons();
        createItemSlots();
        resizeItemSlots();

        super.setRegion(getX(), getY(), getWidth(), getHeight());
        super.addMouseListener(Interactable.Z_BACKGROUND);
        hide();
    } // constructor
    
    private void createButtons() {
        closeWindowButton = new CloseWindowButton(this, WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON);
    } // createButtons

    private void createItemSlots() {
        itemSlots = new InventorySlotArea[NUM_INVENTORY_SLOTS];
        equipmentSlots = new EquipmentSlotArea[NUM_EQUIPMENT_SLOTS];

        for (int i = 0; i < NUM_INVENTORY_SLOTS; i++) {
            itemSlots[i] = new InventorySlotArea(World.INSTANCE.getPlayer().getInventory(), i, -1, -1);
        } // for

        for (int i = 0; i < NUM_EQUIPMENT_SLOTS; i++) {
            equipmentSlots[i] = new EquipmentSlotArea(World.INSTANCE.getPlayer(), i, -1, -1);
        } // for
    } // createItemSlots

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
    public void resize(int gameWidth, int artworkWindowOffsY, int windowButtonX, int windowButtonY) {
        setX(gameWidth - getWidth());
        setY(artworkWindowOffsY - getHeight());

        windowButton.setX(windowButtonX);
        windowButton.setY(windowButtonY);

        closeWindowButton.setX(gameWidth - WindowManager.INTERFACE_WINDOW_CLOSE_BUTTON.getWidth() - 16);
        closeWindowButton.setY(getY() + 10);

        resizeItemSlots();

        super.setRegion(getX(), getY(), getWidth(), getHeight());
    } // resize

    private void resizeItemSlots() {
        int i = 0;
        int xOffs = getX() + INVENTORY_SLOT_OFFS_X;
        int yOffs = getY() + INVENTORY_SLOT_OFFS_Y;

        // Set item slot values
        for (int y = 0; y < INVENTORY_SLOT_ROWS; y++) {
            for (int x = 0; x < INVENTORY_SLOT_COLS; x++) {
                itemSlots[i++].resize(xOffs + INVENTORY_SLOT_STEP_X * x, yOffs + INVENTORY_SLOT_STEP_Y * y);
            } // for
        } // for

        // Set equipment item slot values
        equipmentSlots[Entity.HELMET_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_HELMET_OFFS_X, getY() + EQUIPMENT_SLOT_HELMET_OFFS_Y);
        equipmentSlots[Entity.CHEST_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_CHEST_OFFS_X, getY() + EQUIPMENT_SLOT_CHEST_OFFS_Y);
        equipmentSlots[Entity.GREAVES_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_GREAVES_OFFS_X, getY() + EQUIPMENT_SLOT_GREAVES_OFFS_Y);
        equipmentSlots[Entity.BELT_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_BELT_OFFS_X, getY() + EQUIPMENT_SLOT_BELT_OFFS_Y);
        equipmentSlots[Entity.NECK_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_NECKLACE_OFFS_X, getY() + EQUIPMENT_SLOT_NECKLACE_OFFS_Y);
        equipmentSlots[Entity.EARRING_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_EARRING_OFFS_X, getY() + EQUIPMENT_SLOT_EARRING_OFFS_Y);
        equipmentSlots[Entity.RING_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_RING_OFFS_X, getY() + EQUIPMENT_SLOT_RING_OFFS_Y);
        equipmentSlots[Entity.BOOTS_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_BOOTS_OFFS_X, getY() + EQUIPMENT_SLOT_BOOTS_OFFS_Y);
        equipmentSlots[Entity.MAIN_HAND_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_MAIN_HAND_OFFS_X, getY() + EQUIPMENT_SLOT_MAIN_HAND_OFFS_Y);
        equipmentSlots[Entity.OFF_HAND_SLOT_ID].resize(getX() + EQUIPMENT_SLOT_OFF_HAND_OFFS_X, getY() + EQUIPMENT_SLOT_OFF_HAND_OFFS_Y);

    } // resizeItemSlots

    private void setComponentsEnabled(boolean enabled) {
        for (int slot = 0; slot < NUM_INVENTORY_SLOTS; slot++) {
            itemSlots[slot].setEnabled(enabled);
        } // for

        for (int slot = 0; slot < NUM_EQUIPMENT_SLOTS; slot++) {
            equipmentSlots[slot].setEnabled(enabled);
        } // for

        closeWindowButton.setEnabled(enabled);
    } // setComponentsEnabled

    @Override
    public void show() {
        if (WindowManager.CHARACTER_WINDOW.isVisible()) {
            WindowManager.CHARACTER_WINDOW.hide();
        } // if

        super.show();
        animationY = 0 - INVENTORY_WINDOW_BACKGROUND.getHeight();
        setComponentsEnabled(true);
        setEnabled(true);
        super.addMouseListener(Interactable.Z_BACKGROUND);
    } // show

    @Override
    public final void hide() {
        super.hide();
        setComponentsEnabled(false);
        setEnabled(false);
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
            WindowManager.INSTANCE.clearTooltipText();
            return;
        } // if

        if (caller == windowButton) {
            if (isVisible()) {
                WindowManager.INSTANCE.setTooltipText("Click to close\nthe Inventory\nWindow");
            } // if
            else {
                WindowManager.INSTANCE.setTooltipText("Click to open\nthe Inventory\nWindow");
            } // else
        } // if
        else if (caller == closeWindowButton) {
            WindowManager.INSTANCE.setTooltipText("Click to close\nthe Inventory\nWindow");
        } // else if
    } // hoverOverAction

    @Override
    public void render() {
        String playerGold;
        windowButton.render();

        if (!isVisible()) {
            return;
        } // if

        animationY = Math.min(animationY + (int) (SceneService.INSTANCE.getHeight() * FALL_RATE), getY());

        INVENTORY_WINDOW_BACKGROUND.render(getX(), animationY);

        if (animationY == getY()) {
            closeWindowButton.render();

            // Draw gold text
            playerGold = World.INSTANCE.getPlayer().getInventory().getGold() + " g";
            FontService.drawFont(playerGold, ColorHelper.PLAYER_GOLD_TEXT_COLOR, null, getX() + goldTextOffsX + goldTextAreaWidth - FontService.getStringWidth(playerGold), getY() + goldTextOffsY);
            renderItemSlots();
        } // if
    } // render

    private void renderItemSlots() {
        for (int slot = 0; slot < NUM_INVENTORY_SLOTS; slot++) {
            itemSlots[slot].render();
        } // for

        for (int i = 0; i < NUM_EQUIPMENT_SLOTS; i++) {
            equipmentSlots[i].render();
        } // for
    } // renderItemSlots
} // InventoryWindow
