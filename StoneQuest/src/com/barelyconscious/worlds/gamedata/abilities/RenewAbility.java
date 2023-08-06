package com.barelyconscious.worlds.gamedata.abilities;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.entity.components.StatChangeOverTimeComponent;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.*;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.AdjustEntityStatBehavior;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.EntityHasStatBehavior;

public class RenewAbility extends Ability {

    private final float duration = 5;
    private final int totalTicks = 10;
    private final float healthPerTick = 1.8f;
    private final float cost = 2.4f;

    public RenewAbility() {
        super("Renew", 1.4f);

        setBehaviorWorkflow(new BehaviorWorkflow(
            new EntityHasStatBehavior(StatName.SPIRIT, cost),
            new Behavior() {
                @Override
                public BehaviorFeedback perform(AbilityContext context) {
                    if (!(context.getCaster() instanceof EntityActor caster)) {
                        return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster is not an entity");
                    }

                    caster
                        .addComponent(new StatChangeOverTimeComponent(
                            caster,
                            caster.stat(StatName.HEALTH).get(),
                            duration,
                            duration / totalTicks,
                            healthPerTick
                        ));

                    return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
                }
            },
            new Behavior() {
                @Override
                public BehaviorFeedback perform(AbilityContext context) {
                    context.addTarget(context.getCaster());
                    return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
                }
            },
            new AdjustEntityStatBehavior(StatName.SPIRIT, -cost, AdjustEntityStatBehavior.StatAdjustmentType.CURRENT)
        ));
    }
}
