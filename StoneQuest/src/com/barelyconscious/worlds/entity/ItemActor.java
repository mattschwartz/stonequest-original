package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.game.Requirement;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.ItemTag;
import com.barelyconscious.worlds.game.item.tags.ConsumableItemTag;
import com.barelyconscious.worlds.game.item.tags.StackableItemTag;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ItemActor extends Actor {

    protected int itemLevel;
    protected String description;
    protected Set<ItemTag> tags = new HashSet<>();
    protected BetterSpriteResource sprite;
    protected List<Requirement> requirements = new ArrayList<>();
    protected List<ItemProperty> properties = new ArrayList<>();

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
