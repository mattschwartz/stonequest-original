package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class BackgroundPanelWidget extends Widget {

    @Getter
    @Setter
    private Color fillColor;

    private final boolean isFill;

    public BackgroundPanelWidget(final LayoutData layout, final Color color, final boolean isFill) {
        super(layout);
        this.fillColor = color;
        this.isFill = isFill;
    }

    public BackgroundPanelWidget(final LayoutData layout, final Color fillColor) {
        this(layout, fillColor, true);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        renderContext.renderGuiRect(fillColor, isFill, screenBounds);
    }
}
