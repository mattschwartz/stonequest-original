/* *****************************************************************************
 * Project:           stonequest
 * File Name:         GUIHandler.java
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

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import java.io.IOException;
import org.lwjgl.LWJGLException;

public class GUIHandler {

    public static final GUIHandler INSTANCE = new GUIHandler();

    private boolean quit = false;
    private GUI gui;
    public Menu mainMenu;
    public Menu newPlayerMenu;
    public Menu loadPlayerMenu;
    public Menu optionsMenu;

    public GUIHandler() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }
    }
    
    public void init() {
        mainMenu = new MainMenu();
        newPlayerMenu = new NewPlayerMenu();
        loadPlayerMenu = new LoadPlayerMenu();
        
        try {
            LWJGLRenderer renderer = new LWJGLRenderer();
            ThemeManager theme = ThemeManager.createThemeManager(getClass().getResource("/theme/chutzpah.xml"), renderer);
            gui = new GUI(renderer);
            gui.applyTheme(theme);
        } catch (LWJGLException | IOException ex) {
            System.err.println("error: " + ex);
        }
    }

    public static GUIHandler getInstance() {
        return INSTANCE;
    }

    public void update() {
        gui.update();
    }

    public void setRootWidget(Widget root) {
        gui.setRootPane(root);
    }

    public void quitGame() {
        quit = true;
    }
    
    public boolean hasQuit() {
        return quit;
    }
} // GUIHandler
