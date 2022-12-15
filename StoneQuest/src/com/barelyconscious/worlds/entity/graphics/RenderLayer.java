package com.barelyconscious.worlds.entity.graphics;

import com.google.common.collect.Lists;

import java.util.List;

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
    LOOT(2),
    ENTITIES(3),
    SKY(100),

    /**
     * Applies lighting to the scenes rendered on layers before it.
     */
    LIGHTMAP(400),
    GUI(1000),
    GUI_FOCUS(1001),

    _DEBUG(Integer.MAX_VALUE);

    public static List<RenderLayer> layersToBeLit() {
        return Lists.newArrayList(GROUND, DOODADS, LOOT, ENTITIES, SKY);
    }

    public static List<RenderLayer> layersAboveLight() {
        return Lists.newArrayList(GUI, GUI_FOCUS, _DEBUG);
    }

    public final int zLevel;
    RenderLayer(final int zLevel) {
        this.zLevel = zLevel;
    }
}
