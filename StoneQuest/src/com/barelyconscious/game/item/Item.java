/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        Item.java
 * Author:           Matt Schwartz
 * Date created:     07.04.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: The superclass which every Item must implement.  An item is
                     an object that the player comes across in the game and 
                     serves some purpose or functionality.  Example Items and their
                     general purpose:
                     Weapons and Armor: extend the attack abilities of the player
                     Scrolls: provides a number of benefits and powers for the 
                      player when consumed
                     Potions: provide temporary changes in player attributes and
                      can be beneficial or harmful
               + int USE: option to use, wear, quaff, eat, etc the Item
               + int EXAMINE: option to examine the item
               + int DROP: option to drop the item
               + int SALVAGE: option to salvage the item
               + int NUM_OPTIONS: the number of available options
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.player.AttributeMod;
import java.util.ArrayList;
import java.util.Arrays;

public class Item_2 implements Comparable<Item> {
    /* Various options the player has access to for each item */
    public static final int USE             = 0;
    public static final int EXAMINE         = 1;
    public static final int DROP            = 2;
    public static final int SALVAGE         = 3;
    public static final int NUM_OPTIONS     = 4;
    
    /** The maximum number of attribute mods that can be present on a single
        item. */
    public final int MAX_STAT_AFFIXES       = 12;
    
    /** The id of the Tile that is drawn when the item's artwork is requested
        in the game. */
    protected int tileId; 
    
    /** The Item level associated with each item which determines whether certain 
        attributes can occur on the item, how high the values can go as well as how
        much vendors are willing to give for that item. */
    protected int itemLevel;
    
    /** The value in gold that vendors will give the player in exchange for the
        item. */
    protected int sellValue;
    
    /** How many items exist on top of each other. */
    protected int stackSize;
    
    /** The displayName of the item visible to the player. */
    protected String itemName;
    
    /** An array of AttributeMod's which are attribute mods; these bonuses affect
        the player's attributes when the item is used. */
    protected ArrayList<AttributeMod> itemAffixes;
    
    /** The RGB color value of the rarity of the item. */
    protected int rarityColorRGB;
    
    /** Strings that are printed when the item is examined in the ToolTip menu
        so the player knows which keybind does what. */
    protected String[] options = {"use", "examine", "drop", "salvage"};
    
    /** The keybinds for the various item options. */
    protected String[] keybinds = {"E", "X", "D", "S"};
    
    /** The text that is written to the TextLog when the player examines the
        Item; many items change the description to something more descriptive
        thus "Vendor fodder." is the default value if an Item is merely junk,
        to be sold to a vendor in exchange for gold. */
    protected String itemDescription = "Vendor fodder.";
    
    /**
     * Constructor for creating an item.  Called from within subclasses of more
     * specific item types.
     * @param name the displayName of the item
     * @param sellV the value a player can expect when selling the item to a
     * vendor
     * @param stack the amount of items in the item stack
     * @param tileId the ID of the tile to be drawn to the screen if the item is
     * dropped to the ground
     * @param affixes 
     */
    public Item(String name, int sellV, int stack, int tileId, AttributeMod... affixes) {
        itemLevel   = 1;
        sellValue   = sellV;
        rarityColorRGB = Common.ITEM_RARITY_COMMON_RGB;
        itemName    = name;
        stackSize   = stack;
        this.tileId = tileId;
        
        itemAffixes = new ArrayList(Arrays.asList(affixes));
    } // constructor
    
    public Item(String name, int sellV, int id) {
        itemLevel   = 1;
        sellValue   = sellV;
        rarityColorRGB = Common.ITEM_RARITY_COMMON_RGB;
        itemName    = name;
        stackSize   = 1;
        tileId = id;
        itemAffixes = null;
    } // constructor
    
    /**
     * Changes the displayName visible to the player to displayName
     * @param name the new displayName of the item
     */
    public void setName(String name) {
        itemName = name;
    } // setName
    
    /**
     * Internal names are not visible to the player in any way (ideally); this may
     * be replaced by item ids...
     * @return the item's internal displayName
     */
    public String getInternalName() {
        return itemName;
    } // getInternalName
    
    /**
     * The displayName visible to the player; if the displayName of the item is too long for
     * the inventory menu, it is truncated to fit
     * @return the item's visible displayName
     */
    public String getDisplayName() {
        return itemName;
    } // getDisplayName
    
    /**
     * Set the stack size of the item to amount
     * @param amount the new stack size of the item
     */
    public void setStackSize(int amount) {
        stackSize = amount;
    } // setStackSize
    
    /**
     * Adjust the stack size of the item by amount; useful when a player drops
     * or picks up one item
     * @param amount the adjustment for the item stack size
     */
    public void adjustStackBy(int amount) {
        stackSize += amount;
    } // adjustStackBy
    
    /**
     * 
     * @return the stack size for the item
     */
    public int getStackSize() {
        return stackSize;
    } // getStackSize
    
    /**
     * Change the sell value for the item; used when a player enchants an item,
     * which can increase or decrease its worth to vendors
     * @param sellV the new sell value of the item
     */
    public void setSellValue(int sellV) {
        sellValue = sellV;
    } // setSellValue
    
