package com.barelyconscious.worlds.game.types;

import com.barelyconscious.worlds.game.item.Item;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TerritoryResource {

    public final Item item;

    /**
     * Determines the deposit frequency and capacity spawned of this resource.
     *
     * Also determines how many items are harvested per minute.
     */
    public final double richness;
}
