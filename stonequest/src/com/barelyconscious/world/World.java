/* *****************************************************************************
 * Project:           stonequest
 * File Name:         World.java
 * Author:            Matt Schwartz
 * Date Created:      05.16.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.world;

public class World {
    
    private static final World INSTANCE = new World();
    
    private World() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }
    }
    
    public static World getInstance() {
        return INSTANCE;
    }
    
} // World
