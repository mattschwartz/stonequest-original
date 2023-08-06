package com.barelyconscious.worlds.game.resources;

import com.barelyconscious.worlds.game.resources.spritesheet.SpritesheetManager;
import lombok.AllArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * todo is new way?
 */
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
        return SpritesheetManager.SPRITE_MAP.get(resourceName);
    }
}
