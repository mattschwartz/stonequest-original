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
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledZone extends Zone {

    private TiledMap map;
    private Rectangle mapBounds;
    private List<Rectangle> objectList = new ArrayList<>();

    public TiledZone(String path) {
        try {
            map = new TiledMap(path);
            mapBounds = new Rectangle(0, 0, map.getWidth() * map.getTileWidth(), map.getHeight() * map.getTileHeight());
            System.err.println("map bounds is " + mapBounds);
            createObjectList();

        } catch (SlickException ex) {
            ConsoleWriter.writeError("Failed to create map from path " + path + ": " + ex);
        }
    }

    private void createObjectList() {
        Rectangle rect;

        for (int i = 0; i < map.getObjectCount(0); i++) {
            rect = new Rectangle();
            rect.x = map.getObjectX(0, i);
            rect.y = map.getObjectY(0, i);
            rect.width = map.getObjectWidth(0, i);
            rect.height = map.getObjectHeight(0, i);
            objectList.add(rect);
        }
    }

    @Override
    public void update(UpdateEvent args) {
    }

    @Override
    public void render(UpdateEvent args) {
        map.render((int) xShift, (int) yShift);
    }

    @Override
    public boolean canMove(Rectangle boundingBox) {
        if (!mapBounds.contains(boundingBox)) {
            return false;
        }
        
        for (Rectangle object : objectList) {
            if (object.intersects(boundingBox)) {
                return false;
            }
        }
        
        return true;
    }

} // TiledZone
