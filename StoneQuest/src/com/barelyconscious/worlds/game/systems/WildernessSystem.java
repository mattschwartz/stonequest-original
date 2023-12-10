package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.WildernessLevel;
import com.barelyconscious.worlds.game.rng.TerritoryGenerator;

import java.util.HashMap;
import java.util.Map;

public class WildernessSystem implements GameSystem {

    // World is made up of wilderness levels
    // todo: territory blueprints need to be generated somehow based on spanning biomes and things
    private final Map<Vector, Territory> worldMap = new HashMap<>();

    public WildernessLevel getWildernessLevel(Vector position, int level) {
        Territory territory;
        if (!worldMap.containsKey(position)) {
            territory = TerritoryGenerator.territoryBuilder()
                .at(position)
                .level(level)
                .generate();
            worldMap.put(position, territory);
        } else {
            territory = worldMap.get(position);
        }

        return TerritoryGenerator.wildernessBuilder()
            .territory(territory)
            .generate();
    }


}
