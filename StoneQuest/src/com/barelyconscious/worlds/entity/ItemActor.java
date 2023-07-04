package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.ItemRequirement;
import com.barelyconscious.worlds.game.item.ItemTag;
import com.barelyconscious.worlds.game.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.game.item.tags.StackableItemTag;
import com.barelyconscious.worlds.game.resources.Resources;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ItemActor extends Actor {

    private int itemLevel;
    private String description;
    private Set<ItemTag> tags;
    private Resources.Sprite_Resource sprite;
    private List<ItemRequirement> requirements;
    private List<ItemProperty> properties;

    public boolean isConsumable() {
        return tags.stream().anyMatch(t -> t instanceof ConsumableItemTag);
    }

    public boolean isStackable() {
        return tags.stream().anyMatch(t -> t instanceof StackableItemTag);
    }

    public boolean hasTag(final ItemTag itemTag) {
        return tags.contains(itemTag);
    }


}
