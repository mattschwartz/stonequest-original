package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.types.Biome;
import com.barelyconscious.worlds.game.types.Climate;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Territory extends Actor {

    private final Biome biome;
    private final Climate climate;
    private final double hostility;
    private final double corruption;

    @Getter
    @Setter
    private Settlement owningSettlement;
    private final List<Building> buildings = new ArrayList<>();

    /**
     * Item types available to be gathered.
     * Resources cannot be depleted.
     * While exploring this territory, nodes can be generated matching
     * these resources.
     * <p>
     * when a building is placed on a resource, it is consumed and removed
     * from the availableResources
     *
     * remove a resource when a harvester building is placed on it
     */
    private final List<TerritoryResource> availableResources;

    public Territory(
        String name,
        Vector transform,
        Biome biome,
        Climate climate,
        double hostility,
        double corruption,
        List<TerritoryResource> availableResources
    ) {
        super(name, transform);
        this.biome = biome;
        this.climate = climate;
        this.hostility = hostility;
        this.corruption = corruption;
        this.availableResources = availableResources;
    }

    public List<TerritoryResource> getAvailableResources() {
        return availableResources;
    }
}
