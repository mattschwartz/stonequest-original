package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.common.shape.Box;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class TextFieldWidget extends Widget {

    @Getter
    @Setter
    private float fontSize = 16;

    @Getter
    @Setter
    private String text;
    @Setter
    private FontContext.TextAlign textAlignment;
    @Setter
    private boolean showShadow = true;
    @Setter
    private FontContext.VerticalTextAlignment verticalTextAlignment;

    public TextFieldWidget(final String text, final LayoutData layout) {
        super(layout);
        this.text = text;
        this.textAlignment = FontContext.TextAlign.LEFT;
        this.verticalTextAlignment = FontContext.VerticalTextAlignment.CENTER;
    }

    @Deprecated
    public TextFieldWidget(final LayoutData layout, final String text) {
        super(layout);
        this.text = text;
        this.textAlignment = FontContext.TextAlign.CENTER;
        this.verticalTextAlignment = FontContext.VerticalTextAlignment.CENTER;
    }

    public TextFieldWidget(
        final String text,
        FontContext.TextAlign textAlignment,
        FontContext.VerticalTextAlignment verticalTextAlignment
    ) {
        super(LayoutData.DEFAULT);
        this.text = text;
        this.textAlignment = textAlignment;
        this.verticalTextAlignment = verticalTextAlignment;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (StringUtils.isBlank(text)) {
            return;
        }

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

            font.setFontSize(fontSize);

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
