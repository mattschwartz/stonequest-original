package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.YieldingCallback;

public class LifetimeComponent extends Component{

    private final YieldingCallback yieldedCallback;

    public LifetimeComponent(Actor parent, final long maxLifetimeMillis) {
        super(parent);
        yieldedCallback = new YieldingCallback(maxLifetimeMillis, e -> {
            getParent().destroy();
            return null;
        });
    }

    @Override
    public void update(EventArgs eventArgs) {
        yieldedCallback.tickAndCall(eventArgs);
    }
}
