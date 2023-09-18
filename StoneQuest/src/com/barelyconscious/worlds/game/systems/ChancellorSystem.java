package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Building;
import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.Settlement;
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
    public Map<Settlement, List<Territory>> villageToTerritories = new HashMap<>();
    public List<Territory> allTerritories = new ArrayList<>();

    /**
     * To construct a harvester building, we need to know
     * - the territory in which to construct the harvester
     * - the resource being harvested
     * - the owning settlement of the new building
     * @param territory
     */
    public void constructHarvesterBuilding(
        Territory territory
    ) {

        Settlement owningVillage = territory.getOwningSettlement();
        if (owningVillage == null) {
            log.error("Cannot construct a harvester building on a territory that does not belong to a village.");
            return;
        }
    }

    public void addTerritory(Territory territory, @Nullable Settlement village) {
        territory.setOwningSettlement(village);
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
    public List<Building> getBuildingsWithinVillage(Settlement village) {
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
    public List<Territory> getTerritoriesOwnedByVillage(Settlement village) {
        return villageToTerritories.get(village);
    }
}
