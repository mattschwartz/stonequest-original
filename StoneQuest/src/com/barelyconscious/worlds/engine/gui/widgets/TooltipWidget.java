package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.common.shape.Box;

import java.awt.Color;

public class TooltipWidget extends Widget {

    private static final String TITLE_FORMAT_STRING = "{COLOR=WHITE}";
    private static final String DESCRIPTION_FORMAT_STRING = "{STYLE=ITALIC}{COLOR=LIGHT_GRAY}";
    private static final String ACTION_TEXT_FORMAT_STRING = "{STYLE=BOLD}{COLOR=GREEN}";
    private static final String TOP_RIGHT_TEXT_FORMAT_STRING = "{STYLE=BOLD}{COLOR=WHITE}";

    private String title;
    private String description;
    private String actionText;
    private String topRightText;

    public TooltipWidget(
        final LayoutData layout,
        final String title,
        final String description,
        final String actionText,
        final String topRightText
    ) {
        super(layout);

        this.title = title;
        this.description = description;
        this.actionText = actionText;
        this.topRightText = topRightText;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (!isVisible()) {
            return;
        }

        final FontContext fc = renderContext.getFontContext();

        final StringBuilder sb = new StringBuilder();

        sb.append(TITLE_FORMAT_STRING).append(title).append("\n");
        if (description != null) {
            sb.append(DESCRIPTION_FORMAT_STRING).append(description).append("\n");
        }
        if (actionText != null) {
            sb.append("\n");
            sb.append(ACTION_TEXT_FORMAT_STRING).append(actionText).append("\n");
        }

        final String tooltipText = sb.toString();

        final int maxTextWidth = fc.getStringWidth(tooltipText);
        final int textHeight = fc.getStringHeight(tooltipText);

        final Box textBounds = new Box(
            screenBounds.left,
            screenBounds.left + maxTextWidth,
            screenBounds.top,
            screenBounds.top);

        final Box bb = new Box(
            textBounds.left - 4,
            textBounds.right + 4,
            screenBounds.top - textHeight - 12,
            screenBounds.top - 12);

        renderContext.renderRect(
            new Color(33, 33, 33, 255),
            true,
            bb,
            RenderLayer.GUI_FOCUS);

        renderContext.renderRect(
            new Color(155, 155, 155),
            false,
            bb,
            RenderLayer.GUI_FOCUS);

        fc.setColor(Color.white);
        fc.setRenderLayer(RenderLayer.GUI_FOCUS);

        fc.drawString(
            tooltipText,
            FontContext.TextAlign.LEFT, textBounds.left, textBounds.top - textHeight);
    }
}
