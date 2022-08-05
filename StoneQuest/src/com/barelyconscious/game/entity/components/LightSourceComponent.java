package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

public class LightSourceComponent extends Component {

    private final int width;
    private final int height;
    @Getter
    @Setter
    private float opacity;

    public LightSourceComponent(Actor parent, int width, int height) {
        super(parent);
        this.width = width;
        this.height = height;

        opacity = 1;
    }

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        {
            final int worldX = (int) (getParent().transform.x - (width - 32) / 2);
            final int worldY = (int) (getParent().transform.y - (height - 32) / 2);

            renderContext.renderRect(
                new Color(255, 255, 255, (int) (255 * opacity)),
                true,
                worldX, worldY,
                width, height,
                RenderLayer.LIGHTMAP
            );
        }
    }
}
