package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Sprite;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.common.UMath;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * A component that can render Sprites to a Screen.
 */
@Log4j2
public class SpriteComponent extends Component {

    @NonNull
    public RenderLayer renderLayer;

    @Getter
    private double opacity = 1;

    public final Sprite sprite;
    private final BetterSpriteResource spriteResource;
    private final int width;
    private final int height;

    public SpriteComponent(final Actor parent, final BetterSpriteResource spriteResource, final int width, final int height) {
        super(parent);

        this.spriteResource = spriteResource;
        this.sprite = null;
        this.renderLayer = RenderLayer.GROUND;
        this.width = width;
        this.height = height;
    }

    public SpriteComponent(final Actor parent, final BetterSpriteResource spriteResource) {
        this(parent, spriteResource, spriteResource.getWidth(), spriteResource.getHeight());
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
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    public void setOpacity(double opacity) {
        this.opacity = UMath.clamp(opacity, 0, 1);
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
            renderContext.render(RenderContext.RenderRequest.builder()
                .spriteResource(spriteResource)
                .width(width).height(height)
                .worldX((int) position.x).worldY((int) position.y)
                .renderOpacity(1)
                .renderLayer(renderLayer)
                .build());
            // also render to the accumulated light so this sprite still shows up as "seen"
            // todo - sprites that have been recently seen but are not currently visible should be rendered at the
            //  last place they were seen
            renderContext.render(RenderContext.RenderRequest.builder()
                .spriteResource(spriteResource)
                .width(width).height(height)
                .worldX((int) position.x).worldY((int) position.y)
                .renderOpacity(opacity)
                .renderLayer(RenderLayer.LIGHTMAP)
                .build());
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
