/* *****************************************************************************
   * File Name:         Menu.java
   * Author:            Matt Schwartz
   * Date Created:      02.19.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email stonequest.bcgames@gmail.com for issues or concerns.
   * File Description:  The Menu interface implemented bu Menu objects.  Menus are
                        GUI components drawn to the Screen and allow for visual
                        feedback for Player control.
   ************************************************************************** */

package com.barelyconscious.game.menu;

import com.barelyconscious.game.Screen;

public interface Menu {
    /**
     * This function should be called when the screen changes sizes in order to
     * change the location and size of the Menu.
     * @param newWidth the new width in pixels of the Screen
     * @param newHeight the new height in pixels of the Screen
     */
    public void resize(int newWidth, int newHeight);
    
    /**
     * 
     * @return the width in pixels of the menu 
     */
    public int getPixelWidth();
    
    /**
     * 
     * @return the height in pixels of the menu
     */
    public int getPixelHeight();
    
    /**
     * 
     * @return the x offset for drawing the menu
     */
    public int getOffsX();
    
    /**
     * 
     * @return the y offset for drawing the menu
     */
    public int getOffsY();
    
    /**
     * Move the cursor up in the menu; implemented in subclasses.
     */
    public void moveUp();
    
    /**
     * Move the cursor down in the menu; implemented in subclasses.
     */
    public void moveDown();
    
    /**
     * Select the currently selected object within the menu.  Implemented in
     * subclasses.
     */
    public void select();
    
    /**
     * Draw the Menu to the screen.  Maybe add a "hidden"/"show" boolean to decide
     * whether or not to draw the menu to the screen.  Perhaps these menus should
     * only be open when the player presses the key?
     */
    public void render(Screen screen);
} // Menu