package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.graphics.RenderContext;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class Component {

    @Getter
    @Setter
    private boolean isEnabled = true;

    @Getter
    @Setter
    private boolean isRenderEnabled = true;

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

    public void render(EventArgs eventArgs, RenderContext renderContext) {
    }

    public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
    }
}
