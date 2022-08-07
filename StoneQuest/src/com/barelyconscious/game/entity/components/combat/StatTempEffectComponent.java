package com.barelyconscious.game.entity.components.combat;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.engine.EventArgs;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;

/**
 * Alters a stat for a period of time.
 */
public class StatTempEffectComponent extends Component {

    private final Stats.StatName statName;
    private float remainingDurationSeconds;
    private final float statChangeValue;

    private AdjustableValueComponent statValueComponent;

    public StatTempEffectComponent(
        final Actor parent,
        final Stats.StatName statName,
        final float durationSeconds,
        final float statChangeValue
    ) {
        super(parent);
        this.statName = statName;
        this.statChangeValue = statChangeValue;
        this.remainingDurationSeconds = durationSeconds;
    }

    public void endEffect() {
        remainingDurationSeconds = 0;
    }

    @Override
    public void physicsUpdate(EventArgs eventArgs) {
        if (remainingDurationSeconds <= 0) {
            setRemoveOnNextUpdate(true);
            return;
        }

        remainingDurationSeconds -= eventArgs.getDeltaTime();
    }
}
