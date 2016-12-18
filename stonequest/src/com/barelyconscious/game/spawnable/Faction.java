/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Faction.java
 * Author:           Matt Schwartz
 * Date created:     09.02.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Groups of entities are called Factions. Two entities from
 *                   differing factions might be in one of the following states:
 *                 - Aggressive
 *                   The two entities will attack each other on sight.
 *                 - Neutral
 *                   Neither entity will attack unless provoked. The player can
 *                   provoke a neutral entity by attacking it. Other entities
 *                   will not provoke a neutral entity.
 *                 - Friendly
 *                   Friendly entities can trade with each other if that is an
 *                   option. The player can also engage in dialog with a 
 *                   friendly entity.
 **************************************************************************** */

package com.barelyconscious.game.spawnable;

public class Faction {
    public static final int PLAYER_FACTION = 0;
    public static final int HOSTILE_MOB_FACTION = 1;
    public static final int QUEST_GIVING_NPC_FACTION = 2;
    
    private int faction;
    
    public Faction() {
    }
    
    public Faction(int faction) {
        this.faction = faction;
    }
    
    public boolean isHostile(Sprite sprite) {
        return faction == sprite.getFaction().faction;
    }
}
