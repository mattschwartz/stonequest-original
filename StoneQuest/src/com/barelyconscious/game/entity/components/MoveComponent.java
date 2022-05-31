package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.shape.Vector;

public final class MoveComponent extends Component {

    public float moveSpeed;

    public MoveComponent(
        final Actor parent,
        final float moveSpeed
    ) {
        super(parent);
        this.moveSpeed = moveSpeed;
    }

    private Vector forceVector = Vector.ZERO;

    /**
     * @param direction the direction to apply impulse
     * @param force     the force to apply in respective direction
     */
    public void addForce(final Vector direction, final float force) {
        forceVector = forceVector.plus(direction.unitVector().mult(force));
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
            desiredLocation = getParent().transform;
            forceVector = Vector.ZERO;
            return;
        }

        // apply force to determine how far and in what direction the actor is trying to move
        final Vector startPos = getParent().transform;
        final Vector endPos = startPos.plus(forceVector);

        final float dx = (endPos.x - startPos.x) * eventArgs.getDeltaTime() * moveSpeed;
        final float dy = (endPos.y - startPos.y) * eventArgs.getDeltaTime() * moveSpeed;

        desiredLocation = new Vector(startPos.x + dx, startPos.y + dy);
        forceVector = forceVector.minus(new Vector(dx, dy));
    }
}
