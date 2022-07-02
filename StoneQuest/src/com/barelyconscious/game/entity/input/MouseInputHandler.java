package com.barelyconscious.game.entity.input;

import com.barelyconscious.game.delegate.Delegate;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final class InstanceHolder {
        static final MouseInputHandler instance = new MouseInputHandler();
    }

    public static MouseInputHandler instance() {
        return MouseInputHandler.InstanceHolder.instance;
    }

    public final Delegate<MouseEvent> onMouseClicked = new Delegate<>();
    public final Delegate<MouseEvent> onMouseMoved = new Delegate<>();
    public final Delegate<MouseEvent> onMousePressed = new Delegate<>();
    public final Delegate<MouseEvent> onMouseReleased = new Delegate<>();

    public final Delegate<MouseEvent> onMouseEntered = new Delegate<>();
    public final Delegate<MouseEvent> onMouseExited = new Delegate<>();

    private final Map<InputLayer, List<Interactable>> interactablesByLayer;

    private MouseInputHandler() {
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        onMousePressed.call(e);
        for (final InputLayer layer : InputLayer.sorted()) {
            for (final Interactable it : interactablesByLayer.get(layer)) {
                if (it.isInteractableEnabled()) {
                    it.onMousePressed(e);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        onMouseReleased.call(e);
        boolean isConsumed = false;
        for (final InputLayer layer : InputLayer.sorted()) {
            if (isConsumed) {
                break;
            }

            for (final Interactable it : interactablesByLayer.get(layer)) {
                if (it.isInteractableEnabled()) {
                    if (it.isMouseOver()) {
                        if (!it.contains(e.getX(), e.getY())) {
                            it.onMouseExited(e);
                            isConsumed=true;
                            break;
                        }

                        isConsumed = it.onMouseClicked(e);
                        if (isConsumed) {
                            it.onMouseReleased(e);
                            break;
                        }
                    }
                }
            }
        }

        if (!isConsumed) {
            onMouseClicked.call(e);
        }
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
        boolean isConsumed = false;
        for (final InputLayer layer : InputLayer.sorted()) {
            for (final Interactable it : interactablesByLayer.get(layer)) {
                if (it.isInteractableEnabled()) {
                    if (it.contains(e.getX(), e.getY())) {
                        if (!it.isMouseOver()) {
                            isConsumed = isConsumed || it.onMouseEntered(e);
                        }
                        isConsumed = isConsumed || it.onMouseEntered(e); // 🤔🤔🤔

                    } else if (it.isMouseOver()) {
                        it.onMouseExited(e);
                    }
                }
            }
        }

        if (!isConsumed) {
            onMouseMoved.call(e);
        }
    }
}
