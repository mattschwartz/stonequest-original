/* *****************************************************************************
 * Project:           stonequest
 * File Name:         ZoneFactory.java
 * Author:            Matt Schwartz
 * Date Created:      05.19.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.pcg;

import com.barelyconscious.world.RandomizedZone;
import com.barelyconscious.world.TiledZone;
import com.barelyconscious.world.Zone;

public class ZoneFactory {

    private static final ZoneFactory INSTANCE = new ZoneFactory();

    private ZoneFactory() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }
    }

    public Zone getIntroductionZone() {
        return new TiledZone("maps/introductionMap.tmx");
    }

    public Zone getZone(String path) {
        return new TiledZone(path);
    }

    public static ZoneFactory getInstance() {
        return INSTANCE;
    }

    public Zone getZone(int seed) {
        Zone result = new RandomizedZone();

        return result;
    }
} // ZoneFactory
