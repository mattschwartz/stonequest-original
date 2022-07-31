package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * A component that can render Sprites to a Screen.
 */
@Log4j2
public class SpriteComponent extends Component {

    @NonNull
    public RenderLayer renderLayer;

    public final Sprite sprite;
    private final Resources.Sprite_Resource spriteResource;

    public SpriteComponent(final Actor parent, final Resources.Sprite_Resource spriteResource) {
        super(parent);
        this.spriteResource = spriteResource;
        this.sprite = null;
        this.renderLayer = RenderLayer.GROUND;
    }

    /**
     * Defaults to RenderLayer.GROUND.
     */
    @Deprecated
    public SpriteComponent(final Actor parent, final Sprite sprite) {
        this(parent, sprite, RenderLayer.GROUND);
    }

    /**
     * @param parent      the parent actor
     * @param sprite      the sprite to render to the screen
     * @param renderLayer the layer onto which this Sprite will be rendered
     */
    @Deprecated
    public SpriteComponent(
        final Actor parent,
        final Sprite sprite,
        final RenderLayer renderLayer
    ) {
        super(parent);
        this.sprite = sprite;
        this.renderLayer = renderLayer;
        this.spriteResource = null;
    }

    public Box getBounds() {
        final Vector worldPos = getParent().transform;

        if (spriteResource != null) {
            return new Box(
                (int) worldPos.x, (int) worldPos.x + spriteResource.getWidth(),
                (int) worldPos.y, (int) worldPos.y + spriteResource.getWidth());
        } else {
            // deprecated
            return new Box(
                (int) worldPos.x, (int) worldPos.x + sprite.getWidth(),
                (int) worldPos.y, (int) worldPos.y + sprite.getWidth());
        }
    }

    @Override
    public void render(final EventArgs eventArgs, final RenderContext renderContext) {
        final Vector position = getParent().transform;

        if (spriteResource != null) {
            renderContext.render(
                spriteResource.getTexture(),
                (int) position.x,
                (int) position.y,
                spriteResource.getWidth(),
                spriteResource.getHeight(),
                renderLayer);
        } else {
            // deprecated
            renderContext.render(
                sprite.getTexture(),
                (int) position.x,
                (int) position.y,
                sprite.getWidth(),
                sprite.getHeight(),
                renderLayer);
        }
    }
}
