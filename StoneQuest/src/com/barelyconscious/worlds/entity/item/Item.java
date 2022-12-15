package com.barelyconscious.worlds.entity.item;


import com.barelyconscious.worlds.delegate.Delegate;
import com.barelyconscious.worlds.entity.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.entity.item.tags.StackableItemTag;
import com.barelyconscious.worlds.entity.resources.Resources;
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
    private final Resources.Sprite_Resource sprite;
    private final List<ItemRequirement> requirements;
    private final List<ItemProperty> properties;

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
