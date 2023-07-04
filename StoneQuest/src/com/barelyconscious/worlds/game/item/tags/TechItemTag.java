package com.barelyconscious.worlds.game.item.tags;

import com.barelyconscious.worlds.game.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Describes the type of socket an item can slot into on a Vitaboard.
 */
@Getter
@AllArgsConstructor
public enum TechItemTag implements ItemTag {

    PROCESSOR_TAG("ProcessorSocket"),
    MEMORY_TAG("MemorySocket"),
    STORAGE_TAG("StorageSocket"),
    DATA_BUS_TAG("Data BusSocket"),
    NETWORK_TAG("NetworkSocket"),
    POWER_TAG("PowerSocket")
    ;

    private final String tagName;
}
