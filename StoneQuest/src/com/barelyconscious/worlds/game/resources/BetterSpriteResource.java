package com.barelyconscious.worlds.game.resources;

import com.barelyconscious.worlds.game.resources.spritesheet.SpritesheetManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.awt.image.BufferedImage;

/**
 * A wrapper class for a sprite resource that provides a more convenient way to
 * access the sprite's properties.
 */
@Log4j2
@AllArgsConstructor
public class BetterSpriteResource {
    private final String resourceName;

    public int getWidth() {
        return load().getWidth();
    }

    public BufferedImage getTexture() {
        return load().getTexture();
    }

    public int getHeight() {
        return load().getHeight();
    }

    public WSprite load() {
        var res = SpritesheetManager.SPRITE_MAP.get(resourceName);
        if (res == null) {
            log.error("Could not find sprite resource: " + resourceName);
        }
        return res;
    }
}
