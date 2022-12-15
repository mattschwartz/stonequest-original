package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * for things like DoTs and HoTs and also for general
 * numerical degradation
 */
public class StatChangeOverTime extends Component {

    private final float tickFrequencySeconds;
    private final float changePerTick;
    private float nextTickIn;
    private int ticksRemaining;
    private final AdjustableValueComponent stat;

    /**
     * @param durationSeconds      duration of how long the stat change will be applied
     * @param tickFrequencySeconds frequency of stat change
     * @param changePerTick        how much the stat changes per tick. Can be positive or negative
     */
    public StatChangeOverTime(
        Actor parent,
        AdjustableValueComponent stat,
        float durationSeconds,
        float tickFrequencySeconds,
        float changePerTick
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
            stat.adjust(changePerTick);
            nextTickIn = tickFrequencySeconds;
        }
    }
}
