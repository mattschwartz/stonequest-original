package com.barelyconscious.worlds.entity.gui.widgets;

import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.graphics.RenderContext;
import com.barelyconscious.worlds.entity.gui.LayoutData;
import com.barelyconscious.worlds.entity.gui.Widget;
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
