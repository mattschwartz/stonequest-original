package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.entity.components.MouseInputComponent;
import com.google.common.collect.Lists;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;

public final class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    public enum InputLayer {
        WORLD(0),
        GUI(1);

        public static List<InputLayer> sorted() {
            return Lists.newArrayList(GUI, WORLD);
        }

        public final int zLevel;

        InputLayer(final int zLevel) {
            this.zLevel = zLevel;
        }
    }

    private final SortedMap<InputLayer, List<MouseInputComponent>> listeners;

    public MouseInputHandler() {
        this.listeners = new TreeMap<>();

        for (final InputLayer layer : InputLayer.values()) {
            listeners.put(layer, new ArrayList<>());
        }
    }

    public void registerListener(final InputLayer layer, final MouseInputComponent component) {
        listeners.get(layer).add(component);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (final InputLayer layer : InputLayer.sorted()) {
            for (final MouseInputComponent c : listeners.get(layer)) {
                if (!c.contains(e.getX(), e.getY())) {
                    continue;
                }
                c.delegateOnMouseClicked.call(new com.barelyconscious.game.entity.input.MouseEvent(
                    e.getX(),
                    e.getY(),
                    -1,
                    -1,
                    false,
                    true
                ));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
