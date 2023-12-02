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
public class DynamicValueComponent extends Component {

    /**
     * Fired whenever the value changes.
     */
    public final Delegate<DynamicValueChanged> delegateOnValueChanged = new Delegate<>();

    @AllArgsConstructor
    public static final class DynamicValueChanged {
        public final double delta;
        public final double currentValue;
        public final double maxValue;
    }

    @Getter
    private double currentValue;
    @Getter
    private double maxValue;

    public DynamicValueComponent(final Actor parent) {
        super(parent);
    }

    public DynamicValueComponent(Actor parent, final double currentValue, final double maxValue) {
        super(parent);
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public void setValue(final double newCurrentValue, final double newMaxValue) {
        this.currentValue = newCurrentValue;
        this.maxValue = newMaxValue;
    }

    public void adjustCurrentValueBy(final double currentValueDelta) {
        currentValue = UMath.clamp(currentValue + currentValueDelta, 0, maxValue);
        delegateOnValueChanged.call(new DynamicValueChanged(currentValueDelta, currentValue, maxValue));
    }

    /**
     * increases the max value by maxValueDelta and increases currentValue by the difference.
     * if new maxValueDelta is lower, then currentHealth is not adjusted but is clamped by maxValueDelta
     */
    public void adjustMaxValueBy(final double maxValueDelta) {
        final double proposedNewMaxValue = this.maxValue + maxValueDelta;

        if (proposedNewMaxValue >= this.maxValue) {
            final double diff = proposedNewMaxValue - this.maxValue;
            this.currentValue += diff;
        } else {
            this.currentValue = UMath.clamp(this.currentValue, 0, proposedNewMaxValue);
        }

        this.maxValue = proposedNewMaxValue;

        delegateOnValueChanged.call(new DynamicValueChanged(
            maxValueDelta,
            currentValue,
            this.maxValue));
    }
}
