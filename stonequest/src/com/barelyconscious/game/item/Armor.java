/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Armor.java
 * Author:           Matt Schwartz
 * Date created:     07.09.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: This class is the superclass of all armor-type items, such as:
 *                   Helmets
 *                   Chest pieces
 *                   Off hands like shields
 *                   Belts
 *                   Earrings
 *                   Greaves
 *                   Necklaces
 *                   Boots
 *                   Rings
 *                   All shared attributes exist in this class as well as shared
 *                   functions necessary for most every type of Armor:
 *         -     int slotId: the armor slot that the item fits each slot has a 
 *                    unique integer value
 *         -     int bonusArmor: most armor types give bonus armor to the player, 
 *                    which reduces the amount of physical damage entities can
 *                    deal to the player
 *         - boolean isEquipped: true if the player currently has the item 
 *                    equipped.  Only one piece of armor can fit in a slot at 
 *                    a time
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.AttributeMod;

public class Armor extends Item {

    private int slotId;
    private int bonusArmor;
    private boolean isEquipped;

    /**
     * Create a new piece of armor wearable by the player with the following parameters:
     *
     * @param name the name of the armor displayable to the player
     * @param sellV the value vendors place on the armor. When a player sells Items to the shop, this is the amount of
     * gold credited to them in return
     * @param armor any amount of bonus armor afforded by the Item
     * @param slotId where on the player this piece of armor fits
     * @param tileId the id corresponding to a Tile that will be drawn if the item is on the ground in the world
     * @param affixes if this is not null, is an array of StatBonuses which are bonuses to the player's attributes when
     * the piece of armor is worn
     */
    public Armor(String name, int sellV, int armor, int slotId, int tileId, AttributeMod... affixes) {
        super(name, sellV, 1, tileId, affixes);
        super.setItemDescription("Place " + armorIdToString(slotId) + " for best results.");
        super.options[USE] = "equip";

        this.slotId = slotId;
        bonusArmor = armor;
        isEquipped = false;
    } // constructor

    /**
     *
     * @return the armor slot id as an integer
     */
    public int getArmorType() {
        return slotId;
    } // getArmorType

    /**
     *
     * @return the amount of armor afforded by the armor
     */
    public int getBonusArmor() {
        return bonusArmor;
    } // getBonusArmor

    /**
     * Sets the isEquipped boolean variable to equipped
     *
     * @param equipped if true, the armor is worn by the player; if false, the armor is not being worn by the player
     */
    public void setEquipped(boolean equipped) {
        isEquipped = equipped;

        if (isEquipped) {
            options[USE] = "unequip";
        } // if
        else {
            options[USE] = "equip";
        } // else
    } // setEquipped

    /**
     * Equips or unequips the weapon, granting any attribute bonuses the Weapon carries to the Player.
     */
    @Override
    public void onUse() {
        if (isEquipped) {
            unequip();
            return;
        } // if

        Player player = Game.player;
        // Is the player already wielding a weapon?
        if (player.isArmorSlotEquipped(slotId)) {
            player.unequip(slotId);
        } // if

        equip();
    } // onUse

    /**
     * Equips the Weapon to the Player's self so that its benefits can be reaped; adding any attribute modifiers and the
     * bonus from its min/max damage.
     */
    public void equip() {
        Game.player.equip(slotId, this);
        isEquipped = true;

        for (AttributeMod mod : itemAffixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), mod.getAttributeModifier());
        } // for
    } // equip

    /**
     * Removes the Weapon from the player, as well as any attributes on the Weapon.
     */
    public void unequip() {
        Game.player.unequip(slotId);
        isEquipped = false;

        for (AttributeMod mod : itemAffixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), -mod.getAttributeModifier());
        } // for
    } // unequip

    /**
     *
     * @return if true, the armor is being worn by the player
     */
    public boolean isEquipped() {
        return isEquipped;
    } // isEquipped

    /**
     * Returns the name associated with a given equipment slot ID as a String
     *
     * @param armorId the slot id of a piece of armor
     * @return
     */
    private static String armorIdToString(int armorId) {
        switch (armorId) {
            case Player.NECK_SLOT_ID:
                return "around neck";
            case Player.HELM_SLOT_ID:
                return "on head";
            case Player.EARRING_SLOT_ID:
                return "in ear";
            case Player.CHEST_SLOT_ID:
                return "on chest";
            case Player.OFF_HAND_SLOT_ID:
                return "in off hand";
            case Player.BELT_SLOT_ID:
                return "around waist";
            case Player.GREAVES_SLOT_ID:
                return "on legs";
            case Player.RING_SLOT_ID:
                return "on finger";
            case Player.BOOTS_SLOT_ID:
                return "on feet";
            default:
                return "??";
        } // switch
    } // armorIdToString

    public static String armorTypeToString(int armorId) {
        switch (armorId) {
            case Player.NECK_SLOT_ID:
                return "necklace";
            case Player.HELM_SLOT_ID:
                return "helmet";
            case Player.EARRING_SLOT_ID:
                return "earring";
            case Player.CHEST_SLOT_ID:
                return "chest";
            case Player.OFF_HAND_SLOT_ID:
                return "off-hand";
            case Player.BELT_SLOT_ID:
                return "belt";
            case Player.GREAVES_SLOT_ID:
                return "greaves";
            case Player.RING_SLOT_ID:
                return "ring";
            case Player.BOOTS_SLOT_ID:
                return "boots";
            default:
                return "??";
        } // switch
    } // armorIdToString

    @Override
    public String toString() {
        switch (slotId) {
            case Player.NECK_SLOT_ID:
            case Player.HELM_SLOT_ID:
            case Player.EARRING_SLOT_ID:
            case Player.CHEST_SLOT_ID:
            case Player.OFF_HAND_SLOT_ID:
            case Player.BELT_SLOT_ID:
            case Player.RING_SLOT_ID:
                return "a " + super.getDisplayName();
            case Player.GREAVES_SLOT_ID:
            case Player.BOOTS_SLOT_ID:
                return "some " + super.getDisplayName();
            default:
                return "Papaya fruitsauce";
        } // switch
    } // toString

    /**
     * The compareTo functionality is used to compare to pieces of armor to each other for stacking purposes when the
     * Item is added to the player's inventory; since armor never stacks, compareTo returns -1 every time
     *
     * @param item the Item to compare to
     * @return
     */
    @Override
    public int compareTo(Item item) {
        return -1;
    } // compareTo
} // Armor
