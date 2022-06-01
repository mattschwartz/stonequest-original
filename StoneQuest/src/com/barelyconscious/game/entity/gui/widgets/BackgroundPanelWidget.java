package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
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
