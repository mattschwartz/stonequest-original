/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Player.java
 * Author:           Matt Schwartz
 * Date created:     07.05.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: The Player class represents the user in the world.  All of 
 the user's attributes, known Scroll id's, afflicions, 
 infections and buffs are recorded here.
                     
 * TODO:             Remove the extra data structure to keep track of Poisons
 and Potion effects.  Poisons are Debuffs and should be grouped
 with Curses and other Debuffs and handled that way.  Also 
 shouldn't have to keep track of a Potion effect.  Just adjust
 the Player's current stats according to the effects of the
 Potion when it is first quaffed and readjust the Player 
 attributes when the Potion ends... or something
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.player.activeeffects.Poison;
import com.barelyconscious.game.player.activeeffects.Debuff;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.*;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.player.activeeffects.Buff;
import com.barelyconscious.game.spawnable.Sprite;
import com.barelyconscious.game.spawnable.Entity;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    /* Player attribute constants */

    /**
     * Represents the health of the player; if it goes to 0, the Player dies.
     */
    public static final int HITPOINTS = 0;
    /**
     * Increases the chance for the player to completely evade incoming attacks; evaded attacks deal no damage to the
     * Player.
     */
    public static final int AGILITY = 1;
    /**
     * Increases the chance for the player to hit with a critical strike, significantly increasing the damage of the
     * attack.
     */
    public static final int ACCURACY = 2;
    /**
     * Decreases the amount of incoming physical damage done to the Player.
     */
    public static final int DEFENSE = 3;
    /**
     * Increases the overall physical damage output for the Player.
     */
    public static final int STRENGTH = 4;
    /**
     * The Player's increased bonus to both offensive and defensive Fire magic.
     */
    public static final int FIRE_MAGIC_BONUS = 5;
    /**
     * The Player's increased bonus to both offensive and defensive Frost magic.
     */
    public static final int FROST_MAGIC_BONUS = 6;
    /**
     * The Player's increased bonus to both offensive and defensive Holy magic.
     */
    public static final int HOLY_MAGIC_BONUS = 7;
    /**
     * The Player's increased bonus to both offensive and defensive Chaos magic.
     */
    public static final int CHAOS_MAGIC_BONUS = 8;
    /**
     * Provides no direct bonus to the Player, but increasing this attribute increases all magic schools by an equal
     * amount; however, this attribute is more expensive to level, though not as expensive to level as leveling all
     * magic schools independently.
     */
    public static final int PLUS_ALL_MAGIC_BONUS = 9;
    /* The current values for every attribute.  These change during the course
     of combat freely and can be restored to the default attribute levels
     over time or through the use of various salves. */
    public static final int HITPOINTS_CURRENT = 10;
    public static final int AGILITY_CURRENT = 11;
    public static final int ACCURACY_CURRENT = 12;
    public static final int DEFENSE_CURRENT = 13;
    public static final int STRENGTH_CURRENT = 14;
    public static final int FIRE_MAGIC_CURRENT = 15;
    public static final int FROST_MAGIC_CURRENT = 16;
    public static final int HOLY_MAGIC_CURRENT = 17;
    public static final int CHAOS_MAGIC_CURRENT = 18;
    private final int STAT_LEVEL_CAP = 99; // Does not affect hitpoints
    private final int PLAYER_LEVEL_CAP = 100;
    /* Constants for the slots of armor for the player. */
    public static final int HELM_SLOT_ID = 0;
    public static final int MAIN_HAND_SLOT_ID = 1;
    public static final int CHEST_SLOT_ID = 2;
    public static final int OFF_HAND_SLOT_ID = 3;
    public static final int BELT_SLOT_ID = 4;
    public static final int EARRING_SLOT_ID = 5;
    public static final int GREAVES_SLOT_ID = 6;
    public static final int NECK_SLOT_ID = 7;
    public static final int BOOTS_SLOT_ID = 8;
    public static final int RING_SLOT_ID = 9;
    private int poisonCount = 0;
    private int bonusArmor = 0;
    private int curExperience = 0;
    private int attributePoints = 5;
    private int elementCastSchool;
    private double minBonusFromWeapon;
    private double maxBonusFromWeapon;
    // Holds all levels in an array for simpler retrieval
    private double[] playerAttributes = new double[19];
    public static String playerName = "barelyconscious";
    /**
     * A list of all armor slots on the Player and the Items that are currently equipped in those slots, if any.
     */
    private Item[] equippedArmorSlots = new Item[10];
    private final TextLog textLog;
    private Potion activePotion;
    private Poison[] poisons = new Poison[5];
    private ArrayList<Debuff> debuffs = new ArrayList();
    private ArrayList<Buff> buffs = new ArrayList();
    
    private ArrayList identifiedScrolls = new ArrayList<Integer>();

    /**
     * Creates a new Player; only one should be instantiated per runtime
     *
     * @param textLog the Player class often finds it necessary to write information to the TextLog
     */
    public Player(final TextLog textLog) {
        super(playerName, Tile.PLAYER_TILE_ID);
        this.textLog = textLog;

        setStartingAttributes();
        elementCastSchool = FIRE_MAGIC_BONUS; // testing
        levelUp();
    } // constructor

    /**
     *
     * @return the current amount of experience accumulated by the Player since the previous level up
     */
    public int getCurrentExp() {
        return curExperience;
    } // getCurrentExp

    /**
     *
     * @return the unspent attribute points the Player can spend to increase the levels of his or her skills
     */
    public int getUnspentAttributePoints() {
        return attributePoints;
    } // getUnspentAttributePoints

    /**
     * Sets the level of attributeId to the new attribute value; if attributeId does not exist, no changes are made
     *
     * @param attributeId the id of the attribute to be set
     * @param newAttributeValue the new value for the attribute
     */
    public void setAttribute(int attributeId, int newAttributeValue) {
        if (attributeId < HITPOINTS || attributeId > HOLY_MAGIC_CURRENT) {
            System.err.println(" [ERR_Player] Attempted to set value of attribute: "
                    + attributeId + " to " + newAttributeValue);
            return;
        } // if

        playerAttributes[attributeId] = newAttributeValue;
    } // setAttribute

    /**
     *
     * @param attributeId the id of the attribute desired
     * @return the value of the attribute ad attributeId
     */
    public double getAttribute(int attributeId) {
        if (attributeId < HITPOINTS || attributeId > CHAOS_MAGIC_CURRENT) {
            System.err.println(" [ERR_Player] Attempted to get value of attribute: "
                    + attributeId);
            return -1;
        } // if

        return playerAttributes[attributeId];
    } // getAttribute

    /**
     *
     * @return the school of magic the Player is using to cast his or her basic spells
     */
    public int getSchoolOfMagic() {
        return elementCastSchool;
    } // getSchoolOfMagic

    /**
     * Adds scrollId to the list of Scroll ids identified by the Player for future reference when the Player encounters
     * Scrolls of similar id
     *
     * @param scrollId
     */
    public void addScrollToIdentifieds(int scrollId) {
        identifiedScrolls.add(scrollId);
    } // addScrollToIdentifieds

    /**
     *
     * @param scrollId the id to check against the list of previously identified Scrolls
     * @return true if the Scroll has been previously seen by the Player
     */
    public boolean isScrollIdentified(int scrollId) {
        return identifiedScrolls.contains(scrollId);
    } // isScrollIdentified

    /**
     * Checks if a point can be allocated to Attribute,
     *
     * @statId, returning true if a point can be added and false if not.
     * @param statId the Attribute to add a point to
     * @return true if a point can be allocated to
     * @statId
     */
    public boolean canAddPointToAttribute(int statId) {
        if (statId == PLUS_ALL_MAGIC_BONUS) {
            return attributePoints >= 3;
        }
        return attributePoints > 0;
    } // canAddPointToAttribute

    /**
     * Spends a point, if any are available, on the attribute to raise it by one level
     *
     * @param statId the desired attribute to increase
     */
    public void addPointToAttribute(int statId) {
        int elementsAtCap = 0;

        // Stat has hit the level cap
        if (statId > HITPOINTS && playerAttributes[statId] >= STAT_LEVEL_CAP) {
            return;
        } // if

        // Not enough attribute points
        if (attributePoints < 1) {
            return;
        } // if

        // Player has selected +All Elements
        if (statId == PLUS_ALL_MAGIC_BONUS) {

            // Must have 3 attribute points
            if (attributePoints < 3) {
                return;
            } // if

            for (int i = FIRE_MAGIC_BONUS; i <= HOLY_MAGIC_BONUS; i++) {
                if (playerAttributes[i + 10] == STAT_LEVEL_CAP) {
                    elementsAtCap++;
                    continue;
                } // if

                playerAttributes[i]++;
                playerAttributes[i + 10]++;
            } // for

            // All elements are at cap
            if (elementsAtCap == 4) {
                return;
            } // if

            attributePoints -= 3;
        } // if
        // Player has selected a different stat
        else {
            // Raise the current level in that stat by 1 as well
            // Does not lower or raise boosted stats (potions) - REMVOED
            playerAttributes[statId + 10]++;

            attributePoints--;
        } // else

        // Finally, increase the stat level by one
        playerAttributes[statId]++;
    } // addPointToAttribute

    /**
     * Starting attributes for the Player when a new game is made.
     */
    private void setStartingAttributes() {
        playerAttributes[0] = playerAttributes[10] = 10;
        playerAttributes[1] = playerAttributes[11] = 1;
        playerAttributes[2] = playerAttributes[12] = 1;
        playerAttributes[3] = playerAttributes[13] = 1;
        playerAttributes[4] = playerAttributes[14] = 1;
        playerAttributes[5] = playerAttributes[15] = 1;
        playerAttributes[6] = playerAttributes[16] = 1;
        playerAttributes[7] = playerAttributes[17] = 1;
        playerAttributes[8] = playerAttributes[18] = 1;
        playerAttributes[9] = 0;
    } // setDefaultStats

    /* Causes all current stat levels to stabalize by 1 point, 
     either up (regen life) or down (stat potions) */
    public void normalizeStats() {
        for (int i = ACCURACY_CURRENT; i <= HOLY_MAGIC_CURRENT; i++) {
            if (playerAttributes[i] == playerAttributes[i - 10]) {
                continue;
            } // if

            if (playerAttributes[i] < playerAttributes[i - 10]) {
                playerAttributes[i]++;
            } // if
            else {
                playerAttributes[i]--;
            } // else
        } // for
    } // normalizeStats

    /**
     * Temporarily alters the value of
     *
     * @attribute by
     * @modifier.
     * @param attribute the int value of the attribute to be changed, should be given as a base stat value instead of
     * the current value
     * @param modifier the amount that the attribute should change by; this value will be directly added to the current
     * attribute's level
     */
    public void setTemporaryAttribute(int attribute, double modifier) {
        // Make sure attribute is going to modify the current value of the attribute, not the
        // level of the attribute
        if (attribute < PLUS_ALL_MAGIC_BONUS) {
            attribute += 10;
        } // if
        
        playerAttributes[attribute] += modifier;
    } // setTemporaryAttribute

    /**
     * Whenever a Player levels up, this function is called to increase the level of the Player and increment the amount
     * of unspent attribute points for the Player; his or her hitpoints are also increased by one.
     */
    public void levelUp() {
        setLevel(getLevel() + 1);
        playerAttributes[HITPOINTS]++;

        /* Don't increase the Player's current hitpoints to more than the Player's
         hitpoints level */
        if (playerAttributes[HITPOINTS_CURRENT] < playerAttributes[HITPOINTS]) {
            playerAttributes[HITPOINTS_CURRENT]++;
        } // if

        attributePoints += 5;
    } // levelUp

    /**
     * Interact with a Sprite, attacking a hostile entities and opening dialogue with nonhostile entities; or
     * interacting with Loot
     *
     * @param spr the Sprite to interact with
     */
    @Override
    public void interactWith(Sprite spr) {
        double attackDamage;

        /* If Sprite is an Entity */
        if (spr instanceof Entity) {
            attackDamage = (Math.random() * 3) + 0.1f;
            ((Entity) spr).changeHealthBy(-attackDamage);

            textLog.writeFormattedString(String.format("You hit %s for %.1f physical.",
                    spr.getDisplayName(), attackDamage), Common.FONT_DAMAGE_TEXT_RGB,
                    new LineElement(spr.getDisplayName(), true,
                    Common.FONT_ENTITY_LABEL_RGB));
        } // if
        /* If Sprite is a Loot object or Doodad */ else {
            spr.interact();
        } // if
    } // interactWith

    /**
     * Changes the health of the player by
     *
     * @param amount If the amount falls below 0, the player dies and the game is over.
     */
    @Override
    public void changeHealthBy(double amount) {
        playerAttributes[HITPOINTS_CURRENT] += amount;
        if (playerAttributes[HITPOINTS_CURRENT] <= 0) {
//            textLog.writeFormattedString("You have died.", null);
//            Game.stop();
        } // if
    } // takeDamage

    /**
     * Checks if the Player is currently wearing armor at armorSlot
     *
     * @param armorSlot the type of armor to check against
     * @return true if the Player already has armor on of that type
     */
    public boolean isArmorSlotEquipped(int armorSlot) {
        return equippedArmorSlots[armorSlot] != null;
    } // isArmorSlotEquipped

    public void equip(int slotId, Item item) {
        if (equippedArmorSlots[slotId] != null) {
            System.err.println(" [NOTIFY] Attempting to equip " + item + " before unequipping: \"" + equippedArmorSlots[slotId] + "\"");
        } // if

        equippedArmorSlots[slotId] = item;
    } // equip

    public void unequip(int slotId) {
        equippedArmorSlots[slotId] = null;
    } // unequip

    /**
     * Equip an Item; either a Weapon or a piece of Armor
     *
     * @param item the Item to equip
     */
    public void equipItem(Item item) {
        int numItemAffixes = item.getNumAffixes();
        int armorSlot;

        Armor armor;
        Weapon weapon;
        AttributeMod affix;

        /* If Item to equip is a piece of Armor */
        if (item instanceof Armor) {
            armor = (Armor) item;

            /* If the Item attempting to equip is already worn by the Player,
             unequip it and return */
            if (armor.isEquipped()) {
                unequipItem(armor);
                return;
            } // if

            armorSlot = armor.getArmorType();

            /* If Player is already wearing a piece of that Armor, take it off
             before equipping the new piece of Armor */
            if (equippedArmorSlots[armorSlot] != null) {
                unequipItem(equippedArmorSlots[armorSlot]);
            } // if

            armor.setEquipped(true);
            equippedArmorSlots[armorSlot] = armor;

            bonusArmor += armor.getBonusArmor();
        } // if
        /* If Item to equip is a Weapon */ else if (item instanceof Weapon) {
            weapon = (Weapon) item;

            /* If the Item attempting to equip is already worn by the Player,
             unequip it and return */
            if (weapon.isEquipped()) {
                unequipItem(weapon);
                return;
            } // if

            /* If Player is already weilding a Weapon, take it off
             before equipping the new Weapon */
            if (equippedArmorSlots[MAIN_HAND_SLOT_ID] != null) {
                unequipItem(item);
            } // if

            weapon.setEquipped(true);
            equippedArmorSlots[MAIN_HAND_SLOT_ID] = weapon;
        } // else if

        /* Add any attribute bonuses granted by the Item */
        for (int i = 0; i < numItemAffixes; i++) {
            affix = item.getAffixAt(i);
            playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
        } // for
    } // equipItem

    /**
     * Unequip the Item from the Player
     *
     * @param item the Item to unequip
     */
    public void unequipItem(Item item) {
        int numItemAffixes = item.getNumAffixes();

        Armor armor;
        Weapon weapon;
        AttributeMod affix;

        /* If the Item to unequip is a piece of Armor */
        if (item instanceof Armor) {
            armor = (Armor) item;

            armor.setEquipped(false);
            equippedArmorSlots[armor.getArmorType()] = null;

            bonusArmor -= armor.getBonusArmor();
        } // if
        /* If the Item to unequip is a Weapon */ else if (item instanceof Weapon) {
            weapon = (Weapon) item;

            weapon.setEquipped(false);
            equippedArmorSlots[MAIN_HAND_SLOT_ID] = null;

            minBonusFromWeapon = 0f;
            maxBonusFromWeapon = 0f;
        } // else if

        /* Adjust attribute amounts accordingly */
        for (int i = 0; i < numItemAffixes; i++) {
            affix = item.getAffixAt(i);
            playerAttributes[affix.getAttributeId()] -= affix.getAttributeModifier();
        } // for
    } // unequipItem

    /**
     * Eat some food, acquiring its nutrients
     *
     * @param food the food to eat
     */
    public void eat(Food food) {
        double plusHealth = food.getHealthChange();

        /* If Player health is already at maximum, don't increase it further */
        if (getAttribute(HITPOINTS_CURRENT) > playerAttributes[HITPOINTS]) {
            return;
        } // if

        if (playerAttributes[HITPOINTS_CURRENT] + plusHealth > playerAttributes[HITPOINTS]) {
            plusHealth = playerAttributes[HITPOINTS] - playerAttributes[HITPOINTS_CURRENT];
        } // if

        playerAttributes[HITPOINTS_CURRENT] += plusHealth;
    } // eat

    /**
     * Drink a Potion, gaining its benefits (good or bad) for the duration of the Potion
     *
     * @param pot the Potion to quaff
     */
    public void quaff(Potion pot) {
//        int numAffixes;
//        AttributeMod affix;
//        
//        /* Player has drunk an antimagic potion which removes at least one
//            curse effect from the player */
//        if (pot != null && pot.getPotionType() == Potion.ANTIMAGIC) {
//            for (int i = 0; i < currentNumDebuffs; i++) {
//                if (debuffList[i] instanceof Curse) {
//                    removeDebuffAt(i--);
//                } // if
//            } // for
//            
//            return;
//        } // if
//        
//        /* Player has quaffed an antivenom potion which removes all Poison effects
//            on the Player */
//        else if (pot != null && pot.getPotionType() == Potion.ANTIVENOM) {
//            for (int i = 0; i < currentNumDebuffs; i++) {
//                if (debuffList[i] instanceof Poison) {
//                    removeDebuffAt(i--);
//                } // if
//            } // for
//            
//            return;
//        } // else if
//        
//        /* Potion is a stat buff potion */
//        else if (activePotion != null) {
//            numAffixes = activePotion.getNumAffixes();
//            
//            for (int i = 0; i < numAffixes; i++) {
//                affix = activePotion.getAffixAt(i);
//                playerAttributes[affix.getAttributeId()] -= affix.getAttributeModifier();
//            } // for
//        } // if
//        
//        /* When a Potion's effects end, quaff is called with a null argument
//            for some reason */
//        if (pot == null) {
//            activePotion = null;
//            return;
//        } // if
//        
//        activePotion = pot;
//        numAffixes = activePotion.getNumAffixes();
//        
//        for (int i = 0; i < numAffixes; i++) {
//            affix = activePotion.getAffixAt(i);
//            playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
//        } // for
    } // quaff

    /**
     * Read a Scroll, acquiring its benefits and any extra abilities it possesses as well as adding it to the list of
     * identified Scrolls
     *
     * @param scr
     */
    public void read(Scroll scr) {
        AttributeMod affix;

        // Adjust any player affixes the scroll has
        for (int i = 0; i < scr.getNumAffixes(); i++) {
            affix = scr.getAffixAt(i);
            playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
        } // for

        scr.extraEffects();

        scr.identifyScroll();
        textLog.writeFormattedString("It was a " + scr.getDisplayName() + "!", Common.FONT_NULL_RGB);
    } // read
    
    public void applyBuff(Buff buff) {
        // Only one potion can be applied at any one time, and subsequent potions will override
        // previous potion effects
        if (buff.getBuffType() == Buff.POTION) {
            for (int i = 0; i < buffs.size(); i++) {
                if (buffs.get(i).getBuffType() == Buff.POTION) {
                    buffs.remove(i);
                } // if
            } // for
        } // if
        
        buffs.add(buff);
    } // applyBuff
    
    public void removeBuffAt(int index) {
        if (index < 0 || index >= buffs.size()) {
            return;
        } // if
        
        buffs.remove(index);
    } // removeBuffAt
    
    public void removeBuff(Buff buff) {
        buffs.remove(buff);
    } // removeBuff

    public List<Debuff> getDebuffs() {
        return debuffs;
    }

    public int getNumBuffs() {
        return buffs.size();
    } // getNumBuffs
    
    public Buff getBuffAt(int index) {
        return index < 0 || index >= buffs.size() ? null : buffs.get(index);
    } // getBuffAt

    /**
     * Add debuff to the list of current afflictions on the Player
     *
     * @param debuff the new affliction for the Player
     */
    public void applyDebuff(Debuff debuff) {
//        int numAffixes;
//        int dimCurseIndex = 0;
//        AttributeMod affix;
//
//        /* Replaces curse with shortest duration if player is afflicted by max
//         number of debuffs */
//        if (debuffs.size() == 5) {
//            for (int i = 0; i < debuffs.size() - 1; i++) {
//                if (debuffs.get(i).getDuration() < debuffs.get(i + 1).getDuration()) {
//                    dimCurseIndex = i;
//                } // if
//            } // for
//            removeDebuffAt(dimCurseIndex);
//        } // if
//
//        if (debuff instanceof Curse) {
//
//            numAffixes = ((Curse) debuff).getNumAffectedAttributes();
//
//            for (int i = 0; i < numAffixes; i++) {
//                affix = ((Curse) debuff).getAffectedAttributeAt(i);
//                playerAttributes[affix.getAttributeId()] -= affix.getAttributeModifier();
//            } // for-i
//        } // if
//        else if (debuff instanceof Poison) {
//            poisons[poisonCount++] = (Poison) debuff;
//        } // else if

        debuffs.add(debuff);
    } // applyDebuff

    /**
     * Remove a debuff at index from the list of afflictions on the Player
     *
     * @param index the location of the debuff to be removed
     */
    public void removeDebuffAt(int index) {
        debuffs.remove(index);

//        if (debuff == null) {
//            return;
//        } // if
//
//        currentNumDebuffs--;
//
//        for (int i = index; i < debuffs.length - 1; i++) {
//            debuffs[i] = debuffs[i + 1];
//        } // for
//
//        if (debuff instanceof Curse) {
//            numAffixes = ((Curse) debuff).getNumAffectedAttributes();
//
//            for (int i = 0; i < numAffixes; i++) {
//                affix = ((Curse) debuff).getAffectedAttributeAt(i);
//                playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
//            } // for
//        } // if
//        else if (debuff instanceof Poison) {
//            poisonCount--;
//
//            for (int i = index; i < poisons.length - 1; i++) {
//                poisons[i] = poisons[i + 1];
//            } // for
//        } // else if
    } // removeDebuffAt
    
    public void removeDebuff(Debuff debuff) {
        debuffs.remove(debuff);
    } // removeDebuff

    /**
     *
     * @return the number of afflictions and infections on the Player
     */
    public int getNumDebuffs() {
        return debuffs.size();
    } // getNumDebuffs

    /**
     *
     * @param index the index of the desired debuff
     * @return the debuff located at index
     */
    public Debuff getDebuffAt(int index) {
        return index < 0 || index >= debuffs.size() ? null : debuffs.get(index);
    } // getCurseList

    @Override
    public double getCurrentHealth() {
        return playerAttributes[HITPOINTS_CURRENT];
    } // getHealthPoints

    @Override
    public double getMaxHealth() {
        return playerAttributes[HITPOINTS];
    } // getMaxHealth

    /**
     * Updates the Player class when the Game ticks; debuff effects have their durations decreased; buffs have their
     * durations decreased; dead effects are removed.
     */
    @Override
    public void tick() {
        for (int i = 0; i < debuffs.size(); i++) {
            debuffs.get(i).tick();
        } // for
        
        for (int i = 0; i < buffs.size(); i++) {
            buffs.get(i).tick();
        } // for
        
        
        // Damage-over-time effects; aka poison
//        for (int i = 0; i < poisonCount; i++) {
//            if (poisons[i].nextTick()) {
//                playerAttributes[HITPOINTS_CURRENT] -= poisons[i].getTickDamage();
//
//                // Inform the player how much health was lost and to what
//                textLog.writeFormattedString(poisons[i].toString(), Common.FONT_DAMAGE_TEXT_RGB,
//                        new LineElement(poisons[i].getDisplayName(), true,
//                        Common.FONT_POISON_LABEL_RGB));
//            }  // if
//        } // for
//
//        // Decrease afflicted curses' durations
//        for (int i = 0; i < currentNumDebuffs; i++) {
//            debuffs[i].decrDuration();
//
//            // curse has expired, remove it from the list
//            if (debuffs[i].getDuration() == 0) {
//                removeDebuffAt(i);
//                i--;
//            } // if
//        } // for

//        if (activePotion != null) {
//            // Decrease potion duration
//            activePotion.decrDuration();
//
//            if (activePotion.getDurationInTicks() == 0) {
//                quaff(null);
//                activePotion = null;
//            } // if
//        } // if
    } // tick

    /* XP FORMULA NOT FINAL
     Simple formula to determine the amount of experience needed to 
     reach the level given in the parameters.  Used to let the PLAYER_ICON
     know how much XP he/she must get to reach the next level */
    public int getExperienceReq(int level) {
        return (int) (Math.ceil(level * Math.sqrt(Math.pow(level, 3))));
    } // getExperienceReq

    /* FORMULA NOT FINAL
     Returns the minimum damage necessary for the combat calculator. 
     Minimum damage is based on:
     * Level
     * Weapon damage range (Lower-bound) (NYI)
     * Attack level */
    public double getMinPhysicalDamage() {
        return (getLevel() + ((getLevel() + 1) * .075)) + getStrengthBonus()
                + (equippedArmorSlots[MAIN_HAND_SLOT_ID] == null ? 0 : ((Weapon) equippedArmorSlots[MAIN_HAND_SLOT_ID]).getMinDamageBonus());
    } // getMinPhysicalDamage

    /* FORMULA NOT FINAL 
     Returns the maximum damage necessary for the combat calculator.
     Maximum damage is based on:
     * Level
     * Weapon damage range (Upper-bound) (NYI)
     * Strength level */
    public double getMaxPhysicalDamage() {
        return (getLevel() + ((getLevel() + 1) * 1.2)) + getStrengthBonus()
                + (equippedArmorSlots[MAIN_HAND_SLOT_ID] == null ? 0 : ((Weapon) equippedArmorSlots[MAIN_HAND_SLOT_ID]).getMaxDamageBonus());
    } // getMaxPhysicalDamage

    /* FORMULA NOT FINAL 
     Calculates and returns the minimum magical damage bound.
     Magical damage is based solely on the PLAYER_ICON's level and is increased
     by the magic element being cast */
    public double getMinMagicDamage() {
        return getLevel() * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    } // getMinMagicDamage

    /* FORMULA NOT FINAL 
     Calculates and returns the maximum magical damage bound*/
    public double getMaxMagicDamage() {
        return (getLevel() + ((getLevel() + 1) * 1.25)) * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    } // getMaxMagicDamage

    /* FORMULA NOT FINAL 
     Strength increases the PLAYER_ICON's maximum hit */
    public double getStrengthBonus() {
        double strengthBonus = (getAttribute(STRENGTH_CURRENT) * 1.26);

        return strengthBonus > 0 ? strengthBonus : 0f;
    } // getStrengthBonus

    /* FORMULA NOT FINAL
     Returns the PLAYER_ICON's chance to critically hit, as a percentage
     Critical hit chance is based on Attack, with each point of Attack
     having less of an effect the higher level the PLAYER_ICON is */
    public double getCritChance() {
        double critChance = (1f / (34.91f / (getAttribute(ACCURACY_CURRENT) * 10f)));

        return critChance > 0 ? critChance : 0f;
    } // getCritChance

    /* FORMULA NOT FINAL 
     Returns the PLAYER_ICON's physical damage reduction, as a percentage
     Physical damage reduction reduces the PLAYER_ICON's damage taken from
     physical attacks by a direct percentage.  Physical damage reduction
     is based on Defense, with each point of Defense having less of an 
     effect the higher level the PLAYER_ICON is */
    public double getPhysicalDamageReduction() {
        double defenseBonus = (1f / (13.72f / (getAttribute(DEFENSE_CURRENT) * 10f)));

        return defenseBonus > 0 ? defenseBonus : 0;
    } // getPhysicalDamageReduction

    /* FORMULA NOT FINAL
     Returns the PLAYER_ICON's chance to evade incoming physical attacks in the
     form of a percentage.  Evade chance is based on a PLAYER_ICON's Agility
     level, and each point of Agility is worth less the higher level the 
     PLAYER_ICON is. */
    public double getEvadeChance() {
        double evadeChance = (1f / (45.3491f / (getAttribute(AGILITY_CURRENT) * 10f)));

        return evadeChance > 0 ? evadeChance : 0;
    } // getEvadeChance

    /* FORMULA NOT FINAL 
     Returns the PLAYER_ICON's resistance to a particular element, given as
     the parameter.  Each type of resistance is weighed the same, differing
     only based on the resistance's level.  Each point in an element has
     less of an effect the higher level the PLAYER_ICON is.  The number returned
     is in the form of a percentage */
    public double getBonusToElement(int element) {
        return (1f / (11.147f / (getAttribute(element) * 10f)));
    } // getBonusToElement

    public int getBonusArmor() {
        return bonusArmor;
    } // getBonusArmor

    /* Returns the name associated with a given stat ID as a String */
    public static String idToString(int statId) {
        switch (statId) {
            case HITPOINTS:
                return "hitpoints";
            case ACCURACY:
                return "accuracy";
            case STRENGTH:
                return "strength";
            case DEFENSE:
                return "defense";
            case AGILITY:
                return "agility";

            case FIRE_MAGIC_BONUS:
                return "fire magic";
            case FROST_MAGIC_BONUS:
                return "frost magic";
            case CHAOS_MAGIC_BONUS:
                return "chaos magic";
            case HOLY_MAGIC_BONUS:
                return "holy magic";
            case PLUS_ALL_MAGIC_BONUS:
                return "bonus to all magic";

            case HITPOINTS_CURRENT:
            case ACCURACY_CURRENT:
            case STRENGTH_CURRENT:
            case DEFENSE_CURRENT:
            case AGILITY_CURRENT:
            case FIRE_MAGIC_CURRENT:
            case FROST_MAGIC_CURRENT:
            case CHAOS_MAGIC_CURRENT:
            case HOLY_MAGIC_CURRENT:
                return idToString(statId - 10);

            default:
                return " [NOTIFY] Attempting to parse id (" + statId + ") that was invalid.";
        } // switch
    } // idToString
} // Player
