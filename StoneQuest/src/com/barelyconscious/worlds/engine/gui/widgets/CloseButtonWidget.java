package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.resources.WSprite;

import java.util.EnumMap;

public class CloseButtonWidget extends Widget {

    public CloseButtonWidget(Widget parent) {
        super(LayoutData.builder()
            .anchor(1, 0, -25, 2)
            .size(0, 0, 24, 20)
            .build());

        ButtonWidget buttonWidget = new ButtonWidget(LayoutData.DEFAULT, () -> {
            parent.setEnabled(false);
            return null;
        });

        WSprite btnDefault = new BetterSpriteResource("gui::button_close_up").load();
        WSprite btnOver = new BetterSpriteResource("gui::button_close_over").load();
        WSprite btnDown = new BetterSpriteResource("gui::button_close_down").load();
        WSprite btnDisabled = new BetterSpriteResource("gui::button_close_up").load();

        buttonWidget.setButtonStateSprites(new EnumMap<>(ButtonWidget.ButtonWidgetState.class) {{
            put(ButtonWidget.ButtonWidgetState.DEFAULT, btnDefault);
            put(ButtonWidget.ButtonWidgetState.MOUSE_OVER, btnOver);
            put(ButtonWidget.ButtonWidgetState.MOUSE_DOWN, btnDown);
            put(ButtonWidget.ButtonWidgetState.DISABLED, btnDisabled);
        }});
        addWidget(buttonWidget);
    }
}
