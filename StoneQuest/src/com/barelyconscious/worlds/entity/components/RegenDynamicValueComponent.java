package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;

public class RegenDynamicValueComponent extends Component {

    private final double tickFrequencySeconds;
    private final double changePerTick;
    private double nextTickIn;
    private final DynamicValueComponent stat;


    public RegenDynamicValueComponent(
        Actor parent,
        DynamicValueComponent stat,
        double tickFrequencySeconds,
        double changePerTick
    ) {
        super(parent);
        this.stat = stat;
        this.tickFrequencySeconds = tickFrequencySeconds;
        this.changePerTick = changePerTick;
    }

    @Override
    public void update(EventArgs eventArgs) {
        nextTickIn -= eventArgs.getDeltaTime();

        if (nextTickIn <= 0) {
            stat.adjustCurrentValueBy(changePerTick);
            nextTickIn = tickFrequencySeconds;
        }
    }
}
