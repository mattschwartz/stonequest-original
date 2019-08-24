/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        KeyHandler.java
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
import com.barelyconscious.game.input.KeyMap.Key;
import com.barelyconscious.game.menu.TextLog;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
    private ArrayList<Interactable> keyInput = new ArrayList();
    protected Key focusKey;
    
    /**
     * 
     * @param focusKey the key that causes focus to shift to the Interactable
     */
    public KeyHandler() {
        // Default key-mapping
        KeyMap.changeMapping(KeyMap.MODERN_MAPPING);
    } // constructor
    
    public void setFocusKey(Key key) {
        focusKey = key;
    } // setFocusKey
    
    public void registerKeyInputListener(Interactable keyable) {
        this.keyInput.add(keyable);
    } // 

    @Override
    public void keyTyped(KeyEvent ke) {
        // unused
    } // keyTyped

    @Override
    public void keyPressed(KeyEvent ke) {
        boolean worldFocus = true;
        int keyCode = ke.getKeyCode();
        
        if ( (keyCode == 'Z') && ke.isAltDown()) {
            Game.screen.toggleUI();
        }
        
        if ( (keyCode == 'W' || keyCode == 'Q') && ke.isControlDown()) {
            Game.Stop();
        } // if
        
        for (Interactable interactable : keyInput) {
            if (KeyMap.isFocusKey(keyCode)) {
                if (interactable.focusKeyPressed(keyCode)) {
                    worldFocus = false;
                    interactable.setActive();
                } // if
                
                else {
                    interactable.clearFocus();
                }
            } // if
            
            else if (keyCode == KeyMap.getKeyCode(Key.CLEAR_FOCUS)) {
                interactable.clearFocus();
            } // else if
            
            else if (interactable instanceof TextLog) {
                interactable.keyPressed(keyCode);
            } // else if
            
            else if (interactable.isActive()) {
                worldFocus = false;
                interactable.keyPressed(keyCode);
            } // else if
        } // for
        
        if (worldFocus) {
            Game.world.keyPressed(keyCode);
        } // if
        
//        int keyCode = ke.getKeyCode();
//        
//        /* Question Marks don't exist in the virtual keyboard supported
//            by java's KeyEvent class. */
//        if (ke.getKeyChar() == KeyMap.getKeyCode(Key.OPEN_HELP_MENU)) {
//            log.writeFormattedString("And who would help you?", Common.FONT_DEFAULT_RGB);
//        } // if
//        
//        // Long sequence of ifs because switch statements don't work with non-final variables
//        if (keyCode == KeyMap.getKeyCode(Key.OPEN_SKILL_TAB)) {
//            Game.screen.setMenu(attributesMenu);
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.OPEN_INVENTORY)) {
//            Game.screen.setMenu(invenMenu);
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.MENU_MOVE_UP)) {
//            if (Game.screen.getFocusedMenu() != null && Game.screen.getFocusedMenu().isActive()) {
//                Game.screen.getFocusedMenu().moveUp();
//            } // if
//            
//            else {
//                world.move(ke.isShiftDown(), 0, -1);
//            } // else
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_UP)) {
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                world.move(ke.isShiftDown(), 0, -1);
//            } // if
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.MENU_MOVE_DOWN)) {
//            if (Game.screen.getFocusedMenu() != null && Game.screen.getFocusedMenu().isActive()) {
//                Game.screen.getFocusedMenu().moveDown();
//            } // if
//            
//            else {
//                world.move(ke.isShiftDown(), 0, 1);
//            } // else
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_DOWN)) {
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                world.move(ke.isShiftDown(), 0, 1);
//            } // if
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_LEFT) || 
//                keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_LEFT_ALT)) {
//            
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                world.move(ke.isShiftDown(), -1, 0);
//            } // if
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_RIGHT) || 
//                keyCode == KeyMap.getKeyCode(Key.PLAYER_MOVE_RIGHT_ALT)) {
//            
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                world.move(ke.isShiftDown(), 1, 0);
//            } // if
//        } // if
//        
//        /* Scroll up in the TextLog regardless of which menu has focus, if any. */
//        if (keyCode == KeyMap.getKeyCode(Key.TEXT_LOG_SCROLL_UP)) {
//            log.moveUp();
//        } // if
//        
//        /* Scroll down in the TextLog regardless of which menu has focus, if any. */
//        if (keyCode == KeyMap.getKeyCode(Key.TEXT_LOG_SCROLL_DOWN)) {
//            log.moveDown();
//        } // if
//        
//        if (Game.screen.getFocusedMenu() != null 
//                && Game.screen.getFocusedMenu() instanceof InventoryMenu 
//                && Game.screen.getFocusedMenu().isActive()) {
//            
//            if (keyCode == KeyMap.getKeyCode(Key.SALVAGE_ITEM)) {
//                invenMenu.salvageItem();
//            } // if
//            
//            else if (keyCode == KeyMap.getKeyCode(Key.EXAMINE_ITEM)) {
//                invenMenu.examineItem();
//            } // if
//            
//            else if (keyCode == KeyMap.getKeyCode(Key.USE_ITEM)) {
//                invenMenu.useItem();
//            } // if
//            
//            else if (keyCode == KeyMap.getKeyCode(Key.DROP_ITEM)) {
//                invenMenu.dropItem();
//            } // if
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PICKUP_ITEM)) {
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                Game.screen.setMenu(lootMenu);
//                world.pickUpItem();
//            } // if
//
//            else if (Game.screen.getFocusedMenu() != null && Game.screen.getFocusedMenu() instanceof LootPickupMenu) {
//                Game.screen.getFocusedMenu().select();
//            } // else if
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.MENU_SELECT)) {
//            if (Game.screen.getFocusedMenu() != null && Game.screen.getFocusedMenu().isActive()) {
//                Game.screen.getFocusedMenu().select();
//            } // if
//        } // if
//        
//        if (Game.screen.getFocusedMenu() != null && keyCode == KeyMap.getKeyCode(Key.CLEAR_FOCUS)) {
//            Game.screen.getFocusedMenu().clearFocus();
//            miniMap.setActive();
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.PLAYER_SKIP_TURN)) {
//            if (Game.screen.getFocusedMenu() == null || !Game.screen.getFocusedMenu().isActive()) {
//                world.waitTurn();
//            } // if
//        } // if
//        
//        // Exit game on Ctrl-W or Ctrl-Q
//        if ( (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_Q) && ke.isControlDown()) {
//            Game.Stop();
//        } // if
//        
//        if (keyCode == KeyMap.getKeyCode(Key.ZONE_INFO)) {
//            world.displayZoneInfo();
//        } // if
//        
//        if (keyCode == KeyEvent.VK_9) {
//            Game.player.levelUp();
//        }
    } // keyPressed

    @Override
    public void keyReleased(KeyEvent ke) {
        // unused
    }
//
//    private void testActiveMenu() {
//        if (invenMenu.isActive()) {
//            activeMenu = invenMenu;
//        } // if
//        
//        else if (attributesMenu.isActive()) {
//            activeMenu = attributesMenu;
//        } // else if
//        
//        else if (lootMenu.isActive()) {
//            activeMenu = lootMenu;
//        } // else if
//        
//        else {
//            activeMenu = null;
//        } // else
//    } // testActiveMenu
} // KeyHandler
