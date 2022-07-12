package com.barelyconscious.game.entity.item;


import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.resources.WSprite;
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
    private final ItemClassType itemClassType;
    private final Set<ItemTag> tags;
    private final boolean isConsumable;
    private final boolean isStackable;
    private final String name;
    private final String description;
    private final WSprite sprite;
    private final List<ItemRequirement> requirements;
    private final List<ItemProperty> properties;

    public boolean hasTag(final ItemTag itemTag) {
        return tags.contains(itemTag);
    }

    public final Delegate<ItemContext> onUse = new Delegate<>();

    public static class ItemContext {
        public final Map<String, Object> context = new HashMap<>();
    }
}
