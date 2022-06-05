package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Vector;

import java.awt.*;

/**
 * Renders details about the tile that the mouse is over.
 */
public class TileInfoWidget extends Widget {

    public TileInfoWidget(LayoutData layout) {
        super(layout);

        addWidget(new BackgroundPanelWidget(LayoutData.DEFAULT, new Color(0, 0, 0, 125)));
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        final Vector worldPos = eventArgs.getMouseWorldPos();
        if (worldPos != null) {
            Actor a = GameInstance.getInstance().getWorld().getActorAt(worldPos);
            if (a != null) {
                renderContext.getFontContext().renderString(
                    a.name,
                    Color.YELLOW,
                    screenBounds.left,
                    screenBounds.top,
                    RenderLayer.SKY
                );
            }
        }
    }
}
