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

    public WildernessLevel getWildernessLevel(
        Vector fromPosition,
        Vector position,
        int level
    ) {
        Territory fromTerritory;
        Territory toTerritory;

        if (!worldMap.containsKey(fromPosition)) {
            fromTerritory = TerritoryGenerator.territoryBuilder()
                .at(fromPosition)
                .level(level)
                .generate();
            worldMap.put(fromPosition, fromTerritory);
        } else {
            fromTerritory = worldMap.get(fromPosition);
        }

        if (!worldMap.containsKey(position)) {
            toTerritory = TerritoryGenerator.territoryBuilder()
                .at(position)
                .level(level)
                .generate();
            worldMap.put(position, toTerritory);
        } else {
            toTerritory = worldMap.get(position);
        }

        var fromDirection = fromPosition.minus(position);

        // (0, 1) (1, 1) (2, 1)
        // (0, 2) (1, 2) (2, 2)

        return TerritoryGenerator.wildernessBuilder()
            .fromTerritory(fromTerritory, fromDirection)
            .territory(toTerritory)
            .generate();
    }


}
