package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.common.shape.Box;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Widget {

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
    @Setter
    @Nullable
    private Widget parent;

    @Getter
    protected Box screenBounds;

    public Widget(final LayoutData layout) {
        this.layout = layout;
        this.widgets = new CopyOnWriteArrayList<>();
    }

    public final boolean isEnabled() {
        if (parent != null) {
            return parent.isEnabled && isEnabled;
        }
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * @return the widget added
     */
    @CanIgnoreReturnValue
    public final Widget addWidget(final Widget widget) {
        widget.setParent(this);
        widgets.add(widget);
        return widget;
    }

    public void resize(final Box bounds) {
        screenBounds = layout.applyLayout(bounds);
        widgets.forEach(t -> t.resize(screenBounds));
    }

    public final void render(final EventArgs eventArgs, final RenderContext renderContext) {
        this.onRender(eventArgs, renderContext);
        final List<Widget> toRemove = new ArrayList<>();

        if (EventArgs.IS_DEBUG) {
            renderContext.renderRect(new Color(175, 175, 0, 88), false, screenBounds, RenderLayer._DEBUG);
        }

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
