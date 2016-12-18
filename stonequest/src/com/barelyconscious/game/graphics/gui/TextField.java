/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         TextField.java
 * Author:            Matt Schwartz
 * Date Created:      05.10.2014 
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove 
 *                    credit from code that was not written fully by yourself.  
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:  
 ************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.input.KeyInteractable;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class TextField extends BetterComponent {

    public static final String BUTTON_BORDER_PATH = "/gfx/gui/components/button/buttonBorder.png";
    protected static UIElement borderLeft;
    protected static UIElement borderRight;
    protected static UIElement borderRepeat;

    private String text;
    private ButtonCallback onSubmit;

    public TextField(String text) {
        this.text = text;
        loadImage();
        super.addMouseListener(Interactable.Z_BUTTON);
        super.addKeyListener();
    }

    public TextField(String text, int width, int height) {
        this(text);
        this.width = width;
        this.height = height;
    }

    private void loadImage() {
        if (borderLeft != null) {
            return;
        }

        int[] pixels;
        int subWidth = 4;
        int subHeight = 24;
        BufferedImage unparsedImage = UIElement.loadImage(BUTTON_BORDER_PATH);

        pixels = unparsedImage.getRGB(0, 0, subWidth, subHeight, null, 0, subWidth);
        borderLeft = new UIElement(pixels, subWidth, subHeight);
        pixels = unparsedImage.getRGB(subWidth, 0, subWidth, subHeight, null, 0, subWidth);
        borderRight = new UIElement(pixels, subWidth, subHeight);
        pixels = unparsedImage.getRGB(subWidth * 2, 0, subWidth, subHeight, null, 0, subWidth);
        borderRepeat = new UIElement(pixels, subWidth, subHeight);
    }

    public void setOnSubmit(ButtonCallback callback) {
        this.onSubmit = callback;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            }
            
            return;
        }

        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            if (onSubmit != null) {
                onSubmit.action(null);
            }
            return;
        }

        this.text += key.getKeyChar();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render() {
        renderBorder();
        renderText();
    }
    
    private void renderBorder() {
        if (hasFocus()) {
            borderLeft.renderHighlighted(x, y);

            for (int i = x + borderLeft.getWidth(); i < x + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
                borderRepeat.renderHighlighted(i, y);
            }

            borderRight.renderHighlighted(x + width - borderRight.getWidth(), y);
        } else {
            borderLeft.render(x, y);

            for (int i = x + borderLeft.getWidth(); i < x + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
                borderRepeat.render(i, y);
            }

            borderRight.render(x + width - borderRight.getWidth(), y);
        }
    }

    private void renderText() {
        int offsX = x + Button.MARGIN + (width - Button.MARGIN * 2 - FontService.getStringWidth(text)) / 2;
        int offsY = y + FontService.characterHeight;

        FontService.drawFont(text, Color.white, offsX, offsY);
    }
}
