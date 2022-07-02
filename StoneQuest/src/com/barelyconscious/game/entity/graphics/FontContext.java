package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.resources.FontResource;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public class FontContext {

    public enum TextAlign {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum VerticalTextAlignment {
        TOP,
        BOTTOM,
        CENTER
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


    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\{(\\w+)=([\\w, ]+)}");

    public void drawString(final String msg, final TextAlign textAlign, final Box bounds) {
        final Graphics2D g = (Graphics2D) renderContext.getGraphics(renderLayer);
        final Color prev = g.getColor();
        final Font prevFont = g.getFont();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(color);

        int yOffs = 0;
        for (String line : msg.split("\n")) {

            int xStart = 0;
            int lineWidth = getStringWidth(line);

            int matchesEndIndex = applyFormat(line, g);
            line = line.substring(matchesEndIndex);

            switch (textAlign) {
                case RIGHT:
                    xStart = bounds.right - lineWidth;
                    break;

                case CENTER:
                    xStart = bounds.left + (bounds.width - lineWidth) / 2;
                    break;
                case LEFT:
                    xStart = bounds.left;
                    break;
            }

            g.drawString(line, xStart, bounds.top + yOffs);

            yOffs += getStringHeight(line) + LINE_SPACING;
        }

        g.setColor(prev);
        g.setFont(prevFont);
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

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setFont(font);
        g.setColor(color);
        int yOffs = 0;
        for (String line : msg.split("\n")) {
            int xOffs = 0;
            int lineWidth = getStringWidth(line);

            int matchesEndIndex = applyFormat(line, g);
            line = line.substring(matchesEndIndex);

            // todo(p0): make this formattable?
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

    /**
     * @return the index after any format matching
     */
    private int applyFormat(final String line, final Graphics g) {
        Matcher matcher = COMMAND_PATTERN.matcher(line);

        // make it so first come first serve

        boolean isColorSet = false;
        boolean isStyleSet = false;
        boolean isSizeSet = false;

        int matchesEndIndex = 0;
        while (matcher.find()) {
            matchesEndIndex = matcher.end();
            MatchResult matchResult = matcher.toMatchResult();
            String command = matchResult.group(1);

            switch (command) {
                case "COLOR":
                    if (isColorSet) {
                        continue;
                    } else {
                        isColorSet = true;
                    }

                    if (!applyCommonColors(matchResult.group(2), g)) {
                        List<Integer> values = Lists.newArrayList(matchResult.group(2).split(",")).stream()
                            .map(Integer::valueOf).collect(Collectors.toList());
                        g.setColor(new Color(values.get(0), values.get(1), values.get(2), values.get(3)));
                    }

                    break;
                case "STYLE":
                    if (isStyleSet) {
                        continue;
                    } else {
                        isStyleSet = true;
                    }

                    final String value = matchResult.group(2);
                    if ("BOLD".equals(value)) {
                        g.setFont(font.deriveFont(Font.BOLD));
                    } else if ("ITALIC".equals(value)) {
                        g.setFont(font.deriveFont(Font.ITALIC));
                    } else if ("NONE".equals(value)) {
                        g.setFont(font.deriveFont(Font.PLAIN));
                    }
                    break;
                case "SIZE":
                    if (isSizeSet) {
                        continue;
                    } else {
                        isSizeSet = true;
                    }

                    Font gFont = g.getFont();
                    final int fontSize = Integer.parseInt(matchResult.group(2));
                    g.setFont(gFont.deriveFont((float) fontSize));

                    break;
                default:
                    log.error("Unsupported format command={}. Full line={}", command, line);
            }
        }

        return Math.max(matchesEndIndex, 0);
    }

    /**
     * @return true if format was applied
     */
    private boolean applyCommonColors(String group, Graphics g) {
        switch (group) {
            case "WHITE":
                g.setColor(Color.WHITE);
                return true;
            case "BLACK":
                g.setColor(Color.BLACK);
                return true;
            case "YELLOW":
                g.setColor(Color.YELLOW);
                return true;
            case "GREEN":
                g.setColor(new Color(69, 182, 69));
                return true;
            case "LIGHT_GRAY":
                g.setColor(new Color(165, 165, 165));
                return true;
            case "RED":
                g.setColor(new Color(172, 50, 50));
                return true;

            default:
                return false;
        }
    }

    public int getStringWidth(final String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        int maxWidth = 0;
        for (String part : str.split("\n")) {
            Graphics g = renderContext.getGraphics(renderLayer);
            final int matchesEndIndex = applyFormat(part, g);
            part = part.substring(matchesEndIndex);
            if (part.length() == 0) {
                continue;
            }

            FontRenderContext frc = g.getFontMetrics(g.getFont()).getFontRenderContext();

            TextLayout textLayout = new TextLayout(part, g.getFont(), frc);

            final int partWidth = (int) Math.round(textLayout.getBounds().getWidth());
            maxWidth = Math.max(partWidth, maxWidth);
        }
        return maxWidth;
    }


    public int getStringHeight(final String str) {
        final String[] parts = str.split("\n");
        double totalHeight = 0;

        for (String part : parts) {
            if (StringUtils.isBlank(part)) {
                part = "@"; // newlines should take up space too
            }
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
