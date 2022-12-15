package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

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
        if (!eventArgs.IS_DEBUG) {
            return;
        }

        final Color debugColor = isBlocksMovement()
            ? Color.RED
            : Color.BLUE;

        final Vector location = getParent().transform;
        renderContext.debugRenderBox(
            debugColor,
            (int) location.x + bounds.left,
            (int) location.y + bounds.top,
            bounds.right,
            bounds.bottom
        );
    }

    public boolean intersects(final Vector myPosition, final ColliderComponent other) {
        if (other instanceof BoxColliderComponent) {
            final Box otherBounds = ((BoxColliderComponent) other).bounds;

            final Vector otherPosition = other.getParent().transform;

            final Box positionBounds = new Box(
                bounds.left + (int) myPosition.x,
                bounds.right + (int) myPosition.x,
                bounds.top + (int) myPosition.y,
                bounds.bottom + (int) myPosition.y);
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
