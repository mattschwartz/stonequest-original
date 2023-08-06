package com.barelyconscious.worlds.game.resources;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.entity.Sprite;
import com.barelyconscious.worlds.game.resources.spritesheet.*;
import com.barelyconscious.worlds.common.exception.MissingResourceException;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Resources {

    private static final class InstanceHolder {
        static final Resources instance = new Resources();
    }

    public static Resources instance() {
        return InstanceHolder.instance;
    }

    private final SpriteSheet guiSpriteSheet;
    private final SpriteSheet itemsSpriteSheet;
    private final SpriteSheet craftingWindowSpriteSheet;

    private Resources() {
        this.guiSpriteSheet = GUISpriteSheet.createGuiSpriteSheet();
        this.itemsSpriteSheet = ItemsSpriteSheet.createItemSpriteSheet();
        this.craftingWindowSpriteSheet = CraftingWindowSpriteSheet.createCraftingWindowSpriteSheet();
    }

    public WSprite getSprite(final SpriteResource resource) {
        WSprite sprite = null;

        if (resource instanceof GUISpriteSheet.Resources) {
            sprite = guiSpriteSheet.getSpriteFromSheet(resource);
        } else if (resource instanceof CraftingWindowSpriteSheet.Resources) {
            sprite = craftingWindowSpriteSheet.getSpriteFromSheet(resource);
        }

        if (sprite == null) {
            throw new MissingResourceException("Failed to load resource: " + resource);
        } else {
            return sprite;
        }
    }

    private static final Map<ResourceSprite, Sprite> loadedSprites = new HashMap<>();
    private static final Map<FontResource, Font> fonts = new HashMap<>();

    public static Font loadFont(FontResource resource) {
        if (fonts.containsKey(resource)) {
            return fonts.get(resource);
        }

        try {
            Font font = Font.createFont(resource.fontFormat, Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(resource.filepath))
                .openStream());
            font = font.deriveFont(resource.defaultFontSize);
            fonts.put(resource, font);

            return font;
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load font: " + resource);
        }
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

    public static Sprite getSprite(final ResourceSprite resource) {
        if (loadedSprites.containsKey(resource)) {
            return loadedSprites.get(resource);
        }

        final Sprite sprite = loadSprite(
            resource.filepath, resource.width, resource.height);

        loadedSprites.put(resource, sprite);

        return sprite;
    }
}
