package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Box;

import java.awt.Color;

public class TooltipWidget extends Widget {

    private static final String TITLE_FORMAT_STRING = "{COLOR=YELLOW}";
    private static final String DESCRIPTION_FORMAT_STRING = "{STYLE=ITALIC}{COLOR=180,180,180,200}";
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
        final FontContext fc = renderContext.getFontContext();

        final StringBuilder sb = new StringBuilder();

        sb.append(TITLE_FORMAT_STRING).append(title).append("\n").append("\n");
        if (description != null) {
            sb.append(description).append("\n");
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
            screenBounds.left,
            screenBounds.top,
            screenBounds.top);

        final Box bb = new Box(
            textBounds.left - 4,
            screenBounds.left + maxTextWidth + 4,
            screenBounds.top - textHeight - 12,
            screenBounds.top - 12);

        renderContext.renderRect(
            new Color(33, 33, 33, 255),
            true,
            bb,
            RenderLayer.GUI_FOCUS);

        fc.setColor(Color.white);
        fc.setRenderLayer(RenderLayer.GUI_FOCUS);

        fc.drawString(
            tooltipText,
            FontContext.TextAlign.LEFT, textBounds.left, textBounds.top - textHeight);
    }
}
