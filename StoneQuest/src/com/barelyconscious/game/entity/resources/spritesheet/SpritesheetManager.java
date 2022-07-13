package com.barelyconscious.game.entity.resources.spritesheet;

import com.barelyconscious.game.GameRunner;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.resources.WSprite;
import com.barelyconscious.game.exception.MissingResourceException;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpritesheetManager {

    @AllArgsConstructor
    private static class Frame {
        private final int x;
        private final int y;
        private final int w;
        private final int h;
    }

    @AllArgsConstructor
    private static class SpriteSourceSize {
        private final int x;
        private final int y;
        private final int w;
        private final int h;
    }

    @AllArgsConstructor
    private static class SourceSize {
        private final int w;
        private final int h;
    }

    @AllArgsConstructor
    private static class Pivot {
        private final double x;
        private final double y;
    }

    @AllArgsConstructor
    private static class SourceSprite {
        private final String filename;
        private final Frame frame;
        private final boolean rotated;
        private final boolean trimmed;
        private final SpriteSourceSize spriteSourceSize;
        private final SourceSize sourceSize;
        private final Pivot pivot;
    }

    public static final Map<String, WSprite> SPRITE_MAP = new HashMap<>();

    public static void loadItemsSpritesheet() {
        final String resourceFilepath = "sprites/items_spritesheet.json";
        final String spritesheetFilepath = "sprites/items_spritesheet.png";

        final SourceSprite[] sourceSprites = readSpritesheetDefinitions(resourceFilepath);

        final BufferedImage spritesheet = loadSpritesheet(spritesheetFilepath);

        for (SourceSprite sourceSprite : sourceSprites) {
            final String spriteName = sourceSprite.filename;
            final Frame frame = sourceSprite.frame;

            final BufferedImage spriteImage = spritesheet.getSubimage(
                frame.x, frame.y, frame.w, frame.h);
            SPRITE_MAP.put(spriteName, new WSprite(spriteImage, RenderLayer.GUI,
                sourceSprite.sourceSize.w, sourceSprite.sourceSize.h));
        }
    }

    private static BufferedImage loadSpritesheet(final String spritesheetFilepath) {
        final BufferedImage sheet;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(spritesheetFilepath))
                .openStream();
            sheet = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + spritesheetFilepath, e);
        }
        return sheet;
    }

    @NonNull
    private static SourceSprite[] readSpritesheetDefinitions(String resourceFilepath) {
        final Gson gson = new Gson();

        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(resourceFilepath))
                .openStream();

            final String json = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

            return gson.fromJson(json, SourceSprite[].class);
        } catch (IOException e) {
            throw new MissingResourceException(String.format("Failed to load spritesheet definition '%s'",
                resourceFilepath),
                e);
        }
    }
}
