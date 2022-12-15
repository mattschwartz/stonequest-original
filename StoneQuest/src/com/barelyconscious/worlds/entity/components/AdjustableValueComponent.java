package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.common.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Component that stores a numerical value that can be adjusted. Keeps track of a current and max value and provides
 * a delegate for callbacks whenever the value changes.
 */
public class AdjustableValueComponent extends Component {

    /**
     * Fired whenever the value changes.
     */
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

    public AdjustableValueComponent(final Actor parent) {
        super(parent);
    }

    public AdjustableValueComponent(Actor parent, final float currentValue, final float maxValue) {
        super(parent);
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public void updateValues(final float newCurrentValue, final float newMaxValue) {
        this.currentValue = newCurrentValue;
        this.maxValue = newMaxValue;
    }

    /**
     * increases the max value by maxValueDelta and increases currentValue by the difference.
     * if new maxValueDelta is lower, then currentHealth is not adjusted but is clamped by maxValueDelta
     */
    public void adjustMaxValueBy(final float maxValueDelta) {
        final float proposedNewMaxValue = this.maxValue + maxValueDelta;
        final float previousCurrentValue = this.currentValue;

        if (proposedNewMaxValue >= this.maxValue) {
            final float diff = maxValueDelta - this.maxValue;
            this.currentValue += diff;
        } else if (maxValueDelta < this.maxValue) {
            this.currentValue = UMath.clampf(this.currentValue, this.currentValue, maxValueDelta);
        }
        this.maxValue = proposedNewMaxValue;

        delegateOnValueChanged.call(new StatValueChanged(
            maxValueDelta,
            previousCurrentValue - currentValue,
            this.maxValue));
    }

    public void adjust(final float delta) {
        currentValue = UMath.clampf(currentValue + delta, 0, maxValue);
        delegateOnValueChanged.call(new StatValueChanged(delta, currentValue, maxValue));
    }
}
