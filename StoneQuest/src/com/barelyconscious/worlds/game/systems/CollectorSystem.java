package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.game.GameState;
import com.barelyconscious.worlds.game.item.Item;
import lombok.Getter;

import java.util.List;

public class CollectorSystem implements GameSystem {

    @Getter
    public static final class CollectorState {
        public final List<Item> collection;
        public final List<Item> wantedItems;
        public final List<String> wantedItemsDetails;

        public CollectorState(
            List<Item> collection,
            List<Item> wantedItems,
            List<String> wantedItemsDetails
        ) {
            this.collection = collection;
            this.wantedItems = wantedItems;
            this.wantedItemsDetails = wantedItemsDetails;
        }

        public void addToCollection(Item item) {
            collection.add(item);
        }

        public void addWantedItem(Item item) {
            wantedItems.add(item);
        }
    }

}
