package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.shape.Vector;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A thing which can be spawned into the game world.
 */
@Log4j2
public class Actor {

    public final String id;
    public final String name;

    private final Map<Class<? extends Component>, Component> componentsByType;
    private final List<Component> components;

    private boolean isEnabled = true;
    private boolean isDestroying = false;

    public Vector transform;

    public Actor(final Vector transform) {
        this(null, transform);
    }

    public Actor(
        final String name,
        final Vector transform,
        final Component... components
    ) {
        checkArgument(transform != null, "transform is null");
        checkArgument(components != null, "components is null");

        this.id = UUID.randomUUID().toString();

        if (StringUtils.isBlank(name)) {
            this.name = "Actor#" + id;
        } else {
            this.name = name;
        }

        this.transform = transform;
        this.components = Lists.newArrayList(components);

        this.componentsByType = new HashMap<>();
        this.components.forEach(t -> this.componentsByType.put(t.getClass(), t));
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(final boolean enabled) {
        isEnabled = enabled;
    }

    public void destroy() {
        isDestroying = true;
    }

    public boolean isDestroying() {
        return isDestroying;
    }

    public boolean addComponent(final Component component) {
        checkArgument(component != null, "component is null");

        if (componentsByType.containsKey(component.getClass())) {
            log.warn("Attempted to add component of type={}. Already exists on Actor '{}'",
                component.getClass(),
                this.name);
            return false;
        }

        componentsByType.put(component.getClass(), component);
        return components.add(component);
    }

    @Nullable
    public Component removeComponentByType(final Class<? extends Component> componentType) {
        if (componentsByType.containsKey(componentType)) {
            log.info("Attempted to remove component of type={}. Does not exist on Actor '{}'",
                componentType,
                this.name);
            return null;
        }

        final Component componentRemoved = componentsByType.get(componentType);
        componentsByType.remove(componentType);
        components.remove(componentRemoved);

        return componentRemoved;
    }

    @Nullable
    public Component removeComponent(final Component component) {
        return removeComponentByType(component.getClass());
    }

    public boolean hasComponent(final Class<? extends Component> componentType) {
        return componentsByType.containsKey(componentType);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(final Class<T> componentType) {
        final Component component = componentsByType.get(componentType);

        if (component != null) {
            return (T) component;
        }
        return null;
    }

    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }
}
