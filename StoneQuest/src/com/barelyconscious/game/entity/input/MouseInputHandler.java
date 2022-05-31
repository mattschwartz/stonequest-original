package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.components.MouseInputComponent;
import com.google.common.collect.Lists;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    public final Delegate<MouseEvent> onMouseClicked = new Delegate<>();
    public final Delegate<MouseEvent> onMouseMoved = new Delegate<>();
    public final Delegate<MouseEvent> onMouseReleased = new Delegate<>();

    public final Delegate<MouseEvent> onMouseEntered = new Delegate<>();
    public final Delegate<MouseEvent> onMouseExited = new Delegate<>();

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        onMouseClicked.call(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        onMouseReleased.call(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        onMouseEntered.call(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        onMouseExited.call(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouseMoved.call(e);
    }
}