    /**
     * 
     * @return the item's vendor value in gold
     */
    public int getSellValue() {
        return sellValue;
    } // getSellValue
    
    /**
     * 
     * @return the id for the Tile that represents the item's artwork
     */
    public int getTileId() {
        return tileId;
    } // getTileId
    
    /**
     * Change the item level of the item; used when a player enchants the item,
     * decreasing or increasing its worth and Item level
     * @param ilvl the item's new item level
     */
    public void setItemLevel(int ilvl) {
        itemLevel = ilvl;
    } // setItemLevel
    
    /**
     * 
     * @return the item's item level
     */
    public int getItemLevel() {
        return itemLevel;
    } // getItemLevel
    
    /**
     * Possibly used for enchanting?  Currently not used.  Perhaps some form of
     * cursing and removing cursing on Items?  Such as unremovable, heaviness,
     * bloodloss, health drain, poisonous, etc.  Would require some type of scroll
     * to remove the affix.
     * @param newAffix 
     */
    public void addAffix(AttributeMod newAffix) {
        itemAffixes.add(newAffix);
    } // addAffix
    
    /**
     * Remove affix at index; used when a player fails enchanting, or when removing
     * curses placed on the item
     * @param index the index of the affix to be removed
     */
    public void removeAffix(int index) {
        
    } // removeAffix
    
    /**
     * Search through the list of AttributeMod's and remove the attribute mod equivalent 
     * to attrMod if one exists; used when a player fails enchanting, or when removing
     * curses placed on the item
     * @param attrMod the attribute mod to search for and remove if found
     */
    public void removeAffix(AttributeMod attrMod) {
        
    } // removeAffix
    
    /**
     * Return a list of attribute mods in the form of an array of AttributeMod's
     * @return the array of AttributeMod's
     */
    public AttributeMod[] getAllAffixes() {
        return (AttributeMod[])itemAffixes.toArray();
    } // getAllAffixes
    
    /**
     * Return a AttributeMod at index
     * @param index the index to return
     * @return the AttributeMod at index or null if index is larger than the number
     * of attribute mods on the item
     */
    public AttributeMod getAffixAt(int index) {
        if (index >= itemAffixes.size()) return null;
        return itemAffixes.get(index);
    } // getAffixAt
    
    /**
     * 
     * @return the number of attribute mods on the item
     */
    public int getNumAffixes() {
        return itemAffixes == null ? 0 : itemAffixes.size();
    } // getNumAffixes
    
    /**
     * Change the rarity RGB color value of the rarity associated with each item
     * @param rarity the new rarity
     */
    public void setRarityColor(int rarity) {
        rarityColorRGB = rarity;
    } // setRarityColor
    
    /**
     * 
     * @return the RGB color value of the rarity of the item
     */
    public int getRarityColor() {
        return rarityColorRGB;
    } // getRarityColor
    
    /**
     * Called to translate an integer (optionNum) into a String for display; 
     * different Items call the "use" option different things to be more 
     * descriptive as to what "using" the item will do; for instance, "eat"ing
     * food
     * @param optionNum the integer value of the requested option 
     * @return the String representation of the option number
     */
    public String getOptionText(int optionNum) {
        return options[optionNum];
    } // getOptionText
    
    /**
     * Return the key which should be pressed in order to activate the option
     * @param optionNum the integer value for the option in question
     * @return the keybind for that option
     */
    public String getOptionKeybind(int optionNum) {
        return keybinds[optionNum];
    } // getOptionShortcutKey
    
    /**
     * Change the Item's description, which is printed to the TextLog whenever
     * the Item is examined
     * @param newDescription the new item description text
     */
    public void setItemDescription(String newDescription) {
        itemDescription = newDescription;
    } // setItemDescription
    
    /**
     * 
     * @return the item's description text as a String
     */
    public String getItemDescription() {
        return itemDescription;
    } // getItemDescription
    
    public void onUse() {
        System.err.println(" [NOTIFY] This item has no use!  Is it junk?");
    } // onUse

    /**
     * The compareTo functionality is used to compare two items to each
     * other for stacking purposes when the Item is added to the player's 
     * inventory; subclasses of Item compare other features 
     * @param item the Item to compare to
     * @return -1 if the Items are different and 0 if the two Items are identical
     */
    @Override
    public int compareTo(Item item) {
        // Items names must be equal
        if (!this.getInternalName().equals(item.getInternalName())) {
            return -1;
        } // if

        // Items must sell for the same value
        if (this.getSellValue() != item.getSellValue()) {
            return -1;
        } // if

        // Item must have the same number of affixes 
        if (this.getNumAffixes() != item.getNumAffixes()) {
            return -1;
        } // if
        // and the same affixes
        else {
            for (int j = 0; j < this.getNumAffixes(); j++) {
                if (this.getAffixAt(j).getAttributeId() != item.getAffixAt(j).getAttributeId() &&
                        this.getAffixAt(j).getAttributeModifier() != this.getAffixAt(j).getAttributeModifier()) {
                    return -1;
                } // if
            } // for
        } // else
        
        // Subclasses will override to add more constraints
        
        return 0;
    } // compareTo
} // Item
