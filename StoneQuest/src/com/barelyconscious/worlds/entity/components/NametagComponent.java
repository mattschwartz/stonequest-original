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

    public NametagComponent(Actor parent, Box listenerBounds) {
        super(parent, listenerBounds);
    }

    @Override
    public boolean onMouseEntered(MouseEvent e) {
        super.onMouseEntered(e);

        if (getParent() instanceof EntityActor entity) {
            var gui = GameInstance.instance().getSystem(GuiSystem.class);
            gui.showTooltip(
                entity.getName(),
                "Level " + entity.getEntityLevelComponent().getEntityLevel(),
                null,
                null
            );
        }

        return true;
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
        var position = getParent().getTransform().plus(16, -2);
        fc.drawString(
            getParent().getName(),
            FontContext.TextAlign.CENTER,
            (int) position.x,
            (int) position.y
        );
    }
}
