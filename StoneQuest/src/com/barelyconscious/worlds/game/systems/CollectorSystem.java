package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.item.Item;
import lombok.Getter;

import java.util.List;

public class CollectorSystem implements GameSystem {

    public final Delegate<CollectorState> delegateOnStateChanged = new Delegate<>();

    @Getter
    public static final class CollectorState {
        private final List<Item> collection;
        private final List<Item> wantedItems;
        private final List<String> wantedItemsDetails;

        public CollectorState(
            List<Item> collection,
            List<Item> wantedItems,
            List<String> wantedItemsDetails
        ) {
            this.collection = collection;
            this.wantedItems = wantedItems;
            this.wantedItemsDetails = wantedItemsDetails;
        }
    }

    public void addToCollection(Item item) {
        var state = GameInstance.instance()
            .getGameState()
            .getCollectorState();
        var collection = state
            .getCollection();
        collection.add(item);

        delegateOnStateChanged.call(state);
    }

    public void setWantedItems(
        Item topItem,
        String topItemDetails,
        Item middleItem,
        String middleItemDetails,
        Item bottomItem,
        String bottomItemDetails
    ) {
        var wantedItems = GameInstance.instance()
            .getGameState()
            .getCollectorState()
            .getWantedItems();

        wantedItems.clear();
        wantedItems.add(topItem);
        wantedItems.add(middleItem);
        wantedItems.add(bottomItem);

        var wantedItemsDetails = GameInstance.instance()
            .getGameState()
            .getCollectorState()
            .getWantedItemsDetails();

        wantedItemsDetails.clear();
        wantedItemsDetails.add(topItemDetails);
        wantedItemsDetails.add(middleItemDetails);
        wantedItemsDetails.add(bottomItemDetails);
    }

}
