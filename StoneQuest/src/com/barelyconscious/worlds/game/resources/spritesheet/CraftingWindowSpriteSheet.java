package com.barelyconscious.worlds.game.resources.spritesheet;

import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.common.Region;
import com.barelyconscious.worlds.game.resources.SpriteResource;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.awt.image.RasterFormatException;

@Deprecated // todo - i think so
@Log4j2
public final class CraftingWindowSpriteSheet {

    private static final String SPRITE_SHEET_FILEPATH = "GUI/crafting_panel_sheet.png";

    @AllArgsConstructor
    public enum Resources implements SpriteResource {

        CRAFTING_WINDOW_BACKDROP(new Region(0, 0, 697, 322)),

        CRAFTING_WINDOW_EXPERIENCE_PROGRESS_START(new Region(0, 323, 2, 16)),
        CRAFTING_WINDOW_EXPERIENCE_PROGRESS_MIDDLE(new Region(3, 323, 18, 16)),
        CRAFTING_WINDOW_EXPERIENCE_PROGRESS_PARTIAL_CAP(new Region(22, 323, 2, 16)),

        CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DEFAULT(new Region(25, 323, 113, 20)),
        CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_OVER(new Region(139, 323, 113, 20)),
        CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DOWN(new Region(253, 323, 113, 20)),

        CRAFTING_WINDOW_CREATE_BUTTON_DEFAULT(new Region(367, 323, 50, 20)),
        CRAFTING_WINDOW_CREATE_BUTTON_OVER(new Region(418, 323, 50, 20)),
        CRAFTING_WINDOW_CREATE_BUTTON_DOWN(new Region(469, 323, 50, 20)),

        CRAFTING_WINDOW_CLOSE_BUTTON_DEFAULT(new Region(520, 323, 24, 20)),
        CRAFTING_WINDOW_CLOSE_BUTTON_OVER(new Region(545, 323, 24, 20)),
        CRAFTING_WINDOW_CLOSE_BUTTON_DOWN(new Region(570, 323, 24, 20)),

        CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_DEFAULT(new Region(0, 344, 217, 20)),
        CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_OVER(new Region(218, 344, 217, 20)),
        CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_DOWN(new Region(436, 344, 217, 20)),
        CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_OPEN(new Region(0, 365, 217, 20)),
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

    public static SpriteSheet createCraftingWindowSpriteSheet() {
        try {

            SpriteSheet sheet = SpriteSheet.createSpriteSheet(SPRITE_SHEET_FILEPATH);

            for (final SpriteResource res : CraftingWindowSpriteSheet.Resources.values()) {
                try {
                    sheet = sheet.withSprite(res);
                } catch (final RasterFormatException e) {
                    log.error("Failed to extract sprite {} with region {}", res.getName(), res.getRegion(), e);
                    throw e;
                }
            }
            return sheet;
        } catch (final Exception e) {
            log.error("Failed to load spritesheet={}", SPRITE_SHEET_FILEPATH);
            return null;
        }
    }

    private CraftingWindowSpriteSheet() {
    }
}
