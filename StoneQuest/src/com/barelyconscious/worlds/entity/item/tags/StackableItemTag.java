package com.barelyconscious.worlds.entity.item.tags;

import com.barelyconscious.worlds.entity.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StackableItemTag implements ItemTag {
    STACKABLE("Stackable"),
    ;

    private final String tagName;
}
