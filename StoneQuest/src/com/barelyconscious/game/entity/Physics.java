package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.ColliderComponent;
import com.barelyconscious.game.physics.CollisionData;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.shape.Vector;

import java.util.List;
import java.util.Objects;

public final class Physics {

    /**
     * Runs the physics engine.
     *
     * @param physicsActors actors with physics components that need updating
     */
    public void updatePhysics(
        final EventArgs eventArgs,
        final List<Actor> physicsActors
    ) {
        for (final Actor actor : physicsActors) {
            final MoveComponent moveComponent = actor.getComponent(MoveComponent.class);
            if (moveComponent == null) {
                continue;
            }

            moveComponent.physicsUpdate(eventArgs);

            final Vector desiredLocation = moveComponent.getDesiredLocation();

            // actor did not move
            if (Objects.equals(actor.transform, desiredLocation)) {
                continue;
            }

            final boolean didMove;
            final ColliderComponent collider = actor.getComponent(ColliderComponent.class);
            if (collider != null) {
                didMove = tryMove(collider, physicsActors);
            } else {
                didMove = true;
            }

            if (didMove) {
                actor.transform = desiredLocation;
            }
        }
    }

    /**
     * Tries to move an actor.
     *
     * @return false if the actor did not move
     */
    private boolean tryMove(final ColliderComponent collider, final List<Actor> physicsActors) {
        boolean didMove = false;
        for (final Actor actor : physicsActors) {
            // No collisions against disabled/destroying actors
            if (!actor.isEnabled() || actor.isDestroying()) {
                continue;
            }

            final ColliderComponent other = actor.getComponent(ColliderComponent.class);
            if (other != null && other.isEnabled()) {
                if (collider.intersects(other)) {
                    final CollisionData col = new CollisionData(
                        other.isBlocksMovement(),
                        other.isFiresOverlapEvents(),
                        other.getParent(),
                        collider.getParent());

                    if (other.isBlocksMovement()) {
                        collider.delegateOnHit.call(col);
                        other.delegateOnHit.call(col);

                        didMove = true;
                    }
                    if (other.isFiresOverlapEvents()) {
                        collider.delegateOnOverlap.call(col);
                        other.delegateOnOverlap.call(col);
                    }
                }
            }
        }

        return didMove;
    }
}
