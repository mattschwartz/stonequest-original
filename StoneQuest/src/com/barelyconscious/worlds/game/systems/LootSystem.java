package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.game.Requirement;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.gamedata.GameItems;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootSystem implements GameSystem {

    private final Map<Integer, List<Item>> itemsByLevel = new HashMap<>();

    public LootSystem() {
        for (var gameItem : GameItems.ALL_GAME_ITEMS) {
            itemsByLevel.computeIfAbsent(gameItem.getItemLevel(), k -> List.of());
            itemsByLevel.put(gameItem.getItemLevel(), List.of(gameItem.toItem()));
        }
    }


    /**
     * Rolls a bunch of stuff to come up with a unique, randomly-generated
     * item. Used for determining what an enemy drops, or what spawns
     * in chests, or what quest rewards you can get.
     *
     * @param level
     * @return
     */
    public Item generateRandomItem(
        int level
    ) {
        int randomItemIndex = UMath.RANDOM.nextInt(0, GameItems.ALL_GAME_ITEMS.size() - 1);

        return GameItems.ALL_GAME_ITEMS.get(randomItemIndex).toItem();
    }

    @AllArgsConstructor
    public static class RollResult {
        public final List<ItemProperty> properties;
        public final List<Requirement> requirements;
    }
}
