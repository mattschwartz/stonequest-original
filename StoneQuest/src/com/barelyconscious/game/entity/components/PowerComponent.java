package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;

public final class PowerComponent extends AdjustableValueComponent {

    public PowerComponent(Actor parent, float currentValue, float maxValue) {
        super(parent, currentValue, maxValue);
    }
}
