package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

/**
 * A component that can render Sprites to a Screen.
 */
public class SpriteComponent extends Component {

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

    public Box getBounds() {
        final Vector worldPos = getParent().transform;
        return new Box(
            (int) worldPos.x, (int) worldPos.x + sprite.getWidth(),
            (int) worldPos.y, (int) worldPos.y + sprite.getWidth());
    }

    @Override
    public void render(final EventArgs eventArgs, final RenderContext renderContext) {
        final Vector position = getParent().transform;

        renderContext.render(
            sprite.getTexture(),
            (int) position.x,
            (int) position.y,
            sprite.getWidth(),
            sprite.getHeight(),
            renderLayer);
    }
}
