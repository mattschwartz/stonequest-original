package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

@Getter
public class Hero extends AEntity {

    private final Inventory inventory;

    public Hero(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentHealth,
        final float maxHealth,
        final float currentPower,
        final float maxPower,
        final Stats entityStats,
        final float currentExperience,
        final Inventory inventory
    ) {
        super(name,
            transform,
            entityLevel,
            currentExperience,
            currentHealth,
            maxHealth,
            currentPower,
            maxPower,
            entityStats);
        this.inventory = inventory;
    }
}
