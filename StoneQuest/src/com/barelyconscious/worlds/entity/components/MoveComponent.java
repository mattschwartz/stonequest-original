package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.common.UMath;
import lombok.Getter;

public class MoveComponent extends Component {

    public double moveSpeed;

    public MoveComponent(
        final Actor parent,
        final double moveSpeed
    ) {
        super(parent);
        this.moveSpeed = moveSpeed;
    }

    @Getter
    private Vector forceVector = Vector.ZERO;

    /**
     * @param direction the direction to apply impulse
     * @param force     the force to apply in respective direction
     */
    public void addForce(final Vector direction, final double force) {
        forceVector = UMath.min(
            forceVector.plus(direction.unitVector().multiply(force)),
            direction.unitVector().multiply(moveSpeed));
        getParent().facing = forceVector.unitVector();
    }

    public void setFacing(final Vector facing) {
        getParent().facing = facing;
    }

    private Vector desiredLocation;

    public Vector getDesiredLocation() {
        return desiredLocation;
    }

    private static final Float e = 1f;

    // todo - move speed isn't being applied
    @Override
    public void physicsUpdate(final EventArgs eventArgs) {
        // no force need be applied
        if (Vector.ZERO.equals(forceVector) || forceVector.magnitude() <= e) {
            desiredLocation = getParent().getTransform();
            forceVector = Vector.ZERO;
            return;
        }

        // apply force to determine how far and in what direction the actor is trying to move
        final Vector startPos = getParent().getTransform();
        final Vector endPos = startPos.plus(forceVector);

        final double dx = (endPos.x - startPos.x) * eventArgs.getDeltaTime() * moveSpeed;
        final double dy = (endPos.y - startPos.y) * eventArgs.getDeltaTime() * moveSpeed;

        desiredLocation = new Vector(startPos.x + dx, startPos.y + dy);
        forceVector = forceVector.minus(new Vector(dx, dy));
    }
}
