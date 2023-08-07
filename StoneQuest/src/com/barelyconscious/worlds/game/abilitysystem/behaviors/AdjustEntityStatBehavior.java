package com.barelyconscious.worlds.game.abilitysystem.behaviors;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.abilitysystem.Behavior;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorFeedback;
import com.barelyconscious.worlds.game.abilitysystem.ContinuationResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Adjusts the stat of all entities in `targets` by `amount`.
 */
@Getter
@AllArgsConstructor
public class AdjustEntityStatBehavior implements Behavior {

    public enum StatAdjustmentType {
        CURRENT,
        MAX,
        CURRENT_AND_MAX
    }

    private final StatName stat;
    private final double amount;
    private final StatAdjustmentType statAdjustmentType;

    @Override
    public BehaviorFeedback perform(AbilityContext context) {

        for (var target : context.getTargets()) {
            if (target instanceof EntityActor tar) {
                switch (statAdjustmentType) {
                    case CURRENT:
                        tar.stat(stat).adjustCurrentValueBy(amount);
                        break;
                    case MAX:
                        tar.stat(stat).adjustMaxValueBy(amount);
                        break;
                    case CURRENT_AND_MAX:
                        tar.stat(stat).adjustCurrentValueBy(amount);
                        tar.stat(stat).adjustMaxValueBy(amount);
                        break;
                }
            }
        }

        return new BehaviorFeedback(ContinuationResult.CONTINUE, context);
    }
}
