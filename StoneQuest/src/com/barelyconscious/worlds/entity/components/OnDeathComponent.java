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

    private Void onHealthChanged(AdjustableValueComponent.StatValueChanged statValueChanged) {
        if (statValueChanged.currentValue <= 0.01) {
            isDead = true;
            onDeath(statValueChanged);
        }
        return null;
    }

    protected void onDeath(AdjustableValueComponent.StatValueChanged statValueChanged) {
    }
}
