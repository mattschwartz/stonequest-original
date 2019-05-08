/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Doodad.java
 * Author:            Matt Schwartz
 * Date Created:      03.16.2013 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  A Doodad is a world object with which a player can interact
 *                    to some effect.  Openable chests and doors are two examples
 *                    of Doodads.  
 ************************************************************************** */
package com.barelyconscious.game.spawnable;

public class Doodad extends Sprite {

    protected int initialTileId;
    protected int spentTileId;
    private boolean isLocked;
    private boolean isOpen;

    public Doodad(String name, int initialTileId, int spentTileId, int x, int y) {
        super(name, initialTileId);
        super.setPosition(x, y);
        super.setCollision(true);

        isLocked = true;
        isOpen = false;

        this.initialTileId = initialTileId;
        this.spentTileId = spentTileId;
    } // constructor

    /**
     *
     * @param newLockedValue if true, the Doodad will require a key to open
     */
    public void setLocked(boolean newLockedValue) {
        isLocked = newLockedValue;
    } // setLocked

    /**
     *
     * @return true if the container requires a key to open
     */
    public boolean isLocked() {
        return isLocked;
    } // isLocked

    /**
     * Change whether the Doodad is open or not
     *
     * @param newOpenValue if true, the Doodad will be opened
     */
    public void setOpen(boolean newOpenValue) {
        isOpen = newOpenValue;
    } // setOpen

    /**
     *
     * @return true if the Doodad has been interacted with
     */
    public boolean isOpen() {
        return isOpen;
    } // isOpen

    @Override
    public void interact() {
        isOpen = true;
        super.setTileId(spentTileId);
    } // interact
} // Doodad
