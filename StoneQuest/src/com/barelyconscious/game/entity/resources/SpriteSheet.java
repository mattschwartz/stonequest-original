package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.GameRunner;
import com.barelyconscious.game.exception.MissingResourceException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * An image that contains within it multiple different sprites.
 */
public class SpriteSheet {

    private Map<SpriteResource, BufferedImage> spriteImagesByResource;
    private final BufferedImage spriteSheetImage;

    public SpriteSheet withSprite(final SpriteResource spriteResource) {
        if (spriteImagesByResource.containsKey(spriteResource)) {
            throw new IllegalArgumentException("The sprite " + spriteResource.getName() + " has already been loaded.");
        }

        final Region bounds = spriteResource.getRegion();
        final BufferedImage spriteImage = this.spriteSheetImage.getSubimage(
            bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

        spriteImagesByResource.put(spriteResource, spriteImage);

        return this;
    }

    public WSprite getSpriteFromSheet(final SpriteResource spriteResourceSprite) {
        final BufferedImage spriteImg =  spriteImagesByResource.get(spriteResourceSprite);

        return new WSprite(spriteImg, spriteResourceSprite.getRenderLayer(),
            spriteResourceSprite.getRegion().getWidth(), spriteResourceSprite.getRegion().getHeight());
    }

    public static SpriteSheet createSpriteSheet(final String sheetFilepath) {
        final BufferedImage sheet;
        try {
            final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
                    .getResource(sheetFilepath))
                .openStream();
            sheet = ImageIO.read(inputStream);
        } catch (final Exception e) {
            throw new MissingResourceException("Failed to load resource: " + sheetFilepath, e);
        }

        return new SpriteSheet(sheet);
    }

    private SpriteSheet(final BufferedImage spriteSheetImage) {
        checkArgument(spriteSheetImage != null, "spriteSheetImage is null");
        this.spriteSheetImage = spriteSheetImage;
        this.spriteImagesByResource = new HashMap<>();
    }
}
