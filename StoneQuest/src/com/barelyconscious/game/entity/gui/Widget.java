package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.shape.Box;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Widget {

    @Builder
    public static final class Anchor {
        /**
         * 0 means start at left of screen, 1 means right
         */
        public final float alignLeft;
        /**
         * 0 means start at top of screen, 1 means bottom
         */
        public final float alignTop;

        /**
         * The absolute amount to shift left by
         */
        public final int paddingLeft;
        public final int paddingTop;

        public final int width;
        public final int height;

        public Box applyAnchors(final Box bounds) {
            final float left = bounds.left + (bounds.width * alignLeft) + paddingLeft;
            final float top = bounds.top + (bounds.height * alignTop) + paddingTop;

            return new Box(
                (int) left,
                (int) left + width,
                (int) top,
                (int) top + height);
        }
    }

    @Getter
    @Setter
    private boolean isEnabled = true;

    @Getter
    private final Anchor anchor;

    protected final List<Widget> widgets;

    protected Box screenBounds;

    public Widget(final Anchor anchor) {
        this.anchor = anchor;
        this.widgets = new ArrayList<>();
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
        screenBounds = anchor.applyAnchors(bounds);
        widgets.forEach(t -> t.resize(screenBounds));
    }

    public final void render(final EventArgs eventArgs, final RenderContext renderContext) {
        this.widgets.stream()
            .filter(Widget::isEnabled)
            .forEach(t -> t.render(eventArgs, renderContext));
        this.onRender(eventArgs, renderContext);
    }

    protected abstract void onRender(final EventArgs eventArgs, final RenderContext renderContext);
}
