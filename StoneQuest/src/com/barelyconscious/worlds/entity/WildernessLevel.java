package com.barelyconscious.worlds.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A territory is a collection of tiles that can be explored by the player.
 * enters a territory.
 *
 * has all the tiles, the buildings, etc.
 */
public class WildernessLevel extends Actor {

    @Getter
    private final List<EntityActor> entities = new ArrayList<>();

    @Getter
    private final List<ResourceDeposit> deposits = new ArrayList<>();

    @Getter
    private final List<BuildingActor> buildings = new ArrayList<>();

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
}