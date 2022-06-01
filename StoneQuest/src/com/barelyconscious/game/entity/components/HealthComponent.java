package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;

public class HealthComponent extends StatValueComponent {

    public HealthComponent(Actor parent, float currentValue, float maxValue) {
        super(parent, currentValue, maxValue);
    }
}
