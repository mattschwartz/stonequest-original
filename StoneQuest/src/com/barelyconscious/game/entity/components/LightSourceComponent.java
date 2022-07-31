package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;

import java.awt.Color;

public class LightSourceComponent extends Component {

    private final int width;
    private final int height;

    public LightSourceComponent(Actor parent, int width, int height) {
        super(parent);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        {
            final int worldX = (int) (getParent().transform.x - width / 2);
            final int worldY = (int) (getParent().transform.y - height / 2);

            renderContext.renderRect(
                Color.white,
                true,
                worldX, worldY,
                width, height,
                RenderLayer.LIGHTMAP
            );
        }
        {
            final int width = this.width * 2;
            final int height = this.height * 2;
            final int worldX = (int) (getParent().transform.x - width / 2);
            final int worldY = (int) (getParent().transform.y - height / 2);

            renderContext.renderRect(
                new Color(255, 255, 255, 64),
                true,
                worldX, worldY,
                width, height,
                RenderLayer.LIGHTMAP
            );
        }
    }
}
