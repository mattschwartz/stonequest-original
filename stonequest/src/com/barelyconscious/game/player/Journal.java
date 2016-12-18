/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Journal.java
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

import java.util.ArrayList;
import java.util.List;

public class Journal {

    public static final int MAX_JOURNAL_ENTRIES = 10;
    private final List<JournalEntry> entries = new ArrayList<JournalEntry>();

    public Journal() {
    }

    /**
     * Adds a Journal entry to the player's Journal for future examination.
     *
     * @param entry The Journal entry to be added to the player's Journal
     * @return Returns true if the Journal entry was added successfully and
     * false otherwise
     */
    public boolean addEntry(JournalEntry entry) {
        if (entries.size() >= MAX_JOURNAL_ENTRIES) {
            return false;
        }

        return entries.add(entry);
    }

    /**
     *
     * @param index The index of the Journal log to be returned
     * @return Returns the Journal entry at <code>index</code>
     */
    public JournalEntry getEntryAt(int index) {
        if (index < 0 || index >= entries.size()) {
            return null;
        }

        return entries.get(index);
    }

    /**
     * Removes the Journal entry from the player's Journal log.
     *
     * @param index The entry in the Journal log to be removed
     */
    public void removeEntry(int index) {
        entries.remove(index);
    }

    /**
     *
     * @return Returns the number of Journal entries in the player's log.
     */
    public int getNumEntries() {
        return entries.size();
    }
}
