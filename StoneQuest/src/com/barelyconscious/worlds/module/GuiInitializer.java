package com.barelyconscious.worlds.module;

import com.barelyconscious.worlds.engine.graphics.CanvasScreen;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.gui.CraftingWindowWidget;
import com.barelyconscious.worlds.engine.gui.GuiCanvas;
import com.barelyconscious.worlds.engine.gui.HeroQuickbarPanel;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.UserInputPanel;
import com.barelyconscious.worlds.engine.gui.VDim;
import com.barelyconscious.worlds.engine.gui.widgets.GameMenuWidget;
import com.barelyconscious.worlds.engine.gui.widgets.InventoryBagWidget;
import com.barelyconscious.worlds.engine.gui.widgets.TileInfoWidget;
import com.barelyconscious.worlds.engine.gui.widgets.WorldMapWidget;
import com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.resources.GUISpriteSheet;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.testgamedata.TestHeroInitializer;
import lombok.val;

import static com.barelyconscious.worlds.game.resources.GUISpriteSheet.Resources.INV_ITEM_SLOT_BACKGROUND;

public final class GuiInitializer {

    public static void createGui(final CanvasScreen screen, final World world, final PlayerController playerController) {
        val gui = new GuiCanvas(screen);
        TileInfoWidget tiw = new TileInfoWidget(LayoutData.builder()
            .anchor(new VDim(0.5f, 0, -45, 15))
            .size(new VDim(0, 0, 15, 45))
            .build());
        tiw.setEnabled(false);
        gui.addWidget(tiw);

        configureHeroQuickbarPanel(gui);
        configureInventory(gui, playerController);
        world.addActor(gui);
    }

    private static void configureInventory(GuiCanvas gui, final PlayerController playerController) {
        if (!(playerController instanceof MouseKeyboardPlayerController)) {
            return;
        }

        Inventory inventory = ((MouseKeyboardPlayerController) playerController).getInventory();

        val wBackpack = new InventoryBagWidget(LayoutData.builder()
            .anchor(new VDim(1, 0.5f,
                -(INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth() + 75),
                -(INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight() / 2)))
            .size(new VDim(0, 0,
                INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth(),
                INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight()))
            .build(), inventory, 4, 4);

        val wCraftingMenu = new CraftingWindowWidget();
        var wWorldMapMenu = new WorldMapWidget();
        var wGameMenu = new GameMenuWidget();

        gui.addWidget(wBackpack);
        gui.addWidget(wCraftingMenu);
        gui.addWidget(wWorldMapMenu);
        gui.addWidget(wGameMenu);

        gui.addWidget(new UserInputPanel(
            wBackpack,
            wCraftingMenu,
            wWorldMapMenu,
            wGameMenu));
    }

    private static void configureHeroQuickbarPanel(final GuiCanvas gui) {
        gui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                -(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2),
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            TestHeroInitializer.HERO_NICNOLE,
            Resources.getSprite(ResourceSprite.HERO_2)));

        gui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                +(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2)
                    + 16,
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            TestHeroInitializer.HERO_JOHN,
            Resources.getSprite(ResourceSprite.HERO_3)));

        gui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                -(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2)
                    - GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth()
                    - 16,
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            TestHeroInitializer.HERO_PAUL,
            Resources.getSprite(ResourceSprite.HERO_1)));
    }
}
