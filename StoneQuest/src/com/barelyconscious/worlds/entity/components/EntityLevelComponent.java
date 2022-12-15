package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.delegate.Delegate;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.engine.EventArgs;
import com.barelyconscious.worlds.util.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class EntityLevelComponent extends Component {

    public final Delegate<EntityLevelChanged> delegateOnEntityLevelChanged;

    @AllArgsConstructor
    public static final class EntityLevelChanged {

        public final float delta;
        public final float currentExperience;
        public final float entityLevel;
    }

    @Getter
    private int entityLevel;
    @Getter
    private float currentExperience;

    public EntityLevelComponent(
        final Actor parent,
        final int entityLevel,
        final float currentExperience
    ) {
        super(parent);
        this.entityLevel = entityLevel;
        this.currentExperience = currentExperience;

        delegateOnEntityLevelChanged = new Delegate<>();
    }

    public void adjustExperience(final float delta) {
        currentExperience = UMath.clampf(currentExperience, 0, Float.MAX_VALUE);
        delegateOnEntityLevelChanged.call(new EntityLevelChanged(
            delta, currentExperience, entityLevel));
    }

    @Override
    public void update(EventArgs eventArgs) {

    }
}
