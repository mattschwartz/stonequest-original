package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.Stats;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdjustEntityStatBehavior implements Behavior {

    private final Stats.Stat stat;
    private final float amount;

    @Override
    public BehaviorFeedback perform(AbilityContext context) {
//        context.getTargets().stream().filter(t -> t instanceof EntityActor)
//            .forEach(t -> ((EntityActor) t).getEntityAttributeComponent().adjustValue(stat, amount)));
//
//
//        context.getTargets().get(0).getStats().adjust(stat, amount);
        return null;
    }
}
