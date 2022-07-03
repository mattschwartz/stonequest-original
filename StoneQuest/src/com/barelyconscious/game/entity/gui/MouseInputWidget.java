package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;

import java.awt.event.MouseEvent;

/**
 * A type of widget that can process mouse input
 */
public class MouseInputWidget extends Widget implements Interactable {

    private boolean isMouseOver = false;

    public final Delegate<Boolean> delegateOnMouseOver = new Delegate<>();

    public MouseInputWidget(LayoutData layout) {
        this(layout, InputLayer.USER_INPUT);
    }

    public MouseInputWidget(final LayoutData layout, InputLayer inputLayer) {
        super(layout);
        MouseInputHandler.instance().registerInteractable(this, inputLayer);
    }

    @Override
    public boolean isInteractableEnabled() {
        return isEnabled();
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        return isMouseOver();
    }

    @Override
    public final boolean contains(final int screenX, final int screenY) {
        return screenBounds != null && screenBounds.contains(screenX, screenY);
    }

    @Override
    public final boolean isMouseOver() {
        return isMouseOver;
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        isMouseOver = true;
        delegateOnMouseOver.call(true);
        return true;
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        isMouseOver = false;
        delegateOnMouseOver.call(false);
        return false;
    }
}
