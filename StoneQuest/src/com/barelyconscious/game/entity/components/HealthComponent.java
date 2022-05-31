package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.util.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class HealthComponent extends Component {

    @AllArgsConstructor
    public static final class HealthChangedEvent {

        public final float delta;
        public final float currentHealth;
        public final float maxHealth;
    }

    @Getter
    private float currentHealth;
    @Getter
    private final float maxHealth;
    public final Delegate<HealthComponent.HealthChangedEvent> delegateOnHealthChanged;

    public HealthComponent(final Actor parent, final float currentHealth, final float maxHealth) {
        super(parent);
        this.delegateOnHealthChanged = new Delegate<>();
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public void adjustHealth(final float delta) {
        currentHealth = UMath.clampf(currentHealth + delta, 0, maxHealth);
        delegateOnHealthChanged.call(new HealthComponent.HealthChangedEvent(delta, currentHealth, maxHealth));
    }

    @Override
    public void update(EventArgs eventArgs) {
    }
}
