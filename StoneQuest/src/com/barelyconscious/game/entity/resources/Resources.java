package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.GameRunner;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.exception.MissingResourceException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Resources {

    private static final Map<ResourceSprite, Sprite> loadedSprites = new HashMap<>();

    public static Sprite createSprite(final ResourceSprite res, final int width, final int height) {

        final Image spriteImage;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(res.filepath))
                .openStream();
            spriteImage = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load res: " + res, e);
        }

        return new Sprite(spriteImage, width, height);
    }

    public static Sprite getSprite(final ResourceSprite resource) {
        if (loadedSprites.containsKey(resource)) {
            return loadedSprites.get(resource);
        }

        final Image spriteImage;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(resource.filepath))
                .openStream();
            spriteImage = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + resource, e);
        }

        final Sprite sprite = new Sprite(spriteImage, resource.width, resource.height);

        loadedSprites.put(resource, sprite);

        return sprite;
    }

    private Resources(){}
}
