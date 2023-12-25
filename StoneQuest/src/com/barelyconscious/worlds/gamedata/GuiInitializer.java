package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.engine.gui.*;
import com.barelyconscious.worlds.engine.gui.widgets.*;
import com.barelyconscious.worlds.engine.gui.worldmap.WorldMap;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.game.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.resources.spritesheet.GUISpriteSheet;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.game.systems.SettlementSystem;
import lombok.val;

import static com.barelyconscious.worlds.game.resources.spritesheet.GUISpriteSheet.Resources.INV_ITEM_SLOT_BACKGROUND;

public final class GuiInitializer {

    public static void createGui(
        final GuiCanvas gui,
        final World world,
        final PlayerController playerController
    ) {
        TileInfoWidget tiw = new TileInfoWidget(LayoutData.builder()
            .anchor(new VDim(0.5f, 0, -45, 15))
            .size(new VDim(0, 0, 15, 45))
            .build());
        tiw.setEnabled(false);
        gui.addWidget(tiw);

        gui.addWidget(new ErrorAlertTextWidget());

        configureHeroQuickbarPanel(gui);
        configureInventory(gui);
        world.addPersistentActor(gui);
    }

    private static void configureInventory(GuiCanvas gui) {
        // Player's backpack
        Inventory inventory = GameInstance.instance()
            .getGameState()
            .getPartyState()
            .getWagon().getStorage();
        var wBackpack = new InventoryBagWidget(LayoutData.builder()
            .anchor(new VDim(1, 0,
                -(INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth() + 5),
                105))
            .size(new VDim(0, 0,
                INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth(),
                INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight()))
            .build(), inventory, 4, 4);

        // Faction inventory
        Inventory stockpile = GameInstance.instance()
            .getGameState()
            .getSettlementState()
            .getPlayerSettlement()
            .getStockpile();
        var wStockPile = new InventoryBagWidget(LayoutData.builder()
            .anchor(new VDim(0, 0,
                5,
                33))
            .size(new VDim(0, 0,
                INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth(),
                INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight()))
            .build(), stockpile, 8, 8);

        val wCraftingMenu = new CraftingWindowWidget();
        var wGameMenu = new PlayerPersonalDeviceWidget();
        var wWorldMapMenu = new WorldMap();

        gui.addWidget(wCraftingMenu);
        gui.addWidget(wGameMenu);
        gui.addWidget(wWorldMapMenu);

        gui.addWidget(new UserInputPanel(
            wBackpack,
            wStockPile,
            wCraftingMenu,
            wWorldMapMenu,
            wGameMenu));

        gui.addWidget(wStockPile);
        gui.addWidget(wBackpack);
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
