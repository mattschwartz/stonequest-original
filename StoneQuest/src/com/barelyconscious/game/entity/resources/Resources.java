package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.Sprite;

import java.util.HashMap;
import java.util.Map;

public final class Resources {

    private static final Map<ResourceSprites, Sprite> loadedSprites = new HashMap<>();

    public static Sprite loadSprite(final ResourceSprites resource) {
        if (loadedSprites.containsKey(resource)) {
            return loadedSprites.get(resource);
        }

        final Sprite sprite = new Sprite(null, 0, 0);

        loadedSprites.put(resource, sprite);

        return sprite;
    }

    private Resources(){}
}
