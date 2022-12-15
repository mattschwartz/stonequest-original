package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.graphics.RenderContext;
import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.shape.Vector;
import com.barelyconscious.worlds.util.UMath;
import lombok.extern.log4j.Log4j2;

import java.awt.*;

@Log4j2
public class HealthBarComponent extends Component {

    private final int xOffs;
    private final int yOffs;
    private final int width;
    private final int height;

    private final boolean hideWhenFull;

    private float progress = 0;

    /**
     * creates a standard health bar component that appears under entities.
     */
    public HealthBarComponent(final Actor parent, final AdjustableValueComponent stat) {
        this(parent, 0, 34, 32, 4, stat, true);
    }

    public HealthBarComponent(
        final Actor parent,
        final int xOffs,
        final int yOffs,
        final int width,
        final int height,
        final AdjustableValueComponent stat,
        final boolean hideWhenFull
    ) {
        super(parent);
        this.xOffs = xOffs;
        this.yOffs = yOffs;
        this.width = width;
        this.height = height;
        this.hideWhenFull = hideWhenFull;

        this.progress = UMath.clampf(stat.getCurrentValue() / stat.getMaxValue(), 0, 1);
        stat.delegateOnValueChanged.bindDelegate(e -> {
            progress = UMath.clampf(e.currentValue / e.maxValue, 0, 1);
            return null;
        });
    }

    @Override
    public void render(final EventArgs eventArgs, final RenderContext renderContext) {
        float healthRemainingWidth = width * progress;

        final Vector position = getParent().transform;
        if (progress != 1) {
            renderContext.renderRect(
                Color.BLACK,
                true,
                (int) position.x + xOffs,
                (int) position.y + yOffs,
                width,
                height,
                RenderLayer.ENTITIES);
        }

        if (healthRemainingWidth >= 1f) {
            renderContext.renderRect(
                Color.GREEN,
                true,
                (int) position.x + xOffs,
                (int) position.y + yOffs,
                (int) healthRemainingWidth,
                height,
                RenderLayer.ENTITIES
            );
        }
    }
}
