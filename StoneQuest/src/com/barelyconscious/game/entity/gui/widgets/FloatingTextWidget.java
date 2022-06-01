package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class FloatingTextWidget extends Widget {

    @Getter
    @Setter
    private Vector direction;

    @Getter
    @Setter
    private float durationMs;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Color textColor;

    public FloatingTextWidget(
        final Anchor anchor,
        final Vector direction,
        final float durationMs,
        final Color textColor
    ) {
        super(anchor);
        this.direction = direction;
        this.durationMs = durationMs;
        this.textColor = textColor;
    }

    private final WidgetState state = new WidgetState();

    private final class WidgetState {
        boolean isFloating = false;
        float remainingTime = 0;
        float offsX = 0;
        float offsY = 0;

        void tick(float deltaTime) {
            if (!isFloating) {
                return;
            }
            remainingTime -= deltaTime;
            if (remainingTime <= 0) {
                isFloating = false;
                setRemoving(true);
            }

            final Vector delta = direction.multiply(moveSpeed * deltaTime);
            state.offsX += delta.x;
            state.offsY += delta.y;
        }
    }

    private boolean started = false;

    public void beginFloating(final String text) {
        if (started) {
            return;
        }
        this.text = text;

        state.isFloating = true;
        state.offsX = 0;
        state.offsY = 0;
        state.remainingTime = durationMs;
        started = true;
    }

    final float moveSpeed = 200f;

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        state.tick(eventArgs.getDeltaTime());

        if (state.isFloating) {
            final Box pos = screenBounds.plus(new Vector(
                state.offsX, state.offsY));

            renderContext.renderString(
                text,
                textColor,
                pos.left,
                pos.top,
                RenderLayer.GUI);
        }
    }
}
