/* *****************************************************************************
   * File Name:         Projectile.java
   * Author:            Matt Schwartz
   * Date Created:      12.17.2012
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.item;

public class Projectile extends Item {
    public static final ProjectileTip BRONZE_TIP = new ProjectileTip(1, 5, 5);
    public static final ProjectileTip IRON_TIP = new ProjectileTip(5, 15, 5);
    public static final ProjectileTip STEEL_TIP = new ProjectileTip(15, 35, 12);
    public static final ProjectileTip TITANIUM_TIP = new ProjectileTip(255, 515, 35);
    
    private boolean requiresBow;
    private ProjectileTip metal;
    
    public Projectile(String name, int sellValue, int stack, int tileId, boolean requiresBow, ProjectileTip metal) {
        super(name, sellValue, stack, tileId);
        super.setItemDescription("Fire at an enemy from a distance, dealing damage based on type of metal.");
        this.metal = metal;
        this.requiresBow = requiresBow;
    } // constructor
    
    public void setRequiresBow(boolean bool) {
        requiresBow = bool;
    } // setRequiresBow
    
    public boolean requiresBow() {
        return requiresBow;
    } // requiresBow
    
    public ProjectileTip getMetal() {
        return metal;
    } // getMetal

    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        } // if
        
        if (this.getMetal().compareTo(((Projectile)item).getMetal()) < 0) {
            return -1;
        } // if
        
        if (this.requiresBow != ((Projectile)item).requiresBow()) {
            return -1;
        } // if
        
        return 0;
    } // compareTo
    
    public static class ProjectileTip implements Comparable<ProjectileTip> {
        private float min;
        private float max;
        private float critChance;
        
        public ProjectileTip(float min, float max, float crit) {
            this.min = min;
            this.max = max;
            this.critChance = crit;
        } // constructor
        
        public float getMin() {
            return min;
        } // getMin
        
        public float getMax() {
            return max;
        } // getMax
        
        public float getCrit() {
            return critChance;
        } // getCrit

        @Override
        public int compareTo(ProjectileTip t) {
            if (this.getMin() != t.getMin()) {
                return -1;
            } // if
            
            if (this.getMax() != t.getMax()) {
                return -1;
            } // if
            
            if (this.getCrit() != t.getCrit()) {
                return -1;
            } // if
            
            return 0;
        } // compareTo
    } // ProjectileTip inner class
} // Projectile