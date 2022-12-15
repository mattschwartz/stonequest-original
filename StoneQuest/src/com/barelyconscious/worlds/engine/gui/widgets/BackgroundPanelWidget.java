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

    public BackgroundPanelWidget(final LayoutData layout, final Color fillColor) {
        super(layout);
        this.fillColor = fillColor;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        renderContext.renderGuiRect(fillColor, true, screenBounds);
    }
}
