/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Player.java
 * Author:           Matt Schwartz
 * Date created:     07.05.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.player;

import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.Common;
import com.barelyconscious.game.player.activeeffects.Poison;
import com.barelyconscious.game.player.activeeffects.Curse;
import com.barelyconscious.game.player.activeeffects.Debuff;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.*;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.spawnable.Sprite;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.Loot;
import java.util.ArrayList;

public class Player extends Entity {
    /* Player customization */
    public static String characterName          = "Rogue";
    
    private final TextLog TEXT_LOG;
    
    // Player attributes
    public static final int HITPOINTS           = 0; // amount of damage PLAYER_ICON can take before dying
    public static final int ACCURACY            = 1; // increases chance to critically hit
    public static final int STRENGTH            = 2; // increases PLAYER_ICON damage range
    public static final int DEFENSE             = 3; // decreases physical damage taken
    public static final int AGILITY             = 4; // increases chance to evade incoming attacks
    
    public static final int FIRE_MAGIC          = 5;
    public static final int FROST_MAGIC         = 6;
    public static final int CHAOS_MAGIC         = 7;
    public static final int HOLY_MAGIC          = 8;
    public static final int PLUS_ALL_MAGIC      = 9;
    
    // Player stats at current state; changes in combat and under other circumstances
    public static final int HITPOINTS_CURRENT   = 10;
    public static final int ACCURACY_CURRENT    = 11;
    public static final int STRENGTH_CURRENT    = 12;
    public static final int DEFENSE_CURRENT     = 13;
    public static final int AGILITY_CURRENT     = 14;
    
    // Player resistance bonuses
    public static final int FIRE_MAGIC_CURRENT  = 15;
    public static final int FROST_MAGIC_CURRENT = 16;
    public static final int CHAOS_MAGIC_CURRENT = 17;
    public static final int HOLY_MAGIC_CURRENT  = 18;
    
    private final int STAT_LEVEL_CAP            = 99; // Does not affect hitpoints
    private final int PLAYER_LEVEL_CAP          = 100;
    
    private int level                           = 1;
    private int curExperience                   = 0;
    private int attributePoints                 = 5;
    private int elementCastSchool;
    
    // For armor; can only wear 1 type of armor at a time.  Obviously
    public static final int HELM_SLOT           = 0;
    public static final int MAIN_HAND_SLOT      = 1;
    public static final int CHEST_SLOT          = 2;
    public static final int OFF_HAND_SLOT       = 3;
    public static final int BELT_SLOT           = 4;
    public static final int EARRING_SLOT        = 5;
    public static final int GREAVES_SLOT        = 6;
    public static final int NECK_SLOT           = 7;
    public static final int BOOTS_SLOT          = 8;
    public static final int RING_SLOT           = 9;
    
    // Holds all levels in an array for simpler retrieval
    private float[] pAttributes                 = new float[19];
    
    private Item[] equippedArmorSlots           = new Item[10];
    
    private int bonusArmor                      = 0;
    private float minBonusFromWeapon            = 0f;
    private float maxBonusFromWeapon            = 0f;
    
    // Only 1 potion's effects can benefit you at a time
    private Potion potionEffects;
    
    // List of curses currently affecting the player
    private Debuff[] debuffList                 = new Debuff[5];
    private int debuffCount                     = 0;
    private Poison[] poisons                    = new Poison[5];
    private int poisonCount                     = 0;
    
    private int reach                           = 5;
    private int lightRadius                     = 7;
    
    private ArrayList identifiedScrolls         = new ArrayList<Integer>();
    
    public Player(TextLog log) {
        super(characterName, Tile.PLAYER_TILE_ID);
        setStartingStats();
        TEXT_LOG = log;
        elementCastSchool = FIRE_MAGIC; // testing
    } // constructor
    
    @Override
    public void setReach(int newReach) {
        reach= newReach;
    } // setReach
    
