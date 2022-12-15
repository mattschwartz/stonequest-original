package com.barelyconscious.worlds.entity.gui.widgets;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.TileActor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.graphics.RenderContext;
import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.gui.LayoutData;
import com.barelyconscious.worlds.entity.gui.Widget;
import com.barelyconscious.worlds.shape.Vector;

import java.awt.Color;
import java.util.Optional;

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
            Optional<Actor> actor = eventArgs.getWorldContext().getActorAt(worldPos);
            if (actor.isPresent() && actor.get() instanceof TileActor) {
                renderContext.getFontContext().renderString(
                    actor.get().name,
                    Color.YELLOW,
                    screenBounds.left,
                    screenBounds.top,
                    RenderLayer.SKY
                );
            }
        }
    }
}
