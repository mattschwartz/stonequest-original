package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.resources.FontResource;
import com.barelyconscious.game.entity.resources.Resources;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class FontContext {

    public enum TextAlign {
        LEFT,
        CENTER,
        RIGHT
    }

    @Getter
    @Setter
    @NonNull
    private Color color;

    @Getter
    @NonNull
    private Font font;

    @Getter
    @Setter
    @NonNull
    private RenderLayer renderLayer;

    @NonNull
    private final RenderContext renderContext;

    public FontContext(
        @NonNull Color color,
        @NonNull FontResource font,
        @NonNull RenderLayer renderLayer,
        @NonNull RenderContext renderContext
    ) {
        this.color = color;
        this.font = Resources.loadFont(font);
        this.renderLayer = renderLayer;
        this.renderContext = renderContext;
    }

    public void renderString(
        final String msg,
        final Color color,
        final int screenX,
        final int screenY,
        final RenderLayer renderLayer
    ) {
        val prevLayer = this.renderLayer;
        val prevColor = this.color;

        setRenderLayer(renderLayer);
        setColor(color);

        drawString(msg, TextAlign.LEFT, screenX, screenY);

        setRenderLayer(prevLayer);
        setColor(prevColor);
    }

    public void drawString(
        final String msg,
        final TextAlign textAlignment,
        final int screenX,
        final int screenY
    ) {
        final Graphics2D g = (Graphics2D) renderContext.getGraphics(renderLayer);
        final Color prev = g.getColor();
        final Font prevFont = g.getFont();

        g.setFont(font);
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int lineHeight = g.getFontMetrics(font).getHeight();

        int yOffs = 0;
        final String[] lines = msg.split("\n");
        for (final String line : lines) {
            int xOffs = 0;
            int lineWidth = getStringWidth(line);

            switch (textAlignment) {
                case RIGHT:
                    xOffs -= lineWidth;
                case CENTER:
                    xOffs -= lineWidth / 2;
            }

            g.drawString(line, screenX + xOffs, screenY + yOffs);

            yOffs += lineHeight;
        }


        g.setColor(prev);
        g.setFont(prevFont);
    }

    public int getStringWidth(final String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        return renderContext
            .getGraphics(renderLayer)
            .getFontMetrics(font)
            .stringWidth(str);
    }

    public int getMaxWidthOfStrings(final String... strings) {
        int maxLength = 0;
        for (final String str : strings) {
            maxLength = Math.max(getStringWidth(str), maxLength);
        }
        return maxLength;
    }

    public int getStringHeight() {
        return renderContext.getGraphics(renderLayer).getFontMetrics(font)
            .getHeight();
    }

    public void setFont(final FontResource resource) {
        this.font = Resources.loadFont(resource);
    }

    public void setFontSize(final float newSize) {
        this.font = this.font.deriveFont(newSize);
    }
}
