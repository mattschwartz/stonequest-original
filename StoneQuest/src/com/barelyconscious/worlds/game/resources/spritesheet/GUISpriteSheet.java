package com.barelyconscious.worlds.game.resources.spritesheet;

import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.common.Region;
import com.barelyconscious.worlds.game.resources.SpriteResource;
import lombok.AllArgsConstructor;

@Deprecated // todo - i think so
public final class GUISpriteSheet {

    private static final String SPRITE_SHEET_FILEPATH = "GUI/player_frame_sheet.png";

    @AllArgsConstructor
    public enum Resources implements SpriteResource {

        HERO_UNITFRAME_BACKDROP(new Region(0, 16, 233, 101)),
        HERO_UNITFRAME_SELECTED(new Region(0, 117, 233, 112)),
        /**
         * When displaying health bar, this sprite is the leftmost image rendered.
         */
        HERO_UNITFRAME_HEALTHBAR_PROGRESS_START(new Region(0, 0, 3, 15)),

        /**
         * The horizontally-scalable middle part of the health bar
         */
        HERO_UNITFRAME_HEALTHBAR_PROGRESS_MIDDLE(new Region(5, 0, 18, 15)),

        /**
         * The cap on the health bar when some health has been lost.
         */
        HERO_UNITFRAME_HEALTHBAR_PROGRESS_PARTIAL_CAP(new Region(24, 0, 4, 15)),

        /**
         * The cap on the healthbar when no health has been lost.
         */
        HERO_UNITFRAME_HEALTHBAR_PROGRESS_FULL_CAP(new Region(29, 0, 3, 15)),

        HERO_UNITFRAME_POWERBAR_PROGRESS_START(new Region(33, 0, 3, 10)),
        HERO_UNITFRAME_POWERBAR_PROGRESS_MIDDLE(new Region(37, 0, 18, 10)),
        HERO_UNITFRAME_POWERBAR_PROGRESS_PARTIAL_CAP(new Region(56, 0, 4, 10)),
        HERO_UNITFRAME_POWERBAR_PROGRESS_FULL_CAP(new Region(61, 0, 3, 10)),

        INV_ITEM_SLOT_BACKGROUND(new Region(0, 229, 198, 198)),

        HERO_STAT_SHEET_BACKDROP(new Region(0, 427, 211, 199)),

        BUTTON_DEFAULT(new Region(0, 626, 81, 26)),
        BUTTON_OVER(new Region(81, 626, 81, 26)),
        BUTTON_DOWN(new Region(0, 652, 81, 26)),
        BUTTON_DISABLED(new Region(81, 652, 81, 26)),

        UI_MENU_BUTTON_INVENTORY_DEFAULT(new Region(104, 1097, 52, 77)),
        UI_MENU_BUTTON_INVENTORY_DOWN(new Region(0, 1097, 52, 77)),
        UI_MENU_BUTTON_INVENTORY_OVER(new Region(52, 1097, 52, 77)),
        UI_MENU_BUTTON_INVENTORY_DISABLED(new Region(0, 0, 52, 77)),

        UI_MENU_BUTTON_CRAFTING_DEFAULT(new Region(104, 1174, 52, 77)),
        UI_MENU_BUTTON_CRAFTING_DOWN(new Region(0, 1174, 52, 77)),
        UI_MENU_BUTTON_CRAFTING_OVER(new Region(52, 1174, 52, 77)),
        UI_MENU_BUTTON_CRAFTING_DISABLED(new Region(0, 1174, 52, 77)),

        UI_MENU_BUTTON_WORLD_MAP_DEFAULT(new Region(104, 1328, 52, 77)),
        UI_MENU_BUTTON_WORLD_MAP_DOWN(new Region(0, 1328, 52, 77)),
        UI_MENU_BUTTON_WORLD_MAP_OVER(new Region(52, 1328, 52, 77)),
        UI_MENU_BUTTON_WORLD_MAP_DISABLED(new Region(0, 1328, 52, 77)),

        UI_MENU_BUTTON_GAME_MENU_DEFAULT(new Region(104, 1251, 52, 77)),
        UI_MENU_BUTTON_GAME_MENU_DOWN(new Region(0, 1251, 52, 77)),
        UI_MENU_BUTTON_GAME_MENU_OVER(new Region(52, 1251, 52, 77)),
        UI_MENU_BUTTON_GAME_MENU_DISABLED(new Region(0, 1251, 52, 77)),

        UI_MENU_BUTTON_CLOSE_BUTTON_DEFAULT(new Region(199, 626, 34, 34)),
        UI_MENU_BUTTON_CLOSE_BUTTON_DOWN(new Region(199, 660, 34, 34)),
        UI_MENU_BUTTON_CLOSE_BUTTON_OVER(new Region(165, 626, 34, 34)),
        UI_MENU_BUTTON_CLOSE_BUTTON_DISABLED(new Region(0, 0, 34, 34)),

        UI_INPUT_CONTROL_BACKGROUND(new Region(0, 921, 239, 99))

        ;

        private final Region bounds;

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
    }

    public static SpriteSheet createGuiSpriteSheet() {
        SpriteSheet sheet = SpriteSheet.createSpriteSheet(SPRITE_SHEET_FILEPATH);

        for (final SpriteResource res : Resources.values()) {
            sheet = sheet.withSprite(res);
        }
        return sheet;
    }

    private GUISpriteSheet() {
    }
}
