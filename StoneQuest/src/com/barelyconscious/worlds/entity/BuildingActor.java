package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;

/**
 * A building is a structure that can be built by the player.
 * Buildings can be used to produce items, store items, or
 * provide a service.
 */
public class BuildingActor extends Actor {

    public BuildingActor(
        String name,
        Vector transform
    ) {
        super(name, transform);
    }

    public void update(EventArgs eventArgs) {
        // todo - update building
    }
}
