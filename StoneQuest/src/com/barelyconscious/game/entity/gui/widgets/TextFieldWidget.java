package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;

import java.awt.*;

public class TextFieldWidget extends Widget {

    private String text;

    public TextFieldWidget(final LayoutData layout, final String text) {
        super(layout);
        this.text = text;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        renderContext.renderString(
            text,
            Color.yellow,
            screenBounds.left,
            screenBounds.top,
            RenderLayer.GUI);
    }
}
