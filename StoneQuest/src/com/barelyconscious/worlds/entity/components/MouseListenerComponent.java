package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.input.InputLayer;
import com.barelyconscious.worlds.engine.input.Interactable;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;

import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 * todo: entities that walk into the mouse don't trigger mouse events
 * todo: multiple mouseover components do not work together
 */
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
    public void onRemove() {
        super.onRemove();
        System.out.println("Deregistering mouse listener");
        MouseInputHandler.instance().unregisterInteractable(this);
    }

    @Override
    public boolean contains(int screenX, int screenY) {
        // todo(p0) big ew
        if (GameInstance.instance().getCamera() == null) { return false; }
        final Vector worldPos = getParent().getTransform();
        final Vector screenPos = GameInstance.instance().getCamera().worldToScreenPos(worldPos);
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
