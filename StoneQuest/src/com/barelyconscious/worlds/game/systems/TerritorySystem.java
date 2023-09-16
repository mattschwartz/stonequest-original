package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.entity.Building;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.Village;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerritorySystem implements GameSystem {

    public Map<Territory, List<Building>> territoryToBuildings = new HashMap<>();
    public Map<Village, List<Territory>> villageToTerritories = new HashMap<>();
    public List<Territory> allTerritories = new ArrayList<>();

    public void addTerritory(Territory territory, @Nullable Village village) {
        allTerritories.add(territory);
        villageToTerritories.computeIfAbsent(village, k -> new ArrayList<>()).add(territory);
    }

    /**
     * Returns a list of all buildings within the given territory
     */
    public List<Building> getBuildingsWithinTerritory(Territory territory) {
        return territoryToBuildings.get(territory);
    }

    /**
     * Returns a list of all buildings within the given village
     */
    public List<Building> getBuildingsWithinVillage(Village village) {
        return villageToTerritories.get(village).stream()
            .map(territory -> territoryToBuildings.get(territory))
            .reduce((a, b) -> {
                a.addAll(b);
                return a;
            })
            .orElseGet(() -> new ArrayList<>(0));
    }

    /**
     * Returns a list of all territories owned by the given village
     */
    public List<Territory> getTerritoriesOwnedByVillage(Village village) {

        return villageToTerritories.get(village);
    }
}
