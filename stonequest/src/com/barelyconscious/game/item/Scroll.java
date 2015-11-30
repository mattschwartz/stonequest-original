/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Scroll.java
 * Author:           Matt Schwartz
 * Date created:     12.24.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.item;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.player.StatBonus;

public class Scroll extends Item {
    private final String SCROLL_NAME;
    private final int SCROLL_ID;
    private boolean obfuscateStats;
    
    public Scroll(String scrollName, int sellV, int scrollID, int tileId, StatBonus... effects) {
        super(scrollName, sellV, 1, tileId, effects);
        SCROLL_NAME = scrollName;
        SCROLL_ID = scrollID;
        
        checkIdStatus();
        
        options[USE] = "read";
    } // constructor
    
    /* Perform a check to see whether information about the scroll should 
        be obfuscated or not */
    public final void checkIdStatus() {
        // If player has not seen the scroll before, obfuscate its stats
        obfuscateStats = !Game.player.isScrollIdentified(SCROLL_ID);
        
        if (obfuscateStats) {
            super.setName(obfuscateName());
        } // if
        
        else {
            setName(SCROLL_NAME);
        } // else
    } // checkIdStatus
    
    /* Scrolls that do more than affect stats will override this method */
    public void extraEffects() {
        // overrideable method
    } // extraEffects
    
    @Override
    public String getInternalName() {
        return SCROLL_NAME;
    } // getInternalName

    @Override
    public String getDisplayName() {
        if (Game.player.isScrollIdentified(SCROLL_ID)) {
            return "Scroll of " + super.getInternalName();
        } // if
        
        return "Scroll entitled: '" + super.getDisplayName() + "'";
    } // getDisplayName

    @Override
    public String getItemDescription() {
        if (Game.player.isScrollIdentified(SCROLL_ID)) {
            return "Read to cast this scroll.";
        } // if
        
        return "You do not yet know what this scroll does.  Read it or use a Scroll of Identify to identify it for future use.";
    } // getItemDescription

    @Override
    public String toString() {
        return "a " + getDisplayName().toLowerCase().charAt(0) + getDisplayName().substring(1);
    } // toString
    
    public int getScrollId() {
        return SCROLL_ID;
    } // getScrollId
    
    /* Adds the scroll's ID to the list of scrolls that the player has seen */
    public void identifyScroll() {
        setName(SCROLL_NAME);
        Game.player.addScrollToIdentifieds(SCROLL_ID);
    } // identifyScroll
    
    /* Picks 2-4 gibberish words based on the name of the scroll */
    private String obfuscateName() {
        String str = "";
        int numOfWords;
        int hash = Math.abs(getInternalName().hashCode());
        
        // Get the name's hashcode, % last digit & positive % max 3 + 2 
        numOfWords = (((hash % 10) & 15) % 4) + 2;
        // Create numOfWords number of unintelligible words
        for (int i = 0; i < numOfWords; i++) {
            str += Common.GIBBERISH_WORD_LIST.get((hash + (i * 2586)) % 1000) + " ";
        } // for
        
        str = str.trim();
        
        return str;
    } // obfuscateName

    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        } // if
        
        if (SCROLL_ID != ((Scroll)item).getScrollId()) {
            return -1;
        } // if
        
        return 0;
    } // compareTo

    /**
     * Needs to be reworked for new Screen.java.
     * @param startingLine 
     */
    public void printAdditionalEffects(int startingLine) {
        // overrideable method
//        Game.SCREEN.writeStringToTooltipFrame("Renders you invisible, hiding you from ", Common.DEFAULT_FONT, Common.THEME_FG_COLOR, 0, startingLine++);
//        Game.SCREEN.writeStringToTooltipFrame("monsters.", Common.DEFAULT_FONT, Common.THEME_FG_COLOR, 0, startingLine);
    } // printAdditionalEffects
} // Scroll