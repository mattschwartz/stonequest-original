package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.shape.Vector;
import com.barelyconscious.util.UMath;
import lombok.extern.log4j.Log4j2;

import java.awt.*;

@Log4j2
public class HealthBarComponent extends Component {

    private final int xOffs;
    private final int yOffs;
    private final int width;
    private final int height;

    private final boolean hideWhenFull;

    /**
     * creates a standard health bar component that appears under entities.
     */
    public HealthBarComponent(final Actor parent) {
        this(parent, 0, 34, 32, 4, true);
    }

    public HealthBarComponent(
        final Actor parent,
        final int xOffs,
        final int yOffs,
        final int width,
        final int height,
        final boolean hideWhenFull
    ) {
        super(parent);
        this.xOffs = xOffs;
        this.yOffs = yOffs;
        this.width = width;
        this.height = height;
        this.hideWhenFull = hideWhenFull;
    }

    @Override
    public void render(final EventArgs eventArgs, final RenderContext renderContext) {
        final HealthComponent health = getParent().getComponent(HealthComponent.class);
        if (health == null) {
            log.warn("Actor=" + getParent() + " does not have health component!");
            return;
        }

        float p = UMath.clampf(health.getCurrentValue() / health.getMaxValue(), 0, 1);
        float healthRemainingWidth = width * p;

        final Vector position = getParent().transform;
        if (p != 1) {
            renderContext.renderRect(
                Color.BLACK,
                true,
                (int) position.x + xOffs,
                (int) position.y + yOffs,
                width,
                height,
                RenderLayer.GUI);
        }

        if (healthRemainingWidth >= 1f) {
            renderContext.renderRect(
                Color.GREEN,
                true,
                (int) position.x + xOffs,
                (int) position.y + yOffs,
                (int) healthRemainingWidth,
                height,
                RenderLayer.GUI
            );
        }
    }
}
