/* *****************************************************************************
   * File Name:         Icon.java
   * Author:            Matt Schwartz
   * Date Created:      01.03.2013
   * Redistribution:    You are free to use, reuse, and edit any of the text in
                        this file.  You are not allowed to take credit for code
                        that was not written fully by yourself, or to remove 
                        credit from code that was not written fully by yourself.  
                        Please email schwamat@gmail.com for issues or concerns.
   * File Description:  
   ************************************************************************** */

package com.barelyconscious.game.graphics;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Icon {
    public static final BufferedImage FONT_SHEET = loadImage("/tiles/fontsheet.png");
    public static final Icon CURSE_ICON = new Icon("/tiles/active_effects/debuff_curse.png");
    public static final Icon POISON_ICON = new Icon("/tiles/active_effects/debuff_poison.png");
    public static final Icon POTION_ICON = new Icon("/tiles/active_effects/buff_potion.png");
    public static final Icon COIN_POUCH_ICON = new Icon("/tiles/coin_pouch.png");
    
    private final int TRANSPARENT_COLOR = -65281; // 255,0,255
    private int[] pixels;
    private int width;
    private int height;
    
    public Icon(String imageFile) {
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(Game.class.getResourceAsStream(imageFile));
        } catch (IOException ex) {
            System.err.println(" [ERR] Failed to load image (" + img + "): " + ex);
        } // catch
        
        width = img.getWidth();
        height = img.getHeight();
        pixels = img.getRGB(0, 0, width, height, null, 0, width);
    } // constructor
    
    public static BufferedImage loadImage(String iconFile) {
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(Game.class.getResourceAsStream(iconFile));
        } catch (IOException ex) {
            System.err.println(" [ERR] Failed to load image (" + img + "): " + ex);
        } // catch
        
        return img;
    } // constructor
    
    public void render(Screen screen, int xStart, int yStart) {
        int pix;
        
        for (int x = 0; x < Common.TILE_SIZE; x++) {
            for (int y = 0; y < Common.TILE_SIZE; y++) {
                pix = pixels[x + y * width];
                
                if (pix == TRANSPARENT_COLOR) {
                    continue;
                } // if
                
                screen.setPixel(pix, xStart + x, yStart + y);
            } // for
        } // for
    } // render
} // Icon