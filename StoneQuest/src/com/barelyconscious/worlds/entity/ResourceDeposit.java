package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.item.Item;

import java.util.List;

/**
 * A node which can be harvested for resources. It has a finite stock before being exhausted
 */
public class ResourceDeposit extends Actor {

    private final List<Item> items;

    /**
     * The skill required to prospect and harvest this node.
     */
//    private SkillRequirement skillRequirement;
    public ResourceDeposit(String name, Vector transform, List<Item> items) {
        super(name, transform);
        this.items = items;
    }

    /**
     * By prospecting, the player can determine what resources are available to be gathered
     * from this node. The actor's capabilities determine how much can be determined.
     */
    public List<Item> prospect(Actor prospector) {
//        if (!skillRequirement.meetsRequirement(prospector)) {
//            // do something
//        }
        return items;
    }

    /**
     * Removes an item from the stock based on the harvester's capabilities. More
     * skilled harvesters will grant better items from the stock.
     * todo - maybe this should be capable of returning multiple at a time?
     */
    public Item harvest(Actor harvester) {
//        if (!skillRequirement.meetsRequirement(harvester)) {
//            // do something
//        }
        if (!items.isEmpty()) {
            // test actor's harvester skill against the node's difficulty
            return items.remove(0);
        }

        return null;
    }
}
