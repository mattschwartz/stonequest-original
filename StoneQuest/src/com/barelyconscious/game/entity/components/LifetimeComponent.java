package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;

public class LifetimeComponent extends Component{

    private final float maxLifetime;
    private float timePassed = 0;

    public LifetimeComponent(Actor parent, final float maxLifetime) {
        super(parent);
        this.maxLifetime = maxLifetime;
    }

    @Override
    public void update(EventArgs eventArgs) {
        this.timePassed += eventArgs.getDeltaTime();
        if (timePassed >= maxLifetime) {
            getParent().destroy();
        }
    }
}
