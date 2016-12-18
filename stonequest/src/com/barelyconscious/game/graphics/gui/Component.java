/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Component.java
 * Author:           Matt Schwartz
 * Date created:     08.27.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Components are renderable interface elements like buttons
 *                   and windows.  Therefore, they must define a dimension and
 *                   rendering strategy.
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;


public interface Component {

    /**
     *
     * @return Returns the X starting value for where the Component is located
     * exactly on the screen.
     */
    public int getX();

    /**
     *
     * @return Returns the Y starting value for where the Component is located
     * exactly on the screen.
     */
    public int getY();

    /**
     * Sets the X offset for the Component to the supplied value.
     *
     * @param newX The new value for the X offset of the Component.
     */
    public void setX(int newX);

    /**
     * Sets the Y offset for the Component to the supplied value.
     *
     * @param newY The new value for the Y offset of the Component.
     */
    public void setY(int newY);

    /**
     *
     * @return Returns the width of the Component in pixels.
     */
    public int getWidth();

    /**
     *
     * @return Returns the height of the Component in pixels.
     */
    public int getHeight();

    /**
     * Destroys the Component so that during the next rendering cycle, the
     * Component will no longer be rendered.
     */
    public void dispose();

    /**
     *
     * @return Returns true if the Component should be removed and false
     * otherwise. Components that should be removed are no longer rendered.
     */
    public boolean shouldRemove();

    /**
     * Renders the Component to the screen.
     *
     * @param screen The Screen to render the Component to
     */
    public void render();
}
