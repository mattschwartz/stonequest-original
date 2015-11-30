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
    } // constructor

    public TextField(String text, int width, int height) {
        this(text);
        this.width = width;
        this.height = height;
    } // constructor

    private void loadImage() {
        if (borderLeft != null) {
            return;
        } // if

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
    } // loadImage

    public void setOnSubmit(ButtonCallback callback) {
        this.onSubmit = callback;
    } // setOnSubmit

    @Override
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            } // if
            
            return;
        } // if

        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            if (onSubmit != null) {
                onSubmit.action(null);
            } // if
            return;
        } // if

        this.text += key.getKeyChar();
    } // keyPressed

    public String getText() {
        return text;
    } // getText

    public void setText(String text) {
        this.text = text;
    } // setText

    @Override
    public void render() {
        renderBorder();
        renderText();
    } // render
    
    private void renderBorder() {
        if (hasFocus()) {
            borderLeft.renderHighlighted(x, y);

            for (int i = x + borderLeft.getWidth(); i < x + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
                borderRepeat.renderHighlighted(i, y);
            } // for

            borderRight.renderHighlighted(x + width - borderRight.getWidth(), y);
        } else {
            borderLeft.render(x, y);

            for (int i = x + borderLeft.getWidth(); i < x + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
                borderRepeat.render(i, y);
            } // for

            borderRight.render(x + width - borderRight.getWidth(), y);
        }
    } // renderBorder

    private void renderText() {
        int offsX = x + Button.MARGIN + (width - Button.MARGIN * 2 - FontService.getStringWidth(text)) / 2;
        int offsY = y + FontService.characterHeight;

        FontService.drawFont(text, Color.white, offsX, offsY);
    } // renderText
} // TextField
