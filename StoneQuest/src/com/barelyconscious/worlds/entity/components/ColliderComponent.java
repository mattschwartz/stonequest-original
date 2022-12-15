package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.delegate.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.physics.CollisionData;
import lombok.Getter;
import lombok.Setter;

/**
 * todo: overlapping collisions do not cause Enter and Leave. It is only true while overlapping and
 *  false while not overlapping.
 */
public abstract class ColliderComponent extends Component {

    public final Delegate<CollisionData> delegateOnHit;
    public final Delegate<CollisionData> delegateOnEnter;
    /**
     * Actor provided is the actor that has left.
     */
    public final Delegate<Actor> delegateOnLeave;
    public final Delegate<CollisionData> delegateOnOverlapping;

    @Getter
    @Setter
    private boolean blocksMovement;

    @Getter
    @Setter
    private boolean firesOverlapEvents;

    public ColliderComponent(
        final Actor parent,
        final boolean blocksMovement,
        final boolean firesOverlapEvents
    ) {
        super(parent);
        this.blocksMovement = blocksMovement;
        this.firesOverlapEvents = firesOverlapEvents;

        this.delegateOnHit = new Delegate<>();
        this.delegateOnEnter = new Delegate<>();
        this.delegateOnLeave = new Delegate<>();
        this.delegateOnOverlapping = new Delegate<>();
    }

    public abstract boolean intersects(final ColliderComponent other);

    @Override
    public void physicsUpdate(EventArgs eventArgs) {
    }

    @Override
    public void update(EventArgs eventArgs) {
    }
}
