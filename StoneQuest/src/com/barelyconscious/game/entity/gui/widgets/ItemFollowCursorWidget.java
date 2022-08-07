package com.barelyconscious.game.entity.gui.widgets;

import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.Widget;
import com.barelyconscious.game.shape.Box;
import lombok.Getter;

public class ItemFollowCursorWidget extends Widget {

    // todo(p2): add to saveGame so player doesn't lose items
    @Getter
    private static Inventory.InventoryItem inventoryItemOnCursor;

    public static Inventory.InventoryItem setInventoryItemOnCursor(final Inventory.InventoryItem newWidget) {
        final Inventory.InventoryItem prevWidget = inventoryItemOnCursor;
        inventoryItemOnCursor = newWidget;
        return prevWidget;
    }

    public ItemFollowCursorWidget() {
        super(LayoutData.DEFAULT);
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        if (inventoryItemOnCursor == null || eventArgs.getMouseWorldPos() == null) {
            return;
        }

        final int screenX = (int) eventArgs.getMouseScreenPos().x;
        final int screenY = (int) eventArgs.getMouseScreenPos().y;

        Box bounds = new Box(
            screenBounds.left + screenX,
            screenBounds.left + screenX + 48,
            screenBounds.top + screenY,
            screenBounds.top + screenY + 48
        );

        renderContext.renderImage(inventoryItemOnCursor.item.getSprite().load().getTexture(), bounds, RenderLayer.GUI_FOCUS);
    }
}
