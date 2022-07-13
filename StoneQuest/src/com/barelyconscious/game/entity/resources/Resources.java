package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.GameRunner;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.entity.resources.spritesheet.SpritesheetManager;
import com.barelyconscious.game.exception.MissingResourceException;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Image;
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

    public static final Sprite_Resource ITEM_HEMATITE_ORE = new Sprite_Resource("items::hematite_ore");
    public static final Sprite_Resource ITEM_BLOODY_SKULL = new Sprite_Resource("items::bloody_skull");
    public static final Sprite_Resource ITEM_BLUE_WIZARDS_HAT = new Sprite_Resource("items::blue_wizards_hat");
    public static final Sprite_Resource ITEM_BOLT_OF_CLOTH = new Sprite_Resource("items::bolt_of_cloth");
    public static final Sprite_Resource ITEM_CHAMOMILE_FLOWER = new Sprite_Resource("items::chamomile_flower");
    public static final Sprite_Resource ITEM_CLOTH = new Sprite_Resource("items::cloth");
    public static final Sprite_Resource ITEM_CURED_LEATHER = new Sprite_Resource("items::cured_leather");
    public static final Sprite_Resource ITEM_DIGITAL_IMAGE_CORRUPT = new Sprite_Resource("items::digital_image_corrupt");
    public static final Sprite_Resource ITEM_ELDRITCH_CIRCUIT = new Sprite_Resource("items::eldritch_circuit");
    public static final Sprite_Resource ITEM_ELDRITCH_DRIVE = new Sprite_Resource("items::eldritch_drive");
    public static final Sprite_Resource ITEM_GLOWING_ESSENCE = new Sprite_Resource("items::glowing_essence");
    public static final Sprite_Resource ITEM_GNARLED_BRANCH = new Sprite_Resource("items::gnarled_branch");
    public static final Sprite_Resource ITEM_GOLD_COIN = new Sprite_Resource("items::gold_coin");
    public static final Sprite_Resource ITEM_HEADSTONE = new Sprite_Resource("items::headstone");
    public static final Sprite_Resource ITEM_BLUE_ROBE = new Sprite_Resource("items::blue_robe");
    public static final Sprite_Resource ITEM_HERBS_BUNDLE = new Sprite_Resource("items::herbs_bundle");
    public static final Sprite_Resource ITEM_IRON_OXIDE_DUST = new Sprite_Resource("items::iron_oxide_dust");
    public static final Sprite_Resource ITEM_POTION_BLUE = new Sprite_Resource("items::potion_blue");
    public static final Sprite_Resource ITEM_POTION_ELDRITCH_LARGE = new Sprite_Resource("items::potion_eldritch_large");
    public static final Sprite_Resource ITEM_POTION_ELDRITCH_MEDIUM = new Sprite_Resource("items::potion_eldritch_medium");
    public static final Sprite_Resource ITEM_POTION_ELDRITCH_SMALL = new Sprite_Resource("items::potion_eldritch_small");
    public static final Sprite_Resource ITEM_POTION_RED = new Sprite_Resource("items::potion_red");
    public static final Sprite_Resource ITEM_RECIPE = new Sprite_Resource("items::recipe");
    public static final Sprite_Resource ITEM_RECOVERED_DIGITAL_IMAGE = new Sprite_Resource("items::recovered_digital_image");
    public static final Sprite_Resource ITEM_SCYTHE = new Sprite_Resource("items::scythe");
    public static final Sprite_Resource ITEM_SHIELD = new Sprite_Resource("items::shield");
    public static final Sprite_Resource ITEM_TEXT_FILE_RECOVERED = new Sprite_Resource("items::text_file_recovered");
    public static final Sprite_Resource ITEM_WHEEL_OF_CHEESE = new Sprite_Resource("items::wheel_of_cheese");


    @AllArgsConstructor
    public static class Sprite_Resource {
        private final String resourceName;

        public int getWidth() {
            return load().getWidth();
        }

        public int getHeight() {
            return load().getHeight();
        }

        public WSprite load() {
            return SpritesheetManager.SPRITE_MAP.get(resourceName);
        }
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
        } else if (resource instanceof ItemsSpriteSheet.Resources) {
            sprite = itemsSpriteSheet.getSpriteFromSheet(resource);
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
}
