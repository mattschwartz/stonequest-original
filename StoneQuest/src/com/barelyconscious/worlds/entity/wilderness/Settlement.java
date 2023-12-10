package com.barelyconscious.worlds.entity.wilderness;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.BuildingActor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.HarvesterBuilding;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Getter
@Log4j2
public class Settlement extends Actor {

    /**
     * All the resources owned by the settlement
     */
    private final Inventory stockpile;

    /**
     * All the entities that are citizens of this settlement
     */
    private final List<EntityActor> citizens;
    /**
     * All the buildings within this settlement
     */
    private final List<BuildingActor> buildings;
    /**
     * The territories owned by this settlement
     */
    private final List<Territory> ownedTerritories;

    public Settlement(String name, Vector transform) {
        super(name, transform);
        // todo -should be a FlexibleInventory that is unbounded in size
        stockpile = new Inventory(64);
        citizens = new ArrayList<>();
        buildings = new ArrayList<>();
        ownedTerritories = new ArrayList<>();
    }

    @Builder
    public static class ClaimTerritoryRequest {
        private final Territory territory;
    }

    @AllArgsConstructor
    public static class ClaimTerritoryResponse {
        public final boolean isSuccessful;
        public final String exceptionMessage;
    }

    public ClaimTerritoryResponse claimTerritory(ClaimTerritoryRequest request) {
        if (request.territory.getOwningSettlement() != null) {
            if (request.territory.getOwningSettlement() == this) {
                return new ClaimTerritoryResponse(false, "This settlement already owns this territory.");
            }
            return new ClaimTerritoryResponse(false, "Territory is already owned by " +
                request.territory.getOwningSettlement().getName());
        }
        ownedTerritories.add(request.territory);
        return new ClaimTerritoryResponse(true, "Successful");
    }

    @Builder
    public static class ConstructBuildingRequest {
        public final Territory territory;
        public final TerritoryResource resource;
    }

    @AllArgsConstructor
    public static class ConstructBuildingResponse {

    }

    public ConstructBuildingResponse constructBuilding(ConstructBuildingRequest request) {
        Territory territory = request.territory;
        TerritoryResource resource = request.resource;

        if (!territory.getAvailableResources().contains(resource)) {
            log.error("Cannot construct a harvester building on a resource that is not available in the territory");
            return null;
        }

        var building = new HarvesterBuilding(
            resource.item.getName() + " Harvester",
            HarvesterBuilding.BUILDING_TIER_1,
            resource,
            getStockpile());
        territory.getBuildings().add(building);
        getBuildings().add(building);

        territory.getAvailableResources().remove(resource);

        return new ConstructBuildingResponse();
    }

    public void update(EventArgs args) {
    }
}
