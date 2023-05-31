package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import lombok.Getter;

import javax.annotation.Nullable;

public abstract class OnDeathComponent extends Component {

    @Getter
    private boolean isDead = false;

    public OnDeathComponent(Actor parent, @Nullable HealthComponent health) {
        super(parent);

        if (health != null) {
            health.delegateOnValueChanged.bindDelegate(this::onHealthChanged);
        }
    }

    private Void onHealthChanged(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
        if (dynamicValueChanged.currentValue <= 0.01) {
            isDead = true;
            onDeath(dynamicValueChanged);
        }
        return null;
    }

    protected void onDeath(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
    }
}
