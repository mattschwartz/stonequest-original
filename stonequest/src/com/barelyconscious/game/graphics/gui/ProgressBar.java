/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        ProgressBar.java
 * Author:           Matt Schwartz
 * Date created:     12.18.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.graphics.Viewport;
import com.barelyconscious.game.services.SceneService;
import java.awt.Color;

public class ProgressBar implements Component {

    private static final Color BACKGROUND_COLOR = new Color(22, 40, 22);
    private static final Color BASE_COLOR = new Color(75, 137, 75);
    private static final Color INCREASE_COLOR = new Color(103, 188, 103);
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public int max;
    public int current;
    public int increaseBy;

    public ProgressBar(int startX, int startY, int width) {
        x = startX;
        y = startY;
        this.width = width;
        height = 24;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int newX) {
        x = newX;
    }

    @Override
    public void setY(int newY) {
        y = newY;
    }

    public void resize(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }

    @Override
    public void render() {
        renderBorder();
        renderBackground();
        renderText();
    }

    private void renderBorder() {
        Button.borderLeft.render(x, y);

        for (int i = x + Button.borderLeft.getWidth(); i < x + (width - Button.borderRight.getWidth()); i += Button.borderRepeat.getWidth()) {
            Button.borderRepeat.render( i, y);
        }

        Button.borderRight.render(x + width - Button.borderRight.getWidth(), y);
    }

    private void renderBackground() {
        double baseWidthPercent, increaseWidthPercent;
        int progressBarWidth = width - 10;

        ShapeDrawer.fillRectangle(BACKGROUND_COLOR, x + 5, y + 5, progressBarWidth, height - 10);

        // Base width
        baseWidthPercent = Math.min(((current * 1.0 / max)), 1);

        // Increase width
        increaseWidthPercent = Math.min(((increaseBy * 1.0 / max)), 1 - baseWidthPercent);

        ShapeDrawer.fillRectangle(BASE_COLOR, x + 5, y + 5, (int) (baseWidthPercent * progressBarWidth), height - 10);
        ShapeDrawer.fillRectangle(INCREASE_COLOR, x + 5 + (int) (baseWidthPercent * progressBarWidth), y + 5, (int) (increaseWidthPercent * progressBarWidth), height - 10);

    }

    private void renderText() {
        int startX, startY;
        String msg;

        if (increaseBy == 0) {
            return;
        }

        msg = "+" + increaseBy;

        startX = x + width - 6 - FontService.getStringWidth(msg);
        startY = y + FontService.characterHeight + 1;

        ShapeDrawer.fillTransluscentRectangle(startX, y + 6, FontService.getStringWidth(msg), FontService.characterHeight - 3);
        FontService.drawFont(msg, Color.white.getRGB(), startX, startY);
    }

}
