package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Building;
import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.Settlement;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;

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
    /**
     * Signifies the relationship between every settlement and the territories it owns.
     *
     * the null settlement corresponds to neutral territories
     */
    public Map<Settlement, List<Territory>> settlementToTerritories = new HashMap<>();
    /**
     * Signifies the relationship between every territory and the settlement that owns it.
     */
    public Map<Territory, Settlement> territoryToSettlement = new HashMap<>();
    public List<Territory> allTerritories = new ArrayList<>();

    /**
     * To construct a harvester building, we need to know
     * - the territory in which to construct the harvester
     * - the resource being harvested
     * - the owning settlement of the new building
     *
     * @param territory
     */
    public @Nullable HarvesterBuilding constructHarvester(
        Territory territory,
        TerritoryResource resource,
        Vector location
    ) {
        var settlement = territoryToSettlement.get(territory);
        if (settlement == null) {
            log.error("Cannot construct a harvester building in a territory that does not belong to a settlement");
            return null;
        }
        if (!territory.getAvailableResources().contains(resource)) {
            log.error("Cannot construct a harvester building on a resource that is not available in the territory");
            return null;
        }

        List<Pair<Item, Integer>> constructionCost = HarvesterBuilding.BUILDING_TIER_1.getMaterialCost();

        var building = new HarvesterBuilding(
            resource.item.getName() + " Harvester",
            location,
            HarvesterBuilding.BUILDING_TIER_1,
            resource,
            settlement.getStockpile());

        territory.addChild(building);
        settlement.getBuildings().add(building);

        territory.getAvailableResources().remove(resource);

        if (!territoryToBuildings.containsKey(territory)) {
            territoryToBuildings.put(territory, Lists.newArrayList(building));
        } else {
            territoryToBuildings.get(territory).add(building);
        }

        return building;
    }

    /**
     * doesn't add to world
     */
    public void addTerritory(Territory territory, @Nullable Settlement settlement) {
        allTerritories.add(territory);
        territoryToSettlement.put(territory, settlement);

        if (settlementToTerritories.containsKey(settlement)) {
            settlementToTerritories.get(settlement).add(territory);
        } else {
            List<Territory> settlementTerritories = Lists.newArrayList(territory);
            settlementToTerritories.put(settlement, settlementTerritories);
        }
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
        return settlementToTerritories.get(village).stream()
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
        return settlementToTerritories.get(village);
    }
}
