package com.barelyconscious.worlds.game.item;


import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.game.item.tags.StackableItemTag;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class Item {

    private final int itemId;
    private final int itemLevel;
    private final Set<ItemTag> tags;
    private final String name;
    private final String description;
    private final BetterSpriteResource sprite;
    private final List<ItemRequirement> requirements;
    private final List<ItemProperty> properties;

    /**
     * Checks if the provided entity meets the requirements to use this item. Always
     *
     * @param entityActor
     * @return
     */
    public boolean meetsRequirements(final EntityActor entityActor) {
        return requirements.stream().allMatch(r -> r.meetsRequirement(entityActor));
    }

    /**
     * Applies the properties of this item to the provided entity. Typically used when
     * equipping an item to an entity, or when an item's temporary effects are applied.
     * @param entityActor
     */
    public void applyProperties(final EntityActor entityActor) {
        properties.forEach(p -> p.applyProperty(entityActor));
    }

    /**
     * Removes the properties of this item from the provided entity. Typically used when
     * removing an item from an entity's equipment, or when an item's temporary effects
     * wear off.
     * @param entityActor
     */
    public void removeProperties(final EntityActor entityActor) {
        properties.forEach(p -> p.removeProperty(entityActor));
    }

    public boolean isConsumable() {
        return tags.stream().anyMatch(t -> t instanceof ConsumableItemTag);
    }
    public boolean isStackable() {
        return tags.stream().anyMatch(t -> t instanceof StackableItemTag);
    }

    public boolean hasTag(final ItemTag itemTag) {
        return tags.contains(itemTag);
    }

    public final Delegate<ItemContext> onUse = new Delegate<>();

    public static class ItemContext {
        public final Map<String, Object> context = new HashMap<>();
    }
}
