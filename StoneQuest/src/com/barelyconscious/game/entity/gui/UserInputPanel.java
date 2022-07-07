package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.gui.widgets.ButtonWidget;
import com.barelyconscious.game.entity.gui.widgets.InventoryBagWidget;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;

import java.util.EnumMap;

/**
 * Terrible name but it's the UX for things like menu control, maps, inventory, whatever
 */
public class UserInputPanel extends Widget {

    private final InventoryBagWidget inventoryBagWidget;

    public UserInputPanel(LayoutData layout, final InventoryBagWidget inventoryBagWidget) {
        super(layout);
        this.inventoryBagWidget = inventoryBagWidget;

        configureMenuButtons();
    }

    private void configureMenuButtons() {
        GridLayoutWidget glw = new GridLayoutWidget(LayoutData.builder()
            .anchor(new VDim(1, 1, -47 * 3 - 18, -35))
            .size(new VDim(0, 0, 47 * 3, 35))
            .build(),
            1, 3);

        ButtonWidget invButton = new ButtonWidget(LayoutData.builder()
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
        glw.addWidget(invButton);


        ButtonWidget worldMapButton = new ButtonWidget(LayoutData.builder()
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DEFAULT)
            .build(), () -> {
            System.out.println("Toggling world map");
            return null;
        });
        worldMapButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_WORLD_MAP_DISABLED));
        }});
        glw.addWidget(worldMapButton);


        ButtonWidget craftingButton = new ButtonWidget(LayoutData.builder()
            .size(GUISpriteSheet.Resources.UI_MENU_BUTTON_INVENTORY_DEFAULT)
            .build(), () -> {
            System.out.println("Toggling world map");
            return null;
        });
        craftingButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.UI_MENU_BUTTON_CRAFTING_DISABLED));
        }});
        glw.addWidget(craftingButton);

        addWidget(glw);
    }
}
