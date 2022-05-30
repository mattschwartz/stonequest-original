package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.EventArgs;

public abstract class PhysicsComponent extends Component {

    public PhysicsComponent(final Actor parent) {
        super(parent);
    }

    @Override
    public void update(EventArgs eventArgs) {
    }
}
