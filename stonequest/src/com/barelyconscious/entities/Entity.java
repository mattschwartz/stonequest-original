/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Entity.java
 * Author:            Matt Schwartz
 * Date Created:      05.11.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.entities;

import com.barelyconscious.entities.player.Inventory;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

    public static enum attr {

        HITPOINTS,
        STRENGTH,
        ACCURACY,
        DEFENSE,
        EVASION,
        FIRE_MAGIC,
        ICE_MAGIC,
        HOLY_MAGIC,
        CHAOS_MAGIC,
        FAITH;

        public static final int NUM_ATTRIBUTES = values().length;
    }

    private final Map<attr, Double> attributes;
    private final Inventory inventory;
    protected String name;

    public Entity() {
        attributes = new HashMap<>();
        inventory = new Inventory();
        setDefaultAttributes();
    }

    private void setDefaultAttributes() {
        for (attr attribute : attributes.keySet()) {
            attributes.put(attribute, 10.0);
        }
    }

    public void setAttribute(attr attribute, double value) {
        attributes.put(attribute, value);
    }

    public double getAttribute(attr attribute) {
        return attributes.get(attribute);
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void onSpawn() {
    }

    public void onDeath() {
    }

    public void onDamageTaken() {
    }

    public void onDamageDealt() {
    }
} // Entity
