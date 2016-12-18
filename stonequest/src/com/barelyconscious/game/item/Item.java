/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Item.java
 * Author:           Matt Schwartz
 * Date created:     09.04.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Items are "physical" (in the sense that Entities can 
 *                   interact with them) objects that exist within StoneQuest. 
 *                   Items serve many different purposes and give users a 
 *                   meaningful experience within StoneQuest.
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.util.StringHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Items are "physical" (in the sense that Entities can interact with them)
 * objects that exist within StoneQuest. Items serve many different purposes and
 * give users a meaningful experience within StoneQuest.
 * <br>
 * <br>
 * Some example items:
 * <ul>
 * <li>Weapons</li>
 * <li>Armor</li>
 * <li>Food</li>
 * <li>Potions</li>
 * <li>Salvage</li>
 * <li>Junk</li>
 * <li>Scrolls</li>
 * </ul>
 * <br>
 * <br>
 * Every Item can also be broken down to its basic materials through the act of
 * Salvaging. Salvage can then be applied to other items through the Item
 * Upgrade system in order to improve that Item.
 *
 * @author Matt Schwartz
 */
public class Item implements Comparable<Item> {

    public static final String ITEM_ROOT_FILE_PATH = "/gfx/tiles/sprites/items/";
    public static final int MAX_UPGRADE_LEVEL = 20;
    /**
     * The Item level associated with each item which determines whether certain
     * attributes can occur on the item, how high the values can go as well as
     * how much vendors are willing to give for that item.
     */
    protected int itemLevel;
    /**
     * The upgraded level of the item, which at level 0 represents an Item that
     * has not been upgraded. Through the Upgrade Item system, an Item's
     * upgradeLevel will be increased, providing improved benefits. An Item can
     * only be upgraded up to a certain level.
     */
    protected int upgradeLevel;
    /**
     * The amount of salvage that has been applied to the item so far.
     */
    protected int appliedSalvage;
    protected int sellValue;
    protected int stackSize;
    protected String name;
    protected List<AttributeMod> itemAffixes = null;
    protected UIElement icon;
    protected Entity owner;
    private Augment augment;
    private DivineFavor divineFavor;

    /**
     * An Item is a physical (in the sense that Entities can interact with them)
     * object within StoneQuest. Instantiates a new Item with the following
     * properties:
     *
     * @param name the name of the Item (this is what the Player will see the
     * Item as)
     * @param itemLevel the level of the Item, the value of which proposes
     * certain limitations on the Item
     * @param sellValue the value in gold that vendors are willing to offer the
     * Player for the Item
     * @param stackSize how many of the Item exist in the stack. Consumables
     * must reduce this value to 0 before they will be removed from the Entity's
     * inventory
     * @param icon the object rendered to the screen
     * @param owner the "owning" Entity of the Item. Whenever the Item is used,
     * this Entity is who benefits from it
     * @param itemAffixes if non null, refers to the Entity's attributes that
     * are affected when the Item is used
     */
    public Item(String name, int itemLevel, int sellValue, int stackSize, UIElement icon, Entity owner, AttributeMod... itemAffixes) {
        this.name = name;
        this.itemLevel = itemLevel;
        this.sellValue = sellValue;
        this.stackSize = stackSize;
        this.icon = icon;
        this.owner = owner;
        upgradeLevel = 0;
        appliedSalvage = 0;

        if (itemAffixes != null) {
            this.itemAffixes = new ArrayList<AttributeMod>();
            this.itemAffixes.addAll(Arrays.asList(itemAffixes));
        }
    }

    public Item(String name, int itemLevel, int sellValue, int stackSize, String iconLocation, Entity owner, AttributeMod... itemAffixes) {
        this(name, itemLevel, sellValue, stackSize, UIElement.createUIElement(iconLocation), owner, itemAffixes);
    }

    /**
     *
     * @return the Item's level as an integer
     */
    public int getItemLevel() {
        return itemLevel;
    }

    /**
     *
     * @return the Item's current upgrade level as an integer
     */
    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    /**
     *
     * @return the current amount of salvage that has been applied to the item
     */
    public int getAppliedSalvage() {
        return appliedSalvage;
    }

    /**
     * Applies amount salvage to the Item. When the amount of salvage applied to
     * an Item reaches a specific point, the Item's upgrade level will increase
     * and its power will increase, providing additional benefits.
     *
     * @param amount
     */
    public void applySalvage(int amount) {
        if (upgradeLevel < MAX_UPGRADE_LEVEL && appliedSalvage + amount >= getRequiredSalvage()) {
            amount = getRequiredSalvage() - appliedSalvage;
            appliedSalvage = amount;
            upgrade();
        }
        else {
            appliedSalvage += amount;
        }
    }

