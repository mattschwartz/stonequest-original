 /* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Potion.java
 * Author:           Matt Schwartz
 * Date created:     07.10.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: A subclass of Item and superclass to various potion types.
 *                   Potions are consumable Items which provide attribute
 *                   effects to the player, both beneficial and harmful, for some
 *                   number of game ticks(), denoted by the duration or that cleanse
 *                   afflictions or cure infections of the player.  There are
 *                   three types of potions:
 *                   STATBUFF: potion that provides a temporary change in attributes
 *                    for the player
 *                   ANTIMAGIC: removes all curses currently afflicting the player
 *                   ANTIVENOM: removes all poisons currently affecting the player
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.activeeffects.PotionEffect;
import com.barelyconscious.game.player.activeeffects.StatPotionEffect;

public class Potion extends Item {

    private PotionEffect effects;

    /**
     * Create a new potion with the following parameters
     *
     * @param name the name of the potion, visible to the player
     * @param sellV the amount in gold that a player will receive in exchange for the Item
     * @param stack the amount of Items in one stack
     * @param dur the duration in ticks if the potion is STATBUFF
     * @param type the type of potion being created
     * @param effects if the potion type is STATBUFF, this is an array of attributes which are affected when the potion
     * is quaffed
     */
    public Potion(String name, int sellV, int stack, PotionEffect effects) {
        super(name, sellV, stack, Tile.POTION_TILE_ID);
        super.setItemDescription("" + effects);
        this.effects = effects;

        options[USE] = "quaff";
    } // constructor
    public Potion(String name, int sellV, int stack, int tileId, PotionEffect effects) {
        super(name, sellV, stack, tileId);
        super.setItemDescription("" + effects);
        this.effects = effects;

        options[USE] = "quaff";
    } // constructor

    public PotionEffect getEffects() {
        return effects;
    } // getEffects

    @Override
    public int getNumAffixes() {
        return effects instanceof StatPotionEffect ? ((StatPotionEffect)effects).getNumAffixes() : 0;
    } // getNumAffixes

    @Override
    public AttributeMod getAffixAt(int index) {
        return effects instanceof StatPotionEffect ? ((StatPotionEffect)effects).getAffectedAttributeAt(index) : null;
    } // getAffixAt

    /**
     * Called when the Item is used by the player; the potion is quaffed and the effects are applied if it is a stat
     * buff, or debuffs are removed according to the type of Potion it is.
     */
    @Override
    public void onUse() {
        if (effects.getPotionType() == PotionEffect.STATBUFF) {
            StatPotionEffect neweffects = new StatPotionEffect(effects.getDisplayName(), effects.getDurationInTicks(), ((StatPotionEffect) effects).getAffixesAsArray());
            neweffects.onApplication();
        } // if
        else {
            effects.onApplication();
        } // else

        if (--stackSize <= 0) {
            Game.inventory.removeItem(this);
        } // if
    } // onUse

    /**
     * The compareTo functionality is used to compare two Potions to each other for stacking purposes when the Potion is
     * added to the player's inventory; Potions are considered identical if: -They are of the same type -They have the
     * same number of stats if they are STATBUFF -They have the same value for the same stats if they are STATBUFF -They
     * have the same duration
     *
     * @param item the Item to compare to
     * @return -1 if the Items are different and 0 if the two Items are identical
     */
    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        } // if

        // If both items are the same type of Potion
        if (effects.getPotionType() != ((Potion) item).getEffects().getPotionType()) {
            return -1;
        } // if

        if (effects.getDurationInTicks() != ((Potion) item).getEffects().getDurationInTicks()) {
            return -1;
        } // if

        return 0;
    } // compareTo
} // Potion
