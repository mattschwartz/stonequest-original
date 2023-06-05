package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.StatName;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdjustEntityStatBehavior implements Behavior {

    private final StatName stat;
    private final float amount;

    @Override
    public BehaviorFeedback perform(AbilityContext context) {

        for (var target : context.getTargets()) {
            if (target instanceof EntityActor tar) {
                tar.stat(stat).adjustMaxValueBy(amount);
            }
        }

        return null;
    }
}