    @Override
    public int getReach() {
        return reach;
    } // getReach
    
    public void setLightRadius(int newRadius) {
        lightRadius = newRadius;
    } // setLightRadius
    
    public int getLightRadius() {
        return lightRadius;
    } // getLightRadius
    
    @Override
    public int getLevel() {
        return level;
    } // getLevel
    
    public int getCurrentExp() {
        return curExperience;
    } // getCurrentExp
    
    public int getAttributePoints() {
        return attributePoints;
    } // getAttributePoints
    
    /* Sets the stat, denoted by statID, to a new value, denoted
        by newStat */
    public void setStat(int statId, int newStat) {
        pAttributes[statId] = newStat;
    } // setStat
    
    /* Returns the value of the stat, denoted by statID 
        Assumes statID is a valid ID */
    public float getAttribute(int statId) {
        return pAttributes[statId];
    } // getAttribute
    
    public int getPlayerCastSchool() {
        return elementCastSchool;
    } // getPlayerCastSchool
    
    public void addScrollToIdentifieds(int scrollId) {
        identifiedScrolls.add(scrollId);
    } // addScrollToIdentifieds
    
    public boolean isScrollIdentified(int scrollId) {
        return identifiedScrolls.contains(scrollId);
    } // isScrollIdentified
    
    public void addTalentPointTo(int statId) {
        int elementsAtCap = 0;
        
        // Stat has hit the level cap
        if (statId > HITPOINTS && pAttributes[statId] >= STAT_LEVEL_CAP) {
            return;
        } // if
        
        // Not enough attribute points
        if (attributePoints < 1) {
            return;
        } // if
        
        // Player has selected +All Elements
        if (statId == PLUS_ALL_MAGIC) {
            
            // Must have 3 attribute points
            if (attributePoints < 3) {
                return;
            } // if
            
            for (int i = FIRE_MAGIC; i <= HOLY_MAGIC; i++) {
                if (pAttributes[i + 10] == STAT_LEVEL_CAP) {
                    elementsAtCap++;
                    continue;
                } // if
                
                pAttributes[i]++;
                pAttributes[i + 10]++;
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
            pAttributes[statId + 10]++;
            
            attributePoints--;
        } // else
        
        // Finally, increase the stat level by one
        pAttributes[statId]++; 
    } // addTalentPointTo

    /* The starting stats for a new PLAYER_ICON */
    private void setStartingStats() {
        pAttributes[0] = 10;
        pAttributes[1] = 1;
        pAttributes[2] = 1;
        pAttributes[3] = 1;
        pAttributes[4] = 1;
        pAttributes[5] = 1;
        pAttributes[6] = 1;
        pAttributes[7] = 1;
        pAttributes[8] = 1;
        pAttributes[9] = 0;
        
        // Less code for "copy _CURRENT stats from stat level"
        System.arraycopy(pAttributes, 0, pAttributes, 10, 9);
    } // setDefaultStats
    
    /* Causes all current stat levels to stabalize by 1 point, 
        either up (regen life) or down (stat potions) */
    public void normalizeStats() {
        for (int i = ACCURACY_CURRENT; i <= HOLY_MAGIC_CURRENT; i++ ) {
            if (pAttributes[i] == pAttributes[i - 10]) {
                continue;
            } // if
            
            if (pAttributes[i] < pAttributes[i - 10]) {
                pAttributes[i]++;
            } // if
            else {
                pAttributes[i]--;
            } // else
        } // for
    } // normalizeStats
    
    /* When a PLAYER_ICON levels up, this method is called.
        Increases all primary stats by 1, health by 3 and gives
        the PLAYER_ICON 3 attribute points to spend freely or save 
        for a later date.  Once spent, these cannot be undone.
        Possibly sell attribute points for a certain amount of gold?
        "Respeccing" for a cheaper amount?  Respeccing could remove a
        random attribute point, giving you 0-2 attribute points back? */
    public void levelUp() {
        if (level >= PLAYER_LEVEL_CAP) {
            return;
        } // if
        
        level++;
        pAttributes[HITPOINTS]++;
        
        if (pAttributes[HITPOINTS_CURRENT] < pAttributes[HITPOINTS]) {
            pAttributes[HITPOINTS_CURRENT]++;
        } // if
        
        attributePoints += 5;
    } // levelUp
    
    @Override
    public void interactWith(Sprite spr) {
        if (spr instanceof Entity) {
            float dmg = (float)(Math.random() * 3) + 0.1f;
            ((Entity)spr).changeHealthBy(-dmg);
            TEXT_LOG.writeFormattedString(String.format("You hit %s for %.1f physical.", 
                    spr.getName(), dmg), Common.DAMAGE_TEXT_COLOR, 
                    new LineElement(spr.getName(), true, 
                    Common.ENTITY_TEXT_COLOR));
        } // if
        if (spr instanceof Loot) {
            spr.interact();
        } // if
    } // interactWith
    
    /**
     * Changes the health of the player by @param amount.  If the amount falls
     * below 0, the player dies and the game is over.
     * @param amount
     */
    @Override
    public void changeHealthBy(float amount) {
        pAttributes[HITPOINTS_CURRENT] += amount;
        if (pAttributes[HITPOINTS_CURRENT] <= 0) {
//            TEXT_LOG.writeFormattedString("You have died.", null);
//            Game.stop();
        } // if
    } // takeDamage
    
    public boolean isArmorEquipped(int armorSlot) {
        return equippedArmorSlots[armorSlot] != null;
    } // isArmorEquipped
    
    public void equipItem(Item item) {
        int numItemAffixes = item.getNumAffixes();
        int armorSlot;
        Armor armor;
        Weapon weapon;
        StatBonus affix;

        // Trying to equip a piece of armor
        if (item instanceof Armor) {
            armor = (Armor)item;

            if (armor.isEquipped()) {
                unequipItem(armor);
                return;
            } // if

            armorSlot = armor.getArmorType();

            // Player is already wearing a piece of armor of that type
            if (equippedArmorSlots[armorSlot] != null) {
                unequipItem(equippedArmorSlots[armorSlot]);
            } // if

            armor.setEquipped(true);
            equippedArmorSlots[armorSlot] = armor;
            
            bonusArmor += armor.getBonusArmor();
        } // if
        
        // Trying to equip a weapon
        else if (item instanceof Weapon) {
            weapon = (Weapon)item;
            
            if (weapon.isEquipped()) {
                unequipItem(weapon);
                return;
            } // if
            
            // Player already is wielding a weapon
            if (equippedArmorSlots[MAIN_HAND_SLOT] != null) {
                unequipItem(item);
            } // if
            
            weapon.setEquipped(true);
            equippedArmorSlots[MAIN_HAND_SLOT] = weapon;
            
            minBonusFromWeapon = weapon.getMinDamageBonus();
            maxBonusFromWeapon = weapon.getMaxDamageBonus();
        } // else if
            
        for (int i = 0; i < numItemAffixes; i++) {
            affix = item.getAffixAt(i);
            pAttributes[affix.getStatId()] += affix.getStatMod();
        } // for
    } // equipItem
    
    public void unequipItem(Item item) {
        int numItemAffixes = item.getNumAffixes();
        Armor armor;
        Weapon weapon;
        StatBonus affix;
        
        if (item instanceof Armor) {
            armor = (Armor)item;
        
            armor.setEquipped(false);
            equippedArmorSlots[armor.getArmorType()] = null;
            
            bonusArmor -= armor.getBonusArmor();
        } // if
        
        else if (item instanceof Weapon) {
            weapon = (Weapon)item;
            
            weapon.setEquipped(false);
            equippedArmorSlots[MAIN_HAND_SLOT] = null;
            
            minBonusFromWeapon = 0f;
            maxBonusFromWeapon = 0f;
        } // else if
        
        for (int i = 0; i < numItemAffixes; i++) {
            affix = item.getAffixAt(i);
            pAttributes[affix.getStatId()] -= affix.getStatMod();
        } // for
    } // unequipItem
    
    public void eat(Food food) {
        float plusHealth = food.getHealthGain();
        
        if (getAttribute(HITPOINTS_CURRENT) > pAttributes[HITPOINTS]) {
            return;
        } // if
        
        if (pAttributes[HITPOINTS_CURRENT] + plusHealth > pAttributes[HITPOINTS]) {
            plusHealth = pAttributes[HITPOINTS] - pAttributes[HITPOINTS_CURRENT];
        } // if
        
        pAttributes[HITPOINTS_CURRENT] += plusHealth;
    } // eat
    
    public void quaff(Potion pot) {
        int numAffixes;
        StatBonus affix;
        
        /* Player has drunk an antimagic potion which removes at least one
            curse effect from the player */
        if (pot != null && pot.getPotionType() == Potion.ANTIMAGIC) {
            for (int i = 0; i < debuffCount; i++) {
                if (debuffList[i] instanceof Curse) {
                    removeDebuffAt(i--);
                } // if
            } // for
            
            return;
        } // if
        
        else if (pot != null && pot.getPotionType() == Potion.ANTIVENOM) {
            for (int i = 0; i < debuffCount; i++) {
                if (debuffList[i] instanceof Poison) {
                    removeDebuffAt(i--);
                } // if
            } // for
            
            return;
        } // else if
        
        if (potionEffects != null) {
            numAffixes = potionEffects.getNumAffixes();
            
            for (int i = 0; i < numAffixes; i++) {
                affix = potionEffects.getAffixAt(i);
                pAttributes[affix.getStatId()] -= affix.getStatMod();
            } // for
        } // if
        
        // If potion effects end, quaff() will be passed null type
        if (pot == null) {
            potionEffects = null;
            return;
        } // if
        
        potionEffects = pot;
        numAffixes = potionEffects.getNumAffixes();
        
        for (int i = 0; i < numAffixes; i++) {
            affix = potionEffects.getAffixAt(i);
            pAttributes[affix.getStatId()] += affix.getStatMod();
        } // for
    } // quaff
    
    public Potion getPotionEffects() {
        return potionEffects;
    } // getPotionEffects
    
    /* Read a scroll, releasing its magical effects */
    public void read(Scroll scr) {
        StatBonus affix;
        
        // Adjust any player affixes the scroll has
        for (int i = 0; i < scr.getNumAffixes(); i++) {
            affix = scr.getAffixAt(i);
            pAttributes[affix.getStatId()] += affix.getStatMod();
        } // for
        
        scr.extraEffects();
    } // read
    
    public void applyDebuff(Debuff debuff) {
        int numAffixes;
        int dimCurseIndex = 0;
        StatBonus affix;
        
        /* Replaces curse with shortest duration if player is afflicted by max
            number of debuffs */
        if (debuffCount == 5) {
            for (int i = 0; i < debuffList.length - 1; i++) {
                if (debuffList[i].getDuration() < debuffList[i + 1].getDuration()) {
                    dimCurseIndex = i;
                } // if
            } // for
            removeDebuffAt(dimCurseIndex);
        } // if
        
        if (debuff instanceof Curse) {
        
            numAffixes = ((Curse)debuff).getNumAffected();

            for (int i = 0; i < numAffixes; i++) {
                affix = ((Curse)debuff).getAffectedStatAt(i);
                pAttributes[affix.getStatId()] -= affix.getStatMod();
            } // for-i
        } // if
        
        else if (debuff instanceof Poison) {
            poisons[poisonCount++] = (Poison)debuff;
        } // else if
        
        debuffList[debuffCount++] = debuff;
    } // applyDebuff
    
    public void removeDebuffAt(int index) {
        int numAffixes;
        Debuff debuff = debuffList[index];
        StatBonus affix;
        
        if (debuff == null) {
            return;
        } // if
        
        debuffCount--;
        
        for (int i = index; i < debuffList.length - 1; i++) {
            debuffList[i] = debuffList[i + 1];
        } // for
        
        if (debuff instanceof Curse) {
            numAffixes = ((Curse)debuff).getNumAffected();

            for (int i = 0; i < numAffixes; i++) {
                affix = ((Curse)debuff).getAffectedStatAt(i);
                pAttributes[affix.getStatId()] += affix.getStatMod();
            } // for
        } // if
        
        else if (debuff instanceof Poison) {
            poisonCount--;
            
            for (int i = index; i < poisons.length - 1; i++) {
                poisons[i] = poisons[i + 1];
            } // for
        } // else if
    } // removeDebuffAt
    
    public int getNumDebuffs() {
        return debuffCount;
    } // getNumDebuffs
    
    public Debuff getDebuffAt(int index) {
        return debuffList[index];
    } // getCurseList
    
    /* Updates player (curse and potion durations) each turn of the 
        game (i.e., when the user moves the player a space in the game) */
    public void tick() {
        // Damage-over-time effects; aka poison
        for (int i = 0; i < poisonCount; i++) {
            if (poisons[i].nextTick()) {
                pAttributes[HITPOINTS_CURRENT] -= poisons[i].getTickDamage();
                
                // Inform the player how much health was lost and to what
                TEXT_LOG.writeFormattedString(poisons[i].toString(), Common.DAMAGE_TEXT_COLOR, 
                        new LineElement(poisons[i].getName(), true, 
                        Common.POISON_TEXT_COLOR));
            }  // if
        } // for
        
        // Decrease afflicted curses' durations
        for (int i = 0; i < debuffCount; i++) {
            debuffList[i].decrDuration();
            
            // curse has expired, remove it from the list
            if (debuffList[i].getDuration() == 0) {
                removeDebuffAt(i);
                i--;
            } // if
        } // for
        
        if (potionEffects != null) {
            // Decrease potion duration
            potionEffects.decrDuration();

            if (potionEffects.getDuration() == 0) {
                quaff(null);
                potionEffects = null;
            } // if
        } // if
    } // tick
    
    /* XP FORMULA NOT FINAL
        Simple formula to determine the amount of experience needed to 
        reach the level given in the parameters.  Used to let the PLAYER_ICON
        know how much XP he/she must get to reach the next level */
    public int getExperienceReq(int level) {
        return (int)(Math.ceil(level * Math.sqrt(Math.pow(level, 3))));
    } // getExperienceReq
    
    /* FORMULA NOT FINAL
        Returns the minimum damage necessary for the combat calculator. 
        Minimum damage is based on:
        * Level
        * Weapon damage range (Lower-bound) (NYI)
        * Attack level */
    public float getMinPhysicalDamage() {
        return (float) ( level + ( (level + 1) * .075) ) + getStrengthBonus() + 
                minBonusFromWeapon;
    } // getMinPhysicalDamage
    
    /* FORMULA NOT FINAL 
        Returns the maximum damage necessary for the combat calculator.
        Maximum damage is based on:
        * Level
        * Weapon damage range (Upper-bound) (NYI)
        * Strength level */
    public float getMaxPhysicalDamage() {
        return (float) ( level + ( (level + 1) * 1.2) ) + getStrengthBonus() + 
                maxBonusFromWeapon;
    } // getMaxPhysicalDamage
    
    /* FORMULA NOT FINAL 
        Calculates and returns the minimum magical damage bound.
        Magical damage is based solely on the PLAYER_ICON's level and is increased
        by the magic element being cast */
    public float getMinMagicDamage() {
        return (float) level * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    } // getMinMagicDamage
    
    /* FORMULA NOT FINAL 
        Calculates and returns the maximum magical damage bound*/
    public float getMaxMagicDamage() {
        return (float) ( level + ( (level + 1) * 1.25) ) * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    } // getMaxMagicDamage
    
    /* FORMULA NOT FINAL 
        Strength increases the PLAYER_ICON's maximum hit */
    public float getStrengthBonus() {
        float strengthBonus = (float) (getAttribute(STRENGTH_CURRENT) * 1.26);
        
        return strengthBonus > 0 ? strengthBonus : 0f;
    } // getStrengthBonus
    
    /* FORMULA NOT FINAL
        Returns the PLAYER_ICON's chance to critically hit, as a percentage
        Critical hit chance is based on Attack, with each point of Attack
        having less of an effect the higher level the PLAYER_ICON is */
    public float getCritChance() {
        float critChance = (float) ( 1f / (34.91f / (getAttribute(ACCURACY_CURRENT) * 10f)) );
        
        return critChance > 0 ? critChance : 0f;
    } // getCritChance
    
    /* FORMULA NOT FINAL 
        Returns the PLAYER_ICON's physical damage reduction, as a percentage
        Physical damage reduction reduces the PLAYER_ICON's damage taken from
        physical attacks by a direct percentage.  Physical damage reduction
        is based on Defense, with each point of Defense having less of an 
        effect the higher level the PLAYER_ICON is */
    public float getPhysicalDamageReduction() {
        float defenseBonus = (float) ( 1f / (13.72f / (getAttribute(DEFENSE_CURRENT) * 10f)) );
        
        return defenseBonus > 0 ? defenseBonus : 0;
    } // getPhysicalDamageReduction
    
    /* FORMULA NOT FINAL
        Returns the PLAYER_ICON's chance to evade incoming physical attacks in the
        form of a percentage.  Evade chance is based on a PLAYER_ICON's Agility
        level, and each point of Agility is worth less the higher level the 
        PLAYER_ICON is. */
    public float getEvadeChance() {
        float evadeChance = (float) ( 1f / (45.3491f / (getAttribute(AGILITY_CURRENT) * 10f)) );
        
        return evadeChance > 0 ? evadeChance : 0;
    } // getEvadeChance
    
    /* FORMULA NOT FINAL 
        Returns the PLAYER_ICON's resistance to a particular element, given as
        the parameter.  Each type of resistance is weighed the same, differing
        only based on the resistance's level.  Each point in an element has
        less of an effect the higher level the PLAYER_ICON is.  The number returned
        is in the form of a percentage */
    public float getBonusToElement(int element) {
        return (float) ( 1f / (11.147f / (getAttribute(element) * 10f)) );
    } // getBonusToElement
    
    public int getBonusArmor() {
        return bonusArmor;
    } // getBonusArmor
    
    /* Returns the name associated with a given stat ID as a String */
    public static String idToString(int statId) {
        switch (statId) {
            case HITPOINTS:             return "Hitpoints";
            case ACCURACY:              return "Accuracy";
            case STRENGTH:              return "Strength";
            case DEFENSE:               return "Defense";
            case AGILITY:               return "Agility";

            case FIRE_MAGIC:            return "Fire Magic";
            case FROST_MAGIC:           return "Frost Magic";
            case CHAOS_MAGIC:           return "Chaos Magic";
            case HOLY_MAGIC:            return "Holy Magic";
            case PLUS_ALL_MAGIC:        return "All Magic";


            case HITPOINTS_CURRENT: 
            case ACCURACY_CURRENT:
            case STRENGTH_CURRENT:
            case DEFENSE_CURRENT:
            case AGILITY_CURRENT:


            case FIRE_MAGIC_CURRENT:
            case FROST_MAGIC_CURRENT:
            case CHAOS_MAGIC_CURRENT:
            case HOLY_MAGIC_CURRENT:    return idToString(statId - 10);
                
            default: return " ";
        } // switch
    } // idToString
} // Player