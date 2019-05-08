/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Weapon.java
 * Author:           Matt Schwartz
 * Date created:     07.27.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A Weapon is a subclass of Item and superclass to all Weapons
 *                   in the game.  Weapons extend the physical attacks of a Player
 *                   by increasing damage dealt (amongst other things) to an Entity.
 *                   Weapons have at minimum:
 *                  -Minimum damage bonus: the smallest hit before damage multipliers
 *                    that the weapon can deal
 *                  -Maximum damage bonus: the largest hit before damage multipliers
 *                    that the weapon can deal
 *                   NYI:
 *                   Weapon augmentations: weapons can be enchanted to provide 
 *                   additional effects when an Entity is struck by the weapon:
 *                   burning - Entities are lit ablaze, dealing Fire damage over 
 *                             time; damage is affected by the Player's Fire damage
 *                             bonus.
 *                  freezing - Entities are slowed when hit by a freezing weapon,
 *                             causing them to have a % chance not to move or 
 *                             attack when the game updates.  Slowed Entities 
 *                             hit repeatedly by a freezing weapon will become 
 *                             frozen and cannot move or attack until they thaw.
 *                     bleed - Entities struck by bleed-causing weapons will bleed
 *                             out, dealing physical damage over time.
 *               teleporting - Entities struck by teleporting weapons will occasionally
 *                             be transported to another Tile on the Map.
 *                   Weapon types: different types of weapons have type-specific
 *                   benefits and are not considered an augment:
 *                     heavy - Heavy weapons have a chance to Stun an Entity, 
 *                             preventing it from updating for 1-2 ticks() but
 *                             the Player cannot wield a shield with a heavy weapon
 *                     quick - Quick weapons have a chance to strike multiple
 *                             times in a single attack.  Quick weapons deal less
 *                             damage than most weapons, but the Player can wield
 *                             two quick weapons at once.
 *               retaliating - Retaliating weapons have a chance to parry incoming
 *                             attacks at reduced (up to some percentage) and 
 *                             attack the Entity for 50% weapon damage.  Retaliating
 *                             weapons can only be held in the Player's main hand.
 *                             
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.Player;

public class Weapon extends Item {

    public enum WeaponType {

        HEAVY,
        QUICK,
        RETALIATING
    };
    private WeaponType weaponType;
    private double minimumDamageBonus;
    private double maximumDamageBonus;
    private boolean isEquipped;

    /**
     * Creates a new Weapon with the following parameters
     *
     * @param name the name visible to the Player of the Weapon
     * @param sellV the value in gold that vendors will give to the player in exchange for the Weapon
     * @param min the minimum damage before multipliers that the Weapon can deal
     * @param max the maximum damage before multipliers that the Weapon can deal
     * @param weaponType [NYI] the type of weapon which determines how the weapon can be used as well as restrictions of
     * the weapon
     * @param effects any attribute mods present on the Weapon
     */
    public Weapon(String name, int sellV, double min, double max, int tileId, AttributeMod... effects) {
        super(name, sellV, 1, tileId, effects);
        super.setItemDescription("Hold the pointy end away from you and you'll be fine. Swing wildly for best results.");
        super.options[USE] = "equip";

        minimumDamageBonus = min;
        maximumDamageBonus = max;
        isEquipped = false;
        weaponType = WeaponType.HEAVY;
    } // constructor

    public WeaponType getWeaponType() {
        return weaponType;
    } // getWeaponType

    public String weaponTypeToString(WeaponType weaponType) {
        if (weaponType == WeaponType.HEAVY) {
            return "heavy";
        } // if
        else if (weaponType == WeaponType.QUICK) {
            return "quick";
        } // if
        else if (weaponType == WeaponType.RETALIATING) {
            return "retaliating";
        } // if

        return "huh?";
    } // weaponTypeToString

    /**
     *
     * @return true if the Weapon is equipped
     */
    public boolean isEquipped() {
        return isEquipped;
    } // isEquipped

    /**
     * Equips or unequips the Weapon, changing the option text to the opposite
     *
     * @param equipped the new equip status of the Weapon
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
     *
     * @return the minimum damage of the weapon before multipliers
     */
    public double getMinDamageBonus() {
        return minimumDamageBonus;
    } // getMinDamageBonus

    /**
     *
     * @return the maximum damage of the weapon before multipliers
     */
    public double getMaxDamageBonus() {
        return maximumDamageBonus;
    } // getMaxDamageBonus

    /**
     * Equips or unequips the weapon, granting any attribute bonuses the
     * Weapon carries to the Player.
     */
    @Override
    public void onUse() {
        if (isEquipped) {
            unequip();
            return;
        } // if
        
        Player player = Game.player;
        // Is the player already wielding a weapon?
        if (player.isArmorSlotEquipped(Player.MAIN_HAND_SLOT_ID)) {
            player.unequip(Player.MAIN_HAND_SLOT_ID);
        } // if
        
        equip();
    } // onUse
    
    /**
     * Equips the Weapon to the Player's self so that its benefits can be 
     * reaped; adding any attribute modifiers and the bonus from its min/max
     * damage.
     */
    public void equip() {
        Game.player.equip(Player.MAIN_HAND_SLOT_ID, this);
        isEquipped = true;
        
        for (AttributeMod mod : itemAffixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), mod.getAttributeModifier());
        } // for
    } // equip
    
    /**
     * Removes the Weapon from the player, as well as any attributes
     * on the Weapon.
     */
    public void unequip() {
        Game.player.unequip(Player.MAIN_HAND_SLOT_ID);
        isEquipped = false;
        
        for (AttributeMod mod : itemAffixes) {
            Game.player.setTemporaryAttribute(mod.getStatId(), -mod.getAttributeModifier());
        } // for
    } // unequip

    /**
     * The compareTo functionality is used to compare two Weapons to each other for stacking purposes when the Item is
     * added to the player's inventory; since Weapons never stacks, compareTo returns -1 every time
     *
     * @param item the Item to compare to
     * @return always -1
     */
    @Override
    public int compareTo(Item item) {
        return -1;
    } // compareTo
} // Weapon
