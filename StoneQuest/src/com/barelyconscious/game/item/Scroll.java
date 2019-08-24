/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        Scroll.java
 * Author:           Matt Schwartz
 * Date created:     12.24.2012 
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Scrolls are a subclass of Item and superclass to all types of
 *                   Scrolls found in the game.  Scrolls are initially obfuscated
 *                   so that the player is unable to see what kind of Scroll it is
 *                   and what the Scroll does when used.  Reading an obfuscated Scroll
 *                   or using a Scroll of Identification on the Scroll reveals its
 *                   true power.  Scrolls have a variety of benefits, but rarely
 *                   affect player stats in the way that Potions do.  Scroll effects
 *                   are often temporary and not always beneficial to the player.
 **************************************************************************** */
package com.barelyconscious.game.item;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Sound;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.entities.SewerRatEntity;

public class Scroll extends Item {

    private String scrollName;
    private int scrollId;
    private boolean isIdentified;
    private final TextLog textLog;

    /**
     * Creates a new Scroll with the following parameters
     *
     * @param scrollName the displayName of the scroll which is only visible to the player if the Scroll has been read
     * previously
     * @param sellV the value in gold vendors will give in exchange for the Scroll
     * @param scrollid the internal id of the Scroll, which is unique to each Scroll. The player holds an array of
     * Scroll ids in order to keep track of Scrolls the player has previously read before to determine if the Scroll's
     * displayName and stats should be obfuscated or not
     * @param effects AttributeMod effects if any; most Scrolls do not provide attribute mods when consumed
     */
    public Scroll(
        String scrollName,
        int sellV,
        int scrollid,
        final TextLog textLog,
        AttributeMod... effects
    ) {
        super(scrollName, sellV, 1, Tile.SCROLL_TILE_ID, effects);
        this.scrollName = scrollName;
        this.scrollId = scrollid;
        this.textLog = textLog;

        /* Check if the Scroll has been seen by the player before, obfuscating
         its stats and displayName if so */
        isIdentified = !Game.player.isScrollIdentified(scrollId);

        if (isIdentified) {
            super.setName(obfuscateName());
        } // if
        else {
            super.setName(scrollName);
        } // else

        options[USE] = "read";
    } // constructor

    public int getScrollId() {
        return scrollId;
    } // getScrollId

    /* Adds the scroll's ID to the list of scrolls that the player has seen */
    public void identifyScroll() {
        setName(scrollName);
        Game.player.addScrollToIdentifieds(scrollId);
    } // identifyScroll

    /**
     * When a Scroll is consumed, it may have additional effects on the player and on the world; this method is called
     * when the Scroll is consumed and Scrolls that do have extra effects override this method and implement those
     * effects there.
     */
    public void extraEffects() {
        // does nothing by default
        Game.world.addEntity(new SewerRatEntity(
            Game.world,
            800,
            Game.world.getPlayerX() - 20,
            Game.world.getPlayerY(),
            textLog));
        Game.world.addEntity(new SewerRatEntity(
            Game.world,
            800,
            Game.world.getPlayerX() + 20,
            Game.world.getPlayerY(),
            textLog));
        Game.world.addEntity(new SewerRatEntity(
            Game.world,
            800,
            Game.world.getPlayerX(),
            Game.world.getPlayerY() - 20,
            textLog));
        Game.world.addEntity(new SewerRatEntity(
            Game.world,
            800,
            Game.world.getPlayerX(),
            Game.world.getPlayerY() + 20,
            textLog));
    } // extraEffects

    /* Picks 2-4 gibberish words based on the displayName of the scroll */
    private String obfuscateName() {
        String str = "";
        int numOfWords;
        int hash = Math.abs(getInternalName().hashCode());

        // Get the displayName's hashcode, % last digit & positive % max 3 + 2
        numOfWords = (((hash % 10) & 15) % 4) + 2;
        // Create numOfWords number of unintelligible words
        for (int i = 0; i < numOfWords; i++) {
            str += Common.GIBBERISH_WORD_LIST.get((hash + (i * 2586)) % 1000) + " ";
        } // for

        str = str.trim();

        return str;
    } // obfuscateName

    /**
     *
     * @return the actual displayName of the Scroll
     */
    @Override
    public String getInternalName() {
        return scrollName;
    } // getInternalName

    /**
     * If the Scroll has not been identified by the Player, the displayName returned and thus seen by the Player is obfuscated
     * by concatenation of gibberish words
     *
     * @return the displayName of the Scroll visible to the player
     */
    @Override
    public String getDisplayName() {
        if (Game.player.isScrollIdentified(scrollId)) {
            return "Scroll of " + super.getInternalName();
        } // if

        return "Scroll entitled: '" + super.getDisplayName() + "'";
    } // getDisplayName

    /**
     *
     * @return the description written to the TextLog when the Player examines the Scroll
     */
    @Override
    public String getItemDescription() {
        if (Game.player.isScrollIdentified(scrollId)) {
            return "Read to cast this scroll.";
        } // if

        return "You do not yet know what this scroll does.  Read it or use a Scroll of Identify to identify it for future use.";
    } // getItemDescription

    @Override
    public void onUse() {
        textLog.writeFormattedString("The scroll crumbles to dust...", Common.FONT_NULL_RGB);
        
        
        extraEffects();
        identifyScroll();
        textLog.writeFormattedString("It was a " + getDisplayName() + "!", Common.FONT_NULL_RGB);
        
        Sound.READ_SCROLL.play();
        
        if (--stackSize <= 0) {
            Game.inventory.removeItem(this);
        } // if
    } // onUse

    /**
     * The compareTo functionality is used to compare two Scrolls to each other for stacking purposes when the Scroll is
     * added to the player's inventory; Scrolls are considered equal if both Scroll ids match
     *
     * @param item the Item to compare to
     * @return -1 if the Items are different and 0 if the two Items are identical
     */
    @Override
    public int compareTo(Item item) {
        if (super.compareTo(item) < 0) {
            return -1;
        } // if

        if (scrollId != ((Scroll) item).getScrollId()) {
            return -1;
        } // if

        return 0;
    } // compareTo
} // Scroll
