/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Frame.java
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

import com.barelyconscious.game.Game;
import com.barelyconscious.util.ColorHelper;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Frame {
    private int xOffs;
    private int yOffs;
    private int width;
    private int height;
    private long delay;
    private int framePixels[];
    
    public Frame(int x, int y, long delay, String pathToFrame) {
        BufferedImage frame = null;
        this.delay = delay/35;
        
        xOffs = x;
        yOffs = y;
        
        try {
            frame = ImageIO.read(Game.class.getResourceAsStream(pathToFrame));
        } catch (IOException ex) {
            System.err.println("\n [ERR] Failed to load frame (" + pathToFrame + "): " + ex);
            System.exit(1);
        } 
        
        if (frame == null) {
            System.err.println(" [ERR] Not sure how we got here, but (" + pathToFrame + ") was null.");
            System.exit(1);
        }
        
        width = frame.getWidth();
        height = frame.getHeight();
        
        framePixels = frame.getRGB(0, 0, width, height, null, 0, width);
    }
    
    public long getDelay() {
        return delay;
    }
    
    public void setDelay(long delay) {
        this.delay = delay;
    }
    
    public void render() {
        int pix;
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix = framePixels[x + y * width];
                
                if (pix == ColorHelper.TRANSPARENCY_MASK) {
                    continue;
                }
                
//                screen.setPixel(pix, xOffs + x, yOffs + y);
            }
        }
    }
} // Frame