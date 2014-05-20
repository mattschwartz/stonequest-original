/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         UpdateEvent.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** *//* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         UpdateEvent.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gameobjects;

import com.barelyconscious.gamestate.ClientBase;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class UpdateEvent {

    public GameContainer gc;
    public ClientBase client;
    public Graphics g;
    public int delta;
    
    public UpdateEvent() {
    }

    public UpdateEvent(GameContainer container, ClientBase client, int delta) {
        this.gc = container;
        this.client = client;
        this.delta = delta;
    }

    public UpdateEvent(GameContainer container, ClientBase client, Graphics g) {
        this.gc = container;
        this.client = client;
        this.g = g;
    }

} // UpdateEvent
