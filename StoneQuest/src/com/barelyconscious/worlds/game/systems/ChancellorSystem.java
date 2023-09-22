package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.BuildingActor;
import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.entity.Settlement;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;
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
        World world = GameInstance.instance().getWorld();

        var settlement = world.territoryToSettlement.get(territory);
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

        territory.getBuildings().add(building);
        settlement.getBuildings().add(building);

        territory.getAvailableResources().remove(resource);

        if (!world.territoryToBuildings.containsKey(territory)) {
            world.territoryToBuildings.put(territory, Lists.newArrayList(building));
        } else {
            world.territoryToBuildings.get(territory).add(building);
        }

        return building;
    }

    /**
     * doesn't add to world
     */
    public void claimTerritory(Territory territory, Settlement settlement) {
        World world = GameInstance.instance().getWorld();

        world.territoryToSettlement.put(territory, settlement);

        if (world.settlementToTerritories.containsKey(settlement)) {
            world.settlementToTerritories.get(settlement).add(territory);
        } else {
            List<Territory> settlementTerritories = Lists.newArrayList(territory);
            world.settlementToTerritories.put(settlement, settlementTerritories);
        }
    }

    /**
     * Returns a list of all buildings within the given territory
     */
    public List<BuildingActor> getBuildingsWithinTerritory(Territory territory) {
        return GameInstance.instance().getWorld().territoryToBuildings.get(territory);
    }

    /**
     * Returns a list of all territories owned by the given village
     */
    public List<Territory> getTerritoriesOwnedByVillage(Settlement village) {
        return GameInstance.instance().getWorld().settlementToTerritories.get(village);
    }
}
