package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Box;
import lombok.Setter;

public class TextFieldWidget extends Widget {

    @Setter
    private String text;
    @Setter
    private boolean showShadow = true;

    public TextFieldWidget(final LayoutData layout, final String text) {
        super(layout);
        this.text = text;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        FontContext font = renderContext.getFontContext();
        font.setRenderLayer(RenderLayer.GUI);

        if (showShadow) {
            font.drawString("{COLOR=0,0,0,255}" + text,
                FontContext.TextAlign.CENTER,
                new Box(screenBounds.left + 1, screenBounds.right + 1, screenBounds.top, screenBounds.bottom));
            font.drawString("{COLOR=0,0,0,255}" + text,
                FontContext.TextAlign.CENTER,
                new Box(screenBounds.left - 1, screenBounds.right - 1, screenBounds.top, screenBounds.bottom));
            font.drawString("{COLOR=0,0,0,255}" + text,
                FontContext.TextAlign.CENTER,
                new Box(screenBounds.left, screenBounds.right, screenBounds.top + 1, screenBounds.bottom + 1));
            font.drawString("{COLOR=0,0,0,255}" + text,
                FontContext.TextAlign.CENTER,
                new Box(screenBounds.left, screenBounds.right, screenBounds.top - 1, screenBounds.bottom - 1));
        }

        font.drawString(text,
            FontContext.TextAlign.CENTER,
            screenBounds);
    }
}
