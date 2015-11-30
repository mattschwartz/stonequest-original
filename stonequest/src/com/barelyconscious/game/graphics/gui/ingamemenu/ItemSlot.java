/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         ItemSlot.java
   * Author:            Matt Schwartz
   * Date Created:      04.06.2014 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
   *                    this file.  You are not allowed to take credit for code
   *                    that was not written fully by yourself, or to remove 
   *                    credit from code that was not written fully by yourself.  
   *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.graphics.gui.Component;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemSlot extends Interactable implements Component {

    protected static final UIElement ITEM_SLOT_BACKGROUND = UIElement.createUIElement("/gfx/gui/components/itemSlot/itemSlot.png");
    private int x;
    private int y;
    private int width;
    private int height;
    private Item item;
    public List<Item> acceptableItems = new ArrayList<Item>();
    
    public ItemSlot() {
        
    } // constructor
    
    public boolean itemGoesHere(Item item) {
        for (Item i : acceptableItems) {
            if (item.getClass() == i.getClass()) {
                return true;
            } // if
        } // for
        
        return false;
    } // itemGoesHere
    
    public Item setItem(Item item) {
        Item oldItem = this.item;
        
        if (itemGoesHere(item)) {
            this.item = item;
            return oldItem;
        } // if
        
        return null;
    } // setItem
    
    public Item getItem() {
        return this.item;
    } // getItem
    
    @Override
    public int getX() {
        return x;
    } // getX

    @Override
    public void setX(int newX) {
        this.x = newX;
    } // setX

    @Override
    public int getY() {
        return y;
    } // getY

    @Override
    public void setY(int newY) {
        this.y = newY;
    } // setY

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    @Override
    public int getHeight() {
        return height;
    } // getHeight

    @Override
    public void dispose() {
    } // dispose

    @Override
    public boolean shouldRemove() {
        return false;
    } // shouldRemove

    @Override
    public void render() {
    } // render
} // ItemSlot
