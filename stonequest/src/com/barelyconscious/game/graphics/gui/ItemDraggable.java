/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ItemDraggable.java
 * Author:           Matt Schwartz
 * Date created:     11.05.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;

public class ItemDraggable extends Interactable implements Components {
    private final int ITEM_WIDTH = 47;
    private final int ITEM_HEIGHT = ITEM_WIDTH;
    private Item itemOnCursor;
    private boolean destroy;
    
    public ItemDraggable(Item item) {
        itemOnCursor = item;
    } // constructor
    
    public Item getItem() {
        return itemOnCursor;
    } // getItem
    
    public void setItem(Item item) {
        itemOnCursor = item;
        
        if (itemOnCursor == null) {
            dispose();
        }
//        Item oldItem = itemOnCursor;
//        itemOnCursor = item;
//        destroy = false;
//        
//        if (oldItem == null) {
//            Game.screen.addAlwaysOnTopComponent(this);
//        }
//        
//        return oldItem;
    } // setItem

    /**
     * User has relinquished hold of the Item; now we need to decide where to 
     * place it.
     */
    @Override
    public void mouseReleased() {
        super.mouseReleased(); //To change body of generated methods, choose Tools | Templates.
    } // mouseReleased

    @Override
    public void render(Screen screen) {
        int x, y;
        x = Math.max(0, Math.min(Game.getGameWidth() - ITEM_WIDTH, Game.mouseHandler.getMouseX()));
        y = Math.max(0, Math.min(Game.getGameHeight() - ITEM_HEIGHT, Game.mouseHandler.getMouseY()));
        
        itemOnCursor.render(screen, x, y);
    } // render

    @Override
    public int getX() {
        return -1;
    }

    @Override
    public int getY() {
        return -1;
    }

    @Override
    public void setX(int newX) {
    }

    @Override
    public void setY(int newY) {
    }

    @Override
    public int getWidth() {
        return ITEM_WIDTH;
    }

    @Override
    public int getHeight() {
        return ITEM_HEIGHT;
    }

    public void enable() {
        destroy = false;
    }
    
    @Override
    public void dispose() {
        destroy = true;
    }

    @Override
    public boolean shouldRemove() {
        return destroy;
    }
} // ItemDraggable
