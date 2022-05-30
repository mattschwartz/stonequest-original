package com.barelyconscious.game.entity.graphics;

/**
 * The layer onto which the component will be rendered.
 *
 * Layers are rendered in ascending order ([0, 1, 2, ...]); components with a lower render layer will
 * be rendered first then followed by components with a higher render layer.
 *
 * Components on the same layer rendering at the same location will always cause the component added
 * last to render above all previous components.
 */
public enum RenderLayer {

    GROUND(0),
    DOODADS(1),
    ENTITIES(2),
    SKY(100),

    _DEBUG(Integer.MAX_VALUE);

    public final int zLevel;
    RenderLayer(final int zLevel) {
        this.zLevel = zLevel;
    }
}
