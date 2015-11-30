/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        StatBonus.java
 * Author:           Matt Schwartz
 * Date created:     07.08.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: An ItemStat alters the player's usual stats by some amount,
 *                   denoted by statMod.  This number can be positive or negative
                     to represent stat potions and curses, respectively.  The
                     player stat to be modified is denoted by statID.
 **************************************************************************** */

package com.barelyconscious.game.player;

public class StatBonus {    
    private int statId;
    private float statMod;

    public StatBonus(int id, float mod) {
        statId = id;
        statMod = mod;
    } // constructor 
    
    public void setStatId(int id) {
        statId = id;
    } // setStatId
    
    /* Returns the affected stat plus 10 to reflect that it is
        the player's CURRENT stat level affected and not the player's
        skill level */
    public int getStatId() {
        return statId + 10;
    } // getStatId
    
    public void setStatMod(float mod) {
        statMod = mod;
    } // setStatMod
    
    public float getStatMod() {
        return statMod;
    } // getStatMod

    @Override
    public String toString() {
//        return "+" + (int)statMod + " " + Player.idToString(statId);
        return Player.idToString(statId);
    } // toString
}  // StatBonus