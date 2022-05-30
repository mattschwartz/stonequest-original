package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.RenderContext;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.shape.Vector;

/**
 * A component that can render Sprites to a Screen.
 */
public final class SpriteComponent extends Component {

    public final Sprite sprite;

    public SpriteComponent(
        final Actor parent,
        final Sprite sprite
    ) {
        super(parent);
        this.sprite = sprite;
    }

    @Override
    public void render(final RenderContext renderContext) {
        final Vector position = getParent().transform;

        renderContext.render(
            sprite.image,
            (int) position.x,
            (int) position.y,
            sprite.width,
            sprite.height);
    }
}
