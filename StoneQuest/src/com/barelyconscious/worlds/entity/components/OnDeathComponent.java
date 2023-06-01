package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import lombok.Getter;

import javax.annotation.Nullable;

public abstract class OnDeathComponent extends Component {

    @Getter
    private boolean isDead = false;

    public OnDeathComponent(Actor parent) {
        this(parent, null);
    }

    public OnDeathComponent(Actor parent, @Nullable DynamicValueComponent healthComponent) {
        super(parent);

        if (healthComponent != null) {
            healthComponent.delegateOnValueChanged.bindDelegate(this::onHealthChanged);
        }
    }

    protected Void onHealthChanged(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
        if (dynamicValueChanged.currentValue <= 0.01) {
            isDead = true;
            onDeath(dynamicValueChanged);
        }
        return null;
    }

    protected void onDeath(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
    }
}
