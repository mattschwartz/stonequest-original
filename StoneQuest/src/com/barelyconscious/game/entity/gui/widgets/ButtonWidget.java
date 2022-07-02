package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.MouseInputWidget;
import com.barelyconscious.game.entity.gui.VDim;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.resources.WSprite;
import lombok.SneakyThrows;

import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.concurrent.Callable;

public class ButtonWidget extends MouseInputWidget {

    private enum ButtonWidgetState {
        DEFAULT,
        MOUSE_DOWN,
        DISABLED,
        MOUSE_OVER,
    }

    private ButtonWidgetState currentState;
    private final EnumMap<ButtonWidgetState, WSprite> spritesByState;
    private final TextFieldWidget textWidget;
    private final SpriteWidget buttonSpriteWidget;
    private final Callable<Void> onClick;

    public ButtonWidget(LayoutData layout, String text, Callable<Void> onClick) {
        super(layout, InputLayer.USER_INPUT);

        this.currentState = ButtonWidgetState.DEFAULT;
        this.onClick = onClick;
        this.textWidget = new TextFieldWidget(LayoutData.builder()
            .anchor(new VDim(0, 0.5f, 0, 0))
            .size(new VDim(1, 1, 0, 0))
            .build(),
            text);

        spritesByState = new EnumMap<>(ButtonWidgetState.class) {{
            put(ButtonWidgetState.DEFAULT, Resources.instance().getSprite(GUISpriteSheet.Resources.BUTTON_DEFAULT));
            put(ButtonWidgetState.DISABLED, Resources.instance().getSprite(GUISpriteSheet.Resources.BUTTON_DISABLED));
            put(ButtonWidgetState.MOUSE_OVER, Resources.instance().getSprite(GUISpriteSheet.Resources.BUTTON_OVER));
            put(ButtonWidgetState.MOUSE_DOWN, Resources.instance().getSprite(GUISpriteSheet.Resources.BUTTON_DOWN));
        }};

        buttonSpriteWidget = new SpriteWidget(LayoutData.DEFAULT, spritesByState.get(ButtonWidgetState.DEFAULT));

        delegateOnMouseOver.bindDelegate(e -> {
            currentState = e ? ButtonWidgetState.MOUSE_OVER : ButtonWidgetState.DEFAULT;
            return null;
        });

        addWidget(buttonSpriteWidget);
        addWidget(textWidget);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (isMouseOver()) {
            currentState = ButtonWidgetState.MOUSE_DOWN;
        }
    }

    @SneakyThrows
    @Override
    public void onMouseReleased(MouseEvent e) {
        if (isMouseOver()) {
            currentState = ButtonWidgetState.MOUSE_OVER;
            onClick.call();
        } else if (currentState == ButtonWidgetState.MOUSE_DOWN) {
            currentState = ButtonWidgetState.DEFAULT;
            onClick.call();
        }
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        buttonSpriteWidget.setSprite(spritesByState.get(currentState));
        super.onRender(eventArgs, renderContext);
    }
}
