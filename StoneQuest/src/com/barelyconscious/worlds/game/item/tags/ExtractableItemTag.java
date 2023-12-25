package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtractableItemTag implements ItemTag {
    EXTRACTABLE("Extractable"),
        ;

    private final String tagName;
}
