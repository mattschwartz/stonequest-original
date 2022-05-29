package com.barelyconscious.game.physics;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.EventArgs;

public abstract class PhysicsComponent extends Component {

    public PhysicsComponent(final Actor parent) {
        super(parent);
    }

    public abstract void physicsUpdate(EventArgs eventArgs);

    @Override
    public void update(EventArgs eventArgs) {
    }
}
