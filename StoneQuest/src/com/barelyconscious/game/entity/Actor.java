package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;

import java.util.*;

/**
 * A thing which can be spawned into the game world.
 */
public abstract class Actor {

    private final List<Component> components;

    public Vector transform;

    public Actor() {
        this(new Vector(), new ArrayList<>());
    }

    public Actor(final Vector transform, final List<Component> components) {
        this.transform = transform;
        this.components = components;
    }

    public boolean addComponent(final Component component) {
        return components.add(component);
    }

    public boolean removeComponent(final Component component) {
        return components.remove(component);
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }
}
