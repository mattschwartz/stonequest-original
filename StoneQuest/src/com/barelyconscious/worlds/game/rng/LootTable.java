package com.barelyconscious.worlds.game.rng;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.game.Requirement;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.types.Biome;
import com.barelyconscious.worlds.gamedata.GameItems;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class LootTable {

    /**
     * Rolls a bunch of stuff to come up with a unique, randomly-generated
     * item. Used for determining what an enemy drops, or what spawns
     * in chests, or what quest rewards you can get.
     *
     * @param level
     * @return
     */
    public static Item generateRandomItem(
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
