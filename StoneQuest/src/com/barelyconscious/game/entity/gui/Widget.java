package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.input.Interactable;
import com.barelyconscious.game.shape.Box;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Widget implements Interactable {

    @Getter
    @Setter
    private boolean isEnabled = true;

    @Getter
    @Setter
    private boolean isRemoving = false;

    @Getter
    private final LayoutData layout;

    protected final List<Widget> widgets;

    protected Box screenBounds;

    public Widget(final LayoutData layout) {
        this.layout = layout;
        this.widgets = new CopyOnWriteArrayList<>();
    }

    /**
     * @return the widget added
     */
    public Widget addWidget(final Widget widget) {
        widgets.add(widget);
        return widget;
    }

    public void removeWidget(final Widget widget) {
        widgets.remove(widget);
    }

    public void resize(final Box bounds) {
        screenBounds = layout.applyLayout(bounds);
        widgets.forEach(t -> t.resize(screenBounds));
    }

    public final void render(final EventArgs eventArgs, final RenderContext renderContext) {
        if (EventArgs.IS_DEBUG) {
            renderContext.renderRect(Color.RED, false, screenBounds, RenderLayer._DEBUG);
        }

        this.onRender(eventArgs, renderContext);
        final List<Widget> toRemove = new ArrayList<>();

        for (final Widget w : widgets) {
            if (w.isRemoving) {
                toRemove.add(w);
            } else if (w.isEnabled) {
                w.render(eventArgs, renderContext);
            }
        }

        widgets.removeAll(toRemove);
    }

    protected void onRender(final EventArgs eventArgs, final RenderContext renderContext) {
    }

    private boolean isMouseOver = false;

    @Override
    public final boolean contains(final int screenX, final int screenY) {
        return screenBounds != null && screenBounds.contains(screenX, screenY);
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        isMouseOver = true;
        return true;
    }

    @Override
    public boolean onMouseOver(MouseEvent e) {
        // widgets default to consuming events
        return true;
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        isMouseOver = false;
        return false;
    }

    @Override
    public final boolean isMouseOver() {
        return isMouseOver;
    }
}
