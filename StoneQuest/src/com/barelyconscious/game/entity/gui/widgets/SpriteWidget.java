package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.GuiCanvas;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;

public class SpriteWidget extends Widget {

    private Sprite sprite;
    private ResourceSprite rSprite;

    public SpriteWidget(final Anchor anchor, final ResourceSprite rSprite) {
        super(anchor);
        this.rSprite = rSprite;
    }

    @Override
    public void resize(final Box bounds) {
        super.resize(bounds);
        this.sprite = Resources.createSprite(rSprite, screenBounds.width, screenBounds.height);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (sprite != null) {
            renderContext.renderGui(
                sprite.image,
                screenBounds);
        }
    }
}
