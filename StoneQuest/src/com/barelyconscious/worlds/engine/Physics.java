package com.barelyconscious.worlds.engine;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.ColliderComponent;
import com.barelyconscious.worlds.entity.components.MoveComponent;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * todo: collision events trigger kinda weird right now.
 * <p>
 * todo: collisions aren't detected if something tried to move
 *  very quickly through things it shouldnt have been able to move
 *  to if it had gone more slowly.
 *  Need to consider collisions by projecting the entire
 *  path taken as a collision data to compare against.
 *  \
 *  likely by taking position start and position end, then comparing
 *  the resulting shape to the other collider.
 *  \
 *  basically answering the question "did object collide with, or
 *  pass through, the other?"
 */
public final class Physics {

    private enum CollisionState {CLEAR, OVERLAP}

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class CollisionDescriptor {
        @NonNull
        private final ColliderComponent causedByCollider;
        @NonNull
        private final ColliderComponent victimCollider;
    }

    private final Map<CollisionDescriptor, CollisionState> lastFrameCollisions = new HashMap<>();


    public void updatePhysics(
        final EventArgs eventArgs,
        final List<Actor> actors
    ) {
        for (final Actor actor : actors) {
            final MoveComponent move = actor.getComponent(MoveComponent.class);
            if (move == null) {
                continue;
            }

            move.physicsUpdate(eventArgs);

            final Vector desiredLocation = move.getDesiredLocation();

            // actor did not move
            if (Objects.equals(actor.transform, desiredLocation)) {
                continue;
            }

            final boolean didMove;
            final BoxColliderComponent collider = actor.getComponent(BoxColliderComponent.class);
            if (collider != null) {
                didMove = tryMove(actor, desiredLocation,
                    move.getForceVector(), collider, actors, eventArgs);
            } else {
                didMove = true;
            }

            if (didMove) {
                actor.transform = desiredLocation;
            }
        }

//        postProcess();
    }

    private void postProcess() {
        Set<CollisionDescriptor> collisionDescriptors = lastFrameCollisions.keySet();

        for (final CollisionDescriptor collisionDescriptor : collisionDescriptors) {
            final CollisionState state = lastFrameCollisions.get(collisionDescriptor);

            if (state == CollisionState.CLEAR) {
                // doesn't intersect, but was it intersecting before?
                collisionDescriptor.victimCollider.delegateOnLeave.call(collisionDescriptor.causedByCollider.getParent());
                lastFrameCollisions.remove(collisionDescriptor);
            } else if (state == CollisionState.OVERLAP) {
                lastFrameCollisions.put(collisionDescriptor, CollisionState.CLEAR);
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
        final Vector forceVector,
        final BoxColliderComponent collider,
        final List<Actor> physicsActors,
        final EventArgs eventArgs
    ) {
        boolean didMove = true;
        for (final Actor otherActor : physicsActors) {
            // No collisions against disabled/destroying actors
            if (!otherActor.isEnabled() || otherActor.isDestroying() || actor == otherActor) {
                continue;
            }

            final ColliderComponent other = otherActor.getComponent(BoxColliderComponent.class);
            if (other != null && other.isEnabled()) {
                final CollisionDescriptor collisionDescriptor = new CollisionDescriptor(collider, other);
                final CollisionState lastCollisionState = lastFrameCollisions.get(collisionDescriptor);
                CollisionState newCollisionState = null;

                if (collider.intersects(desiredLocation, other)) {
                    final CollisionData collisionData = new CollisionData(
                        other.isBlocksMovement(),
                        other.isFiresOverlapEvents(),
                        other.getParent(),
                        collider.getParent(),
                        collider,
                        forceVector,
                        eventArgs);

                    if (other.isBlocksMovement()) {
                        collider.delegateOnHit.call(collisionData);

                        didMove = false;
                    }

                    if (other.isFiresOverlapEvents()) {
                        final CollisionState collisionState = lastFrameCollisions.get(collisionDescriptor);

                        if (collisionState == null || collisionState == CollisionState.CLEAR) {
                            collider.delegateOnEnter.call(collisionData);
                            other.delegateOnEnter.call(collisionData);
                        }

                        collider.delegateOnOverlapping.call(collisionData);
                        other.delegateOnOverlapping.call(collisionData);
                    }

                    newCollisionState = CollisionState.OVERLAP;
                    lastFrameCollisions.put(collisionDescriptor, CollisionState.OVERLAP);
                }

                // doesn't intersect, but was it intersecting before?
                if (lastCollisionState == CollisionState.OVERLAP && newCollisionState == null) {
                    collisionDescriptor.victimCollider.delegateOnLeave.call(collisionDescriptor.causedByCollider.getParent());
                    lastFrameCollisions.remove(collisionDescriptor);
                }
            }

        }

        return didMove;
    }
}
