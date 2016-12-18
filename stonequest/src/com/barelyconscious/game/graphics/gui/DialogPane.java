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

import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.graphics.ShapeDrawer;
import com.barelyconscious.game.input.Interactable;
import com.barelyconscious.game.services.InputHandler;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.util.StringHelper;
import com.barelyconscious.util.TextLogHelper;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;

public class DialogPane extends Interactable implements Component, ButtonAction {

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
        determineDimensions();

        messageTextArea = new TextArea(x, y, width, height);
        messageTextArea.setBackgroundColor(new Color(107, 107, 107));
        messageTextArea.setTextColor(Color.black.getRGB());

        okButton = new Button(this, "Okay", Interactable.Z_DIALOG_PANE_BUTTONS, x + BUTTON_MARGIN, y, true);
        cancelButton = new Button(this, "Cancel", Interactable.Z_DIALOG_PANE_BUTTONS, x, y, true);

        okButton.setY(y + height - okButton.getHeight() - BUTTON_MARGIN);
        cancelButton.setX(x + width - cancelButton.getWidth() - BUTTON_MARGIN);
        cancelButton.setY(okButton.getY());

        messageTextArea.setText(message);

        super.setRegion(x, y, width, height);
        super.addMouseListener(Interactable.Z_DIALOG_PANE);
    }

    private void determineDimensions() {
        int maxLineWidth = 0;
        int lineLength;
        List<String> lines = StringHelper.splitStringAlongWords(message, MAX_LINE_LENGTH);
        lines.add(title);

        for (String string : lines) {
            lineLength = string.length();

            if (lineLength > maxLineWidth) {
                maxLineWidth = lineLength;
            }
        }

        // Find a better way to do this...
//        width = Font.getMaxStringWidth(lines) + Font.CHAR_WIDTH * 2;
        height = (lines.size() + 1) * FontService.characterHeight + BUTTON_MARGIN + Button.DEFAULT_HEIGHT;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        moveCursorSet = false;
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int diffX = e.getX() - InputHandler.INSTANCE.getMouseX();
        int diffY = e.getY() - InputHandler.INSTANCE.getMouseY();
        int newX, newY;

        if (!moveCursorSet) {
            Cursors.setCursor(Cursors.MOVE_CURSOR);
            moveCursorSet = true;
        }

        newX = Math.min(SceneService.INSTANCE.getWidth() - (width + BORDER_WIDTH * 4), Math.max(BORDER_WIDTH, this.x - diffX));
        newY = Math.min(SceneService.INSTANCE.getHeight() - (BORDER_WIDTH + height + BORDER_WIDTH + TOP_BORDER_HEIGHT + 2 + BORDER_WIDTH + 3), Math.max(TOP_BORDER_HEIGHT, this.y - diffY));

        setX(newX);
        setY(newY);
    }

    /**
     * The callback function for when one of the buttons are pressed.
     *
     * @param buttonPressed the calling button
     */
    @Override
    public void action(Button buttonPressed) {
        dispose();
    }

    @Override
    public void hoverOverAction(Button caller) {
        // Does nothing
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
        x = Math.max(0, newX);

        messageTextArea.setX(x);
        okButton.setX(x + BUTTON_MARGIN);
        cancelButton.setX(x + width - cancelButton.getWidth() - BUTTON_MARGIN);

        super.setRegion(x, y, width, height);
    }

    @Override
    public void setY(int newY) {
        y = Math.max(0, newY);

        messageTextArea.setY(y);
        okButton.setY(y + height - okButton.getHeight() - BUTTON_MARGIN);
        cancelButton.setY(okButton.getY());

        super.setRegion(x, y, width, height);
    }

    public void setWidth(int newWidth) {
        width = newWidth;
        super.setRegion(x, y, width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
        super.setRegion(x, y, width, height);
    }

    @Override
    public int getHeight() {
        return height + TOP_BORDER_HEIGHT;
    }

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
    }

    @Override
    public boolean shouldRemove() {
        return destroy;
    }

    @Override
    public void render() {
        if (destroy) {
            System.err.println("Why is this happening??");
            return;
        }
        // Render title of pane
        renderTitle();

        try {
            // Render text area - a border with text
            messageTextArea.render();

            // Render buttons
            okButton.render();
            cancelButton.render();
        } catch (NullPointerException ex) {
            System.err.println(" [ERROR]: " + ex);
        }
    }

    private void renderTitle() {
        int borderWidth, borderHeight, borderOffsX, borderOffsY;
        int titleLineHeight, titleOffsX, titleOffsY;

        titleLineHeight = FontService.characterHeight;
        titleOffsX = x + (width - FontService.getStringWidth(title)) / 2;
        titleOffsY = y - BORDER_WIDTH - 3;

        borderWidth = BORDER_WIDTH + width + BORDER_WIDTH;
        borderHeight = BORDER_WIDTH + height + BORDER_WIDTH + titleLineHeight + 2;
        borderOffsX = x - BORDER_WIDTH;
        borderOffsY = y - TOP_BORDER_HEIGHT;

        ShapeDrawer.fillTransluscentRectangle(borderOffsX, borderOffsY, borderWidth, borderHeight);
        FontService.drawFont(title, TextLogHelper.TEXTLOG_DEFAULT_COLOR, titleOffsX, titleOffsY);
    }
}
