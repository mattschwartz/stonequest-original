package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.shape.Box;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Widget {

    @Getter
    private boolean isEnabled = true;

    @Getter
    @Setter
    private boolean isVisible = true;

    @Getter
    @Setter
    private boolean isRemoving = false;

    @Getter
    private final LayoutData layout;

    protected final List<Widget> widgets;

    @Getter
    protected Box screenBounds;

    public Widget(final LayoutData layout) {
        this.layout = layout;
        this.widgets = new CopyOnWriteArrayList<>();
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        widgets.stream().filter(t -> t instanceof MouseInputWidget).forEach(t -> t.setEnabled(enabled));
    }

    /**
     * @return the widget added
     */
    @CanIgnoreReturnValue
    public Widget addWidget(final Widget widget) {
        widgets.add(widget);
        return widget;
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
}
