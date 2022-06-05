package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.entity.resources.ItemsSpriteSheet;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Box;
import lombok.Getter;

import javax.annotation.Nullable;

public class ItemSlot extends Widget {

    @Getter
    @Nullable
    private Item item;

    public ItemSlot(LayoutData layout, @Nullable final Item item) {
        super(layout);
        this.item = item;
    }

    /**
     * @return the previous item if any, else null
     */
    @Nullable
    public Item setItem(final Item item) {
        if (acceptsItem(item)) {
            Item prevItem = this.item;
            this.item = item;
            return prevItem;
        } else {
            return null;
        }
    }

    public Item removeItem(final Item item) {
        final Item prevItem = this.item;
        this.item = item;
        return prevItem;
    }

    /**
     * Returns true if this item slot can hold this item.
     * default=true
     */
    public boolean acceptsItem(final Item item) {
        return true;
    }

    @Override
    public void onRender(
        EventArgs eventArgs,
        RenderContext renderContext
    ) {
        if (item != null) {
            renderContext.renderGui(
                item.getSprite().getTexture(),
                screenBounds);
        }
    }
}
