package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.common.UColor;
import com.barelyconscious.worlds.engine.gui.widgets.BackgroundPanelWidget;
import com.barelyconscious.worlds.engine.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.engine.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.game.resources.spritesheet.GUISpriteSheet;
import com.barelyconscious.worlds.game.resources.Resources;

import java.awt.*;
import java.util.EnumMap;

/**
 * Terrible name but it's the UX for things like menu control, maps, inventory, whatever
 */
public class UserInputPanel extends Widget {

    private static final LayoutData LAYOUT = LayoutData.builder()
        .anchor(new VDim(1, 0,
            -GUISpriteSheet.Resources.UI_INPUT_CONTROL_BACKGROUND.getWidth() - 1,
            1))
        .size(GUISpriteSheet.Resources.UI_INPUT_CONTROL_BACKGROUND)
        .build();

    private final Widget inventoryBagWidget;
    private final Widget stockpileBagWidget;
    private final Widget craftingMenuWidget;
    private final Widget worldMapWidget;
    private final Widget gameMenuWidget;

    public UserInputPanel(
        final Widget inventoryBagWidget,
        final Widget stockpileBagWidget,
        final Widget craftingMenuWidget,
        final Widget worldMapWidget,
        final Widget gameMenuWidget
    ) {
        super(LayoutData.DEFAULT);
        this.inventoryBagWidget = inventoryBagWidget;
        this.stockpileBagWidget = stockpileBagWidget;
        this.craftingMenuWidget = craftingMenuWidget;
        this.worldMapWidget = worldMapWidget;
        this.gameMenuWidget = gameMenuWidget;

        this.inventoryBagWidget.setEnabled(false);
        this.stockpileBagWidget.setEnabled(false);
        this.craftingMenuWidget.setEnabled(false);
        this.worldMapWidget.setEnabled(false);
        this.gameMenuWidget.setEnabled(false);

        configureFactionMenuButtons();
        configureUserInputButtons();
    }

    private void configureFactionMenuButtons() {
        Widget topLeftMenuWidget = new BackgroundPanelWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 0, 0))
            .build()
            , UColor.TRANSPARENT);
        addWidget(topLeftMenuWidget);

        ButtonWidget stockButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 2, 2))
            .size(GUISpriteSheet.Resources.BUTTON_DEFAULT)
            .build(),
            "Stockpile",
            () -> {
                stockpileBagWidget.setEnabled(!stockpileBagWidget.isEnabled());
                return null;
            });
        topLeftMenuWidget.addWidget(stockButton);

        ButtonWidget constructionButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 84, 2))
            .size(new VDim(0, 0, 120, 26))
            .build(),
            "Construction",
            () -> {
                return null;
            });
        topLeftMenuWidget.addWidget(constructionButton);

        ButtonWidget settlementButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 205, 2))
            .size(new VDim(0, 0, 120, 26))
            .build(),
            "Settlement",
            () -> {
                return null;
            });
        topLeftMenuWidget.addWidget(settlementButton);
    }

    private void configureUserInputButtons() {
        Widget topRightMenuWidget = new BackgroundPanelWidget(LAYOUT, UColor.TRANSPARENT);
        addWidget(topRightMenuWidget);
        topRightMenuWidget.addWidget(new SpriteWidget(LayoutData.DEFAULT,
            Resources.instance().getSprite(GUISpriteSheet.Resources.UI_INPUT_CONTROL_BACKGROUND)));

        ButtonWidget invButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 11, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DEFAULT)
            .build(),
            () -> {
                inventoryBagWidget.setEnabled(!inventoryBagWidget.isEnabled());
                return null;
            });
        invButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DISABLED));
        }});
        topRightMenuWidget.addWidget(invButton);

        ButtonWidget craftingButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 54 + 12, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DEFAULT)
            .build(), () -> {
            craftingMenuWidget.setEnabled(!craftingMenuWidget.isEnabled());
            return null;
        });
        craftingButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DISABLED));
        }});
        topRightMenuWidget.addWidget(craftingButton);

        ButtonWidget worldMapButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 107 + 14, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DEFAULT)
            .build(), () -> {
            worldMapWidget.setEnabled(!worldMapWidget.isEnabled());
            return null;
        });
        worldMapButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DISABLED));
        }});
        topRightMenuWidget.addWidget(worldMapButton);

        ButtonWidget gameMenuButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 176, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DEFAULT)
            .build(),
            () -> {
                gameMenuWidget.setEnabled(!gameMenuWidget.isEnabled());
                return null;
            });
        gameMenuButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DISABLED));
        }});
        topRightMenuWidget.addWidget(gameMenuButton);
    }
}
