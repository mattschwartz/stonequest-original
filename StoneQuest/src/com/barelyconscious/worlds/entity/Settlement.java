package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.game.Inventory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
        stockpile = new Inventory(128);
        citizens = new ArrayList<>();
        buildings = new ArrayList<>();
        ownedTerritories = new ArrayList<>();
    }

    public void update(EventArgs args) {
    }
}
