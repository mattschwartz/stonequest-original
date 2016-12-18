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

import com.barelyconscious.game.graphics.UIElement;

public class Doodad extends Sprite {
    
    protected UIElement initialTile;
    protected UIElement spentTile;
    private boolean isLocked;
    private boolean isOpen;

    public Doodad(String name, int x, int y, boolean locked, UIElement initialTile, UIElement spentTile) {
        super(name, x, y, true, initialTile);
        super.setPosition(x, y);
        this.initialTile = initialTile;
        this.spentTile = spentTile;

        isLocked = locked;
        isOpen = false;
    }

    /**
     *
     * @param newLockedValue if true, the Doodad will require a key to open
     */
    public void setLocked(boolean newLockedValue) {
        isLocked = newLockedValue;
    }

    /**
     *
     * @return true if the container requires a key to open
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Change whether the Doodad is open or not
     *
     * @param newOpenValue if true, the Doodad will be opened
     */
    public void setOpen(boolean newOpenValue) {
        isOpen = newOpenValue;
    }

    /**
     *
     * @return true if the Doodad has been interacted with
     */
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void interact(Sprite interactee) {
        isOpen = true;
//        super.setTileId(spentTile);
    }
}
