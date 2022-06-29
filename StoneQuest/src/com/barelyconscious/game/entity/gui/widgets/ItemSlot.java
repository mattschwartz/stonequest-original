package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.FontContext;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.entity.input.InputLayer;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.item.Item;
import com.barelyconscious.game.shape.Box;
import lombok.Getter;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class ItemSlot extends Widget {

    @Getter
    @Nullable
    private Item item;

    private boolean shouldShowTooltip() {
        return isMouseOver();
    }

    public ItemSlot(LayoutData layout, @Nullable final Item item) {
        super(layout);
        this.item = item;

        addWidget(new TooltipWidget());

        MouseInputHandler.instance().registerInteractable(this, InputLayer.GUI);
    }

    private class TooltipWidget extends Widget {
        public TooltipWidget() {
            super(LayoutData.DEFAULT);
        }

        @Override
        protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
            if (item == null || !shouldShowTooltip()) {
                return;
            }

            final String name = item.getName();
            final String description = item.getDescription();
            FontContext fontContext = renderContext.getFontContext();

            final int ttWidth = fontContext.getMaxWidthOfStrings(name, description);
            final int ttHeight = 2 * fontContext.getStringHeight();

            final Box box = new Box(
                screenBounds.left - ttWidth,
                screenBounds.left,
                screenBounds.top - ttHeight - 2,
                screenBounds.top - 2);

            final Box bb = new Box(
                box.left - 2,
                screenBounds.left + 2,
                box.top - 2,
                screenBounds.top);

            renderContext.renderRect(
                new Color(33, 33, 33, 175),
                true,
                bb,
                RenderLayer.GUI);
            renderContext.renderRect(
                Color.white,
                false,
                bb,
                RenderLayer.GUI);

            fontContext.setColor(Color.yellow);
            fontContext.setRenderLayer(RenderLayer.GUI);
            fontContext.drawString(
                name + "\n" + description,
                FontContext.TextAlign.LEFT, box.left, box.top + ttHeight / 2 - 5);
        }
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

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        return isMouseOver();
    }
}
