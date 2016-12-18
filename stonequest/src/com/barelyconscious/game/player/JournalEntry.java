/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        JournalEntry.java
 * Author:           Matt Schwartz
 * Date created:     12.18.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.player;

import com.barelyconscious.game.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JournalEntry {

    public final int goldReward;
    private final String title;
    public List<Item> rewards;
    private boolean complete = false;

    /**
     * Creates a new Journal entry for the player's journal log.
     *
     * @param goldReward The amount of gold awarded to the player for the
     * Journal entry
     * @param title The name of the Journal entry for player identification
     * @param rewards The Item rewards for the Journal entry
     */
    public JournalEntry(int goldReward, String title, Item... rewards) {
        this.goldReward = goldReward;
        this.title = title;
        this.rewards = new ArrayList<Item>(Arrays.asList(rewards));
    }

    /**
     * Journal entries have titles to be quickly identified by the player.
     *
     * @return Returns the title of the journal entry
     */
    public String getTitle() {
        return title;
    }
    
    public boolean isComplete() {
        return complete;
    }
    
    public void completeJournalEntry() {
        complete = true;
    }

    /**
     * Journal entries have descriptions to give the player more knowledge about
     * the entry as well as background story for the game.
     *
     * @return Returns the description of the journal entry
     */
    public String getDescription() {
        return "This quest has no description. How mysterious.";
    }
}
