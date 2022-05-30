package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkArgument;

public class BoxColliderComponent extends ColliderComponent {

    @Getter
    @Setter
    private Box bounds;

    public BoxColliderComponent(
        final Actor parent,
        final boolean blocksMovement,
        final boolean firesOverlapEvents,
        final Box bounds
    ) {
        super(parent, blocksMovement, firesOverlapEvents);
        checkArgument(bounds != null, "bounds is null");

        this.bounds = bounds;
    }

    @Override
    public void render(final EventArgs eventArgs, final RenderContext renderContext) {
        final Vector location = getParent().transform;
        renderContext.debugRenderBox(
            (int) location.x + bounds.left,
            (int) location.y + bounds.top,
            bounds.right,
            bounds.bottom
        );
    }

    @Override
    public boolean intersects(final ColliderComponent other) {
        if (other instanceof BoxColliderComponent) {
            final Box otherBounds = ((BoxColliderComponent) other).bounds;

            final Vector position = getParent().transform;
            final Vector otherPosition = other.getParent().transform;

            final Box positionBounds = new Box(
                bounds.left + (int) position.x,
                bounds.right + (int) position.x,
                bounds.top + (int) position.y,
                bounds.bottom + (int) position.y);
            final Box otherPositionBounds = new Box(
                otherBounds.left + (int) otherPosition.x,
                otherBounds.right + (int) otherPosition.x,
                otherBounds.top + (int) otherPosition.y,
                otherBounds.bottom + (int) otherPosition.y);

            return positionBounds.intersects(otherPositionBounds);
        } else {
            // todo: ignoring non-box shapes...
            return false;
        }
    }
}
