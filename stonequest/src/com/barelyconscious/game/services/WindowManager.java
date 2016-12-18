/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         WindowManager.java
 * Author:            Matt Schwartz
 * Date Created:      12.23.2013 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */


package com.barelyconscious.game.services;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.ingamemenu.BrewingWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.CharacterWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.InterfaceArtworkWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.InventoryWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.JournalWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.SalvageWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.UpgradeItemWindow;
import com.barelyconscious.game.graphics.gui.ingamemenu.Window;
import java.awt.Point;

public class WindowManager implements Service {

    public static final Point WINDOW_BUTTON_OFFS = new Point(1,1);
    // Buttons
    public static final UIElement INTERFACE_WINDOW_CLOSE_BUTTON = UIElement.createUIElement("/gfx/gui/components/button/closeWindowButton.png");
    public static final UIElement UPGRADE_ITEM_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/upgradeItem/upgradeItemButton.png");
    public static final UIElement INVENTORY_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/inventory/inventoryButton.png");
    public static final UIElement CHARACTER_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/character/characterButton.png");
    public static final UIElement BREWING_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/brewing/brewingButton.png");
    public static final UIElement JOURNAL_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/journal/journalButton.png");
    public static final UIElement SALVAGE_WINDOW_BUTTON = UIElement.createUIElement("/gfx/gui/components/windows/salvage/salvageButton.png");
    
    public static final WindowManager INSTANCE = new WindowManager();
    public static final BrewingWindow BREWING_WINDOW = new BrewingWindow();
    public static final CharacterWindow CHARACTER_WINDOW = new CharacterWindow();
    public static final InventoryWindow INVENTORY_WINDOW = new InventoryWindow();
    public static final JournalWindow JOURNAL_WINDOW = new JournalWindow();
    public static final SalvageWindow SALVAGE_WINDOW = new SalvageWindow();
    public static final UpgradeItemWindow UPGRADE_ITEM_WINDOW = new UpgradeItemWindow();
    public static final InterfaceArtworkWindow INTERFACE_ARTWORK_WINDOW = new InterfaceArtworkWindow();

    private WindowManager() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been instantiated.");
        }
    }

    public void resize(int newWidth, int newHeight) {

    }

    /**
     * Toggles a window based on the Window object passed into this method.
     *
     * @param w a subclass of the Window class, which refers to the window that
     * should be hidden or shown
     */
    public void toggleWindow(Window w) {
        w.toggleUI();
    }

    public void closeAllWindows() {
        BREWING_WINDOW.hide();
        CHARACTER_WINDOW.hide();
        INVENTORY_WINDOW.hide();
        JOURNAL_WINDOW.hide();
        SALVAGE_WINDOW.hide();
        UPGRADE_ITEM_WINDOW.hide();
    }
    
    /**
     * Create the Window buttons that, when pressed, will open the corresponding
     * Window.
     */
    private void createWindowButtons() {
        
    }
    
    public void setTooltipText(String text) {
    }
    
    public void clearTooltipText() {
    }

    @Override
    public void start() {
//        windowOffsX = gameWidth - width;
//        windowOffsY = artworkWindowOffsY - height;
//
//        windowButton = new InterfaceWindowButton(windowButtonX, windowButtonY, this, InterfaceDelegate.INVENTORY_WINDOW_BUTTON);
//        
//        BREWING_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        CHARACTER_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        INVENTORY_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        JOURNAL_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        SALVAGE_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        UPGRADE_ITEM_WINDOW.windowButton.setRegion(xStart, yStart, width, height);
//        SceneService.INSTANCE.addComponent(BREWING_WINDOW);
//        SceneService.INSTANCE.addComponent(CHARACTER_WINDOW);
//        SceneService.INSTANCE.addComponent(INVENTORY_WINDOW);
//        SceneService.INSTANCE.addComponent(JOURNAL_WINDOW);
//        SceneService.INSTANCE.addComponent(SALVAGE_WINDOW);
//        SceneService.INSTANCE.addComponent(UPGRADE_ITEM_WINDOW);
    }

    @Override
    public void stop() {
    }

    @Override
    public void restart() {
        stop();
        start();
    }
}
