package com.barelyconscious.game.entity.resources;

import com.barelyconscious.game.entity.graphics.RenderLayer;
import lombok.AllArgsConstructor;

public class ItemsSpriteSheet {

    private static String SPRITE_SHEET_FILEPATH = "sprites/items_sprite_sheet.png";

    private static Region regionOf(int row, int col) {
        return new Region(64 * row, 64 * col, 64, 64);
    }

    @AllArgsConstructor
    public enum Resources implements SpriteResource {
        ITEM_WILLOW_BARK(regionOf(0, 0)),
        ITEM_CHAMOMILE_FLOWER(regionOf(1, 0)),
        ITEM_CORRUPTED_IMAGE_FILE(regionOf(2, 0)),
        ITEM_CURED_LEATHER(regionOf(3, 0)),
        ITEM_FLASK_OF_WATER(regionOf(4, 0)),
        ITEM_IRON_ORE(regionOf(5, 0)),
        ITEM_IRON_DUST(regionOf(6, 0)),
        ITEM_LINEN_CLOTH(regionOf(7, 0)),
        ITEM_RECIPE(regionOf(8, 0)),

        ITEM_RECOVERED_IMAGE_FILE(regionOf(0, 1)),
        ITEM_ESSENCE_OF_MIND(regionOf(1, 1)),
        ITEM_BOLT_OF_SILK(regionOf(2, 1)),
        ITEM_STREAM_DRIVE(regionOf(3, 1)),
        ITEM_STREAM_GEL_FLASK_MEDIUM(regionOf(4, 1)),
        ITEM_STREAM_GEL_FLASK_LARGE(regionOf(5, 1)),
        ITEM_STREAM_GEL_FLASK_SMALL(regionOf(6, 1)),
        ITEM_STREAM_GEL_COMPONENT(regionOf(7, 1)),
        ITEM_RECOVERED_TEXT_FILE(regionOf(8, 1)),

        ITEM_FLASK_RED(regionOf(0, 2)),
        ITEM_BLOOD_OF_THE_FALLEN(regionOf(1, 2)),
        ITEM_CHEESE_WHEEL(regionOf(2, 2)),
        ITEM_CLOTH_HAT(regionOf(3, 2)),
        ITEM_CLOTH_ROBE(regionOf(4, 2)),
        ITEM_GRAVE_DUST(regionOf(5, 2)),
        ITEM_GNARLED_STAFF(regionOf(6, 2)),
        ITEM_IRON_SHIELD(regionOf(7, 2)),
        ITEM_COIN_OF_LUCK(regionOf(8, 2)),

        ITEM_MORTALITY_BANE(regionOf(0, 3)),
        ;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public Region getRegion() {
            return bounds;
        }

        @Override
        public RenderLayer getRenderLayer() {
            return RenderLayer.GUI;
        }

        private final Region bounds;
    }

    public static SpriteSheet createItemSpriteSheet() {
        SpriteSheet sheet = SpriteSheet.createSpriteSheet(SPRITE_SHEET_FILEPATH);

        for (final SpriteResource res : Resources.values()) {
            sheet = sheet.withSprite(res);
        }
        return sheet;
    }

    private ItemsSpriteSheet() {
    }
}
