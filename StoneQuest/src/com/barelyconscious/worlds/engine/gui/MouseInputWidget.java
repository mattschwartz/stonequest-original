package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.engine.input.Interactable;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;

import java.awt.Color;
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
        return isEnabled() && isMouseOver();
    }

    @Override
    public final boolean contains(final int screenX, final int screenY) {
        return isEnabled() &&
            (screenBounds != null && screenBounds.contains(screenX, screenY));
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        super.onRender(eventArgs, renderContext);

        if (EventArgs.IS_DEBUG) {
            renderContext.renderRect(new Color(0, 0, 200, 75), true, screenBounds, RenderLayer._DEBUG);
            if (EventArgs.IS_VERBOSE) {
                renderContext.getFontContext()
                    .drawString(getClass().getSimpleName(), FontContext.TextAlign.LEFT,
                        screenBounds
                    );
            }
        }
    }

    @Override
    public final boolean isMouseOver() {
        return isEnabled() && isMouseOver;
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        if (!isEnabled()) {
            return false;
        }

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
