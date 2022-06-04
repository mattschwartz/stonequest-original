package com.barelyconscious.game.entity.graphics;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class RenderString {

    public enum Align {
        LEFT,
        CENTER,
        RIGHT
    }

    @Setter
    private Align textAlignment = Align.LEFT;
    private final List<RenderChar> renderChars;

    public RenderString(@NonNull String str, @Nullable Color color, @Nullable Font font) {
        this(RenderChar.fromString(str, color, font));
    }

    public RenderString(List<RenderChar> renderChars) {
        this.renderChars = Lists.newArrayList(renderChars);
    }

    public void render(Graphics2D g, int screenX, int screenY) {
        final Color prevColor = g.getColor();
        final Font prevFont = g.getFont();

        int xOffs = screenX;
        int yOffs = screenY;

        for (final RenderChar renderChar : renderChars) {
            if ("\n".equals(renderChar.character)) {
                yOffs += g.getFontMetrics(renderChar.font)
                    .getHeight();
                xOffs = screenX;
                continue;
            }

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

            g.setColor(renderChar.color);
            g.setFont(renderChar.font);
            g.drawString(renderChar.character, xOffs, yOffs);

            xOffs += g.getFontMetrics(renderChar.font)
                .stringWidth(renderChar.character);
        }

        g.setColor(prevColor);
        g.setFont(prevFont);
    }

    @AllArgsConstructor
    private static final class RenderChar {

        private final String character;
        private final Color color;
        private final Font font;

        static List<RenderChar> fromString(final String str, final Color color, final Font font) {
            List<RenderChar> results = new ArrayList<>();
            for (char c : str.toCharArray()) {
                results.add(new RenderChar(c + "", color, font));
            }
            return results;
        }
    }
}
