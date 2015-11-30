/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        InputHandler.java
 * Author:           Matt Schwartz
 * Date created:     07.06.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.input;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.WorldFrame;
import com.barelyconscious.game.menu.InventoryMenu;
import com.barelyconscious.game.menu.AttributesMenu;
import com.barelyconscious.game.menu.Menu;
import com.barelyconscious.game.menu.TextLog;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private Menu activeMenu = null;
    private final AttributesMenu attributesMenu;
    private final InventoryMenu invenMenu;
    private final WorldFrame world;
    private final TextLog log;
    
    public InputHandler() {
        attributesMenu = Game.attributesMenu;
        invenMenu = Game.invenMenu;
        world = Game.world;
        log = Game.textLog;
        // Default key-mapping
        KeyMap.changeMapping(KeyMap.MODERN_MAPPING);
    } // constructor

    @Override
    public void keyTyped(KeyEvent ke) {
        // unused
    } // keyTyped

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        
        /* Question Marks don't exist in the virtual keyboard supported
            by java's KeyEvent class. */
        if (ke.getKeyChar() == KeyMap.OPEN_HELP_MENU) {
            System.err.println("help me heeeeeelp me");
            return;
        } // if
        
        // Long sequence of ifs because switch statements don't work with non-final variables
        if (keyCode == KeyMap.OPEN_SKILL_TAB) {
            // Clear previous menu, change menu, and set it as focused
            if (activeMenu != null) {
                activeMenu.clearFocus();
            } // if
            
            activeMenu = Game.attributesMenu;
            activeMenu.setActive();
            return;
        } // if
        
        if (keyCode == KeyMap.OPEN_INVENTORY) {
            if (activeMenu != null) {
                activeMenu.clearFocus();
            } // if
            
            activeMenu = Game.invenMenu;
            activeMenu.setActive();
            return;
        } // if
        
        if (keyCode == KeyMap.MENU_MOVE_UP) {
            if (activeMenu != null) {
                activeMenu.moveUp();
            } // if
            
            else {
                world.move(ke.isShiftDown(), 0, -1);
            } // else
        } // if
        
        if (keyCode == KeyMap.PLAYER_MOVE_UP) {
            if (activeMenu == null) {
                world.move(ke.isShiftDown(), 0, -1);
            } // if
        } // if
        
        if (keyCode == KeyMap.MENU_MOVE_DOWN) {
            if (activeMenu != null) {
                activeMenu.moveDown();
            } // if
            
            else {
                world.move(ke.isShiftDown(), 0, 1);
            } // else
        } // if
        
        if (keyCode == KeyMap.PLAYER_MOVE_DOWN) {
            if (activeMenu == null) {
                world.move(ke.isShiftDown(), 0, 1);
            } // if
        } // if
        
        if (keyCode == KeyMap.PLAYER_MOVE_LEFT || keyCode == KeyMap.PLAYER_MOVE_LEFT_ALT) {
            if (activeMenu == null) {
                world.move(ke.isShiftDown(), -1, 0);
            } // if
        } // if
        
        if (keyCode == KeyMap.PLAYER_MOVE_RIGHT || keyCode == KeyMap.PLAYER_MOVE_RIGHT_ALT) {
            if (activeMenu == null) {
                world.move(ke.isShiftDown(), 1, 0);
            } // if
        } // if
        
        if (keyCode == KeyMap.TEXT_LOG_SCROLL_UP) {
            log.moveUp();
        } // if
        
        if (keyCode == KeyMap.TEXT_LOG_SCROLL_DOWN) {
            log.moveDown();
        } // if
        
        if (activeMenu != null && activeMenu instanceof InventoryMenu) {
            if (keyCode == KeyMap.SALVAGE_ITEM) {
                invenMenu.salvageItem();
            } // if
            if (keyCode == KeyMap.EXAMINE_ITEM) {
                invenMenu.examineItem();
            } // if
            if (keyCode == KeyMap.USE_ITEM) {
                invenMenu.useItem();
            } // if
            if (keyCode == KeyMap.DROP_ITEM) {
                invenMenu.dropItem();
            } // if
        } // if
        
        if (keyCode == KeyMap.PICKUP_ITEM) {
            if (activeMenu == null) {
                world.pickupItem();
            } // if
        } // if
        
        if (keyCode == KeyMap.MENU_SELECT) {
            if (attributesMenu.isActive()) {
                attributesMenu.select();
            } // if
        } // if
        
        if (keyCode == KeyMap.MENU_CLEAR_FOCUS) {
            activeMenu.clearFocus();
            activeMenu = null;
        } // if
        
        if (keyCode == KeyMap.PLAYER_SKIP_TURN) {
            if (activeMenu == null) {
                world.waitTurn();
            } // if
        } // if
        
        // Exit game on Ctrl-W or Ctrl-Q
        if ( (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_Q) && ke.isControlDown()) {
            Game.stop();
        } // if
        
        if (keyCode == KeyEvent.VK_Y) {
            Game.inventory.gold += (int)( Math.random() * 15.0);
        }
    } // keyPressed

    @Override
    public void keyReleased(KeyEvent ke) {
        // unused
    }
} // InputHandler