package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.item.Item;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Territory extends Actor {

    @AllArgsConstructor
    public static class TerritoryResource {
        public final Item resource;
        public final double richness;
    }

    /**
     * Item types available to be gathered.
     * Resources cannot be depleted.
     * While exploring this territory, nodes can be generated matching
     * these resources.
     * <p>
     * when a building is placed on a resource, it is consumed and removed
     * from the availableResources
     */
    private final List<TerritoryResource> availableResources;

    public Territory(
        String name,
        Vector transform,
        List<TerritoryResource> availableResources
    ) {
        super(name, transform);
        this.availableResources = availableResources;
    }

    /**
     * Resources that are being gathered or on hold and either way
     * not available to be gathered.
     * <p>
     * for example if an area is rich in iron and the player builds
     * an iron mine, then iron deposits will no longer spawn on the map
     */
    private final List<TerritoryResource> consumedResources
        = new ArrayList<>();

    public List<TerritoryResource> getAvailableResources() {
        return availableResources;
    }
}
