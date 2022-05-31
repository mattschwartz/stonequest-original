package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.shape.Vector;

import static com.google.common.base.Preconditions.checkArgument;

public class MouseInputComponent extends Component {

    private final int width;
    private final int height;

    public MouseInputComponent(final Actor parent, final int width, final int height) {
        super(parent);
        checkArgument(width < 0, "width is < 0");
        checkArgument(height < 0, "height is < 0");
        this.width = width;
        this.height = height;
    }

    public boolean contains(final int mouseX, final int mouseY) {
        final Vector location = getParent().transform;

        return mouseX >= location.x && mouseX <= location.x + width
            && mouseY >= location.y && mouseY <= location.y + height;
    }

    @Override
    public void physicsUpdate(EventArgs eventArgs) {
    }

    @Override
    public void update(EventArgs eventArgs) {
    }
}
