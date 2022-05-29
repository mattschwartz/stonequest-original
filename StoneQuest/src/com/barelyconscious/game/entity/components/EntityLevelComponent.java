package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.util.UMath;
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
