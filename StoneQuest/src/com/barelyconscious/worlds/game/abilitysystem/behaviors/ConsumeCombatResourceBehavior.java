package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import com.barelyconscious.worlds.game.abilitysystem.ContinuationResult;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsumeCombatResourceBehavior implements Behavior {

    private final StatName stat;
    private final double amount;

    @Override
    public BehaviorFeedback perform(AbilityContext context) {
        if (!(context.getCaster() instanceof EntityActor caster)) {
            return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster is not an entity");
        }

        caster.stat(stat).adjustCurrentValueBy(amount);

        return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
    }

}
