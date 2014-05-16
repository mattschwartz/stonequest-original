/* *****************************************************************************
 * Project:           stonequest
 * File Name:         MenuState.java
 * Author:            Matt Schwartz
 * Date Created:      05.15.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.gamestate.menu;

import TWLSlick.RootPane;
import com.barelyconscious.gamestate.ClientBase;
import com.barelyconscious.gamestate.GameData;
import com.barelyconscious.gamestate.GameStateBase;
import com.barelyconscious.gamestate.State;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class MenuState extends GameStateBase<GameData> {

    protected RootPane root;
    private Image backgroundImage;

    public MenuState(ClientBase<GameData> client, State state) {
        super(client, state);
    }

    @Override
    protected RootPane createRootPane() {
        root = super.createRootPane();

        createWidgets();
        addWidgets();
        registerEvents();

        return root;
    }

    protected abstract void createWidgets();

    protected abstract void addWidgets();

    protected void registerEvents() {
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        super.init(container, game);
        backgroundImage = new Image("images/menu_bg.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int y = (container.getHeight() - backgroundImage.getHeight()) / 2;
        backgroundImage.draw(0, y);
        
        String str = "StoneQuest";
        int x = (container.getWidth() - g.getFont().getWidth(str)) / 2;
        y -= g.getFont().getHeight(str);
        
        g.setColor(Color.black);
        g.drawString(str, x, y - 1);
        g.drawString(str, x, y + 1);
        g.drawString(str, x - 1, y);
        g.drawString(str, x + 1, y);
        
        g.setColor(Color.white);
        g.drawString(str, x, y);
        
        str = "Version 2.0.1";
        x = (container.getWidth() - g.getFont().getWidth(str)) / 2;
        y += g.getFont().getHeight(str);
        
        g.setColor(Color.black);
        g.drawString(str, x, y - 1);
        g.drawString(str, x, y + 1);
        g.drawString(str, x - 1, y);
        g.drawString(str, x + 1, y);
        
        g.setColor(Color.white);
        g.drawString(str, x, y);
    }

} // MenuState