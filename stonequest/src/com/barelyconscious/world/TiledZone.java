/* *****************************************************************************
 * Project:           stonequest
 * File Name:         TiledZone.java
 * Author:            Matt Schwartz
 * Date Created:      05.19.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.world;

import com.barelyconscious.gameobjects.UpdateEvent;
import com.barelyconscious.util.ConsoleWriter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledZone extends Zone {

    private TiledMap map;

    public TiledZone(String path) {
        try {
            map = new TiledMap(path);
        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to create map from path " + path + ": " + ex);
        }
    }

    @Override
    public void update(UpdateEvent args) {
    }

    @Override
    public void render(UpdateEvent args) {
        map.render((int) xShift, (int) yShift);
    }
} // TiledZone
