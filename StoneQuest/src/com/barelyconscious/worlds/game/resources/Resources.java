package com.barelyconscious.worlds.game.resources;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.entity.Sprite;
import com.barelyconscious.worlds.game.resources.spritesheet.SpritesheetManager;
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
    public static final Sprite_Resource ITEM_RECURVE_BOW = new Sprite_Resource("items::recurve_bow");
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

    public static final Sprite_Resource TEX_MYCELIUM = new Sprite_Resource("texture::mycelium");
    public static final Sprite_Resource TEX_ADAMANTINEVEIN_512 = new Sprite_Resource("texture::adamantinevein_512");
    public static final Sprite_Resource TEX_CAVE512 = new Sprite_Resource("texture::cave512");
    public static final Sprite_Resource TEX_CAVE_BRICK = new Sprite_Resource("texture::cave_brick");
    public static final Sprite_Resource TEX_CAVE_BRICK_U = new Sprite_Resource("texture::cave_brick_u");
    public static final Sprite_Resource TEX_CAVE_MARBLE = new Sprite_Resource("texture::cave_marble");
    public static final Sprite_Resource TEX_CAVE_MARBLE_U = new Sprite_Resource("texture::cave_marble_u");
    public static final Sprite_Resource TEX_CAVE_RENDERED = new Sprite_Resource("texture::cave_rendered");
    public static final Sprite_Resource TEX_CAVE_ROUNDED = new Sprite_Resource("texture::cave_rounded");
    public static final Sprite_Resource TEX_CAVE_ROUNDED_U = new Sprite_Resource("texture::cave_rounded_u");
    public static final Sprite_Resource TEX_CAVE_SANDSTONE_U = new Sprite_Resource("texture::cave_sandstone_u");
    public static final Sprite_Resource TEX_CAVE_SLATE = new Sprite_Resource("texture::cave_slate");
    public static final Sprite_Resource TEX_CAVE_SLATE_U = new Sprite_Resource("texture::cave_slate_u");
    public static final Sprite_Resource TEX_CAVE_STONEHOUSE = new Sprite_Resource("texture::cave_stonehouse");
    public static final Sprite_Resource TEX_CAVE_STONEHOUSE_U = new Sprite_Resource("texture::cave_stonehouse_u");
    public static final Sprite_Resource TEX_CAVE_WOOD = new Sprite_Resource("texture::cave_wood");
    public static final Sprite_Resource TEX_CAVE_WOOD_U = new Sprite_Resource("texture::cave_wood_u");
    public static final Sprite_Resource TEX_CLAY = new Sprite_Resource("texture::clay");
    public static final Sprite_Resource TEX_CLIFF = new Sprite_Resource("texture::cliff");
    public static final Sprite_Resource TEX_COBBLE = new Sprite_Resource("texture::cobble");
    public static final Sprite_Resource TEX_COBBLE2 = new Sprite_Resource("texture::cobble2");
    public static final Sprite_Resource TEX_COBBLE3 = new Sprite_Resource("texture::cobble3");
    public static final Sprite_Resource TEX_COPPERVEIN_512 = new Sprite_Resource("texture::coppervein_512");
    public static final Sprite_Resource TEX_DIRT = new Sprite_Resource("texture::dirt");
    public static final Sprite_Resource TEX_ENCHANTEDFOREST = new Sprite_Resource("texture::enchantedforest");
    public static final Sprite_Resource TEX_ENCHANTEDGRASS = new Sprite_Resource("texture::enchantedgrass");
    public static final Sprite_Resource TEX_FALLDIRT = new Sprite_Resource("texture::falldirt");
    public static final Sprite_Resource TEX_FALLGRASS = new Sprite_Resource("texture::fallgrass");
    public static final Sprite_Resource TEX_FALLGRAVEL = new Sprite_Resource("texture::fallgravel");
    public static final Sprite_Resource TEX_FALLPACKED = new Sprite_Resource("texture::fallpacked");
    public static final Sprite_Resource TEX_FALLROCK = new Sprite_Resource("texture::fallrock");
    public static final Sprite_Resource TEX_FALLSAND = new Sprite_Resource("texture::fallsand");
    public static final Sprite_Resource TEX_FARM = new Sprite_Resource("texture::farm");
    public static final Sprite_Resource TEX_WINTERSNOW3 = new Sprite_Resource("texture::wintersnow3");
    public static final Sprite_Resource TEX_FLOORBOARDTARRED = new Sprite_Resource("texture::floorBoardTarred");
    public static final Sprite_Resource TEX_FLOOR_PREPARED = new Sprite_Resource("texture::floor_prepared");
    public static final Sprite_Resource TEX_FOREST_MYCELIUM = new Sprite_Resource("texture::forest-Mycelium");
    public static final Sprite_Resource TEX_FOREST = new Sprite_Resource("texture::forest");
    public static final Sprite_Resource TEX_GLIMMERSTEELVEIN_512 = new Sprite_Resource("texture::glimmersteelvein_512");
    public static final Sprite_Resource TEX_GOLDVEIN_512 = new Sprite_Resource("texture::goldvein_512");
    public static final Sprite_Resource TEX_GRASS = new Sprite_Resource("texture::grass");
    public static final Sprite_Resource TEX_GRAVEL = new Sprite_Resource("texture::gravel");
    public static final Sprite_Resource TEX_IRONVEIN_512 = new Sprite_Resource("texture::ironvein_512");
    public static final Sprite_Resource TEX_LAVA = new Sprite_Resource("texture::lava");
    public static final Sprite_Resource TEX_LAWN = new Sprite_Resource("texture::lawn");
    public static final Sprite_Resource TEX_LEADVEIN_512 = new Sprite_Resource("texture::leadvein_512");
    public static final Sprite_Resource TEX_MARBLEBRICKS = new Sprite_Resource("texture::marbleBricks");
    public static final Sprite_Resource TEX_MARBLESLAB = new Sprite_Resource("texture::marbleslab");
    public static final Sprite_Resource TEX_MARBLEVEIN_512 = new Sprite_Resource("texture::marblevein_512");
    public static final Sprite_Resource TEX_MARSH = new Sprite_Resource("texture::marsh");
    public static final Sprite_Resource TEX_MOSS = new Sprite_Resource("texture::moss");
    public static final Sprite_Resource TEX_BRIDGEPREPARED = new Sprite_Resource("texture::bridgePrepared");
    public static final Sprite_Resource TEX_PACKED = new Sprite_Resource("texture::packed");
    public static final Sprite_Resource TEX_PEAT = new Sprite_Resource("texture::peat");
    public static final Sprite_Resource TEX_PLANKS = new Sprite_Resource("texture::planks");
    public static final Sprite_Resource TEX_POTTERYBRICKPAVING = new Sprite_Resource("texture::potterybrickpaving");
    public static final Sprite_Resource TEX_REED = new Sprite_Resource("texture::reed");
    public static final Sprite_Resource TEX_REINFORCEDCAVEFLOOR_V1 = new Sprite_Resource("texture::reinforcedcaveFloor_v1");
    public static final Sprite_Resource TEX_REINFORCEDCAVE_V1 = new Sprite_Resource("texture::reinforcedcave_v1");
    public static final Sprite_Resource TEX_ROCK = new Sprite_Resource("texture::rock");
    public static final Sprite_Resource TEX_ROCKSALT = new Sprite_Resource("texture::rocksalt");
    public static final Sprite_Resource TEX_SAND = new Sprite_Resource("texture::sand");
    public static final Sprite_Resource TEX_SANDSTONEBRICK = new Sprite_Resource("texture::sandstonebrick");
    public static final Sprite_Resource TEX_SANDSTONESLAB = new Sprite_Resource("texture::sandstoneslab");
    public static final Sprite_Resource TEX_SANDSTONEVEIN = new Sprite_Resource("texture::sandstonevein");
    public static final Sprite_Resource TEX_SILVERVEIN_512 = new Sprite_Resource("texture::silvervein_512");
    public static final Sprite_Resource TEX_SLAB = new Sprite_Resource("texture::slab");
    public static final Sprite_Resource TEX_SLATETILES = new Sprite_Resource("texture::slateTiles");
    public static final Sprite_Resource TEX_SLATEBRICKS = new Sprite_Resource("texture::slatebricks");
    public static final Sprite_Resource TEX_SLATEVEIN_512 = new Sprite_Resource("texture::slatevein_512");
    public static final Sprite_Resource TEX_STEPPE = new Sprite_Resource("texture::steppe");
    public static final Sprite_Resource TEX_TAR = new Sprite_Resource("texture::tar");
    public static final Sprite_Resource TEX_TINVEIN_512 = new Sprite_Resource("texture::tinvein_512");
    public static final Sprite_Resource TEX_TUNDRA = new Sprite_Resource("texture::tundra");
    public static final Sprite_Resource TEX_WINTERSNOW1 = new Sprite_Resource("texture::wintersnow1");
    public static final Sprite_Resource TEX_WINTERSLATEBRICKS = new Sprite_Resource("texture::winterslatebricks");
    public static final Sprite_Resource TEX_WINTERCLIFF = new Sprite_Resource("texture::wintercliff");
    public static final Sprite_Resource TEX_WINTERCOBBLE = new Sprite_Resource("texture::wintercobble");
    public static final Sprite_Resource TEX_WINTERCOBBLE2 = new Sprite_Resource("texture::wintercobble2");
    public static final Sprite_Resource TEX_WINTERCOBBLE3 = new Sprite_Resource("texture::wintercobble3");
    public static final Sprite_Resource TEX_WINTERDIRT = new Sprite_Resource("texture::winterdirt");
    public static final Sprite_Resource TEX_WINTERFARM = new Sprite_Resource("texture::winterfarm");
    public static final Sprite_Resource TEX_WINTERGRAVEL = new Sprite_Resource("texture::wintergravel");
    public static final Sprite_Resource TEX_WINTERMARBLEBRICKS = new Sprite_Resource("texture::wintermarblebricks");
    public static final Sprite_Resource TEX_WINTERMARBLESLAB = new Sprite_Resource("texture::wintermarbleslab");
    public static final Sprite_Resource TEX_WINTERMARSH = new Sprite_Resource("texture::wintermarsh");
    public static final Sprite_Resource TEX_WINTERMOSS = new Sprite_Resource("texture::wintermoss");
    public static final Sprite_Resource TEX_WINTERMYCELIUM = new Sprite_Resource("texture::wintermycelium");
    public static final Sprite_Resource TEX_WINTERPACKED = new Sprite_Resource("texture::winterpacked");
    public static final Sprite_Resource TEX_WINTERPEAT = new Sprite_Resource("texture::winterpeat");
    public static final Sprite_Resource TEX_WINTERPLANKS = new Sprite_Resource("texture::winterplanks");
    public static final Sprite_Resource TEX_WINTERPOTTERYBRICKS = new Sprite_Resource("texture::winterpotterybricks");
    public static final Sprite_Resource TEX_WINTERREED = new Sprite_Resource("texture::winterreed");
    public static final Sprite_Resource TEX_WINTERSAND = new Sprite_Resource("texture::wintersand");
    public static final Sprite_Resource TEX_WINTERSANDSTONEBRICKS = new Sprite_Resource("texture::wintersandstonebricks");
    public static final Sprite_Resource TEX_WINTERSANDSTONESLAB = new Sprite_Resource("texture::wintersandstoneslab");
    public static final Sprite_Resource TEX_WINTERSLAB = new Sprite_Resource("texture::winterslab");
    public static final Sprite_Resource TEX_WINTERSLATETILES = new Sprite_Resource("texture::winterslateTiles");
    public static final Sprite_Resource TEX_ZINCVEIN_512 = new Sprite_Resource("texture::zincvein_512");
    public static final Sprite_Resource TEX_WATER = new Sprite_Resource("texture::water");
    public static final Sprite_Resource TEX_UNDETAIL = new Sprite_Resource("texture::undetail");
    public static final Sprite_Resource TEX_FARMLAND = new Sprite_Resource("texture::farmland");

    @AllArgsConstructor
    public static class Sprite_Resource {
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
