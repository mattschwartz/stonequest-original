package com.barelyconscious.worlds.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A territory is a collection of tiles that can be explored by the player.
 * enters a territory.
 *
 * has all the tiles, the buildings, etc.
 */
public class WildernessLevel extends Actor {

    private List<EntityActor> entities = new ArrayList<>();
}
