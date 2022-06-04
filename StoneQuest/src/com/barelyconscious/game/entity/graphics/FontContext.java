package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.resources.FontResource;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Vector;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class FontContext {

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

    public void renderString(String msg, Color color, int screenX, int screenY, RenderLayer renderLayer) {
        val prevLayer = this.renderLayer;
        val prevColor = this.color;

        setRenderLayer(renderLayer);
        setColor(color);

        drawString(msg, screenX, screenY);

        setRenderLayer(prevLayer);
        setColor(prevColor);
    }

    public void drawString(final String msg, final int screenX, final int screenY) {
        final Graphics2D g = (Graphics2D) renderContext.getGraphics(renderLayer);
        final Color prev = g.getColor();

        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(msg, screenX, screenY);
        g.setColor(prev);
    }

    public void worldDrawString(final String msg, final int worldX, final int worldY) {
        Vector screenPos = renderContext.camera.worldToScreenPos(worldX, worldY);
        this.drawString(msg, (int) screenPos.x, (int) screenPos.y);
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

    public void setFont(final FontResource resource) {
        this.font = Resources.loadFont(resource);
    }

    public void setFontSize(final float newSize) {
        this.font = this.font.deriveFont(newSize);
    }
}
