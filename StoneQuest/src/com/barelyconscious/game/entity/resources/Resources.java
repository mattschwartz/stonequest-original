package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.GameRunner;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.exception.MissingResourceException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Resources {

    private static final Map<ResourceSprite, Sprite> loadedSprites = new HashMap<>();

    private static WSprite create(
        final String filepath,
        final RenderLayer layer,
        final int width,
        final int height
    ) {
        final Image spriteImage;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(filepath))
                .openStream();
            spriteImage = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + filepath, e);
        }

        return new WSprite(spriteImage, layer, width, height);
    }

    private static Sprite loadSprite(
        final String filepath,
        final int width,
        final int height
    ) {
        final Image spriteImage;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(filepath))
                .openStream();
            spriteImage = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + filepath, e);
        }

        return new Sprite(spriteImage, width, height);
    }

    public static Sprite createGuiSprite(final ResourceGUI res, final int width, final int height) {
        return loadSprite(res.filepath, width, height);
    }

    public static Sprite createSprite(final ResourceSprite res, final int width, final int height) {
        return loadSprite(res.filepath, width, height);
    }

    public static Sprite getSprite(final ResourceSprite resource) {
        if (loadedSprites.containsKey(resource)) {
            return loadedSprites.get(resource);
        }

        final Sprite sprite = loadSprite(
            resource.filepath, resource.width, resource.height);

        loadedSprites.put(resource, sprite);

        return sprite;
    }

    private Resources(){}
}
