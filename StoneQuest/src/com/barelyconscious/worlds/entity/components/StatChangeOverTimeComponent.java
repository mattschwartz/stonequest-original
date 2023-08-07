package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * for things like DoTs and HoTs and also for general
 * numerical degradation
 */
public class StatChangeOverTimeComponent extends Component {

    private final double tickFrequencySeconds;
    private final double changePerTick;
    private double nextTickIn;
    private int ticksRemaining;
    private final DynamicValueComponent stat;

    /**
     * @param durationSeconds      duration of how long the stat change will be applied
     * @param tickFrequencySeconds frequency of stat change
     * @param changePerTick        how much the stat changes per tick. Can be positive or negative
     */
    public StatChangeOverTimeComponent(
        Actor parent,
        DynamicValueComponent stat,
        double durationSeconds,
        double tickFrequencySeconds,
        double changePerTick
    ) {
        super(parent);
        checkArgument(stat != null, "stat is null");
        checkArgument(durationSeconds > 0, "durationSeconds is less than 0");
        checkArgument(tickFrequencySeconds > 0, "tickFrequencySeconds is less than 0");

        this.stat = stat;
        this.tickFrequencySeconds = tickFrequencySeconds;
        this.changePerTick = changePerTick;
        this.nextTickIn = tickFrequencySeconds;

        this.ticksRemaining = (int) Math.ceil(
            durationSeconds / tickFrequencySeconds);
    }

    @Override
    public void update(EventArgs eventArgs) {
        if (ticksRemaining <= 0) {
            setRemoveOnNextUpdate(true);
            return;
        }

        nextTickIn -= eventArgs.getDeltaTime();

        if (nextTickIn <= 0) {
            --ticksRemaining;
            stat.adjustCurrentValueBy(changePerTick);
            nextTickIn = tickFrequencySeconds;
        }
    }
}
