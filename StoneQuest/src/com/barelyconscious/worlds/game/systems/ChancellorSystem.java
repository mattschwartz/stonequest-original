package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.entity.Building;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.Village;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The system that controls all the chancellor stuff like
 * constructing buildings, expanding territories, and
 * managing the player's village.
 */
@Log4j2
public class ChancellorSystem implements GameSystem {

    public Map<Territory, List<Building>> territoryToBuildings = new HashMap<>();
    public Map<Village, List<Territory>> villageToTerritories = new HashMap<>();
    public List<Territory> allTerritories = new ArrayList<>();

    public void constructHarvesterBuilding(
        Territory territory
    ) {
        Village owningVillage = territory.getOwningVillage();
        if (owningVillage == null) {
            log.error("Cannot construct a harvester building on a territory that does not belong to a village.");
            return;
        }
    }

    public void addTerritory(Territory territory, @Nullable Village village) {
        territory.setOwningVillage(village);
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
