package com.barelyconscious.worlds.game.tech;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.ItemActor;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.tags.TechItemTag;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MotherboardSocket extends Actor {

    private MotherboardSocketType socketType;
    private ItemActor socketedItem;
    private float powerConsumption;

    /**
     * Maps a socket type to the tag that describes it.
     */
    private static final Map<MotherboardSocketType, TechItemTag> SOCKET_TYPE_TO_TAG = Map.of(
        MotherboardSocketType.PROCESSOR, TechItemTag.PROCESSOR_TAG,
        MotherboardSocketType.MEMORY, TechItemTag.MEMORY_TAG,
        MotherboardSocketType.STORAGE, TechItemTag.STORAGE_TAG,
        MotherboardSocketType.DATA_BUS, TechItemTag.DATA_BUS_TAG,
        MotherboardSocketType.NETWORK, TechItemTag.NETWORK_TAG,
        MotherboardSocketType.POWER, TechItemTag.POWER_TAG);

    /**
     * Determines if the item can be slotted into this socket.
     *
     * @param item the item to check.
     * @return true if the item can be slotted into this socket, false otherwise.
     */
    public boolean fitsSocket(final ItemActor item) {
        var requiredTag = SOCKET_TYPE_TO_TAG.getOrDefault(socketType, null);
        return item.hasTag(requiredTag);
    }

    public boolean isSocketed() {
        return socketedItem != null;
    }

    public void socketItem(final ItemActor item) {
        if (!fitsSocket(item)) {
            throw new IllegalArgumentException("Item does not fit into this socket.");
        }

        socketedItem = item;
    }
}
