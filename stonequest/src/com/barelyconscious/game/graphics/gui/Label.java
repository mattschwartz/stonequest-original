/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         Label.java
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

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.UIElement;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Label extends BetterComponent {

    public static final String BUTTON_BORDER_PATH = "/gfx/gui/components/button/buttonBorder.png";
    protected static UIElement borderLeft;
    protected static UIElement borderRight;
    protected static UIElement borderRepeat;

    private String text;

    public Label(String text) {
        this.text = text;
        loadImage();
    } // constructor

    public Label(String text, int width, int height) {
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

    public String getText() {
        return text;
    } // getText

    public void setText(String text) {
        this.text = text;
    } // setText

    @Override
    public void render() {
        borderLeft.render(x, y);

        for (int i = x + borderLeft.getWidth(); i < x + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
            borderRepeat.render(i, y);
        } // for

        borderRight.render(x + width - borderRight.getWidth(), y);

        renderText();
    } // render

    private void renderText() {
        int offsX = x + Button.MARGIN + (width - Button.MARGIN * 2 - FontService.getStringWidth(text)) / 2;
        int offsY = y + FontService.characterHeight;

        FontService.drawFont(text, Color.white, offsX, offsY);
    } // renderText
} // Label
