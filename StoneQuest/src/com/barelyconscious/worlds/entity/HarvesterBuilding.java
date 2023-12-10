package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.gamedata.GameItems;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.Clock;
import java.util.List;

public class HarvesterBuilding extends BuildingActor {

    public Delegate<ItemProducedEvent> delegateOnItemProduced = new Delegate<>();
    public Delegate<ProductionHaltedEvent> delegateOnProductionHalted = new Delegate<>();

    public static class ProductionHaltedEvent {
    }

    @AllArgsConstructor
    public static class ItemProducedEvent {
        public final Item item;
        public final int amount;
    }

    /**
     * The resource being processed.
     */
    private final TerritoryResource resource;

    private final BuildingTier tier;

    /**
     * Where items go as they get produced
     */
    @Getter
    private final Inventory stockpile;

    /**
     * Accumulates every second. at 100%, an item is produced.
     */
    private double materialsHarvested = 0;
    private double itemsPerSecond;

    private boolean isProductionEnabled = true;
    private long timeOfLastProductionMillis;

    private final Clock clock;

    public HarvesterBuilding(
        String name,
        BuildingTier tier,
        TerritoryResource resource,
        Inventory stockpile
    ) {
        super(name, Vector.ZERO);
        this.tier = tier;
        this.resource = resource;
        this.stockpile = stockpile;

        itemsPerSecond = (tier.itemsPerMinute * resource.richness) / 60.0;
        clock = Clock.systemDefaultZone();

        timeOfLastProductionMillis = clock.millis();
    }

    @Override
    public void update(EventArgs eventArgs) {
        super.update(eventArgs);
        if (!isProductionEnabled) {
            return;
        }

        long current = clock.millis();
        if (current - timeOfLastProductionMillis > 1000) { // a second has passed
            // to preserve cpu delay, we need to carry over the remainder of the previous second
            long carryover = current - timeOfLastProductionMillis - 1000;
            timeOfLastProductionMillis = current;
            if (carryover > 0) {
                timeOfLastProductionMillis -= carryover;
            }

            // accumulate some material
            materialsHarvested += itemsPerSecond;

            int wholeItemsHarvestedSoFar = (int) materialsHarvested;
            if (wholeItemsHarvestedSoFar >= 1) {
                var item = resource.item;

                materialsHarvested -= wholeItemsHarvestedSoFar;
                stockpile.addItem(item);
                delegateOnItemProduced.call(new ItemProducedEvent(item, wholeItemsHarvestedSoFar));
            }
        }
    }

    public static BuildingTier BUILDING_TIER_1 = new BuildingTier(
        Lists.newArrayList(
            Pair.of(GameItems.WOOD.toItem(), 10),
            Pair.of(GameItems.STONE.toItem(), 10)),
        Lists.newArrayList(
            Pair.of(GameItems.WOOD.toItem(), 5),
            Pair.of(GameItems.STONE.toItem(), 5),
            Pair.of(GameItems.IRON_INGOT.toItem(), 5)),
        15 /* items per minute */);
    public static BuildingTier BUILDING_TIER_2 = new BuildingTier(
        Lists.newArrayList(
            Pair.of(GameItems.WOOD.toItem(), 15),
            Pair.of(GameItems.STONE.toItem(), 15),
            Pair.of(GameItems.IRON_INGOT.toItem(), 5)),
        null, // no tier3
        15 /* items per minute */);


    @Getter
    @AllArgsConstructor
    public static class BuildingTier {
        private List<Pair<Item, Integer>> materialCost;
        @Nullable
        private List<Pair<Item, Integer>> upgradeCost; // cost to get to the next tier
        private int itemsPerMinute;
    }
}
