package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.GuiSystem;

import java.awt.event.MouseEvent;

public class NametagComponent extends MouseListenerComponent {

    private final int width;
    private final int height;
    private final String nametagText;
    public NametagComponent(Actor parent, Box listenerBounds) {
        super(parent, listenerBounds);

        width = listenerBounds.right - listenerBounds.left;
        height = listenerBounds.bottom - listenerBounds.top;
        nametagText = parent.getName();
    }

    public NametagComponent(Actor parent, Box listenerBounds, String name) {
        super(parent, listenerBounds);

        width = listenerBounds.right - listenerBounds.left;
        height = listenerBounds.bottom - listenerBounds.top;
        nametagText = name;
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        super.onMouseEntered(e);

        if (getParent() instanceof EntityActor entity) {
            var gui = GameInstance.instance().getSystem(GuiSystem.class);
            gui.showTooltip(
                nametagText,
                "Level " + entity.getEntityLevelComponent().getEntityLevel(),
                null,
                null
            );
        }

        return false;
    }

    @Override
    public boolean onMouseExited(MouseEvent e) {
        super.onMouseExited(e);

        var gui = GameInstance.instance().getSystem(GuiSystem.class);
        gui.hideTooltip();

        return false;
    }

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        if (!isMouseOver()) {
            return;
        }

        var fc = renderContext.getFontContext();
        var position = getParent().getTransform().plus(width/2, -2);

        var screenPosition = GameInstance.instance().getCamera().worldToScreenPos(position);

        fc.drawString(
            nametagText,
            FontContext.TextAlign.CENTER,
            (int) screenPosition.x,
            (int) screenPosition.y
        );
    }
}
