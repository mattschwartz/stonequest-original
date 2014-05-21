/* *****************************************************************************
 * Project:           stonequest
 * File Name:         Main.java
 * Author:            Matt Schwartz
 * Date Created:      05.11.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.main;

import com.barelyconscious.gamestate.Client;
import com.barelyconscious.gamestate.GameData;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        try {
            AppGameContainer apc = new AppGameContainer(new Client("StoneQuest v2.0", new GameData()), 800, 600, false);

            apc.setIcons(new String[]{"images/app/applicationIconSmall.png", "images/app/applicationIcon.png"});
            apc.setDisplayMode(1280, 720, false);
            apc.setTargetFrameRate(60);
            apc.setShowFPS(false);
            apc.start();
        } catch (SlickException ex) {
            System.err.println("Error: " + ex);
        }
    }

}
