package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.Inventory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Village extends Actor {

    /**
     * All the resources owned by the village
     */
    private final Inventory stockpile;

    /**
     * All the entities that are citizens of this village
     */
    private final List<EntityActor> citizens;
    /**
     * All the buildings within this village
     */
    private final List<Building> buildings;
    /**
     * The territories owned by this village
     */
    private final List<Territory> ownedTerritories;

    public Village(String name, Vector transform) {
        super(name, transform);
        // todo -should be a FlexibleInventory that is unbounded in size
        stockpile = new Inventory(128);
        citizens = new ArrayList<>();
        buildings = new ArrayList<>();
        ownedTerritories = new ArrayList<>();
    }
}
