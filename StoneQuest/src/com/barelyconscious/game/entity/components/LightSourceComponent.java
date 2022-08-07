package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.shape.Box;
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
            final int worldX = (int) (getParent().transform.x - (radius - 16));
            final int worldY = (int) (getParent().transform.y - (radius - 16));

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
