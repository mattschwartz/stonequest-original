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

public class HarvesterBuilding extends Building {

    public Delegate<ItemProducedEvent> delegateOnItemProduced = new Delegate<>();
    public Delegate<ProductionHaltedEvent> delegateOnProductionHalted = new Delegate<>();

    public static class ProductionHaltedEvent {
    }

    @AllArgsConstructor
    public static class ItemProducedEvent {
        public final Item item;
    }

    /**
     * The resource being processed.
     */
    private final ResourceNode resourceNode;

    /**
     * Where items go as they get produced
     */
    @Getter
    private final Inventory stockpile;
    /**
     * Amount of time in seconds it takes this building to
     * produce 1 item.
     */
    private final double timeToProduceSeconds;

    private boolean isProductionEnabled = true;

    private final Clock clock;

    public HarvesterBuilding(
        String name,
        Vector transform,
        ResourceNode resourceNode,
        Inventory stockpile,
        double timeToProduceSeconds
    ) {
        super(name, transform);
        this.resourceNode = resourceNode;
        this.stockpile = stockpile;
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
                timeOfLastProductionMillis = current;

                var item = resourceNode.harvest(getParent());
                if (item != null) {
                    delegateOnItemProduced.call(new ItemProducedEvent(item));
                    stockpile.addItem(item);
                } else {
                    delegateOnProductionHalted.call(new ProductionHaltedEvent());
                    isProductionEnabled = false;
                }
            }
        }
    }
}
