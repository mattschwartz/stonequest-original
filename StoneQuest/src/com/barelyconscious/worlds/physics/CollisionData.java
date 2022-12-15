package com.barelyconscious.worlds.physics;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.shape.Vector;
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

    public final Component triggeredByComponent;

    /**
     * The amount of force imparted by the causedByActor.
     */
    public final Vector forceVector;

    /**
     * Data about the tick.
     */
    public EventArgs eventArgs;
}
