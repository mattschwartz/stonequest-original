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
    private FontContext.TextAlign textAlignment;
    @Setter
    private boolean showShadow = true;
    @Setter
    private FontContext.VerticalTextAlignment verticalTextAlignment;

    public TextFieldWidget(final LayoutData layout, final String text) {
        super(layout);
        this.text = text;
        this.textAlignment = FontContext.TextAlign.CENTER;
        this.verticalTextAlignment = FontContext.VerticalTextAlignment.CENTER;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        FontContext font = renderContext.getFontContext();
        font.setRenderLayer(RenderLayer.GUI);

        final int textWidth = font.getStringWidth(text);
        final int textHeight = font.getStringHeight(text);

        final int yOffs;
        switch (verticalTextAlignment) {
            case BOTTOM:
                yOffs = screenBounds.height;
                break;
            case CENTER:
                yOffs = textHeight / 2;
                break;

            case TOP:
            default:
                yOffs = 0;
        }

        final Box textBounds = new Box(screenBounds.left, screenBounds.right,
            screenBounds.top + yOffs, screenBounds.bottom + yOffs);

        if (showShadow) {
            final String shadowText = "{COLOR=0,0,0,255}" + text;

            font.drawString(shadowText,
                textAlignment,
                new Box(textBounds.left + 1, textBounds.right + 1, textBounds.top, textBounds.bottom));
            font.drawString(shadowText,
                textAlignment,
                new Box(textBounds.left - 1, textBounds.right - 1, textBounds.top, textBounds.bottom));
            font.drawString(shadowText,
                textAlignment,
                new Box(textBounds.left, textBounds.right, textBounds.top + 1, textBounds.bottom + 1));
            font.drawString(shadowText,
                textAlignment,
                new Box(textBounds.left, textBounds.right, textBounds.top - 1, textBounds.bottom - 1));
        }

        font.drawString(text,
            textAlignment,
            textBounds);
    }
}
