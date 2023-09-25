package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nullable;

@Log4j2
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
            log.info("Actor {} has died.", getParent().getName());
        }
        return null;
    }

    protected void onDeath(DynamicValueComponent.DynamicValueChanged dynamicValueChanged) {
    }
}
