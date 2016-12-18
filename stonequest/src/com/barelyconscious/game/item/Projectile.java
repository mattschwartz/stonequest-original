/* *****************************************************************************
 * File Name:         Projectile.java
 * Author:            Matt Schwartz
 * Date Created:      12.17.2012
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  Projectiles are a subclass of Item and superclass to all
 *                    types of projectiles found in the game.  A projectile is
 *                    such as an arrow, which require Bows to use, bolts, which
 *                    require crossbows to use, and darts, which can be thrown
 *                    free hand.  Different types of projectile tips provide the
 *                    arrow with different types of attack bonuses when used.
 *                    Projectiles add a ranged combat mechanic to the game and
 *                    can be enchanted or augmented to provide additional effects
 *                    which cannot be reused when the Projectile is picked up again
 *                    by the player.  
 *                    Some features NYI but that I would like implemented:
 *                   -Projectile augmentations such as flame, paralysis, freezing,
 *                     envenomed, returning (projectiles automatically return to
 *                     the player when fired), confusion (causes monsters to attack
 *                     other monsters for some amount of time), explosive
 ************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.spawnable.Entity;

public class Projectile extends Item {

    public static final ProjectileTip BRONZE_TIP = new ProjectileTip(1, 5, 5);
    public static final ProjectileTip IRON_TIP = new ProjectileTip(5, 15, 5);
    public static final ProjectileTip STEEL_TIP = new ProjectileTip(15, 35, 12);
    public static final ProjectileTip TITANIUM_TIP = new ProjectileTip(255, 515, 35);
    private boolean requiresBow;
    private ProjectileTip metal;

    /**
     * Create a new Projectile Item type with the following parameters
     *
     * @param name the name of the Projectile visible to the player
     * @param sellValue the value in gold vendors are willing to give the player
     * in exchange for the item
     * @param stackSize the stack size of the Projectile
     * @param itemIcon each type of Projectile has corresponding artwork that is
     * drawn to the Screen when the Item is in the world
     * @param doesRequireBow true if the Projectile requires a bow or crossbow
     * to fire
     * @param metal the type of metal of the Projectile; stronger metals have
     * better attack bonuses
     */
    public Projectile(String name, int itemLevel, int sellValue, int stackSize, boolean requiresBow, ProjectileTip metal, UIElement itemIcon, Entity owner) {
        super(name, itemLevel, sellValue, stackSize, itemIcon, owner);

        this.metal = metal;
        this.requiresBow = requiresBow;
    }

    /**
     * Change the bow requirement for the Projectile
     *
     * @param bool the new value for the bow requirement
     */
    public void setRequiresBow(boolean bool) {
        requiresBow = bool;
    }

    /**
     *
     * @return true if the Projectile must be fired from a bow or crossbow
     */
    public boolean doesRequireBow() {
        return requiresBow;
    }

    /**
     *
     * @return the ProjectileTip metal of the Projectile
     */
    public ProjectileTip getMetal() {
        return metal;
    }

    @Override
    public String getDescription() {
        return "Fire at an enemy from a distance, dealing damage based on type of metal.";
    }

    /**
     * The compareTo functionality is used to compare two items to each other
     * for stacking purposes when the Item is added to the player's inventory;
     * subclasses of Item compare other features
     *
     * @param item the Item to compare to
     * @return -1 if the Items are different and 0 if the two Items are
     * identical
     */
    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        }

        if (this.getMetal().compareTo(((Projectile) item).getMetal()) < 0) {
            return -1;
        }

        if (this.requiresBow != ((Projectile) item).doesRequireBow()) {
            return -1;
        }

        return 0;
    }

    /**
     * ProjectileTip inner class; every Projectile has a ProjectileTip which
     * defines the attack bonuses for the Projectile
     */
    public static class ProjectileTip implements Comparable<ProjectileTip> {

        private float min;
        private float max;
        private float critChance;

        public ProjectileTip(float min, float max, float crit) {
            this.min = min;
            this.max = max;
            this.critChance = crit;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public float getCrit() {
            return critChance;
        }

        @Override
        public int compareTo(ProjectileTip t) {
            if (this.getMin() != t.getMin()) {
                return -1;
            }

            if (this.getMax() != t.getMax()) {
                return -1;
            }

            if (this.getCrit() != t.getCrit()) {
                return -1;
            }

            return 0;
        }

        public static String metalToString(ProjectileTip projectileTip) {
            if (projectileTip == BRONZE_TIP) {
                return "bronze";
            }
            else if (projectileTip == IRON_TIP) {
                return "iron";
            }
            else if (projectileTip == STEEL_TIP) {
                return "steel";
            }
            else if (projectileTip == TITANIUM_TIP) {
                return "titanium";
            }
            return "{ERR:UNDEF}";
        }
    }
}
