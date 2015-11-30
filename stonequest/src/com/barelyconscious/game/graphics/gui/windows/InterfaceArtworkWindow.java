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
package com.barelyconscious.game.graphics.gui.windows;

import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.UIElement;

public class InterfaceArtworkWindow extends Window {

    private static final UIElement BOTTOM_LEFT_FRAME = new UIElement("/gfx/gui/components/windows/interfaceArtwork/bottomLeftFrame.png");
    private static final UIElement BOTTOM_RIGHT_FRAME = new UIElement("/gfx/gui/components/windows/interfaceArtwork/bottomRightFrame.png");
    private static final UIElement TEXT_LOG_BORDER_REPEAT = new UIElement("/gfx/gui/components/windows/interfaceArtwork/borderRepeat.png");
    private int bottomLeftFrameOffsX;
    private int bottomLeftFrameOffsY;
    private int bottomRightFrameOffsX;
    private int bottomRightFrameOffsY;
    private int textLogOffsX;
    private int textLogOffsY;
    private int textLogWidth;

    public InterfaceArtworkWindow(int gameWidth, int gameHeight) {
        // Determine offset values for this window's elements
        bottomLeftFrameOffsX = 0;
        bottomLeftFrameOffsY = gameHeight - BOTTOM_LEFT_FRAME.getHeight();

        bottomRightFrameOffsX = gameWidth - BOTTOM_RIGHT_FRAME.getWidth();
        bottomRightFrameOffsY = bottomLeftFrameOffsY;

        textLogOffsX = bottomLeftFrameOffsX + BOTTOM_LEFT_FRAME.getWidth();
        textLogOffsY = bottomLeftFrameOffsY;
        textLogWidth = bottomRightFrameOffsX - textLogOffsX;

        windowOffsX = bottomLeftFrameOffsX;
        windowOffsY = bottomLeftFrameOffsY;
    } // constructor

    /**
     * Reposition and resize the elements as the game application is resized.
     * The left and right sides of the frame require their windowOffsX and windowOffsY coordinates
     * to be redetermined and from these values should be derived the width of
     * the text log.
     *
     * @param gameWidth the new width of the application
     * @param gameHeight the new height of the application
     */
    public void resize(int gameWidth, int gameHeight) {
        bottomLeftFrameOffsY = gameHeight - BOTTOM_LEFT_FRAME.getHeight();

        bottomRightFrameOffsX = gameWidth - BOTTOM_RIGHT_FRAME.getWidth();
        bottomRightFrameOffsY = bottomLeftFrameOffsY;

        textLogOffsY = bottomLeftFrameOffsY;
        textLogWidth = bottomRightFrameOffsX - textLogOffsX;

        windowOffsY = bottomLeftFrameOffsY;
    } // resize
    
    public int getTextLogOffsX() {
        return textLogOffsX - 19;
    } // getTextLogOffsX
    
    public int getTextLogOffsY() {
        return textLogOffsY + 6;
    } // getTextLogOffsY

    /**
     * The text log needs to know this information and can easily get it here
     * rather than doing more calculations elsewhere.
     *
     * @return the width in pixels of the text log
     */
    public int getTextLogWidth() {
        return textLogWidth+22;
    } // getTextLogWidth
    
    public int getTextLogHeight() {
        return 100;
    } // getTextLogHeight
    
    public int getTooltipOffsX() {
        return bottomRightFrameOffsX + 15;
    } // getTooltipOffsX
    
    public int getTooltipOffsY() {
        return bottomRightFrameOffsY + 6;
    } // getTooltipOffsY

    public int getInventoryButtonOffsX() {
        return bottomRightFrameOffsX + 195;
    } // getInventoryButtonOffsX

    public int getInventoryButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    } // getInventoryButtonOffsY

    public int getCharacterButtonOffsX() {
        return bottomRightFrameOffsX + 244;
    } // getCharacterButtonOffsX

    public int getCharacterButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    } // getCharacterButtonOffsY

    public int getUpgradeItemButtonOffsX() {
        return bottomRightFrameOffsX + 293;
    } // getUpgradeItemButtonOffsX

    public int getUpgradeItemButtonOffsY() {
        return bottomRightFrameOffsY + 20;
    } // getUpgradeItemButtonOffsY

    public int getJournalButtonOffsX() {
        return bottomRightFrameOffsX + 195;
    } // getJournalButtonOffsX

    public int getJournalButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    } // getJournalButtonOffsY

    public int getSalvageButtonOffsX() {
        return bottomRightFrameOffsX + 244;
    } // getSalvageButtonOffsX

    public int getSalvageButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    } // getSalvageButtonOffsY

    public int getBrewingButtonOffsX() {
        return bottomRightFrameOffsX + 293;
    } // getBrewingButtonOffsX

    public int getBrewingButtonOffsY() {
        return bottomRightFrameOffsY + 57;
    } // getBrewingButtonOffsY

    @Override
    public void render(Screen screen) {
        int textLogBorderRepeatWidth = TEXT_LOG_BORDER_REPEAT.getWidth();

        for (int i = textLogOffsX; i < textLogOffsX + textLogWidth; i += textLogBorderRepeatWidth) {
            TEXT_LOG_BORDER_REPEAT.render(screen, i, textLogOffsY);
        } // for

        BOTTOM_LEFT_FRAME.render(screen, bottomLeftFrameOffsX, bottomLeftFrameOffsY);
        BOTTOM_RIGHT_FRAME.render(screen, bottomRightFrameOffsX, bottomRightFrameOffsY);
    } // render
} // InterfaceArtworkWindow
