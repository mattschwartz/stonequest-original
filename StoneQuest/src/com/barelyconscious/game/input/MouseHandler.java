/* *****************************************************************************
 * Project:           StoneQuest
 * File Name:         MouseHandler.java
 * Author:            Matt Schwartz
 * Date Created:      03.14.2013
 * Redistribution:    You are free to use, reuse, and edit any of the text in
 *                    this file.  You are not allowed to take credit for code
 *                    that was not written fully by yourself, or to remove
 *                    credit from code that was not written fully by yourself.
 *                    Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File Description:
 ************************************************************************** */
package com.barelyconscious.game.input;

import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.services.messaging.MessageSystem;
import com.barelyconscious.services.messaging.logs.TextLogWriterService;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static final byte NO_CLICK = 0;
    public static final byte CLICK = 1;
    public static final byte LEFT_CLICK = MouseEvent.BUTTON1;
    public static final byte MIDDLE_CLICK = MouseEvent.BUTTON2;
    public static final byte RIGHT_CLICK = MouseEvent.BUTTON3;
    /**
     * Areas that are checked when the mouse is clicked in order to determine
     * where to send that event.
     */
    private ArrayList<Interactable> clickableAreas = new ArrayList();
    /**
     * Areas that are checked where ever the mouse cursor is located to
     * determine where to send that event.
     */
    private ArrayList<Interactable> hoverableAreas = new ArrayList();

    private final MessageSystem messageSystem;

    public MouseHandler(final MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    /**
     * Registers a Mouse zone that will be called when the user presses a mouse
     * button within that zone.
     *
     * @param clickable the class that will receive actions when the user
     *                  presses a mouse button
     */
    public void registerClickableListener(Interactable clickable) {
        if (clickableAreas.contains(clickable)) {
            return;
        }

        clickableAreas.add(clickable);
    }

    public void removeClickableListener(Interactable toRemove) {
        clickableAreas.remove(toRemove);
    }

    /**
     * Registers a Mouse zone that will be called when the user moves his or her
     * mouse over the hoverable area.
     *
     * @param hoverable the class that will receive actions when the user moves
     *                  his or her mouse over the designated Mouse zone
     */
    public void registerHoverableListener(Interactable hoverable) {
        if (hoverableAreas.contains(hoverable)) {
            return;
        }

        hoverableAreas.add(hoverable);
    }

    public void removeHoverableListener(Interactable toRemove) {
        hoverableAreas.remove(toRemove);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Interactable clickable;

        for (int i = 0; i < clickableAreas.size(); i++) {
            clickable = clickableAreas.get(i);

            if (clickable.mouseInFocus(me.getX(), me.getY())) {
                clickable.mouseClicked(me.getButton(), me.getX(), me.getY());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Interactable hoverable;

        for (int i = 0; i < hoverableAreas.size(); i++) {
            hoverable = hoverableAreas.get(i);

            if (hoverable.mouseInFocus(e.getX(), e.getY())) {
                hoverable.mouseMoved(e.getX(), e.getY());
            } else {
                hoverable.mouseExited();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            messageSystem.sendAlert(TextLogWriterService.LOG_SCROLL_UP, this);
            messageSystem.sendAlert(TextLogWriterService.LOG_SCROLL_UP, this);
        }

        if (e.getWheelRotation() > 0) {
            messageSystem.sendAlert(TextLogWriterService.LOG_SCROLL_DOWN, this);
            messageSystem.sendAlert(TextLogWriterService.LOG_SCROLL_DOWN, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }
}
