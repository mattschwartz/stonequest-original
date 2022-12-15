package com.barelyconscious.worlds.entity.gui;

import com.barelyconscious.worlds.entity.graphics.FontContext;
import com.barelyconscious.worlds.entity.gui.widgets.ButtonWidget;
import com.barelyconscious.worlds.entity.gui.widgets.ListWidget;
import com.barelyconscious.worlds.entity.gui.widgets.ProgressBarWidget;
import com.barelyconscious.worlds.entity.gui.widgets.SpriteWidget;
import com.barelyconscious.worlds.entity.gui.widgets.TextFieldWidget;
import com.barelyconscious.worlds.entity.input.InputLayer;
import com.barelyconscious.worlds.entity.resources.CraftingWindowSpriteSheet;
import com.barelyconscious.worlds.entity.resources.Resources;

import java.util.EnumMap;

public class CraftingWindowWidget extends MouseInputWidget {

    private static final LayoutData LAYOUT = LayoutData.builder()
        .anchor(new VDim(0.5f, 0.5f,
            -(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP.getWidth() / 2),
            -(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP.getHeight() / 2)
        ))
        .size(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_BACKDROP)
        .build();

    public CraftingWindowWidget() {
        super(LAYOUT, InputLayer.GUI);

        addSpriteBackdrop();
        addRecipeBookDropdown();
        addCloseButton();
        addCreateButtons();
        addExperienceWidget();
        addRecipeListWidget();
    }

    private void addRecipeListWidget() {
        final ListWidget listWidget = new ListWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 223, 63))
            .size(new VDim(0, 0, 228, 224))
            .build());
        listWidget.addItem(new ListWidget.ListItemWidget("0", "Aspirin"));
        listWidget.addItem(new ListWidget.ListItemWidget("0", "Bandages"));
        listWidget.addItem(new ListWidget.ListItemWidget("0", "Salve"));
        addWidget(listWidget);
    }

    private void addExperienceWidget() {
        ProgressBarWidget pbw = new ProgressBarWidget(LayoutData.builder()
            .anchor(new VDim(0, 0, 222, 4))
            .size(new VDim(0, 0, 445, 16))
            .build(),
            Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_EXPERIENCE_PROGRESS_START),
            Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_EXPERIENCE_PROGRESS_MIDDLE),
            Resources.instance().getSprite(CraftingWindowSpriteSheet.Resources.CRAFTING_WINDOW_EXPERIENCE_PROGRESS_PARTIAL_CAP),
            0.33f);
        TextFieldWidget tfw = new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0.5f, 2, 0))
            .size(LayoutData.SIZE_FILL)
            .build(), "{COLOR=BLACK}{SIZE=14}Beginner");
        tfw.setTextAlignment(FontContext.TextAlign.LEFT);
        tfw.setShowShadow(false);
        pbw.addWidget(tfw);

        addWidget(pbw);
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

        TextFieldWidget tfw = new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0.5f, 0, -2))
            .size(LayoutData.SIZE_FILL)
            .build(), "Medicine Crafting");
        buttonWidget.addWidget(tfw);
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
