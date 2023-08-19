package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;

/**
 * A building is a structure that can be built by the player.
 * Buildings can be used to produce items, store items, or
 * provide a service.
 */
public class Building extends Actor {

    public Building(
        String name,
        Vector transform
    ) {
        super(name, transform);
    }
}
