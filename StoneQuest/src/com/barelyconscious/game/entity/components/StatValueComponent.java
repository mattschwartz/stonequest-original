package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.util.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Stores 2 values for things like health, power
 */
public class StatValueComponent extends Component {

    public final Delegate<StatValueChanged> delegateOnValueChanged = new Delegate<>();

    @AllArgsConstructor
    public static final class StatValueChanged {
        public final float delta;
        public final float currentValue;
        public final float maxValue;
    }

    @Getter
    private float currentValue;
    @Getter
    private float maxValue;

    public StatValueComponent(Actor parent, final float currentValue, final float maxValue) {
        super(parent);
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public void setMaxValue(final float maxValue) {
        this.maxValue = maxValue;

        delegateOnValueChanged.call(new StatValueChanged(
            0,
            currentValue,
            this.maxValue));
    }

    public void adjust(final float delta) {
        currentValue = UMath.clampf(currentValue + delta, 0, maxValue);
        delegateOnValueChanged.call(new StatValueChanged(delta, currentValue, maxValue));
    }
}
