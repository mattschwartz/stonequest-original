package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.resources.FontResource;
import com.barelyconscious.game.entity.resources.Resources;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

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

    private static final int LINE_SPACING = 2;

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

        int yOffs = 0;
        for (final String line : msg.split("\n")) {
            int xOffs = 0;
            int lineWidth = getStringWidth(line);

            switch (textAlignment) {
                case RIGHT:
                    xOffs -= lineWidth;
                case CENTER:
                    xOffs -= lineWidth / 2;
            }

            g.drawString(line, screenX + xOffs, screenY + yOffs);

            yOffs += getStringHeight(line) + LINE_SPACING;
        }

        g.setColor(prev);
        g.setFont(prevFont);
    }

    public int getStringWidth(final String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        int maxWidth = 0;
        for (final String part : str.split("\n")) {
            Graphics g = renderContext.getGraphics(renderLayer);
            FontRenderContext frc = g.getFontMetrics(font).getFontRenderContext();

            TextLayout textLayout = new TextLayout(part, font, frc);

            final int partWidth = (int) Math.round(textLayout.getBounds().getWidth());
            maxWidth = Math.max(partWidth, maxWidth);
        }
        return maxWidth;

    }


    public int getStringHeight(final String str) {
        final String[] parts = str.split("\n");
        double totalHeight = 0;

        for (final String part : parts) {
            Graphics g = renderContext.getGraphics(renderLayer);
            FontRenderContext frc = g.getFontMetrics(font).getFontRenderContext();

            TextLayout textLayout = new TextLayout(part, font, frc);
            totalHeight += Math.round(textLayout.getBounds().getHeight());
        }

        if (parts.length > 1) {
            totalHeight += (parts.length - 1) * LINE_SPACING;
        }

        return (int) totalHeight;
    }

    public void setFont(final FontResource resource) {
        this.font = Resources.loadFont(resource);
    }

    public void setFontSize(final float newSize) {
        this.font = this.font.deriveFont(newSize);
    }
}
