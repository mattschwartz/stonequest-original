/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Item.java
 * Author:           Matt Schwartz
 * Date created:     07.04.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.player.StatBonus;
import java.awt.Color;

public class Item implements Comparable<Item> {
    public static final int USE             = 0;
    public static final int EXAMINE         = 1;
    public static final int DROP            = 2;
    public static final int SALVAGE         = 3;
    public static final int NUM_OPTIONS     = 4;
    
    public final int ITEM_NAME_MAX_LENGTH   = 45;
    public final int MAX_STAT_AFFIXES       = 6;
    
    protected int tileId; // used to draw the item's artwork to the screen
    protected int itemLevel;
    protected int sellValue;
    protected int stackSize;
    protected int numAffixes;
    protected String itemName;
    protected StatBonus[] itemAffixes = new StatBonus[MAX_STAT_AFFIXES];
    
    protected int rarityColorRGB;
    
    protected String[] options = {"use", "examine", "drop", "salvage"};
    protected String[] keybinds = {"E", "X", "D", "S"};
    protected String itemDescription = "Vendor fodder.";
    
    /**
     * Constructor for creating an item.  Called from within subclasses of more
     * specific item types.
     * @param name the name of the item
     * @param sellV the value a player can expect when selling the item to a
     * vendor
     * @param stack the amount of items in the item stack
     * @param tileId the ID of the tile to be drawn to the screen if the item is
     * dropped to the ground
     * @param affixes 
     */
    public Item(String name, int sellV, int stack, int tileId, StatBonus... affixes) {
        itemLevel   = 1;
        sellValue   = sellV;
        numAffixes  = affixes.length;
        rarityColorRGB = Common.COMMON_ITEM_COLOR_RGB;
        itemName    = name;
        stackSize   = stack;
        this.tileId = tileId;
        
        System.arraycopy(affixes, 0, itemAffixes, 0, numAffixes);
    } // constructor
    
    public Item(String name, int sellV, int id) {
        itemLevel   = 1;
        sellValue   = sellV;
        numAffixes  = 0;
        rarityColorRGB = Common.COMMON_ITEM_COLOR_RGB;
        itemName    = name;
        stackSize   = 1;
        tileId = id;
        itemAffixes = null;
    } // constructor
    
    public void setName(String name) {
        itemName = name;
    } // setName
    
    public String getInternalName() {
        return itemName;
    } // getInternalName
    
    public String getDisplayName() {
        String name = itemName;
        
        if (name.length() > ITEM_NAME_MAX_LENGTH) {
            return name.substring(0, ITEM_NAME_MAX_LENGTH - 2) + "..";
        } // if
        
        return name;
    } // getDisplayName

    /**
     * Whenever the Item gets called, toString is what is called.  This should be
     * set to whatever the player needs to know when the Item is consumed.
     * @return 
     */
    @Override
    public String toString() {
        return getDisplayName();
    } // toString
    
    public void setStackSize(int amount) {
        stackSize = amount;
    } // setStackSize
    
    public void adjustStackBy(int amount) {
        stackSize += amount;
    } // adjustStackBy
    
    public int getStackSize() {
        return stackSize;
    } // getStackSize
    
    public void setSellValue(int sellV) {
        sellValue = sellV;
    } // setSellValue
    
    public int getSellValue() {
        return sellValue;
    } // getSellValue
    
    public int getTileId() {
        return tileId;
    } // getTileId
    
    /**
     * This value should never be changed.  Method might be removed later.
     * @param ilvl 
     */
    public void setItemLevel(int ilvl) {
        itemLevel = ilvl;
    } // setItemLevel
    
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
    public void addAffix(StatBonus newAffix) {
        if (numAffixes == MAX_STAT_AFFIXES) {
            return;
        } // if
        
        itemAffixes[numAffixes++] = newAffix;
    } // addAffix
    
    /**
     * Remove an affix such as a curse, or possibly when failing at enchantment.
     */
    public void removeAffix() {
        
    } // removeAffix
    
    public StatBonus[] getAllAffixes() {
        StatBonus[] affixList = new StatBonus[numAffixes];
        System.arraycopy(itemAffixes, 0, affixList, 0, numAffixes);
        
        return affixList;
    } // getAllAffixes
    
    public StatBonus getAffixAt(int index) {
        return itemAffixes[index];
    } // getAffixAt
    
    public int getNumAffixes() {
        return numAffixes;
    } // getNumAffixes
    
    /**
     * The rarity color of the item.  Currently only shows in the text log.
     * @param rarity the new rarity
     */
    public void setRarityColor(int rarity) {
        rarityColorRGB = rarity;
    } // setRarityColor
    
    public int getRarityColor() {
        return rarityColorRGB;
    } // getRarityColor
    
    public String getOptionText(int optionNum) {
        return options[optionNum];
    } // getOptionText
    
    public String getOptionKeybind(int optionNum) {
        return keybinds[optionNum];
    } // getOptionShortcutKey
    
    public void setItemDescription(String desc) {
        itemDescription = desc;
    } // setItemDescription
    
    public String getItemDescription() {
        return itemDescription;
    } // getItemDescription

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
                if (this.getAffixAt(j).getStatId() != item.getAffixAt(j).getStatId() &&
                        this.getAffixAt(j).getStatMod() != this.getAffixAt(j).getStatMod()) {
                    return -1;
                } // if
            } // for
        } // else
        
        // Subclasses will override to add more constraints
        
        return 0;
    } // compareTo
} // Item