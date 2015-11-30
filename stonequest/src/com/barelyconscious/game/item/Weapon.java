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

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;

public class Weapon extends Equippable {

    public static final int HEAVY_WEAPON_TYPE = 0;
    public static final int QUICK_WEAPON_TYPE = 1;
    public static final int RETALIATING_WEAPON_TYPE = 2;
    private int weaponType;
    private double minimumDamageBonus;
    private double maximumDamageBonus;

    /**
     * Creates a new Weapon with the following parameters
     *
     * @param name the name visible to the Player of the Weapon
     * @param sellValue the value in gold that vendors will give to the player in
     * exchange for the Weapon
     * @param weaponMinDamage the minimum damage before multipliers that the
     * Weapon can deal
     * @param weaponMaxDamage the maximum damage before multipliers that the
     * Weapon can deal
     * @param weaponType [NYI] the type of weapon which determines how the
     * weapon can be used as well as restrictions of the weapon
     * @param affixes any attribute mods present on the Weapon
     */
    public Weapon(String name, int itemLevel, int sellValue, double weaponMinDamage, double weaponMaxDamage, int weaponType, UIElement itemIcon, Entity owner, AttributeMod... affixes) {
        super(name, itemLevel, sellValue, Entity.MAIN_HAND_SLOT_ID, itemIcon, owner, affixes);

        minimumDamageBonus = weaponMinDamage;
        maximumDamageBonus = weaponMaxDamage;
        this.weaponType = weaponType;
    } // constructor

    /**
     *
     * @return the type of weapon this is
     */
    public int getWeaponType() {
        return weaponType;
    } // getWeaponType

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

    @Override
    public String getDescription() {
        return "Hold the pointy end away from you and swing wildly for best results.";
    } // getDescription

    /**
     * Returns a string representation of the weapon types available for the
     * different weapons.
     *
     * @param weaponType the type of weapon for which a string representation is
     * desired
     * @return a string representation of the supplied weapon type
     */
    public static String weaponTypeToString(int weaponType) {
        if (weaponType == HEAVY_WEAPON_TYPE) {
            return "heavy";
        } // if
        else if (weaponType == QUICK_WEAPON_TYPE) {
            return "quick";
        } // if
        else if (weaponType == RETALIATING_WEAPON_TYPE) {
            return "retaliating";
        } // if

        return "{ERR:UNDEF}";
    } // weaponTypeToString
} // Weapon
