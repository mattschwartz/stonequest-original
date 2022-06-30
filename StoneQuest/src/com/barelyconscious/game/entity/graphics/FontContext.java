package com.barelyconscious.game.entity.graphics;

import com.barelyconscious.game.entity.resources.FontResource;
import com.barelyconscious.game.entity.resources.Resources;
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

        int yOffs = 0;
        for (String line : msg.split("\n")) {
            g.setFont(font);
            g.setColor(color);

            int xOffs = 0;
            int lineWidth = getStringWidth(line);


            Matcher matcher = COMMAND_PATTERN.matcher(line);
            int matchesEndIndex = 0;
            while (matcher.find()) {
                matchesEndIndex = matcher.end();
                MatchResult matchResult = matcher.toMatchResult();
                String command = matchResult.group(1);

                switch (command) {
                    case "COLOR":
                        List<Integer> values = Lists.newArrayList(matchResult.group(2).split(",")).stream()
                            .map(Integer::valueOf).collect(Collectors.toList());
                        g.setColor(new Color(values.get(0), values.get(1), values.get(2), values.get(3)));
                        break;
                    case "STYLE":
                        final String value = matchResult.group(2);
                        if ("BOLD".equals(value)) {
                            g.setFont(font.deriveFont(Font.BOLD));
                        } else if ("ITALIC".equals(value)) {
                            g.setFont(font.deriveFont(Font.ITALIC));
                        }
                        break;
                    case "SIZE":
                        Font gFont = g.getFont();
                        final int fontSize = Integer.parseInt(matchResult.group(2));
                        g.setFont(gFont.deriveFont((float) fontSize));

                        break;
                    default:
                        log.error("Unsupported format command={}. Full line={}", command, line);
                }
            }
            line = line.substring(matchesEndIndex);

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
        for (String part : str.split("\n")) {

            Matcher matcher = COMMAND_PATTERN.matcher(part);
            int matchesEndIndex = 0;
            while (matcher.find()) {
                matchesEndIndex = matcher.end();
            }
            part = part.substring(matchesEndIndex);
            if (part.length() == 0) {
                continue;
            }

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
