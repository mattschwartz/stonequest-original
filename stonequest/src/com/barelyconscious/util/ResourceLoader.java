/* *****************************************************************************
 * Project:           stonequest
 * File Name:         ResourceLoader.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.util;

import java.util.Map;
import org.newdawn.slick.Image;

public class ResourceLoader {

    private static final ResourceLoader INSTANCE = new ResourceLoader();

    private Map<String, Image> imageResources;

    private ResourceLoader() {
        if (INSTANCE != null) {
            throw new IllegalStateException(this + " has already been initialized.");
        }
    }

    public static ResourceLoader getInstance() {
        return INSTANCE;
    }

} // ResourceLoader
