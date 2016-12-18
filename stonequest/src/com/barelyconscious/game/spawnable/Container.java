/* *****************************************************************************
   * Project:           StoneQuest
   * File Name:         Container.java
   * Author:            Matt Schwartz
   * Date Created:      03.17.2013 
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Item;
import java.util.ArrayList;

public class Container extends Doodad {
    private ArrayList<Item> itemList = new ArrayList();
    
    public Container(String name, int x, int y, boolean locked, UIElement initialTile, UIElement spentTile) {
        super(name, x, y, locked, initialTile, spentTile);
    }
    
    /**
     * Fill the contents of the container with loot
     * @param itemContents the conents of the container
     */
    public void setContents(ArrayList<Item> itemContents) {
        for (Item item : itemContents) {
            itemList.add(item);
        }
    }
    
    @Override
    public void interact(Sprite interactee) {
        if (itemList == null || itemList.isEmpty()) {
//            Game.textLog.writeFormattedString("The " + getDisplayName() + " is empty.", Common.FONT_NULL_RGB);
        }
        
        else {
//            Game.textLog.writeFormattedString("You open the " + getDisplayName() + ".", Common.FONT_NULL_RGB);
//
//            Game.lootWindow.setItemList(itemList);
//            Game.lootWindow.setActive();
        }
        
//        super.interact();
    }
}
