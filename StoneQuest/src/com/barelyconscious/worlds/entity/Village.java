package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Village extends Actor {

    /**
     * All the resources owned by the village
     */
    private final Inventory stockpile;

    // keeps track of
    // stock, how much wood and ore and things are in the village's stockpile
    /**
     * All the entities that are citizens of this village
     */
    private final List<EntityActor> citizens;
    // politics
    /**
     * All the buildings within this village
     */
    private final List<Building> buildings;
    // job postings
    // reputation
    /**
     * Territories this village has claim of
     */
    private final List<Territory> territories;
    private final Clock clock;

    public Village(String name, Vector transform) {
        super(name, transform);
        // todo -should be a FlexibleInventory that is unbounded in size
        stockpile = new Inventory(128);
        citizens = new ArrayList<>();
        buildings = new ArrayList<>();
        territories = new ArrayList<>();
        clock = Clock.systemDefaultZone();

        addComponent(new StockCollectorComponent(this));
    }

    /**
     * Periodically goes around the buildings and collects
     * all their stock.
     */
    private class StockCollectorComponent extends Component {

        private long timeOfLastProductionMillis;
        private final double timeToProduceSeconds = 5;

        public StockCollectorComponent(Actor parent) {
            super(parent);
            timeOfLastProductionMillis = clock.millis();
        }

        @Override
        public void update(EventArgs eventArgs) {
            super.update(eventArgs);

            long current = clock.millis();
            if (current - timeOfLastProductionMillis > timeToProduceSeconds * 1000) {
                timeOfLastProductionMillis = current;

                List<Building> harvesterBuildings = buildings.stream()
                    .filter(t -> t instanceof HarvesterBuilding)
                    .toList();
                for (var building : harvesterBuildings) {
                    var harvesterBuilding = (HarvesterBuilding) building;
                    ArrayList<Inventory.InventoryItem> inventoryItems = Lists.newArrayList(harvesterBuilding.getStockpile().getItems());
                    harvesterBuilding.getStockpile().clear();

                    for (var inventoryItem : inventoryItems) {
                        for (var i = 0; i < inventoryItem.stackSize; ++i) {
                            stockpile.addItem(inventoryItem.item);
                        }
                    }
                }


                List<Building> refinerBuildings = buildings.stream()
                    .filter(t -> t instanceof RefinerBuilding)
                    .toList();
                for (var building : refinerBuildings) {
                    var harvesterBuilding = (RefinerBuilding) building;
                    ArrayList<Inventory.InventoryItem> inventoryItems = Lists.newArrayList(harvesterBuilding.getOutputStockpile().getItems());
                    harvesterBuilding.getOutputStockpile().clear();

                    for (var inventoryItem : inventoryItems) {
                        for (var i = 0; i < inventoryItem.stackSize; ++i) {
                            stockpile.addItem(inventoryItem.item);
                        }
                    }
                }
            }
        }
    }
}
