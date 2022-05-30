package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.RenderContext;
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

    public void physicsUpdate(EventArgs eventArgs) {
    }

    public void update(EventArgs eventArgs) {
    }

    public void render(final RenderContext renderContext) {
    }

    public void guiRender(final RenderContext renderContext) {
    }
}
