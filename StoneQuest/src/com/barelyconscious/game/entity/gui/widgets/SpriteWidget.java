package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.Sprite;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.resources.ResourceGUI;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import lombok.Setter;

public class SpriteWidget extends Widget {

    private Sprite sprite;
    private ResourceGUI rSprite;
    @Setter
    private boolean renderGrayscale = false;

    public SpriteWidget(final Anchor anchor, final ResourceGUI rSprite) {
        super(anchor);
        this.rSprite = rSprite;
    }

    @Override
    public void resize(final Box bounds) {
        super.resize(bounds);
        this.sprite = Resources.createGuiSprite(rSprite, screenBounds.width, screenBounds.height);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (sprite != null) {
            if (renderGrayscale) {
                renderContext.renderGuiGrayscale(
                    sprite.image,
                    screenBounds);
            } else {
                renderContext.renderGui(
                    sprite.image,
                    screenBounds);
            }
        }
    }
}
