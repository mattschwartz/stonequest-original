package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.components.Component;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A thing which can be spawned into the game world.
 * <p>
 * todo: components' inheritance is messy
 */
@Log4j2
public class Actor {

    private Actor parent = null;
    @Getter
    private final Set<Actor> children = new HashSet<>();

    public final String id;
    @Getter
    public final String name;

    private final Map<Class<? extends Component>, List<Component>> componentsByType;
    //    private final List<Component> components;
    private final List<Component> allComponents;

    private boolean isEnabled = true;
    private boolean isDestroying = false;

    private Vector transform;
    public Vector facing = Vector.UP;

    // todo: game owns instantiation on actors?
    public void init() {
    }

    // todo (p0) implement this better
    public Box getBoundingBox() {
        return new Box(
            (int) transform.x,
            (int) transform.x + 32,
            (int) transform.y,
            (int) transform.y + 32);
    }

    public Actor() {
        this(Vector.ZERO);
    }

    public Actor(final Vector transform) {
        this(null, transform);
    }

    public Vector getTransform() {
        if (parent != null) {
            return parent.transform.plus(transform);
        }
        return transform;
    }

    public void setTransform(Vector other) {
        this.transform = other;
    }

    /**
     * todo: need to actually implement parent logic
     *
     * @param newParent
     * @return
     */
    public Actor setParent(final Actor newParent) {
        Actor oldParent = parent;
        if (oldParent != null) {
            // do anything needed here
            oldParent.children.remove(this);
        }

        parent = newParent;

        if (parent != null) {
            parent.children.add(this);
        }

        return oldParent;
    }

    public void addChild(final Actor child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(final Actor child) {
        if (child.parent != this) {
            throw new IllegalArgumentException("child is not a child of this actor");
        }

        child.setParent(null);
        children.remove(child);
    }

    public Actor(
        final String name,
        final Vector transform
    ) {
        checkArgument(transform != null, "transform is null");

        this.id = UUID.randomUUID().toString();

        if (StringUtils.isBlank(name)) {
            this.name = "Actor#" + id;
        } else {
            this.name = name;
        }

        this.transform = transform;
        this.allComponents = new ArrayList<>();

        this.componentsByType = new HashMap<>();
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

        List<Component> components;
        if (!componentsByType.containsKey(component.getClass())) {
            components = new ArrayList<>();
        } else {
            components = componentsByType.get(component.getClass());
        }
        components.add(component);

        component.setParent(this);
        componentsByType.put(component.getClass(), components);
        allComponents.add(component);
        return true;
    }

    /**
     * Attempts to remove the provided component. If removed, the provided component's parent is also set to null.
     */
    public void removeComponent(
        @NonNull final Component componentToRemove
    ) {
        List<Component> components = componentsByType.get(componentToRemove.getClass());
        if (components != null) {
            Optional<Component> first = components.stream()
                .filter(t -> t == componentToRemove)
                .findFirst();
            first.ifPresent(t -> {
                t.setParent(null);
                components.remove(t);
            });
        }
    }

    // returns the first one, not necessarily the one you want...
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(final Class<T> componentType) {
        final List<T> components = (List<T>) componentsByType.get(componentType);

        if (components != null && !components.isEmpty()) {
            return components.get(0);
        }
        return null;
    }

    /**
     * Attempts to get the component of the provided type. If the component does not exist, the provided callback is
     * not called.
     *
     * @param componentType the type of component to get
     * @param callback      the callback to call if the component exists
     * @param <T>           the type of component to get
     */
    public <T extends Component> void tryGetComponent(final Class<T> componentType, final Function<T, Void> callback) {
        final var component = getComponent(componentType);
        if (component != null) {
            try {
                callback.apply(component);
            } catch (Exception ex) {
                log.error("Error calling callback", ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> List<T> getComponentsOfType(final Class<T> componentType) {
        return (List<T>) componentsByType.get(componentType);
    }

    public List<Component> getComponents() {
        return allComponents;
    }
}
