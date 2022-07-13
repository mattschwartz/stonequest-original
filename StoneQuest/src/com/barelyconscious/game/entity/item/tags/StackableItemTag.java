package com.barelyconscious.game.entity.item.tags;

import com.barelyconscious.game.entity.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StackableItemTag implements ItemTag {
    STACKABLE("Stackable"),
    ;

    private final String tagName;
}
