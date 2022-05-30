package com.barelyconscious.game.physics;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.ColliderComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.shape.Vector;

import java.util.List;
import java.util.Objects;

public final class Physics {

    public void updatePhysics(
        final EventArgs eventArgs,
        final List<Actor> actors
    ) {
        for (final Actor actor : actors) {
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
            final BoxColliderComponent collider = actor.getComponent(BoxColliderComponent.class);
            if (collider != null) {
                didMove = tryMove(actor, desiredLocation, collider, actors);
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
    private boolean tryMove(
        final Actor actor,
        final Vector desiredLocation,
        final BoxColliderComponent collider,
        final List<Actor> physicsActors
    ) {
        boolean didMove = true;
        for (final Actor otherActor : physicsActors) {
            // No collisions against disabled/destroying actors
            if (!otherActor.isEnabled() || otherActor.isDestroying() || actor == otherActor) {
                continue;
            }

            final ColliderComponent other = otherActor.getComponent(BoxColliderComponent.class);
            if (other != null && other.isEnabled()) {
                if (collider.intersects(desiredLocation, other)) {
                    final CollisionData col = new CollisionData(
                        other.isBlocksMovement(),
                        other.isFiresOverlapEvents(),
                        other.getParent(),
                        collider.getParent());

                    if (other.isBlocksMovement()) {
                        collider.delegateOnHit.call(col);
                        other.delegateOnHit.call(col);

                        didMove = false;
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
