package com.barelyconscious.game.physics;

import com.barelyconscious.game.entity.Actor;
import lombok.AllArgsConstructor;

/**
 * Represents the collision between two entities.
 */
@AllArgsConstructor
public final class CollisionData {

    /**
     * If true, the actor generating the collision should not move.
     */
    public final boolean blocksMovement;

    /**
     * If true, the actor generating the collision is overlapping with another.
     */
    public final boolean isOverlapping;

    /**
     * The other actor involved in the collision.
     */
    public final Actor hit;

    /**
     * The actor instigating the collision.
     */
    public final Actor causedByActor;
}
