package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.shape.Vector;

/**
 * A component that can render Sprites to a Screen.
 */
public final class SpriteComponent extends Component {

    public RenderLayer renderLayer;

    public final Sprite sprite;

    /**
     * Defaults to RenderLayer.GROUND.
     */
    public SpriteComponent(final Actor parent, final Sprite sprite) {
        this(parent, sprite, RenderLayer.GROUND);
    }

    /**
     * @param parent      the parent actor
     * @param sprite      the sprite to render to the screen
     * @param renderLayer the layer onto which this Sprite will be rendered
     */
    public SpriteComponent(
        final Actor parent,
        final Sprite sprite,
        final RenderLayer renderLayer
    ) {
        super(parent);
        this.sprite = sprite;
        this.renderLayer = renderLayer;
    }

    @Override
    public void render(final RenderContext renderContext) {
        final Vector position = getParent().transform;

        renderContext.render(
            sprite.image,
            (int) position.x,
            (int) position.y,
            sprite.width,
            sprite.height,
            renderLayer);
    }
}
