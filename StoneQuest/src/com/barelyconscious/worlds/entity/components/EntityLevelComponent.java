package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.common.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class EntityLevelComponent extends Component {

    public final Delegate<EntityLevelChanged> delegateOnEntityLevelChanged;

    @AllArgsConstructor
    public static final class EntityLevelChanged {

        public final double delta;
        public final double currentExperience;
        public final double entityLevel;
    }

    @Getter
    private int entityLevel;
    @Getter
    private double currentExperience;

    public EntityLevelComponent(
        final Actor parent,
        final int entityLevel,
        final double currentExperience
    ) {
        super(parent);
        this.entityLevel = entityLevel;
        this.currentExperience = currentExperience;

        delegateOnEntityLevelChanged = new Delegate<>();
    }

    public void adjustExperience(final double delta) {
        currentExperience = UMath.clamp(currentExperience, 0, Double.MAX_VALUE);
        delegateOnEntityLevelChanged.call(new EntityLevelChanged(
            delta, currentExperience, entityLevel));
    }

    @Override
    public void update(EventArgs eventArgs) {

    }
}
