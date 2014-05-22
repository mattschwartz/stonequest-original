/* *****************************************************************************
 * Project:           stonequest
 * File Name:         LightObject.java
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

import org.newdawn.slick.Image;

public class LightObject extends GameObject {

    private float radius;
    private final Image alphaMap;

    public LightObject(float x, float y, float radius) {
        this.x = x - radius;
        this.y = y - radius;
        this.radius = radius;

        alphaMap = LightManager.getInstance().alphaMap;
    }

    @Override
    public void render(UpdateEvent args) {
        alphaMap.draw(x, y, radius * 2, radius * 2);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x - radius;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y - radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

} // LightObject
