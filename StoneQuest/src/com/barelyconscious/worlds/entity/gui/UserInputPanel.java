package com.barelyconscious.worlds.entity.gui;

import com.barelyconscious.worlds.entity.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.entity.resources.GUISpriteSheet;
import com.barelyconscious.worlds.entity.resources.Resources;

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
    private final Widget craftingMenuWidget;
    private final Widget worldMapWidget;
    private final Widget gameMenuWidget;

    public UserInputPanel(
        final Widget inventoryBagWidget,
        final Widget craftingMenuWidget,
        final Widget worldMapWidget,
        final Widget gameMenuWidget
    ) {
        super(LAYOUT);
        this.inventoryBagWidget = inventoryBagWidget;
        this.craftingMenuWidget = craftingMenuWidget;
        this.worldMapWidget = worldMapWidget;
        this.gameMenuWidget = gameMenuWidget;

        this.inventoryBagWidget.setEnabled(false);
        this.craftingMenuWidget.setEnabled(false);

        addWidget(new SpriteWidget(LayoutData.DEFAULT,
            Resources.instance().getSprite(GUISpriteSheet.Resources.UI_INPUT_CONTROL_BACKGROUND)));

        configureMenuButtons();
    }

    private void configureMenuButtons() {
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
        addWidget(invButton);

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
        addWidget(craftingButton);

        ButtonWidget worldMapButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 107 + 14, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DEFAULT)
            .build(), () -> {
            worldMapWidget.setEnabled(worldMapWidget.isEnabled());
            return null;
        });
        worldMapButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DISABLED));
        }});
        addWidget(worldMapButton);

        ButtonWidget gameMenuButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 176, 11))
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DEFAULT)
            .build(), () -> {
            gameMenuWidget.setEnabled(gameMenuWidget.isEnabled());
            return null;
        });
        gameMenuButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_GAME_MENU_DISABLED));
        }});
        addWidget(gameMenuButton);
    }
}
