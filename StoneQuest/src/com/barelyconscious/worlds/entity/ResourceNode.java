package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.item.Item;

import java.util.List;

/**
 * A node which can be harvested for resources. It has a finite stock before being exhausted
 */
public abstract class ResourceNode extends Actor {

    /**
     * By prospecting, the player can determine what resources are available to be gathered
     * from this node. The actor's capabilities determine how much can be determined.
     */
    public abstract List<Item> prospect(Actor prospector);

    /**
     * Removes an item from the stock based on the harvester's capabilities. More
     * skilled harvesters will grant better items from the stock.
     * todo - maybe this should be capable of returning multiple at a time?
     */
    public abstract Item harvest(Actor harvester);

    public ResourceNode(final String name, final Vector transform) {
        super(name, transform);
    }
}
