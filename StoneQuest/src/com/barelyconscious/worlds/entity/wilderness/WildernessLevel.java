package com.barelyconscious.worlds.entity.wilderness;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.BuildingActor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.ResourceDeposit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A territory is a collection of tiles that can be explored by the player.
 * enters a territory.
 * <p>
 * has all the tiles, the buildings, etc.
 */
public class WildernessLevel extends Actor {

    @Getter
    private final Territory territory;

    @Getter
    private final List<EntityActor> entities = new ArrayList<>();

    @Getter
    private final List<ResourceDeposit> deposits = new ArrayList<>();

    @Getter
    private final List<BuildingActor> buildings = new ArrayList<>();

    public WildernessLevel(Territory territory) {
        super("Level " + territory.getTerritoryLevel() + " territory",
            Vector.ZERO);
        this.territory = territory;
    }

    public void addEntity(EntityActor entity) {
        this.entities.add(entity);
        addChild(entity);
    }

    public void addDeposit(ResourceDeposit deposit) {
        this.deposits.add(deposit);
        addChild(deposit);
    }

    public void addBuilding(BuildingActor building) {
        this.buildings.add(building);
        addChild(building);
    }

    @Override
    public void removeChild(Actor child) {
        super.removeChild(child);
        if (child instanceof EntityActor) {
            entities.remove(child);
        } else if (child instanceof ResourceDeposit) {
            deposits.remove(child);
        } else if (child instanceof BuildingActor) {
            buildings.remove(child);
        }
    }
}
