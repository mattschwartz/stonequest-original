package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class Component {

    @Getter
    @Setter
    private boolean isEnabled = true;

    @Getter
    private final Actor parent;

    public Component(final Actor parent) {
        checkArgument(parent != null, "parent is null");

        this.parent = parent;
    }

    public abstract void physicsUpdate(EventArgs eventArgs);

    public abstract void update(EventArgs eventArgs);
}
