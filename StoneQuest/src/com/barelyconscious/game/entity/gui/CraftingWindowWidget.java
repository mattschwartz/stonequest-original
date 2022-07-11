package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.gui.widgets.ButtonWidget;
import com.barelyconscious.game.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.game.entity.resources.CraftingWindowSpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;

import java.util.EnumMap;

public class CraftingWindowWidget extends Widget {

    private static final LayoutData LAYOUT = LayoutData.builder()
        .anchor(new VDim(0.5f, 0.5f,
            -(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP.getWidth() / 2),
            -(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP.getHeight() / 2)
        ))
        .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP)
        .build();

    public CraftingWindowWidget() {
        super(LAYOUT);

        addSpriteBackdrop();
        addRecipeBookDropdown();
        addCloseButton();
        addCreateButtons();
    }

    private void addSpriteBackdrop() {
        addWidget(new SpriteWidget(LayoutData.DEFAULT,
            Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP)));
    }

    private void addCloseButton() {
        ButtonWidget buttonWidget = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(1, 0,
                -(2 + CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_DEFAULT.getWidth()),
                2))
            .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_DEFAULT)
            .build(), () -> {
            setEnabled(false);
            return null;
        });
        buttonWidget.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CLOSE_BUTTON_DEFAULT));
        }});
        addWidget(buttonWidget);
    }

    private void addRecipeBookDropdown() {
        ButtonWidget buttonWidget = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 2, 2))
            .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_DEFAULT)
            .build(), null);
        buttonWidget.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_RECIPE_BOOK_BUTTON_OPEN));
        }});
        addWidget(buttonWidget);
    }

    private void addCreateButtons() {
        ButtonWidget createAmountButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0,
                528,
                300))
            .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DEFAULT)
            .build(), () -> {
            System.out.println("Create how many?");
            return null;
        });
        createAmountButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_AMOUNT_BUTTON_DEFAULT));
        }});
        addWidget(createAmountButton);

        ButtonWidget createOneButton = new ButtonWidget(LayoutData.builder()
            .anchor(new VDim(0, 0,
                645,
                300))
            .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_BUTTON_DEFAULT)
            .build(), () -> {
            System.out.println("Create just one");
            return null;
        });
        createOneButton.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_BUTTON_DEFAULT));
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_BUTTON_OVER));
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_BUTTON_DOWN));
            put(ButtonWidget.ButtonWidgetState.DISABLED, Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_CREATE_BUTTON_DEFAULT));
        }});
        addWidget(createOneButton);
    }
}