package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.shape.Box;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Widget {

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
