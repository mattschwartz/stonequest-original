/* *****************************************************************************
   * File Name:         Menu.java
   * Author:            Matt Schwartz
   * Date Created:      02.19.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;

public interface Menu {
    /**
     * This function should be called when the screen changes sizes in order to
     * change the location and size of the Menu.
     * @param w
     * @param h 
     */
    public void resizeMenu(int w, int h);
    
    public int getWidth();
    
    public int getHeight();
    
    public int getOffsX();
    
    public int getOffsY();
    /**
     * Move the cursor up.  Implemented in subclasses.
     */
    public void moveUp();
    
    /**
     * Move the cursor down.  Implemented in subclasses.
     */
    public void moveDown();
    
    /**
     * Select the currently selected object within the menu.  Implemented in
     * subclasses.
     */
    public void select();
    
    /**
     * Returns true if the menu is the currently active Menu.  Only one menu at
     * a time may be active.
     * @return 
     */
    public boolean isActive();
    
    /**
     * Menu has gained focus.
     */
    public void setActive();
    
    /**
     * Menu has lost focus.
     */
    public void clearFocus();
    
    /**
     * Draw the Menu to the screen.  Maybe add a "hidden"/"show" boolean to decide
     * whether or not to draw the menu to the screen.  Perhaps these menus should
     * only be open when the player presses the key?
     */
    public void render(Screen screen);
} // Menu