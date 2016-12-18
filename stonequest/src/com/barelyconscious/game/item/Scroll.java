/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Scroll.java
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

import com.barelyconscious.game.Sound;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.util.StringHelper;

public class Scroll extends Item {

    private int scrollId;
    private boolean isIdentified = false;

    /**
     * Creates a new Scroll with the following parameters
     *
     * @param name the name of the scroll which is only visible to the player if
     * the Scroll has been read previously
     * @param sellValue the value in gold vendors will give in exchange for the
     * Scroll
     * @param scrollId the internal id of the Scroll, which is unique to each
     * Scroll. The player holds an array of Scroll ids in order to keep track of
     * Scrolls the player has previously read before to determine if the
     * Scroll's name and stats should be obfuscated or not
     * @param affectedAttributes AttributeMod effects if any; most Scrolls do
     * not provide attribute mods when consumed
     */
    public Scroll(String name, int itemLevel, int sellValue, int stackSize, int scrollId, UIElement itemIcon, Entity owner, AttributeMod... affectedAttributes) {
        super(name, itemLevel, sellValue, stackSize, itemIcon, owner, affectedAttributes);
        this.scrollId = scrollId;

        /* Check if the Scroll has been seen by the player before, obfuscating
         its stats and name if so */
        if (owner instanceof Player) {
            isIdentified = ((Player)owner).isScrollIdentified(this.scrollId);
        }
    }

    public int getScrollId() {
        return scrollId;
    }

    /**
     * Identifies a scroll for future reference. Scrolls can be identified by 
     * either using another Item which performs this function or by reading
     * the Scroll.
     */
    public void identifyScroll() {
        if (owner instanceof Player) {
            ((Player)owner).read(this);
        }
    }

    /**
     * When a Scroll is consumed, it may have additional effects on the player
     * and on the world; this method is called when the Scroll is consumed and
     * Scrolls that do have extra effects override this method and implement
     * those effects there.
     */
    public void extraEffects() {
    }

    /**
     * Chooses 2-4 gibberish words, based on the hash from the name of the 
     * Scroll.
     * @return the obfuscated name of the Scroll which consists of a few 
     * gibberish words
     */
    private String obfuscateName() {
        String str = "";
        int numOfWords;
        int hash = Math.abs(name.hashCode());

        // Get the name's hashcode, % last digit & positive % max 3 + 2 
        numOfWords = (((hash % 10) & 15) % 4) + 2;
        // Create numOfWords number of unintelligible words
        for (int i = 0; i < numOfWords; i++) {
            str += StringHelper.GIBBERISH_WORD_LIST.get((hash + (i * 2586)) % 1000) + " ";
        }

        str = str.trim();

        return str;
    }

    /**
     * 
     * @return the actual name of the Scroll only if the Scroll has been 
     * previously identified by the Player; otherwise returns an obfuscated
     * name associated with the true name of the Scroll
     */
    @Override
    public String getName() {
        String displayName = super.name;
        
        if (!isIdentified) {
            return obfuscateName();
        }
        
        return displayName;
    }

    /**
     *
     * @return the description written to the TextLog when the Player examines
     * the Scroll
     */
    @Override
    public String getDescription() {
        if (isIdentified) {
            return "Read to cast this scroll.";
        }

        return "You do not yet know what this scroll does.  Read it or use a Scroll of Identify to identify it for future use.";
    }

    /**
     * When used, an Entity gains the benefits from the scroll and if the owner
     * is the Player, the Scroll is remembered so that later Scrolls are instantly
     * known to the Player.
     */
    @Override
    public void onUse() {
        // Temporary.. will eventually write to text log
        System.out.println("The scroll crumbles to dust...");
        Sound.READ_SCROLL.play();
        
        for (AttributeMod attributeMod : itemAffixes) {
            owner.adjustAttribute(attributeMod.getAttributeId(), attributeMod.getAttributeModifier());
        }

        extraEffects();
        identifyScroll();

        adjustStackBy(-1);
        
        System.out.println("It was a " + getName() + "!");
    }

    /**
     * The compareTo functionality is used to compare two Scrolls to each other
     * for stacking purposes when the Scroll is added to the player's inventory;
     * Scrolls are considered equal if both Scroll ids match
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

        if (scrollId != ((Scroll) item).getScrollId()) {
            return -1;
        }

        return 0;
    }
}
