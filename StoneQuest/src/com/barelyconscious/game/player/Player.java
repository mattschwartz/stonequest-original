/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        Player.java
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

import com.barelyconscious.game.Common;
import com.barelyconscious.game.item.definitions.*;
import com.barelyconscious.game.player.activeeffects.Poison;
import com.barelyconscious.game.player.activeeffects.Debuff;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.*;
import com.barelyconscious.game.player.activeeffects.Buff;
import com.barelyconscious.game.spawnable.Sprite;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.systems.messaging.MessageSystem;
import com.barelyconscious.services.TextLogMessageData;
import com.barelyconscious.services.TextLogWriterService;

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
    private Potion activePotion;
    private Poison[] poisons = new Poison[5];
    private ArrayList<Debuff> debuffs = new ArrayList();
    private ArrayList<Buff> buffs = new ArrayList();

    private ArrayList identifiedScrolls = new ArrayList<Integer>();

    /**
     * Creates a new Player; only one should be instantiated per runtime
     */
    public Player(final MessageSystem messageSystem) {
        super(playerName, Tile.PLAYER_TILE_ID, messageSystem);

        setStartingAttributes();
        elementCastSchool = FIRE_MAGIC_BONUS; // testing
        levelUp();
    }

    /**
     * @return the current amount of experience accumulated by the Player since the previous level up
     */
    public int getCurrentExp() {
        return curExperience;
    }

    /**
     * @return the unspent attribute points the Player can spend to increase the levels of his or her skills
     */
    public int getUnspentAttributePoints() {
        return attributePoints;
    }

    /**
     * Sets the level of attributeId to the new attribute value; if attributeId does not exist, no changes are made
     *
     * @param attributeId       the id of the attribute to be set
     * @param newAttributeValue the new value for the attribute
     */
    public void setAttribute(int attributeId, int newAttributeValue) {
        if (attributeId < HITPOINTS || attributeId > HOLY_MAGIC_CURRENT) {
            System.err.println(" [ERR_Player] Attempted to set value of attribute: "
                + attributeId + " to " + newAttributeValue);
            return;
        }

        playerAttributes[attributeId] = newAttributeValue;
    }

    /**
     * @param attributeId the id of the attribute desired
     * @return the value of the attribute ad attributeId
     */
    public double getAttribute(int attributeId) {
        if (attributeId < HITPOINTS || attributeId > CHAOS_MAGIC_CURRENT) {
            System.err.println(" [ERR_Player] Attempted to get value of attribute: "
                + attributeId);
            return -1;
        }

        return playerAttributes[attributeId];
    }

    /**
     * @return the school of magic the Player is using to cast his or her basic spells
     */
    public int getSchoolOfMagic() {
        return elementCastSchool;
    }

    /**
     * Adds scrollId to the list of Scroll ids identified by the Player for future reference when the Player encounters
     * Scrolls of similar id
     *
     * @param scrollId
     */
    public void addScrollToIdentifieds(int scrollId) {
        identifiedScrolls.add(scrollId);
    }

    /**
     * @param scrollId the id to check against the list of previously identified Scrolls
     * @return true if the Scroll has been previously seen by the Player
     */
    public boolean isScrollIdentified(int scrollId) {
        return identifiedScrolls.contains(scrollId);
    }

    /**
     * Checks if a point can be allocated to Attribute,
     *
     * @param statId the Attribute to add a point to
     * @return true if a point can be allocated to
     * @statId, returning true if a point can be added and false if not.
     * @statId
     */
    public boolean canAddPointToAttribute(int statId) {
        if (statId == PLUS_ALL_MAGIC_BONUS) {
            return attributePoints >= 3;
        }
        return attributePoints > 0;
    }

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
        }

        // Not enough attribute points
        if (attributePoints < 1) {
            return;
        }

        // Player has selected +All Elements
        if (statId == PLUS_ALL_MAGIC_BONUS) {

            // Must have 3 attribute points
            if (attributePoints < 3) {
                return;
            }

            for (int i = FIRE_MAGIC_BONUS; i <= HOLY_MAGIC_BONUS; i++) {
                if (playerAttributes[i + 10] == STAT_LEVEL_CAP) {
                    elementsAtCap++;
                    continue;
                }

                playerAttributes[i]++;
                playerAttributes[i + 10]++;
            }

            // All elements are at cap
            if (elementsAtCap == 4) {
                return;
            }

            attributePoints -= 3;
        }
        // Player has selected a different stat
        else {
            // Raise the current level in that stat by 1 as well
            // Does not lower or raise boosted stats (potions) - REMVOED
            playerAttributes[statId + 10]++;

            attributePoints--;
        }

        // Finally, increase the stat level by one
        playerAttributes[statId]++;
    }

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
    }

    /* Causes all current stat levels to stabalize by 1 point, 
     either up (regen life) or down (stat potions) */
    public void normalizeStats() {
        for (int i = ACCURACY_CURRENT; i <= HOLY_MAGIC_CURRENT; i++) {
            if (playerAttributes[i] == playerAttributes[i - 10]) {
                continue;
            }

            if (playerAttributes[i] < playerAttributes[i - 10]) {
                playerAttributes[i]++;
            } else {
                playerAttributes[i]--;
            }
        }
    }

    /**
     * Temporarily alters the value of
     *
     * @param attribute the int value of the attribute to be changed, should be given as a base stat value instead of
     *                  the current value
     * @param modifier  the amount that the attribute should change by; this value will be directly added to the current
     *                  attribute's level
     * @attribute by
     * @modifier.
     */
    public void setTemporaryAttribute(int attribute, double modifier) {
        // Make sure attribute is going to modify the current value of the attribute, not the
        // level of the attribute
        if (attribute < PLUS_ALL_MAGIC_BONUS) {
            attribute += 10;
        }

        playerAttributes[attribute] += modifier;
    }

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
        }

        attributePoints += 5;
    }

    /**
     * Interact with a Sprite, attacking a hostile entities and opening dialogue with nonhostile entities; or
     * interacting with Loot
     *
     * @param entity the Sprite to interact with
     */
    @Override
    public void interactWith(Sprite entity) {
        double attackDamage;

        /* If Sprite is an Entity */
        if (entity instanceof Entity) {
            attackDamage = (Math.random() * 3) + 0.1f;
            ((Entity) entity).changeHealthBy(-attackDamage);

            final String message = String.format(
                "You hit %s for %.1f physical.",
                entity.getDisplayName(),
                attackDamage);

            getMessageSystem().sendMessage(
                TextLogWriterService.LOG_EVENT_CODE,
                new TextLogMessageData(message)
                    .with(new LineElement(entity.getDisplayName(), true, Common.FONT_ENTITY_LABEL_RGB)),
                this);
        }
        /* If Sprite is a Loot object or Doodad */
        else {
            entity.interact();
        }
    }

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
        }
    }

    /**
     * Checks if the Player is currently wearing armor at armorSlot
     *
     * @param armorSlot the type of armor to check against
     * @return true if the Player already has armor on of that type
     */
    public boolean isArmorSlotEquipped(int armorSlot) {
        return equippedArmorSlots[armorSlot] != null;
    }

    public void equip(int slotId, Item item) {
        if (equippedArmorSlots[slotId] != null) {
            unequip(slotId);
        }

        equippedArmorSlots[slotId] = item;
        item.getItemAffixes().forEach(t ->
            setTemporaryAttribute(t.getAttributeId(), t.getAttributeModifier()));
    }

    public void unequip(int slotId) {
        unequipItem(equippedArmorSlots[slotId]);
    }

    /**
     * Equip an Item; either a Weapon or a piece of Armor
     *
     * @param item the Item to equip
     */
    public void equipItem(Item item) {
        if (item instanceof Armor) {
            final Armor armor = (Armor) item;
            final int armorSlot = armor.getSlotId();

            if (equippedArmorSlots[armorSlot] != null) {
                unequipItem(equippedArmorSlots[armorSlot]);
            }

            armor.setEquipped(true);
            equippedArmorSlots[armorSlot] = armor;

            bonusArmor += armor.getBonusArmor();
        } else if (item instanceof Weapon) {
            final Weapon weapon = (Weapon) item;

            if (equippedArmorSlots[MAIN_HAND_SLOT_ID] != null) {
                unequipItem(item);
            }

            weapon.setEquipped(true);
            equippedArmorSlots[MAIN_HAND_SLOT_ID] = weapon;
        }

        item.getItemAffixes().forEach(t ->
            setTemporaryAttribute(t.getAttributeId(), t.getAttributeModifier()));
    }

    /**
     * Unequip the Item from the Player
     *
     * @param item the Item to unequip
     */
    public void unequipItem(Item item) {
        if (item instanceof Armor) {
            final Armor armor = (Armor) item;

            armor.setEquipped(false);
            equippedArmorSlots[armor.getSlotId()] = null;

            bonusArmor -= armor.getBonusArmor();
        } else if (item instanceof Weapon) {
            final Weapon weapon = (Weapon) item;

            weapon.setEquipped(false);
            equippedArmorSlots[MAIN_HAND_SLOT_ID] = null;

            minBonusFromWeapon = 0f;
            maxBonusFromWeapon = 0f;
        }

        item.getItemAffixes().forEach(t ->
            setTemporaryAttribute(t.getAttributeId(), -t.getAttributeModifier()));
    }

    /**
     * Eat some food, acquiring its nutrients
     *
     * @param food the food to eat
     */
    public void eat(Food food) {
        double plusHealth = food.getChangeInHealth();

        // Don't adjust health if it's above max
        if (getAttribute(HITPOINTS_CURRENT) > playerAttributes[HITPOINTS]) {
            return;
        }

        /* If Player health is already at maximum, don't increase it further */
        if (getAttribute(HITPOINTS_CURRENT) > playerAttributes[HITPOINTS]) {
            return;
        }

        if (playerAttributes[HITPOINTS_CURRENT] + plusHealth > playerAttributes[HITPOINTS]) {
            plusHealth = playerAttributes[HITPOINTS] - playerAttributes[HITPOINTS_CURRENT];
        }

        playerAttributes[HITPOINTS_CURRENT] += plusHealth;
    }

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
//                }
//            }
//            
//            return;
//        }
//        
//        /* Player has quaffed an antivenom potion which removes all Poison effects
//            on the Player */
//        else if (pot != null && pot.getPotionType() == Potion.ANTIVENOM) {
//            for (int i = 0; i < currentNumDebuffs; i++) {
//                if (debuffList[i] instanceof Poison) {
//                    removeDebuffAt(i--);
//                }
//            }
//            
//            return;
//        }
//        
//        /* Potion is a stat buff potion */
//        else if (activePotion != null) {
//            numAffixes = activePotion.getNumAffixes();
//            
//            for (int i = 0; i < numAffixes; i++) {
//                affix = activePotion.getAffixAt(i);
//                playerAttributes[affix.getAttributeId()] -= affix.getAttributeModifier();
//            }
//        }
//        
//        /* When a Potion's effects end, quaff is called with a null argument
//            for some reason */
//        if (pot == null) {
//            activePotion = null;
//            return;
//        }
//        
//        activePotion = pot;
//        numAffixes = activePotion.getNumAffixes();
//        
//        for (int i = 0; i < numAffixes; i++) {
//            affix = activePotion.getAffixAt(i);
//            playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
//        }
    }

    /**
     * Read a Scroll, acquiring its benefits and any extra abilities it possesses as well as adding it to the list of
     * identified Scrolls
     *
     * @param scroll
     */
    public void read(final Scroll scroll) {
        scroll.getItemAffixes().forEach(t ->
            setTemporaryAttribute(t.getAttributeId(), t.getAttributeModifier()));

        scroll.extraEffects();

        scroll.identifyScroll();


        getMessageSystem().sendMessage(
            TextLogWriterService.LOG_EVENT_CODE,
            new TextLogMessageData("It was a " + scroll.getDisplayName() + "!"),
            this);
    }

    public void applyBuff(Buff buff) {
        // Only one potion can be applied at any one time, and subsequent potions will override
        // previous potion effects
        if (buff.getBuffType() == Buff.POTION) {
            for (int i = 0; i < buffs.size(); i++) {
                if (buffs.get(i).getBuffType() == Buff.POTION) {
                    buffs.remove(i);
                }
            }
        }

        buffs.add(buff);
    }

    public void removeBuffAt(int index) {
        if (index < 0 || index >= buffs.size()) {
            return;
        }

        buffs.remove(index);
    }

    public void removeBuff(Buff buff) {
        buffs.remove(buff);
    }

    public List<Debuff> getDebuffs() {
        return debuffs;
    }

    public int getNumBuffs() {
        return buffs.size();
    }

    public Buff getBuffAt(int index) {
        return index < 0 || index >= buffs.size() ? null : buffs.get(index);
    }

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
//                }
//            }
//            removeDebuffAt(dimCurseIndex);
//        }
//
//        if (debuff instanceof Curse) {
//
//            numAffixes = ((Curse) debuff).getNumAffectedAttributes();
//
//            for (int i = 0; i < numAffixes; i++) {
//                affix = ((Curse) debuff).getAffectedAttributeAt(i);
//                playerAttributes[affix.getAttributeId()] -= affix.getAttributeModifier();
//            }
//        }
//        else if (debuff instanceof Poison) {
//            poisons[poisonCount++] = (Poison) debuff;
//        }

        debuffs.add(debuff);
    }

    /**
     * Remove a debuff at index from the list of afflictions on the Player
     *
     * @param index the location of the debuff to be removed
     */
    public void removeDebuffAt(int index) {
        debuffs.remove(index);

//        if (debuff == null) {
//            return;
//        }
//
//        currentNumDebuffs--;
//
//        for (int i = index; i < debuffs.length - 1; i++) {
//            debuffs[i] = debuffs[i + 1];
//        }
//
//        if (debuff instanceof Curse) {
//            numAffixes = ((Curse) debuff).getNumAffectedAttributes();
//
//            for (int i = 0; i < numAffixes; i++) {
//                affix = ((Curse) debuff).getAffectedAttributeAt(i);
//                playerAttributes[affix.getAttributeId()] += affix.getAttributeModifier();
//            }
//        }
//        else if (debuff instanceof Poison) {
//            poisonCount--;
//
//            for (int i = index; i < poisons.length - 1; i++) {
//                poisons[i] = poisons[i + 1];
//            }
//        }
    }

    public void removeDebuff(Debuff debuff) {
        debuffs.remove(debuff);
    }

    /**
     * @return the number of afflictions and infections on the Player
     */
    public int getNumDebuffs() {
        return debuffs.size();
    }

    /**
     * @param index the index of the desired debuff
     * @return the debuff located at index
     */
    public Debuff getDebuffAt(int index) {
        return index < 0 || index >= debuffs.size() ? null : debuffs.get(index);
    }

    @Override
    public double getCurrentHealth() {
        return playerAttributes[HITPOINTS_CURRENT];
    }

    @Override
    public double getMaxHealth() {
        return playerAttributes[HITPOINTS];
    }

    /**
     * Updates the Player class when the Game ticks; debuff effects have their durations decreased; buffs have their
     * durations decreased; dead effects are removed.
     */
    @Override
    public void tick() {
        for (int i = 0; i < debuffs.size(); i++) {
            debuffs.get(i).tick();
        }

        for (int i = 0; i < buffs.size(); i++) {
            buffs.get(i).tick();
        }


        // Damage-over-time effects; aka poison
//        for (int i = 0; i < poisonCount; i++) {
//            if (poisons[i].nextTick()) {
//                playerAttributes[HITPOINTS_CURRENT] -= poisons[i].getTickDamage();
//
//                // Inform the player how much health was lost and to what
//                textLog.writeFormattedString(poisons[i].toString(), Common.FONT_DAMAGE_TEXT_RGB,
//                        new LineElement(poisons[i].getDisplayName(), true,
//                        Common.FONT_POISON_LABEL_RGB));
//            }
//        }
//
//        // Decrease afflicted curses' durations
//        for (int i = 0; i < currentNumDebuffs; i++) {
//            debuffs[i].decrDuration();
//
//            // curse has expired, remove it from the list
//            if (debuffs[i].getDuration() == 0) {
//                removeDebuffAt(i);
//                i--;
//            }
//        }

//        if (activePotion != null) {
//            // Decrease potion duration
//            activePotion.decrDuration();
//
//            if (activePotion.getDurationInTicks() == 0) {
//                quaff(null);
//                activePotion = null;
//            }
//        }
    }

    /* XP FORMULA NOT FINAL
     Simple formula to determine the amount of experience needed to 
     reach the level given in the parameters.  Used to let the PLAYER_ICON
     know how much XP he/she must get to reach the next level */
    public int getExperienceReq(int level) {
        return (int) (Math.ceil(level * Math.sqrt(Math.pow(level, 3))));
    }

    /* FORMULA NOT FINAL
     Returns the minimum damage necessary for the combat calculator. 
     Minimum damage is based on:
     * Level
     * Weapon damage range (Lower-bound) (NYI)
     * Attack level */
    public double getMinPhysicalDamage() {
        return (getLevel() + ((getLevel() + 1) * .075)) + getStrengthBonus()
            + (equippedArmorSlots[MAIN_HAND_SLOT_ID] == null ? 0 : ((Weapon) equippedArmorSlots[MAIN_HAND_SLOT_ID]).getMinDamageBonus());
    }

    /* FORMULA NOT FINAL 
     Returns the maximum damage necessary for the combat calculator.
     Maximum damage is based on:
     * Level
     * Weapon damage range (Upper-bound) (NYI)
     * Strength level */
    public double getMaxPhysicalDamage() {
        return (getLevel() + ((getLevel() + 1) * 1.2)) + getStrengthBonus()
            + (equippedArmorSlots[MAIN_HAND_SLOT_ID] == null ? 0 : ((Weapon) equippedArmorSlots[MAIN_HAND_SLOT_ID]).getMaxDamageBonus());
    }

    /* FORMULA NOT FINAL 
     Calculates and returns the minimum magical damage bound.
     Magical damage is based solely on the PLAYER_ICON's level and is increased
     by the magic element being cast */
    public double getMinMagicDamage() {
        return getLevel() * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    }

    /* FORMULA NOT FINAL 
     Calculates and returns the maximum magical damage bound*/
    public double getMaxMagicDamage() {
        return (getLevel() + ((getLevel() + 1) * 1.25)) * (getBonusToElement(elementCastSchool) * 0.01f + 1f);
    }

    /* FORMULA NOT FINAL 
     Strength increases the PLAYER_ICON's maximum hit */
    public double getStrengthBonus() {
        double strengthBonus = (getAttribute(STRENGTH_CURRENT) * 1.26);

        return strengthBonus > 0 ? strengthBonus : 0f;
    }

    /* FORMULA NOT FINAL
     Returns the PLAYER_ICON's chance to critically hit, as a percentage
     Critical hit chance is based on Attack, with each point of Attack
     having less of an effect the higher level the PLAYER_ICON is */
    public double getCritChance() {
        double critChance = (1f / (34.91f / (getAttribute(ACCURACY_CURRENT) * 10f)));

        return critChance > 0 ? critChance : 0f;
    }

    /* FORMULA NOT FINAL 
     Returns the PLAYER_ICON's physical damage reduction, as a percentage
     Physical damage reduction reduces the PLAYER_ICON's damage taken from
     physical attacks by a direct percentage.  Physical damage reduction
     is based on Defense, with each point of Defense having less of an 
     effect the higher level the PLAYER_ICON is */
    public double getPhysicalDamageReduction() {
        double defenseBonus = (1f / (13.72f / (getAttribute(DEFENSE_CURRENT) * 10f)));

        return defenseBonus > 0 ? defenseBonus : 0;
    }

    /* FORMULA NOT FINAL
     Returns the PLAYER_ICON's chance to evade incoming physical attacks in the
     form of a percentage.  Evade chance is based on a PLAYER_ICON's Agility
     level, and each point of Agility is worth less the higher level the 
     PLAYER_ICON is. */
    public double getEvadeChance() {
        double evadeChance = (1f / (45.3491f / (getAttribute(AGILITY_CURRENT) * 10f)));

        return evadeChance > 0 ? evadeChance : 0;
    }

    /* FORMULA NOT FINAL 
     Returns the PLAYER_ICON's resistance to a particular element, given as
     the parameter.  Each type of resistance is weighed the same, differing
     only based on the resistance's level.  Each point in an element has
     less of an effect the higher level the PLAYER_ICON is.  The number returned
     is in the form of a percentage */
    public double getBonusToElement(int element) {
        return (1f / (11.147f / (getAttribute(element) * 10f)));
    }

    public int getBonusArmor() {
        return bonusArmor;
    }

    /* Returns the displayName associated with a given stat ID as a String */
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
        }
    }
}
