package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.entity.wilderness.WildernessLevel;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.rng.TerritoryGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class WildernessSystem implements GameSystem {

    @Getter
    @Builder
    public static final class WildernessState {
        private final Map<Vector, Territory> worldMap = new HashMap<>();
    }

    public Delegate<TerritoryAdded> delegateOnTerritoryAdded = new Delegate<>();
    @AllArgsConstructor
    public static class TerritoryAdded {
        public final Vector location;
        public final Territory newTerritory;
    }

    public void update(EventArgs args) {
        var worldMap = args.getGameState()
            .getWildernessState()
            .getWorldMap();
        for (Territory territory : worldMap.values()) {
            territory.update(args);
        }
    }

    // only for now(tm)
    @Deprecated
    public void putTerritory(Vector position, Territory territory) {
        var worldMap = GameInstance.instance()
            .getGameState()
            .getWildernessState()
            .getWorldMap();
        worldMap.put(position, territory);
    }

    public WildernessLevel getWildernessLevel(
        Vector fromPosition,
        Vector toPosition,
        int level
    ) {
        Territory fromTerritory;
        Territory toTerritory;
        var worldMap = GameInstance.instance()
            .getGameState()
            .getWildernessState()
            .getWorldMap();

        if (!worldMap.containsKey(fromPosition)) {
            fromTerritory = TerritoryGenerator.territoryBuilder()
                .at(fromPosition)
                .level(level)
                .generate();
            worldMap.put(fromPosition, fromTerritory);
            delegateOnTerritoryAdded.call(new TerritoryAdded(fromPosition, fromTerritory));
        } else {
            fromTerritory = worldMap.get(fromPosition);
        }

        if (!worldMap.containsKey(toPosition)) {
            toTerritory = TerritoryGenerator.territoryBuilder()
                .at(toPosition)
                .level(level)
                .generate();
            worldMap.put(toPosition, toTerritory);
            delegateOnTerritoryAdded.call(new TerritoryAdded(toPosition, toTerritory));
        } else {
            toTerritory = worldMap.get(toPosition);
        }

        var fromDirection = fromPosition.minus(toPosition);

        return TerritoryGenerator.wildernessBuilder()
            .fromTerritory(fromTerritory, fromDirection)
            .territory(toTerritory)
            .generate();
    }


}
