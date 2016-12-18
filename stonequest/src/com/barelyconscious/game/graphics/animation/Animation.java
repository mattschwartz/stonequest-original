/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Animation.java
 * Author:           Matt Schwartz
 * Date created:     06.10.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game.graphics.animation;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {
    private long nextFrame;
    private ArrayList<Frame> frames;
    private int currentFrame;
    
    public Animation(Frame... frames) {
        this.frames = new ArrayList(Arrays.asList(frames));
        
        currentFrame = 0;
        nextFrame = this.frames.get(0).getDelay();
    }
    
    public void addFrame(Frame frame) {
        frames.add(frame);
    }
    
    public void render() {
        if (--nextFrame <= 0) {
            currentFrame++;
            
            if (currentFrame >= frames.size()) {
                currentFrame = 0;
            }
            nextFrame = frames.get(currentFrame).getDelay();
        }
        
        frames.get(currentFrame).render();
    }
} // Animation