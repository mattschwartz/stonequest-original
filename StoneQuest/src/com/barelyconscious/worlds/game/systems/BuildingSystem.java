package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.entity.Settlement;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.GameItems;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

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
}
