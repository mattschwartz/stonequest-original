/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         BetterComponent.java
 * Author:            Matt Schwartz
 * Date Created:      05.09.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.services.SceneService;

public abstract class BetterComponent extends Interactable {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean removeOnUpdate;
    
    public BetterComponent() {
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setPosition(float absoluteX, float relativeX, float absoluteY, float relativeY) {
        float xStart = SceneService.INSTANCE.getWidth() * absoluteX;
        float yStart = SceneService.INSTANCE.getHeight() * absoluteY;
        
        xStart += relativeX;
        yStart += relativeY;
        
        x = (int) xStart;
        y = (int) yStart;
        
        super.setRegion(x, y, this.width, this.height);
    } // setPosition
    
    public void setSize(float absoluteX, float relativeX, float absoluteY, float relativeY) {
        float newWidth = SceneService.INSTANCE.getWidth() * absoluteX;
        float newHeight = SceneService.INSTANCE.getHeight() * absoluteY;
        
        newWidth += relativeX;
        newHeight += relativeY;
        
        width = (int) newWidth;
        height = (int) newHeight;
        
        super.setRegion(x, y, this.width, this.height);
    } // setSize
    
    public boolean shouldRemove() {
        return removeOnUpdate;
    } // shouldRemove
    
    public void render() {
    } // render
} // BetterComponent
