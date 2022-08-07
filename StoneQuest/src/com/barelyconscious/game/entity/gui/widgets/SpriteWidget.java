package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.VDim;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.resources.WSprite;
import lombok.Setter;

public class SpriteWidget extends Widget {

    @Setter
    private WSprite sprite;
    @Setter
    private boolean renderGrayscale = false;

    /**
     * uses sprite's width and height to set the absolute size of this widget. Uses TOP_LEFT
     * anchor
     */
    public SpriteWidget(final WSprite sprite) {
        this(LayoutData.builder()
            .anchor(LayoutData.ANCHOR_TOP_LEFT)
            .size(new VDim(0, 0, sprite.getWidth(), sprite.getHeight()))
            .build(),
            sprite);
    }

    public SpriteWidget(final LayoutData layout, final WSprite sprite) {
        super(layout);
        this.sprite = sprite;
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (sprite != null) {
            if (renderGrayscale) {
                renderContext.renderGuiGrayscale(
                    sprite.getTexture(),
                    screenBounds);
            } else {
                renderContext.renderGui(
                    sprite.getTexture(),
                    screenBounds);
            }
        }
    }
}
