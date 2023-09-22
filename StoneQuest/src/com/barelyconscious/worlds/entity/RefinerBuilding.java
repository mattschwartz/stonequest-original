package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Clock;

/**
 * Building takes raw materials and creates items.
 */
public class RefinerBuilding extends BuildingActor {

    public Delegate<ItemProducedEvent> delegateOnItemProduced = new Delegate<>();
    public Delegate<ProductionHaltedEvent> delegateOnProductionHalted = new Delegate<>();

    public static class ProductionHaltedEvent {
    }

    @AllArgsConstructor
    public static class ItemProducedEvent {
        public final Item item;
    }

    private final Inventory intakeStockpile;
    @Getter
    private final Inventory outputStockpile;

    /**
     * The recipe that this building is currently processing.
     */
//    private final Recipe recipe;

    /**
     * Amount of time in seconds it takes this building to
     * produce 1 item.
     */
    private final double timeToProduceSeconds;

    private boolean isProductionEnabled = true;

    private final Clock clock;

    public RefinerBuilding(
        String name,
        Vector transform,
        Inventory intakeStockpile,
        Inventory outputStockpile,
        double timeToProduceSeconds
    ) {
        super(name, transform);
        this.intakeStockpile = intakeStockpile;
        this.outputStockpile = outputStockpile;
        this.timeToProduceSeconds = timeToProduceSeconds;
        clock = Clock.systemDefaultZone();

        addComponent(new ProductionComponent(this));
    }

    private class ProductionComponent extends Component {

        private long timeOfLastProductionMillis;

        public ProductionComponent(Actor parent) {
            super(parent);
            timeOfLastProductionMillis = clock.millis();
        }

        @Override
        public void update(EventArgs eventArgs) {
            super.update(eventArgs);
            if (!isProductionEnabled) {
                return;
            }

            long current = clock.millis();
            if (current - timeOfLastProductionMillis > timeToProduceSeconds * 1000) {
                if (intakeStockpile.isEmpty()) {
                    delegateOnProductionHalted.call(new ProductionHaltedEvent());
                    isProductionEnabled = false;
                }

                timeOfLastProductionMillis = current;

                // todo - implement
                // from recipe, get items required
                // try to pull items from stock
                // produce an item and put it in the output stockpile
            }
        }
    }
}
