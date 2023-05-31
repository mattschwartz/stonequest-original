package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;

public final class PowerComponent extends DynamicValueComponent {

    public PowerComponent(Actor parent, float currentValue, float maxValue) {
        super(parent, currentValue, maxValue);
    }
}
