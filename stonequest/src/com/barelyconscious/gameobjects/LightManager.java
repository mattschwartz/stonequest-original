/* *****************************************************************************
 * Project:           stonequest
 * File Name:         LightManager.java
 * Author:            Matt Schwartz
 * Date Created:      05.21.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.util.ConsoleWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LightManager {

    private static final LightManager INSTANCE = new LightManager();

    private List<LightObject> lights = new CopyOnWriteArrayList<>();
    public Image alphaMap;

    private LightManager() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }

        try {
            alphaMap = new Image("sprites/alphaMap.png");
        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to open file: " + ex);
        }
    }

    public static LightManager getInstance() {
        return INSTANCE;
    }

    public LightObject addLight(float x, float y, float radius) {
        LightObject light = new LightObject(x, y, radius);
        lights.add(light);
        
        return light;
    }

    /**
     * @see http://slick.ninjacave.com/forum/viewtopic.php?t=3250
     * @param args
     */
    public void render(UpdateEvent args) {
        args.g.clearAlphaMap();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        for (LightObject light : lights) {
            if (light.shouldRemove()) {
                lights.remove(light);
            } else {
                light.render(args);
            }
        } // for

        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_DST_ALPHA);

        args.g.fillRect(0, 0, args.gc.getWidth(), args.gc.getHeight());

        args.g.setDrawMode(Graphics.MODE_NORMAL);
    }
    
    public void removeAllLights() {
        lights.clear();
    }

} // LightManager
