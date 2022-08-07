package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Vector;
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
