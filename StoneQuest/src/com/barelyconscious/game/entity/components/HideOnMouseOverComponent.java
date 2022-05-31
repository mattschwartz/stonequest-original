package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;

import java.awt.*;

public class HideOnMouseOverComponent extends Component {

    private final Box mouseCaptureBounds;

    public HideOnMouseOverComponent(final Actor parent, final Box mouseCaptureBounds) {
        super(parent);
        this.mouseCaptureBounds = mouseCaptureBounds;
    }

    @Override
    public void render(EventArgs eventArgs, RenderContext renderContext) {
        final Vector mouseWorldPos = GameInstance.getInstance().getPlayerController().getMouseWorldPos();

        final Vector worldPos = getParent().transform;
        final Box worldBounds = new Box(
            (int) worldPos.x + mouseCaptureBounds.left,
            (int) worldPos.x + mouseCaptureBounds.right,
            (int) worldPos.y + mouseCaptureBounds.top,
            (int) worldPos.y + mouseCaptureBounds.bottom);

        if (mouseWorldPos != null && worldBounds.contains(mouseWorldPos)) {
            renderContext.renderRect(
                Color.yellow,
                false,
                (int) worldPos.x, (int) worldPos.y,
                mouseCaptureBounds.right,
                mouseCaptureBounds.bottom,
                RenderLayer.GUI);
        }
    }
}
