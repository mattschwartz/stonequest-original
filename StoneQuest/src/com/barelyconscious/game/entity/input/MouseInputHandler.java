package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.components.MouseInputComponent;
import com.google.common.collect.Lists;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    public final Delegate<MouseEvent> onMouseClicked = new Delegate<>();
    public final Delegate<MouseEvent> onMouseMoved = new Delegate<>();
    public final Delegate<MouseEvent> onMouseReleased = new Delegate<>();

    public final Delegate<MouseEvent> onMouseEntered = new Delegate<>();
    public final Delegate<MouseEvent> onMouseExited = new Delegate<>();

    private final Map<InputLayer, List<Interactable>> interactablesByLayer;

    public MouseInputHandler() {
        this.interactablesByLayer = new EnumMap<>(InputLayer.class);
        InputLayer.sorted().forEach(layer -> interactablesByLayer.put(layer, new CopyOnWriteArrayList<>()));
    }

    public void registerInteractable(final Interactable interactable, final InputLayer inputLayer) {
        List<Interactable> interactables = interactablesByLayer.get(inputLayer);
        interactables.add(interactable);
    }

    public void deregisterInteractable(final Interactable interactable) {
        InputLayer.sorted().forEach(layer -> interactablesByLayer.get(layer).remove(interactable));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        InputLayer.sorted().forEach(layer -> {
            interactablesByLayer.get(layer).forEach(it ->
                it.onMouseClicked(e));
        });

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
        InputLayer.sorted().forEach(layer -> {
            interactablesByLayer.get(layer).forEach(it -> {
                if (it.contains(e.getX(), e.getY())) {
                    if (!it.isMouseOver()) {
                        it.onMouseEntered(e);
                    }
                } else if (it.isMouseOver()) {
                    it.onMouseExited(e);
                }
            });
            onMouseMoved.call(e);
        });
    }
}
