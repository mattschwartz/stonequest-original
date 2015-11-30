/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Entity.java
 * Author:           Matt Schwartz
 * Date created:     07.19.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 this file.  You are not allowed to take credit for code
 that was not written fully by yourself, or to remove 
 credit from code that was not written fully by yourself.  
 Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: An Entity is some character that the player can interact with
 in some way.  All enemies, vendors and the Player are considered
 Entities.  Each Enity has some number of health points, a level
 and a reach, which is how far they can interact with the Player.
 **************************************************************************** */
package com.barelyconscious.game.spawnable;

import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.item.Equippable;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.player.condition.Condition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Entity extends Sprite {

    protected int level;
    protected int reach = 1;
    protected int lightRadius = 7;
    protected double totalHealth;
    /* Integer accessor values for the various entity attributes. All entities
     have the same attributes (including the player); though, most entities
     do not make use of all attributes, except the player. */
    public static final int HEALTH_ATTRIBUTE = 0;
    public static final int STRENGTH_ATTRIBUTE = 1;
    public static final int ACCURACY_ATTRIBUTE = 2;
    public static final int DEFENSE_ATTRIBUTE = 3;
    public static final int EVASION_ATTRIBUTE = 4;
    public static final int FIRE_MAGIC_ATTRIBUTE = 5;
    public static final int FROST_MAGIC_ATTRIBUTE = 6;
    public static final int HOLY_MAGIC_ATTRIBUTE = 7;
    public static final int CHAOS_MAGIC_ATTRIBUTE = 8;
    public static final int FAITH_ATTRIBUTE = 9;
    public static final int NUM_ATTRIBUTES = 10;
    /* Equippable items (armor and weapons) variables */
    public static final int HELMET_SLOT_ID = 0;
    public static final int MAIN_HAND_SLOT_ID = 1;
    public static final int CHEST_SLOT_ID = 2;
    public static final int OFF_HAND_SLOT_ID = 3;
    public static final int BELT_SLOT_ID = 4;
    public static final int EARRING_SLOT_ID = 5;
    public static final int GREAVES_SLOT_ID = 6;
    public static final int NECK_SLOT_ID = 7;
    public static final int BOOTS_SLOT_ID = 8;
    public static final int RING_SLOT_ID = 9;
    public static final int NUM_EQUIP_SLOTS = 10;
    // Array to keep track of all of the attributes easily
    protected double[] attributes;
    // Array to keep track of equipped items
    protected Equippable[] equippedItems;
    protected final Inventory inventory;
    /**
     * Temporary effects consist of both beneficial effects (e.g., stat boosting
     * potions) and harmful effects (e.g., curses). This List keeps track of all
     * of the temporary effects that affect the Entity.
     */
    protected List<Condition> conditions = new ArrayList<Condition>();

    public Entity(String name, UIElement entityIcon) {
        super(name, 0, 0, true, entityIcon);
        inventory = new Inventory();
        equippedItems = new Equippable[NUM_EQUIP_SLOTS];
        attributes = new double[NUM_ATTRIBUTES];
    } // constructor

    public Entity(String name, int x, int y, UIElement entityIcon) {
        super(name, x, y, true, entityIcon);
        inventory = new Inventory();
        equippedItems = new Equippable[NUM_EQUIP_SLOTS];
        attributes = new double[NUM_ATTRIBUTES];
        attributes[HEALTH_ATTRIBUTE] = totalHealth = 10;
    } // constructor

    /**
     *
     * @return the Entity's inventory
     */
    public Inventory getInventory() {
        return inventory;
    } // getInventory

    /**
     * Returns the value for the supplied attribute identifier.
     *
     * @param id the attribute identifier for which a value is expected
     * @return the value of the supplied attribute identifier
     */
    public double getAttribute(int id) {
        if (id >= NUM_ATTRIBUTES) {
            System.err.println(" [ERROR] Attempted access to non-existent player attribute (" + id + ").");
            return -1;
        } // if

        return Math.max(0, attributes[id]);
    } // getAttribute

    /**
     * Adjusts the entity's attribute, as designated by the supplied attribute
     * id by amount.
     *
     * @param attributeId the integer value for the attribute to be adjusted
     * @param amount the amount by which the attribute should be adjusted
     */
    public void adjustAttribute(int attributeId, double amount) {
        if (attributeId < 0 || attributeId >= NUM_ATTRIBUTES) {
            System.err.println(" [ERROR] Attempted to adjust attribute (" + attributeId + ") that is not valid.");
            return;
        } // if

        attributes[attributeId] = attributes[attributeId] + amount;
    } // adjustAttribute

    /**
     * Change the health of the Entity by amount
     *
     * @param amount the amount to change the health of the Entity by
     */
    public void changeHealthBy(double amount) {
        attributes[HEALTH_ATTRIBUTE] += amount;
        if (attributes[HEALTH_ATTRIBUTE] < 0) {
            attributes[HEALTH_ATTRIBUTE] = 0;
            remove();
        } // ifw
    } // changeHealthBy

    public Equippable getEquippedItemAt(int slotId) {
        if (slotId >= NUM_EQUIP_SLOTS) {
            return null;
        } // if

        return equippedItems[slotId];
    } // getEquippedItemAt

    /**
     * Consumes an index in the equippable items array on the player by setting
     * its value to the supplied item. It is expected that the item being
     * equipped adjust the player's attribute values as necessary.
     *
     * @param equippableItem the item to be equipped to the player
     */
    public void equipItem(Equippable equippableItem) {
        int slotId = equippableItem.getSlotId();

        if (equippedItems[slotId] != null) {
            System.err.println(" [NOTIFY] Attempting to equip " + equippableItem + " before unequipping: \"" + equippedItems[slotId] + "\"");
        } // if

        equippedItems[slotId] = equippableItem;
    } // equipItem

    /**
     * Removes an item from the player's equipped item slots by setting its
     * value to null. It is expected that the item being unequipped adjust the
     * player's attribute values as necessary.
     *
     * @param slotId which item to remove from the player's person
     */
    public void unequipItem(int slotId) {
        equippedItems[slotId] = null;
    } // unequipItem

    /**
     * Affects the Entity with a temporary effect which can be either beneficial
     * or harmful, depending on the effect. Any boosted or lowered attributes
     * are expected to be handled outside of this method.
     *
     * @param condition the effect with which to affect the Entity
     */
    public void affect(Condition condition) {
        conditions.add(condition);
    } // affect

    /**
     * Removes a temporary effect from the list of effects that affect the
     * Entity.
     *
     * @param condition the temporary effect to remove
     */
    public void dissolveCondiiton(Condition condition) {
        conditions.remove(condition);
    } // dissolveCondition

    /**
     * Removes a temporary effect from the list of effects that affect the
     * Entity.
     *
     * @param effect the temporary effect to remove
     */
    public void dissolveCondition(int index) {
        if (index >= conditions.size()) {
            return;
        } // if

        conditions.remove(index);
    } // dissolveCondition

    /**
     *
     * @return the current number of temporary effects that affect the Entity
     */
    public int getNumConditions() {
        return conditions.size();
    } // getNumConditions

    /**
     *
     * @return the Conditions currently affecting the Entity
     */
    public List<Condition> getConditions() {
        return conditions;
    } // getConditions

    /**
     *
     * @return the minimum amount of physical damage done by the Entity after
     * effects from Strength and the equipped weapon are considered but without
     * considering the opponent's defenses
     */
    public double getMinimumPhysicalDamage() {
        return 0;
    } // getMinimumPhysicalDamage

    /**
     *
     * @return the maximum amount of physical damage done by the Entity after
     * effects from Strength and the equipped weapon are considered but without
     * considering the opponent's defenses
     */
    public double getMaximumPhysicalDamage() {
        return 0;
    } // getMaximumPhysicalDamage

    /**
     *
     * @return the minimum amount of magical damage done by the Entity after
     * effects from equipped weapons are considered but without considering the
     * opponent's defenses
     */
    public double getMinimumMagicDamage() {
        return 0;
    } // getMinimumMagicDamage

    /**
     *
     * @return the maximum amount of magical damage done by the Entity after
     * effects from equipped weapons are considered but without considering the
     * opponent's defenses
     */
    public double getMaximumMagicDamage() {
        return 0;
    } // getMaximumMagicDamage

    /**
     *
     * @return the percentage amount (i.e., 1 = 1%) of increased physical damage
     * done by the Entity based on its amount of Strength
     */
    public double getBonusFromStrength() {
        return 0;
    } // getBonusFromStrength

    /**
     *
     * @return the chance as a percent (i.e., 1 = 1%) the Entity has to hit
     * another Entity with physical damage
     */
    public double getBonusFromAccuracy() {
        return 0;
    } // getBonusFromAccuracy

    /**
     *
     * @return the physical damage reduction from incoming attacks as a percent
     * (i.e., 1 = 1%)
     */
    public double getBonusFromDefense() {
        return 0;
    } // getBonusFromDefense

    /**
     *
     * @return the chance to completely avoid incoming physical attacks as a
     * percent chance (i.e., 1 = 1%)
     */
    public double getBonusFromEvasion() {
        return 0;
    } // getBonusFromEvasion

    /**
     *
     * @param elementId the element in question
     * @return the bonus to an element of magic as a percent (i.e., 1 = 1%)
     * which refers to both the increased amount of magic damage done in that
     * element as well as the reduced amount of magic taken by the Entity in
     * that element
     */
    public double getBonusFromElement(int elementId) {
        return 0;
    } // getBonusFromElement

    /**
     *
     * @return the level of the Entity
     */
    public int getLevel() {
        return level;
    } // getLevel

    /**
     * Should only be set once per Entity
     *
     * @param level the level of the Entity
     */
    public void setLevel(int level) {
        this.level = level;
    } // setLevel

    /**
     * Used by subclasses when creating the entity.
     *
     * @param health the amount of health for the Entity
     */
    protected void setHealth(float health) {
        attributes[HEALTH_ATTRIBUTE] = health;
        totalHealth = health;
    } // setHealth

    /**
     *
     * @return the maximum amount of hitpoints for the Entity
     */
    public double getMaxHealth() {
        return totalHealth;
    } // getMaxHealth

    /**
     *
     * @return the current health points of the Entity
     */
    public double getCurrentHealth() {
        return attributes[HEALTH_ATTRIBUTE];
    } // getCurrentHealth

    /**
     * Set the reach of the Entity to r; the default value for the reach of
     * Entities is 1
     *
     * @param r the new value for the reach of the Entity
     */
    public void setReach(int r) {
        reach = r;
    } // setReach

    /**
     * How far an Entity can perform action() from another Entity
     *
     * @return the reach for the Entity
     */
    public int getReach() {
        return reach;
    } // getReach

    /**
     *
     * @param newLightRadius changes the light radius of the Entity
     */
    public void setLightRadius(int newLightRadius) {
        lightRadius = newLightRadius;
    } // setLightRadius

    /**
     *
     * @return the light radius for the Entity
     */
    public int getLightRadius() {
        return lightRadius;
    } // getLightRadius

    /**
     * Whatever the Entity needs to do on a game tick, including marking the
     * Entity for removal if its health points fall below 0.
     */
    @Override
    public void tick() {
        if (attributes[HEALTH_ATTRIBUTE] <= 0) {
            remove();
            return;
        } // if

        /* Goes through the list of current temporary effects and calls their
         tick() function which handles whatever activities must be done every 
         game tick. */
        for (Condition condition : conditions) {
            condition.tick();
        } // for
    } // checkCanRemove

    /**
     * Removes the Entity from the world.
     */
    @Override
    public void remove() {
//        Sound.ENTITY_DEATH.play();
        // loot table stuff here?
        super.remove();
    } // onDeath

    /**
     * Entities additionally have a health bar.
     *
     * @param screen the screen to render to
     * @param xOffs the x location where to render the Entity
     * @param yOffs the y locaiton where to render the Entity
     */
    @Override
    public void render(Screen screen, int xOffs, int yOffs) {
        setVisible(Game.world.isTileLit(x, y));
        super.render(screen, xOffs, yOffs);

        if (!isVisible) {
            return;
        } // if

        int healthBarWidth = spriteIcon.getWidth() - 6;
        Color health = Color.GREEN;
        Color damagedHealth = Color.red;

        yOffs = yOffs + spriteIcon.getHeight() - 6;
        xOffs += 2;

        screen.drawRectangle(Color.black, xOffs, yOffs, healthBarWidth + 2, 4);
        screen.fillRectangle(damagedHealth, xOffs + 1, yOffs + 1, healthBarWidth, 2);
        screen.fillRectangle(health, xOffs + 1, yOffs + 1, (int) (healthBarWidth * 1.0 * Math.min(1, attributes[HEALTH_ATTRIBUTE] / totalHealth)), 2);
    } // render

    /* MOVEMENT ############################################################# */
    /**
     * Moves the Entity up on the Map one Tile.
     */
    public void moveUp() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x, y - 1)) {
            sprite = Game.getWorld().getSpriteAt(x, y - 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(0, -1);
    } // moveUp

    /**
     * Moves the Entity down on the Map one Tile.
     */
    public void moveDown() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x, y + 1)) {
            sprite = Game.getWorld().getSpriteAt(x, y + 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(0, 1);
    } // moveDown

    /**
     * Moves the Entity left on the Map one Tile.
     */
    public void moveLeft() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x - 1, y)) {
            sprite = Game.getWorld().getSpriteAt(x - 1, y);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(-1, 0);
    } // moveLeft

    /**
     * Moves the Entity right on the Map one Tile.
     */
    public void moveRight() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x + 1, y)) {
            sprite = Game.getWorld().getSpriteAt(x + 1, y);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(1, 0);
    } // moveRight

    /**
     * Moves the Entity up and left in the world by one tile in both directions.
     */
    public void moveUpLeft() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x - 1, y - 1)) {
            sprite = Game.getWorld().getSpriteAt(x - 1, y - 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(-1, -1);
    } // moveUpLeft

    /**
     * Moves the Entity up and right in the world by one tile in both
     * directions.
     */
    public void moveUpRight() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x + 1, y - 1)) {
            sprite = Game.getWorld().getSpriteAt(x + 1, y - 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(1, -1);
    } // moveUpRight

    /**
     * Moves the Entity down and left in the world by one tile in both
     * directions.
     */
    public void moveDownLeft() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x - 1, y + 1)) {
            sprite = Game.getWorld().getSpriteAt(x - 1, y + 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(-1, 1);
    } // moveDownLeft

    /**
     * Moves the Entity down and right in the world by one tile in both
     * directions.
     */
    public void moveDownRight() {
        Sprite sprite;

        if (hasCollision && !Game.getWorld().canMove(x + 1, y + 1)) {
            sprite = Game.getWorld().getSpriteAt(x + 1, y + 1);

            if (sprite != null) {
                interact(sprite);
            } // if

            return;
        } // if

        shiftBy(1, 1);
    } // moveDownRight

    /**
     * Returns a string describing the supplied attribute id integer.
     *
     * @param id the id of the attribute for which a string should be returned
     * @return the string for the attribute in question
     */
    public static String attributeIdToString(int id) {
        switch (id) {
            case HEALTH_ATTRIBUTE:
                return "Health";
            case STRENGTH_ATTRIBUTE:
                return "Strength";
            case ACCURACY_ATTRIBUTE:
                return "Accuracy";
            case DEFENSE_ATTRIBUTE:
                return "Defense";
            case EVASION_ATTRIBUTE:
                return "Evasion";
            case FIRE_MAGIC_ATTRIBUTE:
                return "Fire magic";
            case FROST_MAGIC_ATTRIBUTE:
                return "Frost magic";
            case HOLY_MAGIC_ATTRIBUTE:
                return "Holy magic";
            case CHAOS_MAGIC_ATTRIBUTE:
                return "Chaos magic";
            case FAITH_ATTRIBUTE:
                return "Faith";
            default:
                return " {ERROR:" + id + "}";
        } // switch-case
    } // attributeIdToString

    public static String equipmentSlotToString(int slotId) {
        switch (slotId) {
            case NECK_SLOT_ID:
                return "necklace";
            case HELMET_SLOT_ID:
                return "helmet";
            case EARRING_SLOT_ID:
                return "earring";
            case CHEST_SLOT_ID:
                return "chest";
            case MAIN_HAND_SLOT_ID:
                return "main hand";
            case OFF_HAND_SLOT_ID:
                return "off hand";
            case BELT_SLOT_ID:
                return "belt";
            case GREAVES_SLOT_ID:
                return "greaves";
            case RING_SLOT_ID:
                return "ring";
            case BOOTS_SLOT_ID:
                return "boots";
            default:
                return "??";
        } // switch
    } // equipmentSlotToString
} // Entity
