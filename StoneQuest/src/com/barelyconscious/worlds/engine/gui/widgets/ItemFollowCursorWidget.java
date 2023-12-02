package com.barelyconscious.worlds.engine.gui.widgets;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.gui.LayoutData;
import com.barelyconscious.worlds.engine.gui.Widget;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.common.shape.Box;
import lombok.Getter;

public class ItemFollowCursorWidget extends Widget {

    // todo(p2): add to saveGame so player doesn't lose items
    @Getter
    private static Inventory.InventoryItem inventoryItemOnCursor;

    public static Inventory.InventoryItem setInventoryItemOnCursor(final Inventory.InventoryItem newWidget) {
        final Inventory.InventoryItem prevWidget = inventoryItemOnCursor;
        if (newWidget == null || newWidget.item == null) {
            inventoryItemOnCursor = null;
        } else {
            inventoryItemOnCursor = newWidget;
        }
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
