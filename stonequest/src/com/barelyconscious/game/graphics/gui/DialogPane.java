/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        DialogPane.java
 * Author:           Matt Schwartz
 * Date created:     08.27.2013
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */
package com.barelyconscious.game.graphics.gui;

import com.barelyconscious.game.Common;
import com.barelyconscious.game.Game;
import com.barelyconscious.game.Screen;
import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.input.Interactable;
import java.awt.Color;
import java.util.List;

public class DialogPane extends Interactable implements Components, ButtonAction {

    private final int TOP_BORDER_HEIGHT = 19;
    public static final int MAX_LINE_LENGTH = 35;
    public static final int BUTTON_MARGIN = 7;
    public static final int BORDER_WIDTH = 2;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String title;
    protected String message;
    private Button okButton;
    private Button cancelButton;
    private TextArea messageTextArea;
    private boolean destroy;
    private boolean moveCursorSet = false;

    public DialogPane(int startX, int startY, String title, String message) {
        x = Math.max(0, startX);
        y = Math.max(0, startY);
        this.title = title;
        this.message = message;
        determineDimensions(Game.screen);

        messageTextArea = new TextArea(x, y, width, height);
        messageTextArea.setBackgroundColor(new Color(107, 107, 107).getRGB());
        messageTextArea.setTextColor(Color.black.getRGB());

        okButton = new Button(this, "Okay", Interactable.Z_DIALOG_PANE_BUTTONS, x + BUTTON_MARGIN, y, true);
        cancelButton = new Button(this, "Cancel", Interactable.Z_DIALOG_PANE_BUTTONS, x, y, true);

        okButton.setY(y + height - okButton.getHeight() - BUTTON_MARGIN);
        cancelButton.setX(x + width - cancelButton.getWidth() - BUTTON_MARGIN);
        cancelButton.setY(okButton.getY());

        messageTextArea.setText(message);

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_DIALOG_PANE);
    } // constructor

    private void determineDimensions(Screen screen) {
        int maxLineWidth = 0;
        int lineLength;
        List<String> lines = Common.splitStringAlongWords(message, MAX_LINE_LENGTH);
        lines.add(title);

        for (String string : lines) {
            lineLength = string.length();

            if (lineLength > maxLineWidth) {
                maxLineWidth = lineLength;
            } // if
        } // for

        width = Font.getMaxStringWidth(screen, lines) + Font.NEW_CHAR_WIDTH * 2;
        height = (lines.size() + 1) * Font.NEW_CHAR_HEIGHT + BUTTON_MARGIN + Button.DEFAULT_HEIGHT;
    } // determineDimensions

    @Override
    public void mouseReleased() {
        super.mouseReleased();
        moveCursorSet = false;
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    } // mouseReleased

    @Override
    public void mouseDragged(int x, int y) {
        int diffX = mouseX - x;
        int diffY = mouseY - y;
        int newX, newY;

        if (!moveCursorSet) {
            Cursors.setCursor(Cursors.MOVE_CURSOR);
            moveCursorSet = true;
        } // if

        super.mouseDragged(x, y);
        newX = Math.min(Game.getGameWidth() - (width + BORDER_WIDTH * 4), Math.max(BORDER_WIDTH, this.x - diffX));
        newY = Math.min(Game.getGameHeight() - (BORDER_WIDTH + height + BORDER_WIDTH + TOP_BORDER_HEIGHT + 2 + BORDER_WIDTH + 3), Math.max(TOP_BORDER_HEIGHT, this.y - diffY));

        setX(newX);
        setY(newY);
    } // mouseDragged

    /**
     * The callback function for when one of the buttons are pressed.
     *
     * @param buttonPressed the calling button
     */
    @Override
    public void action(Button buttonPressed) {
        dispose();
    } // action

    @Override
    public void hoverOverAction(Button caller) {
        // Does nothing
    } // hoverOverAction

    @Override
    public int getX() {
        return x;
    } // getX

    @Override
    public int getY() {
        return y;
    } // getY

    @Override
    public void setX(int newX) {
        x = Math.max(0, newX);

        messageTextArea.setX(x);
        okButton.setX(x + BUTTON_MARGIN);
        cancelButton.setX(x + width - cancelButton.getWidth() - BUTTON_MARGIN);

        super.setRegion(x, y, width, height);
    } // setX

    @Override
    public void setY(int newY) {
        y = Math.max(0, newY);

        messageTextArea.setY(y);
        okButton.setY(y + height - okButton.getHeight() - BUTTON_MARGIN);
        cancelButton.setY(okButton.getY());

        super.setRegion(x, y, width, height);
    } // setY

    public void setWidth(int newWidth) {
        width = newWidth;
        super.setRegion(x, y, width, height);
    } // setWidth

    @Override
    public int getWidth() {
        return width;
    } // getWidth

    public void setHeight(int newHeight) {
        height = newHeight;
        super.setRegion(x, y, width, height);
    } // setHeight

    @Override
    public int getHeight() {
        return height + TOP_BORDER_HEIGHT;
    } // getHeight

    /**
     * This function is called when the DialogPane is no longer necessary and
     * should be removed as determined by the JRE.
     */
    @Override
    public void dispose() {
        destroy = true;

        messageTextArea.dispose();
        okButton.dispose();
        cancelButton.dispose();
        removeMouseListener();

        messageTextArea = null;
        okButton = null;
        cancelButton = null;

        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    } // dispose

    @Override
    public boolean shouldRemove() {
        return destroy;
    } // shouldDestroy

    @Override
    public void render(Screen screen) {
        if (destroy) {
            System.err.println("Why is this happening??");
            return;
        }
        // Render title of pane
        renderTitle(screen);

        try {
            // Render text area - a border with text
            messageTextArea.render(screen);

            // Render buttons
            okButton.render(screen);
            cancelButton.render(screen);
        } catch (NullPointerException ex) {
            System.err.println(" [ERROR]: " + ex);
        }
    } // render

    private void renderTitle(Screen screen) {
        int borderWidth, borderHeight, borderOffsX, borderOffsY;
        int titleLineHeight, titleOffsX, titleOffsY;

        titleLineHeight = Font.NEW_CHAR_HEIGHT;
        titleOffsX = x + (width - Font.getStringWidth(screen, title)) / 2;
        titleOffsY = y - BORDER_WIDTH - 3;

        borderWidth = BORDER_WIDTH + width + BORDER_WIDTH;
        borderHeight = BORDER_WIDTH + height + BORDER_WIDTH + titleLineHeight + 2;
        borderOffsX = x - BORDER_WIDTH;
        borderOffsY = y - TOP_BORDER_HEIGHT;

        screen.fillTransluscentRectangle(borderOffsX, borderOffsY, borderWidth, borderHeight);
        Font.drawFont(screen, title, Common.FONT_DEFAULT_RGB, titleOffsX, titleOffsY);
    } // renderTitle
} // DialogPane
