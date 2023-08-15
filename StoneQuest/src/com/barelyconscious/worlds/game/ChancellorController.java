package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.entity.BuildingActor;
import com.barelyconscious.worlds.entity.ResourceNode;
import com.barelyconscious.worlds.entity.VillageActor;

import java.util.List;

/**
 * The player's interface into the faction/government of the
 * village. The player can interact with the village's government
 * through this actor.
 *
 */
public class ChancellorController {

    private VillageActor village;

    public BuildingActor buildGatheringBuilding(
        ResourceNode resourceNode
    ) {
        World world = GameInstance.instance()
            .getWorld();

        // village.addBuilding(building);

        return null;
    }
}
