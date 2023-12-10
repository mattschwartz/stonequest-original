package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

public abstract class Component {

    /**
     * If false, the component will not be called on game updates.
     */
    @Getter
    @Setter
    private boolean isEnabled = true;

    /**
     * If false, the component will not be called on render updates.
     */
    @Getter
    @Setter
    private boolean isRenderEnabled = true;

    /**
     * If true, this component will be removed before the next game
     * update. The component will not receive an update before it
     * is removed.
     */
    @Getter
    @Setter
    private boolean removeOnNextUpdate = false;

    private Actor parent;

    public Component(final Actor parent) {
        this.parent = parent;
    }

    public Actor getParent() {
        if (parent == null) {
            throw new RuntimeException("Component with null parent tried to access parent! Component=" + this);
        }
        return parent;
    }

    /**
     * Removes this component from its current parent and reassigns the component's parent to the supplied parent
     */
    public void setParent(Actor parent) {
        if (this.parent != null && this.parent != parent && parent != null) {
            this.parent.removeComponent(this);
        }
        this.parent = parent;
    }

    public void physicsUpdate(EventArgs eventArgs) {
    }

    // todo: probably want to do `final update` which calls pendingupdate and an inner update
    //  but that's a lot of work
    public void update(EventArgs eventArgs) {
        if (pendingUpdate != null) {
            pendingUpdate.apply(eventArgs);
            pendingUpdate = null;
        }
    }

    public void render(EventArgs eventArgs, RenderContext renderContext) {
    }

    public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
    }

    public void onRemove() {
        // do any cleanup
    }

    private Function<EventArgs, Void> pendingUpdate;

    /**
     * Until to-do above is fixed, if you want to have an update fired on the next update, then you
     * must call update.super(...);
     */
    public void onNextUpdate(Function<EventArgs, Void> update) {
        this.pendingUpdate = update;
    }
}
