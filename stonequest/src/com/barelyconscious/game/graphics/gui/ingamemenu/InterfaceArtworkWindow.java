/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        InterfaceArtworkWindow.java
 * Author:           Matt Schwartz
 * Date created:     08.29.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: This class handles the artwork for the game that is 
 *                   constantly present but performs no other action. The 
 *                   interface artwork consists of the static images at the 
 *                   bottom of the screen to hold smaller components such as
 *                   buttons to access other Windows and the information text
 *                   log but has no direct interaction between them.
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui.ingamemenu;

import com.barelyconscious.game.graphics.UIElement;
import com.barelyconscious.game.services.SceneService;
import java.awt.image.BufferedImage;

public class InterfaceArtworkWindow extends Window {

    public static final String INTERFACE_ARTWORK_PATH = "/gfx/gui/components/windows/interfaceArtwork/interfaceArtwork.png";
    private static UIElement bottomLeftFrame;
    private static UIElement bottomRightFrame;
    private static UIElement textLogBorderRepeat;
    private int bottomLeftFrameOffsX;
    private int bottomLeftFrameOffsY;
    private int bottomRightFrameOffsX;
    private int bottomRightFrameOffsY;
    private int textLogOffsX;
    private int textLogOffsY;
    private int textLogWidth;

    public InterfaceArtworkWindow() {
        loadBorder();
        // Determine offset values for this window's elements
        bottomLeftFrameOffsX = 0;
        bottomLeftFrameOffsY = SceneService.INSTANCE.getHeight() - bottomLeftFrame.getHeight();

        bottomRightFrameOffsX = SceneService.INSTANCE.getWidth() - bottomRightFrame.getWidth();
        bottomRightFrameOffsY = bottomLeftFrameOffsY;

        textLogOffsX = bottomLeftFrameOffsX + bottomLeftFrame.getWidth();
        textLogOffsY = bottomLeftFrameOffsY;
        textLogWidth = bottomRightFrameOffsX - textLogOffsX;

        setX(bottomLeftFrameOffsX);
        setY(bottomLeftFrameOffsY);
    }

    /**
     * Loads the border from disk by locating subimages within the larger image.
     * This results in a single, large disk access instead of multiple, smaller
     * accesses
     */
    private void loadBorder() {
        int[] pixels;
        BufferedImage unparsedImage = UIElement.loadImage(INTERFACE_ARTWORK_PATH);

        pixels = unparsedImage.getRGB(0, 0, 314, 112, null, 0, 314);
        bottomLeftFrame = new UIElement(pixels, 314, 112);

        pixels = unparsedImage.getRGB(314, 0, 6, 112, null, 0, 6);
        textLogBorderRepeat = new UIElement(pixels, 6, 112);

        pixels = unparsedImage.getRGB(320, 0, 363, 112, null, 0, 363);
        bottomRightFrame = new UIElement(pixels, 363, 112);
    }

    /**
     * Reposition and resize the elements as the game application is resized.
     * The left and right sides of the frame require their getX() and getY()
     * coordinates to be redetermined and from these values should be derived
     * the width of the text log.
     */
    public void resize() {
        bottomLeftFrameOffsY = SceneService.INSTANCE.getHeight() - bottomLeftFrame.getHeight();

        bottomRightFrameOffsX = SceneService.INSTANCE.getWidth() - bottomRightFrame.getWidth();
        bottomRightFrameOffsY = bottomLeftFrameOffsY;

        textLogOffsY = bottomLeftFrameOffsY;
        textLogWidth = bottomRightFrameOffsX - textLogOffsX;

        setY(bottomLeftFrameOffsY);
    }

    public int getTextLogOffsX() {
        return textLogOffsX - 19;
    }

    public int getTextLogOffsY() {
        return textLogOffsY + 6;
    }

    /**
     * The text log needs to know this information and can easily get it here
     * rather than doing more calculations elsewhere.
     *
     * @return the width in pixels of the text log
     */
    public int getTextLogWidth() {
        return textLogWidth + 22;
    }

    public int getTextLogHeight() {
        return 100;
    }

    public int getTooltipOffsX() {
        return bottomRightFrameOffsX + 15;
    }

    public int getTooltipOffsY() {
        return bottomRightFrameOffsY + 6;
    }

    public int getInventoryButtonOffsX() {
        return bottomRightFrameOffsX + 195;
    }

    public int getInventoryButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    }

    public int getCharacterButtonOffsX() {
        return bottomRightFrameOffsX + 244;
    }

    public int getCharacterButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    }

    public int getUpgradeItemButtonOffsX() {
        return bottomRightFrameOffsX + 293;
    }

    public int getUpgradeItemButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    }

    public int getJournalButtonOffsX() {
        return bottomRightFrameOffsX + 195;
    }

    public int getJournalButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    }

    public int getSalvageButtonOffsX() {
        return bottomRightFrameOffsX + 244;
    }

    public int getSalvageButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    }

    public int getBrewingButtonOffsX() {
        return bottomRightFrameOffsX + 293;
    }

    public int getBrewingButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    }

    @Override
    public void render() {
        int textLogBorderRepeatWidth = textLogBorderRepeat.getWidth();

        for (int i = textLogOffsX; i < textLogOffsX + textLogWidth; i += textLogBorderRepeatWidth) {
            textLogBorderRepeat.render(i, textLogOffsY);
        }

        bottomLeftFrame.render(bottomLeftFrameOffsX, bottomLeftFrameOffsY);
        bottomRightFrame.render(bottomRightFrameOffsX, bottomRightFrameOffsY);
    }
}
