package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.util.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PowerComponent extends Component {

    @AllArgsConstructor
    public static final class PowerChangedEvent {

        public final float delta;
        public final float currentPower;
        public final float maxPower;
    }

    private float currentPower;
    private final float maxPower;
    public final Delegate<PowerChangedEvent> delegateOnPowerChanged;

    public PowerComponent(final Actor parent, final float currentPower, final float maxPower) {
        super(parent);
        this.delegateOnPowerChanged = new Delegate<>();
        this.currentPower = currentPower;
        this.maxPower = maxPower;
    }

    public void adjustPower(final float delta) {
        currentPower = UMath.clampf(currentPower - delta, 0, maxPower);
        delegateOnPowerChanged.call(new PowerChangedEvent(delta, currentPower, maxPower));
    }

    @Override
    public void update(EventArgs eventArgs) {
    }

}
