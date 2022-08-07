package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class MouseListenerComponent extends Component implements Interactable {

    private final Box listenerBounds;
    public final Delegate<Boolean> delegateOnMouseOver = new Delegate<>();
    public final Delegate<MouseEvent> delegateOnMouseClicked = new Delegate<>();

    public MouseListenerComponent(final Actor parent, final Box listenerBounds) {
        super(parent);
        this.listenerBounds = listenerBounds;
        MouseInputHandler.instance().registerInteractable(this, InputLayer.GAME_WORLD);
    }

    private boolean mouseOver = false;

    private Box screenListenerBounds = null;

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        if (screenListenerBounds != null && EventArgs.IS_DEBUG) {
            renderContext.renderRect(Color.GREEN, false, screenListenerBounds, RenderLayer._DEBUG);
        }
    }

    @Override
    public boolean contains(int screenX, int screenY) {
        // todo(p0) big ew
        if (GameInstance.getInstance().getCamera() == null) { return false; }
        final Vector worldPos = getParent().transform;
        final Vector screenPos = GameInstance.getInstance().getCamera().worldToScreenPos(worldPos);
        final Box bounds = listenerBounds.boxAtPosition(screenPos);
        screenListenerBounds = bounds;

        return bounds.contains(screenX, screenY);
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        mouseOver = true;
        delegateOnMouseOver.call(true);
        return true;
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        mouseOver = false;
        delegateOnMouseOver.call(false);
        return false;
    }

    @Override
    public boolean isMouseOver() {
        return mouseOver;
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        delegateOnMouseClicked.call(e);
        return Interactable.super.onMouseClicked(e);
    }
}
