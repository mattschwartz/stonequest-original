package com.barelyconscious.worlds.gamedata.abilities;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.entity.components.StatChangeOverTimeComponent;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.*;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.AdjustEntityStatBehavior;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.EntityHasStatBehavior;

public class RenewAbility extends Ability {

    private final double duration = 5;
    private final int totalTicks = 10;
    private final double healthPerTick = 1.8;
    private final double cost = 2.4;

    public RenewAbility() {
        super("Renew", 5);

        setBehaviorWorkflow(new BehaviorWorkflow(
            new EntityHasStatBehavior(StatName.SPIRIT, cost),
            new Behavior() {
                @Override
                public BehaviorFeedback perform(AbilityContext context) {
                    if (!(context.getCaster() instanceof EntityActor caster)) {
                        return new BehaviorFeedback(ContinuationResult.STOP, context, "Caster is not an entity");
                    }

                    // todo should go thru combat system
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
