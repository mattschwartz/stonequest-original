package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.entity.YieldingCallback;

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
