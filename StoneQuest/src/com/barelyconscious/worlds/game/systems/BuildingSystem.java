package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.entity.ResourceDeposit;
import com.barelyconscious.worlds.entity.Village;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.item.Item;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingSystem {

    enum BuildingType {
        HARVESTER,
        PRODUCTION,
        CRAFTING
    }

    private static final Map<BuildingType, List<Item>> CONSTRUCTION_COST_BY_TYPE = new HashMap<>() {{
        put(BuildingType.HARVESTER, Lists.newArrayList(
            GameItems.LUMBER.toItem(),
            GameItems.LUMBER.toItem(),
            GameItems.LUMBER.toItem()
        ));
        put(BuildingType.PRODUCTION, Lists.newArrayList());
        put(BuildingType.CRAFTING, Lists.newArrayList());
    }};

    /**
     * Constructs a new harvester building which will immediately begin harvesting the
     * specified resource node.
     *
     * @param resourceNode the resource node to harvest
     * @param village the village that will own the building
     */
    public HarvesterBuilding constructHarvesterBuilding(
        ResourceDeposit resourceNode,
        Village village
    ) {
        List<Item> constructionCost = CONSTRUCTION_COST_BY_TYPE.get(BuildingType.HARVESTER);

        var building = new HarvesterBuilding(resourceNode.name + " Harvester", resourceNode.transform,
            resourceNode, village.getStockpile(), 3);

        village.getBuildings().add(building);

        GameInstance.instance().getWorld().addActor(building);

        return building;
    }
}
