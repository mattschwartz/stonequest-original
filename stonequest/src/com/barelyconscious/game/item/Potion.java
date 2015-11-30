 /* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Potion.java
 * Author:           Matt Schwartz
 * Date created:     07.10.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.player.StatBonus;

public class Potion extends Item {
    public static final int STATBUFF    = 0;
    public static final int ANTIMAGIC   = 1;
    public static final int ANTIVENOM   = 2;
    
    private int duration;
    private int potionType;
    
    public Potion(String name, int sellV, int stack, int dur, int type, int tileId, StatBonus... effects) {
        super(name, sellV, stack, tileId, effects);
        super.setItemDescription("Quaff to " + typeIdToString(type));
        duration        = dur;
        potionType      = type;
        options[USE]    = "quaff";
    } // constructor
    
    public void decrDuration() {
        duration--;
    } // decrDuration
    
    public int getDuration() {
        return duration;
    } // getDuration
    
    public int getPotionType() {
        return potionType;
    } // getPotionType
    
    public static String typeIdToString(int type) {
        switch (type) {
            case STATBUFF:  return "provide a temporary increase in stats";
            case ANTIMAGIC: return "remove your afflictions";
            case ANTIVENOM: return "remove your infections";
            default:        return "sit around and dance";
        } // switch
    } // typeIdToString

    @Override
    public String toString() {
        switch(potionType) {
            case STATBUFF:      return "a " + super.getDisplayName() + "";
            case ANTIMAGIC:     return "an antimagic potion";
            case ANTIVENOM:     return "an antivenom potion";
            default:            return "a magical potion of love and peace etc etc ad nauseum";
        } // switch
    } // toString

    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        } // if
        
        if (this.getDuration() != ((Potion)item).getDuration()) {
            return -1;
        } // if
        
        if (this.getPotionType() != ((Potion)item).getPotionType()) {
            return -1;
        } // if
        
        return 0;
    } // compareTo
} // Potion