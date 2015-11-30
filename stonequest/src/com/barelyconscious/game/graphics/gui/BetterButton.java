/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         BetterButton.java
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
import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.util.GUIHelper;
import com.barelyconscious.util.StringHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BetterButton extends BetterComponent {

    private final int DEFAULT_WIDTH = 108;
    private final int DEFAULT_HEIGHT = 24;
    private final int MARGIN = 5;
    private static UIElement borderLeft;
    private static UIElement borderRight;
    private static UIElement borderRepeat;
    private final Color borderHighlightColor = new Color(137, 137, 137);
    private final Color borderShadowColor = new Color(38, 38, 38);

    private boolean hasBorder;
    private String text;
    private ButtonCallback callbackFunction;

    public BetterButton(String text) {
        this.text = text;
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        loadImage();
        super.addMouseListener(Interactable.Z_BUTTON);
    } // constructor

    public BetterButton(ButtonCallback callback, String text) {
        this(text);
        callbackFunction = callback;
        loadImage();
    } // constructor

    private void loadImage() {
        int[] pixels;
        int subWidth = 4;
        int subHeight = 24;
        BufferedImage unparsedImage = UIElement.loadImage("/gfx/gui/components/label/labelBorder.png");

        pixels = unparsedImage.getRGB(0, 0, subWidth, subHeight, null, 0, subWidth);
        borderLeft = new UIElement(pixels, subWidth, subHeight);
        pixels = unparsedImage.getRGB(subWidth, 0, subWidth, subHeight, null, 0, subWidth);
        borderRight = new UIElement(pixels, subWidth, subHeight);
        pixels = unparsedImage.getRGB(subWidth * 2, 0, subWidth, subHeight, null, 0, subWidth);
        borderRepeat = new UIElement(pixels, subWidth, subHeight);
    } // loadImage

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCallbackFunction(ButtonCallback callbackFunction) {
        this.callbackFunction = callbackFunction;
    } // setCallbackFunction

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
            return;
        } // if

        if (callbackFunction != null && e.getButton() == Interactable.MOUSE_LEFT_CLICK) {
            callbackFunction.action(this);
        } // if
    } // mouseClicked

    @Override
    public void mouseEntered() {
        if (!isEnabled()) {
            return;
        } // if

        if (callbackFunction != null) {
            callbackFunction.hoverOverAction(this);
        } // if

        super.mouseEntered();
    } // mouseEntered

    @Override
    public void mouseExited() {
        if (!isEnabled()) {
            return;
        } // if

        if (callbackFunction != null) {
            callbackFunction.hoverOverAction(null);
        } // if

        super.mouseExited();
    } // mouseExited

    @Override
    public void render() {
        int xOffs = x;
        int yOffs = y;

        if (isMouseInFocus()) {
            renderHighlighted();
            return;
        } // if

        if (isMouseButtonDown()) {
            xOffs++;
            yOffs++;
        } // if

        borderLeft.render(xOffs, yOffs);

        for (int i = xOffs + borderLeft.getWidth(); i < xOffs + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
            borderRepeat.render(i, yOffs);
        } // for

        borderRight.render(xOffs + width - borderRight.getWidth(), yOffs);

        renderText();

        if (hasBorder) {
            renderBorder();
        } // if
    } // render
    
    protected void renderHighlighted() {
        int xOffs = x;
        int yOffs = y;

        if (isMouseButtonDown()) {
            xOffs++;
            yOffs++;
        } // if

        borderLeft.renderHighlighted(xOffs, yOffs);

        for (int i = xOffs + borderLeft.getWidth(); i < xOffs + (width - borderRight.getWidth()); i += borderRepeat.getWidth()) {
            borderRepeat.renderHighlighted(i, yOffs);
        } // for

        borderRight.renderHighlighted(xOffs + width - borderRight.getWidth(), yOffs);

        if (!StringHelper.isNullOrEmpty(text)) {
            renderText();
        } // if

        if (hasBorder) {
            renderBorder();
        } // if
    } // renderHighlighted

    protected void renderText() {
        int offsX = x + MARGIN + (width - MARGIN * 2 - FontService.getStringWidth(text)) / 2;
        int offsY = y + FontService.characterHeight;

        if (isMouseButtonDown()) {
            offsX++;
            offsY++;
            FontService.drawFont(text, GUIHelper.DEFAULT_FONT_COLOR, offsX, offsY);
        } // if
        else {
            FontService.drawFont(text, Color.white, offsX, offsY);
        } // else
    } // renderText

    private void renderBorder() {
        int xOffs = x;
        int yOffs = y;

        if (isMouseButtonDown()) {
            xOffs++;
            yOffs++;
            ShapeDrawer.drawLine(borderShadowColor, xOffs - 1, yOffs - 1, xOffs - 1, yOffs + height + 1);
            ShapeDrawer.drawLine(borderShadowColor, xOffs, yOffs - 1, xOffs + width, yOffs - 1);

            ShapeDrawer.drawLine(borderHighlightColor, xOffs + width, yOffs, xOffs + width, yOffs + height);
            ShapeDrawer.drawLine(borderHighlightColor, xOffs, yOffs + height, xOffs + width, yOffs + height);
        } // if
        else {
            ShapeDrawer.drawLine(borderHighlightColor, xOffs - 1, yOffs - 1, xOffs - 1, yOffs + height + 1);
            ShapeDrawer.drawLine(borderHighlightColor, xOffs, yOffs - 1, xOffs + width, yOffs - 1);

            ShapeDrawer.drawLine(borderShadowColor, xOffs + width, yOffs, xOffs + width, yOffs + height);
            ShapeDrawer.drawLine(borderShadowColor, xOffs, yOffs + height, xOffs + width, yOffs + height);
        } // else
    } // renderButton
} // BetterButton
