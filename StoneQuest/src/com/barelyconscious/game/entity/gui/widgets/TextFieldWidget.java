package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.RenderString;
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
        FontContext font = renderContext.getFontContext();
        font.setRenderLayer(RenderLayer.GUI);
        font.drawString(new RenderString(
                text,
                Color.yellow,
                font.getFont()),
            screenBounds.left,
            screenBounds.top);
    }
}
