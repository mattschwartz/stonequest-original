package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

public class FloatingTextWidget extends Widget {

    @Getter
    @Setter
    private Vector direction;

    @Getter
    @Setter
    private long durationMillis;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Color textColor;

    public FloatingTextWidget(
        final LayoutData layoutData,
        final Vector direction,
        final long durationMillis,
        final Color textColor
    ) {
        super(layoutData);
        this.direction = direction;
        this.durationMillis = durationMillis;
        this.textColor = textColor;
    }

    private final float moveSpeed = 30f;
    private int deltaY = 0;

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        eventArgs.submitJob(t -> {
            t.yield(durationMillis, u -> {
                setRemoving(true);
                return null;
            });
            return null;
        });

        renderContext.getFontContext().renderString(
            text,
            textColor,
            screenBounds.left,
            screenBounds.top - deltaY,
            RenderLayer.GUI);

        deltaY += Math.round(moveSpeed * (eventArgs.getDeltaTime() + 0.075));
    }
}
