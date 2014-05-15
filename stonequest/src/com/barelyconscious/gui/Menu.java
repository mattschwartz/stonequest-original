/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Menu.java
 * Author:            Matt Schwartz
 * Date Created:      05.14.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gui;

public abstract class Menu {

    protected final GUIHandler guiHandler;
    
    public Menu() {
        guiHandler = GUIHandler.getInstance();
    }

    protected abstract void createWidgets();

    protected abstract void createRootWidget();

    protected void registerHandlers() {
    }

    public abstract void show();

    public abstract void hide();
} // Menu
