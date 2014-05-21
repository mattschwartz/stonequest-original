/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Zone.java
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
import java.awt.Rectangle;

public abstract class Zone {

    protected float xShift;
    protected float yShift;

    public void shift(float xShift, float yShift) {
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public void update(UpdateEvent args) {
    }

    public abstract void render(UpdateEvent args);
    
    public abstract boolean canMove(Rectangle boundingBox);
} // Zone
