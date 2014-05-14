/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Main.java
 * Author:            Matt Schwartz
 * Date Created:      05.11.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.main;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main extends Widget {

    boolean destroy = false;
    GUI gui;
    Button button;
    Button innerButton;

    /**
     * Start the example
     */
    public void start() {
        initGL(800, 600);
        init();
        initGUI();

        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            if (destroy) {
                gui.removeAllChildren();
            } else {
                gui.update();
            }
            Display.update();
            Display.sync(100);

            if (Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
            }
        }
    }

    @Override
    protected void layout() {
        button.setPosition(100, 100);
        button.setSize(100, 33);
    }

    /**
     * Initialise the GL display
     *
     * @param width The width of the display
     * @param height The height of the display
     */
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     * Initialise resources
     */
    public void init() {
    }

    private void initGUI() {
        try {
            LWJGLRenderer renderer = new LWJGLRenderer();
            ThemeManager theme = ThemeManager.createThemeManager(getClass().getResource("/theme/chutzpah.xml"), renderer);
            gui = new GUI(this, renderer);
            gui.applyTheme(theme);
            button = new Button("Click me");
            button.addCallback(new Runnable() {

                @Override
                public void run() {
                    destroy = true;
                }
            });
            button.setTheme("button");
            add(button);
        } catch (LWJGLException | IOException ex) {
            System.err.println("error: " + ex);
        }
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    long lastFrame;

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
