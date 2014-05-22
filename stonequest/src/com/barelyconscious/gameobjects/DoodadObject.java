/* *****************************************************************************
 * Project:           stonequest
 * File Name:         DoodadObject.java
 * Author:            Matt Schwartz
 * Date Created:      05.19.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.doodads.Doodad;

public class DoodadObject extends GameObject {

    protected Doodad doodad;

    public DoodadObject(Doodad doodad, String filepath, float x, float y) {
        super(filepath, x, y);
        this.doodad = doodad;
    }

} // DoodadObject
