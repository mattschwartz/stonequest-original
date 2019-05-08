/* *****************************************************************************
   * File Name:         Loot.java
   * Author:            Matt Schwartz
   * Date Created:      10.19.2012
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  Loot are Items that exist in the World and can be interacted
                        with by the Player either by walking over the Loot or by
                        actively ineracting with it by pressing the pick up
                        keybind.  
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.Item;
import java.util.ArrayList;
import java.util.Arrays;

public class Loot extends Sprite {
    private boolean removeOnWalk;
    private Item item;
    
    /**
     * Create a new Loot object with the following parameters
     * @param item the Item that the Loot object is representing
     * @param x the x location for the Loot object
     * @param y the y location for the Loot object
     */
    public Loot(Item item, int x, int y) {
        super(item.getDisplayName(), item.getTileId());
        super.setPosition(x, y);
        
        this.item = item;
    } // constructor
    
    /**
     * If true, the Loot object is removed when the Player steps on top of it
     * @param removable the new value for the boolean that tests whether the Loot
     * should be removed when the Player walks over it
     */
    public void setRemovableOnWalkover(boolean removable) {
        removeOnWalk = removable;
    } // setRemovableOnWalkover
    
    /**
     * 
     * @return the Item associated with the Loot object
     */
    public Item getItem() {
        return item;
    } // getItem

    /**
     * 
     * @return the Tile artwork for the Item based on its TileId
     */
    @Override
    public Tile getTile() {
        return Tile.getTile(item.getTileId());
    } // getTile
    
    /**
     * This method is called when the player walks over Loot.  It prints a message
     * to the text log informing the player of the Loot's existence and in the
     * case of some items, automatically loots the item.
     * @return 
     */
    @Override
    public void onWalkOver() {
        String message = "There is a";
        ArrayList<Character> vowels = new ArrayList(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        
        if (removeOnWalk) {
            interact();
            Game.world.removeLoot(this);
        } // if
        else {
            if (vowels.contains(item.getDisplayName().toLowerCase().charAt(0))) {
                message += "n";
            } // if
            
            Game.textLog.writeFormattedString(message + " " + item.getDisplayName() + " here.", Common.FONT_NULL_RGB, new LineElement(item.getDisplayName(), true, item.getRarityColor()));
        } // else
    } // checkCanRemove
    
    /**
     * Adds the item to the player's inventory and writes a "loot message" to the
     * text log.
     */
    @Override
    public void interact() {
        Game.inventory.addItem(item);
        Game.textLog.writeLootMessage(item);
//        Sound.LOOT_ITEM.play();
        remove();
    } // interact

    @Override
    public String toString() {
        return item.getDisplayName();
    } // toString
} // Loot
