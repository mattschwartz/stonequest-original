/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Menu.java
 * Author:            Matt Schwartz
 * Date Created:      05.10.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.graphics;

import com.barelyconscious.game.graphics.gui.BetterComponent;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private List<BetterComponent> components = new ArrayList<BetterComponent>();
    
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    } // resize
    
    public void show() {
        for (BetterComponent c : components) {
            c.setEnabled(true);
        } // for
    } // show
    
    public void hide() {
        for (BetterComponent c : components) {
            c.setEnabled(false);
        } // for
    } // hide

    public void addComponent(BetterComponent c) {
        components.add(c);
    } // addComponent

    public List<BetterComponent> getComponents() {
        return components;
    } // getComponents
} // Menu