    /**
     * Determines the total amount of salvage necessary to upgrade an Item's
     * upgrade level.
     *
     * @return the total amount of salvage necessary to upgrade the Item to the
     * next level as an integer
     */
    public int getRequiredSalvage() {
        // FORMULA NOT FINAL
        return Math.max(MAX_UPGRADE_LEVEL, (upgradeLevel + 1)) * 20;
    }

    /**
     * Upgrades the Item to the next upgrade level, applying additional
     * benefits. Is called internally when sufficient salvage is applied to the
     * item and is overriden by subclasses of Item to provide functionality for
     * unique benefits per Item.
     */
    protected void upgrade() {
        upgradeLevel++;
    }

    /**
     *
     * @return the value for which the Item sells to vendors
     */
    public int getSellValue() {
        return sellValue;
    }

    /**
     *
     * @return the current stack size of the Item as an integer
     */
    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    /**
     * Adjust the current stack size of the Item by amount.
     *
     * @param amount the stack size adjustment
     * @return the new stack size of the Item
     */
    public void adjustStackBy(int amount) {
        stackSize = Math.max(0, stackSize + amount);

        if (stackSize == 0) {
            // Removes itself from the owner's inventory
//            owner.removeFromInventory(this);
        }
    }

    /**
     *
     * @return the name of the Item as a String
     */
    public String getName() {
        return name;
    }

    public void setAugment(Augment augment) {
        this.augment = augment;
    }

    public Item getAugment() {
        return augment;
    }

    public void setDivineFavor(DivineFavor divineFavor) {
        this.divineFavor = divineFavor;
    }

    public DivineFavor getDivineFavor() {
        return divineFavor;
    }

    /**
     * Sets the name of the Item to newName, overwriting the old value for the
     * name of the Item.
     *
     * @param newName the new name of the Item
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     *
     * @return a copy of the Item's affixes in the form of an array
     */
    public AttributeMod[] getItemAffixes() {
        if (itemAffixes == null) {
            return null;
        }

        return itemAffixes.toArray(new AttributeMod[]{});
    }

    /**
     *
     * @param index the index of the itemAffix list to get
     * @return the AttributeMod at index of the list of Item affixes associated
     * with this Item or null if index is invalid
     */
    public AttributeMod getItemAffixAt(int index) {
        if (itemAffixes == null || index < 0 || index >= itemAffixes.size()) {
            return null;
        }

        return itemAffixes.get(index);
    }

    /**
     *
     * @return the number of Item affixes, or 0 if there are none
     */
    public int getNumAffixes() {

        if (itemAffixes == null) {
            return 0;
        }

        return itemAffixes.size();
    }

    /**
     * This method should be overridden by subclasses and altered to more
     * appropriately fit the Item.
     *
     * @return the description associated with this Item as a String
     */
    public String getDescription() {
        return "You see " + StringHelper.aOrAn(name) + " here.";
    }
    
    public String getMaterial() {
        Random ran = new Random(name.hashCode());
        return new String[]{"cloth", "leather", "metal", "magical", "celestial", "ethereal"}[ran.nextInt(5)];
    }

    public String getType() {
        return "Not your type.";
    }

    /**
     * This method is called when the owner (an Entity object) uses the Item.
     * This method should be overriden by subclasses to provide additional
     * unique benefits.
     */
    public void onUse() {
    }

    /**
     * Method is called when the Item is removed from the Entity's inventory,
     * such as removing beneficial stats, unequipping the item from the Entity
     * and so on.
     */
    public void onDrop() {
    }

    @Override
    public Item clone() {
        return null;
    }

    /**
     * Every Item can be broken down to its basic materials through the act of
     * Salvaging. These materials can then be applied to other Items in the game
     * to improve them.
     */
    public void salvage() {
    }

    /**
     * Renders the Item's icon at x,y on the screen.
     *
     * @param screen the Screen to render the Item's icon to
     * @param x the x starting coordinate of where the Item should be rendered
     * @param y the y starting coordinate of where the Item should be rendered
     */
    public void render(int x, int y) {
        icon.render(x, y);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Compares this Item to another to see if they are equivalent.
     *
     * @param item the Item to be compared to
     * @return 0 if the Items are equivalent; -1 if they are not
     */
    @Override
    public int compareTo(Item item) {
        // In order for two Items to be considered equal, they must both...
        // ...have the same name
        if (!name.equals(item.getName())) {
            return -1;
        }

        // ...have the same number of affixes (or none)
        if (getNumAffixes() != item.getNumAffixes()) {
            return -1;
        }
        else {
            for (int i = 0; i < getNumAffixes(); i++) {
                if (getItemAffixAt(i).compareTo(item.getItemAffixAt(i)) != 0) {
                    return -1;
                }
            }
        }

        // Subclasses will override to add more constraints
        return 0;
    }
}
