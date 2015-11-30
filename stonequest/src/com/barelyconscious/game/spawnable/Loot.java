/* *****************************************************************************
   * File Name:         Loot.java
   * Author:            Matt Schwartz
   * Date Created:      10.19.2012
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Projectile;
import java.util.ArrayList;
import java.util.Arrays;

public class Loot extends Sprite {
    private boolean removeOnWalk;
    public Item item;
    
    public Loot(Item item, int tileId, int x, int y) {
        super(item.getDisplayName(), tileId);
        super.setPosition(x, y);
        
        this.item = item;
    } // constructor
    
    public void setRemovableOnWalkover(boolean removable) {
        removeOnWalk = removable;
    } // setRemovableOnWalkover
    
    public Item getItem() {
        return item;
    } // getItem

    @Override
    public String getName() {
        if (item instanceof Projectile) {
            return item.getStackSize() + " " + super.getName();
        } // if 
        
        return super.getName();
    } // getDisplayName

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
            
            Game.textLog.writeFormattedString(message + " " + item.getDisplayName() + " here.", null, new LineElement(item.getDisplayName(), true, item.getRarityColor()));
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