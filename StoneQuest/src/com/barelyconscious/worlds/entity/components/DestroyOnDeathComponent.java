package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.AGravestone;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.EntityActor;

/**
 * Simple componet that destroys an actor when its health falls to 0.
 */
public class DestroyOnDeathComponent extends OnDeathComponent {

    private float remainingCorpseDuration;

    public DestroyOnDeathComponent(final Actor parent, final float corpseDuration) {
        super(parent);
        this.remainingCorpseDuration = corpseDuration;

        if (parent instanceof EntityActor entityActor) {
            DynamicValueComponent healthComponent = entityActor.getHealthComponent();
            if (healthComponent != null) {
                healthComponent.delegateOnValueChanged.bindDelegate(super::onHealthChanged);
            }
        }
    }

    @Override
    public void update(EventArgs eventArgs) {
        if (this.isDead()) {
            // disable all other components
            getParent().getComponents().stream()
                .filter(t -> t != this)
                .forEach(t -> t.setEnabled(false));

            remainingCorpseDuration -= eventArgs.getDeltaTime();
            if (remainingCorpseDuration <= 0) {
                getParent().destroy();
                eventArgs.getWorldContext().addActor(new AGravestone(getParent().transform));
            }
        }
    }
}
