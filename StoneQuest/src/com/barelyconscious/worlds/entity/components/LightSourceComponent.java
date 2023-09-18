package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.common.shape.Box;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

public class LightSourceComponent extends BoxColliderComponent {

    private final int radius;
    @Getter
    @Setter
    private float opacity;

    public LightSourceComponent(Actor parent, int radius) {
        super(parent, false, true, new Box(-(radius-16), radius*2, -(radius-16), radius*2));
        this.radius = radius;

        opacity = 1;
    }

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        {
            final int worldX = (int) (getParent().getTransform().x - (radius - 16));
            final int worldY = (int) (getParent().getTransform().y - (radius - 16));

            renderContext.renderCircle(
                new Color(255, 255, 255, (int) (255 * opacity)),
                true,
                worldX, worldY,
                radius,
                RenderLayer.LIGHTMAP
            );
        }
    }
}
