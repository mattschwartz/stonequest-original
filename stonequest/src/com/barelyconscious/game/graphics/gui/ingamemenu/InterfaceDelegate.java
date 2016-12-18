/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        InterfaceDelegate.java
 * Author:           Matt Schwartz
 * Date created:     08.30.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Cursors;
import com.barelyconscious.game.graphics.gui.ingamemenu.ItemDraggable;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.player.Player;

public class InterfaceDelegate {
//    // Interface windows
//    // Buttons for accessing windows and closing them
//    public static final UIElement INTERFACE_WINDOW_CLOSE_BUTTON = UIElement.createUIElement("/gfx/gui/components/button/closeWindowButton.png");
//    public static final UIElement UPGRADE_ITEM_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/upgradeItem/upgradeItemButton.png");
//    public static final UIElement INVENTORY_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/inventory/inventoryButton.png");
//    public static final UIElement CHARACTER_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/character/characterButton.png");
//    public static final UIElement BREWING_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/brewing/brewingButton.png");
//    public static final UIElement JOURNAL_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/journal/journalButton.png");
//    public static final UIElement SALVAGE_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/salvage/salvageButton.png");
//
//    private static final InterfaceDelegate INSTANCE = new InterfaceDelegate();
//    protected InterfaceArtworkWindow artworkWindow;
//    protected UpgradeItemWindow upgradeItemWindow;
//    protected InventoryWindow inventoryWindow;
//    protected CharacterWindow characterWindow;
//    protected BrewingWindow brewingWindow;
//    protected JournalWindow journalWindow;
//    protected SalvageWindow salvageWindow;
//    protected TooltipTextArea tooltipWindow;
//    // Game mechanic objects
//    protected ItemDraggable itemOnCursor;
//    protected boolean isSalvageActive;
//    private boolean inited = false;
//
//    private InterfaceDelegate() {
//        if (INSTANCE != null) {
//            throw new IllegalStateException("Only one interface delegate may be active at runtime");
//        }
//    }
//
//    public void init(int gameWidth, int gameHeight) {
//        if (inited) {
//            throw new IllegalStateException("Already initialized!");
//        }
//
//        itemOnCursor = new ItemDraggable(null);
//        isSalvageActive = false;
//
//        artworkWindow = new InterfaceArtworkWindow(gameWidth, gameHeight);
//        tooltipWindow = new TooltipTextArea(artworkWindow.getTooltipOffsX(), artworkWindow.getTooltipOffsY(), 157, 100, 5);
//        upgradeItemWindow = new UpgradeItemWindow();
//        inventoryWindow = new InventoryWindow();
//        characterWindow = new CharacterWindow();
//        brewingWindow = new BrewingWindow();
//        journalWindow = new JournalWindow();
//        salvageWindow = new SalvageWindow();
//        TextLog.INSTANCE.init(artworkWindow.getTextLogOffsX(), artworkWindow.getTextLogOffsY(), artworkWindow.getTextLogWidth(), artworkWindow.getTextLogHeight());
//
//        Game.screen.addForegroundComponent(TextLog.INSTANCE);
//        Game.screen.addForegroundComponent(tooltipWindow);
//        Game.screen.addForegroundComponent(artworkWindow);
//        Game.screen.addForegroundComponent(inventoryWindow);
//        Game.screen.addForegroundComponent(characterWindow);
//        Game.screen.addForegroundComponent(brewingWindow);
//        Game.screen.addForegroundComponent(journalWindow);
//        Game.screen.addForegroundComponent(salvageWindow);
//        Game.screen.addForegroundComponent(upgradeItemWindow);
//        inited = true;
//    }
//
//    public static InterfaceDelegate getInstance() {
//        return INSTANCE;
//    }
//
//    public void closeWindows() {
//        upgradeItemWindow.hide();
//        inventoryWindow.hide();
//        characterWindow.hide();
//        brewingWindow.hide();
//        journalWindow.hide();
//        salvageWindow.hide();
//    }
//
//    /**
//     * Toggles a window based on the Window object passed into this method.
//     *
//     * @param w a subclass of the Window class, which refers to the window that
//     * should be hidden or shown
//     */
//    public void toggleUI(Object w) {
//        if (w instanceof UpgradeItemWindow) {
//            upgradeItemWindow.toggleUI();
//        }
//        else if (w instanceof InventoryWindow) {
//            inventoryWindow.toggleUI();
//        }
//        else if (w instanceof CharacterWindow) {
//            characterWindow.toggleUI();
//        }
//        else if (w instanceof BrewingWindow) {
//            brewingWindow.toggleUI();
//        }
//        else if (w instanceof JournalWindow) {
//            journalWindow.toggleUI();
//        }
//        else if (w instanceof SalvageWindow) {
//            salvageWindow.toggleUI();
//        }
//    }
//
//    public Player getPlayer() {
//        return Game.getCurrentPlayer();
//    }
//
//    public Inventory getPlayerInventory() {
//        return Game.getCurrentPlayer().getInventory();
//    }
//
//    public Item putItemOnCursor(Item item) {
//        Item oldItem = itemOnCursor.getItem();
//        itemOnCursor.setItem(item);
//
//        if (itemOnCursor.getItem() == null) {
//            itemOnCursor.dispose();
//        }
//        else if (oldItem == null) {
//            itemOnCursor.enable();
//            Game.screen.addAlwaysOnTopComponent(itemOnCursor);
//        }
//
//        return oldItem;
//    }
//
//    /**
//     *
//     * @return if any, the Item that is on the user's cursor.
//     */
//    public Item getItemOnCursor() {
//        return itemOnCursor.getItem();
//    }
//
//    public void setSalvageActive(boolean active) {
//        isSalvageActive = active;
//
//        if (isSalvageActive) {
//            Cursors.setCursor(Cursors.SALVAGE_ITEM_CURSOR);
//        }
//        else {
//            Cursors.setCursor(Cursors.DEFAULT_CURSOR);
//        }
//    }
//
//    /**
//     *
//     * @return true if the user has indicated that he/she wishes to salvage the
//     * next item clicked
//     */
//    public boolean isSalvageActive() {
//        return isSalvageActive;
//    }
//
//    public void setTooltipText(String tooltipText) {
//        tooltipWindow.setText(tooltipText);
//    }
//
//    public void clearTooltipText() {
//        tooltipWindow.clearTooltipText();
//    }
//
//    /**
//     * Resize the elements that comprise StoneQuest's interface based on the
//     * given width and height of the application window.
//     *
//     * @param newWidth the new width in pixels of the application
//     * @param newHeight the new height in pixels of the application
//     */
//    public void resize(int newWidth, int newHeight) {
//        artworkWindow.resize(newWidth, newHeight);
//        tooltipWindow.resize(artworkWindow.getTooltipOffsX(), artworkWindow.getTooltipOffsY());
//        inventoryWindow.resize(newWidth, artworkWindow.getY(), artworkWindow.getInventoryButtonOffsX(), artworkWindow.getInventoryButtonOffsY());
//        characterWindow.resize(newWidth, artworkWindow.getY(), artworkWindow.getCharacterButtonOffsX(), artworkWindow.getCharacterButtonOffsY());
//        brewingWindow.resize(newWidth, artworkWindow.getY(), artworkWindow.getBrewingButtonOffsX(), artworkWindow.getBrewingButtonOffsY());
//        journalWindow.resize(artworkWindow.getX(), artworkWindow.getY(), artworkWindow.getJournalButtonOffsX(), artworkWindow.getJournalButtonOffsY());
//        salvageWindow.resize(artworkWindow.getSalvageButtonOffsX(), artworkWindow.getSalvageButtonOffsY());
//        upgradeItemWindow.resize(artworkWindow.getX(), artworkWindow.getY(), artworkWindow.getUpgradeItemButtonOffsX(), artworkWindow.getUpgradeItemButtonOffsY());
//        TextLog.INSTANCE.resize(artworkWindow.getTextLogOffsX(), artworkWindow.getTextLogOffsY(), artworkWindow.getTextLogWidth(), artworkWindow.getTextLogHeight());
//    }
//
//    public int getVisibleHeight() {
//        return artworkWindow.getY();
//    }
}
