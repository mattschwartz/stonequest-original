package com.barelyconscious.game;

import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.Stats;

import java.util.HashMap;

public final class SaveGame {

    public static Hero loadPlayerFromFile(final String filepath) {
        return new Hero(
            3,
            100,
            100,
            12,
            12,
            new Stats(new HashMap<Stats.StatName, Float>() {{
                put(Stats.StatName.STRENGTH, 14f);
                put(Stats.StatName.CONSTITUTION, 18f);
                put(Stats.StatName.DEXTERITY, 11f);
                put(Stats.StatName.INTELLIGENCE, 8f);
                put(Stats.StatName.WISDOM, 9f);
                put(Stats.StatName.CHARISMA, 10f);
            }}),
            133,
            new Inventory(28)
        );
    }
}
