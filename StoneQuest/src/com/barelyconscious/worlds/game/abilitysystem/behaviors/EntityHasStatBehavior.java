package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import com.barelyconscious.worlds.game.abilitysystem.ContinuationResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EntityHasStatBehavior implements Behavior {

    private final StatName stat;
    @Getter
    private final double cost;

    @Override
    public BehaviorFeedback perform(AbilityContext context) {
        if (!(context.getCaster() instanceof EntityActor caster)) {
            return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster is not an entity");
        }

        DynamicValueComponent power = caster.stat(stat).get();

        if (power == null || power.getCurrentValue() < cost) {
            return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster does not have enough " + stat);
        }

        return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
    }
}
