package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.UMath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@AllArgsConstructor
@Log4j2
public class Ability {

    private String name;
    private float cooldownSeconds;
    private float remainingCooldownSeconds;
    private float castTime;
    private BehaviorWorkflow behaviorWorkflow;

    public final Delegate<Object> delegateInterruptAbility = new Delegate<>();

    public void updateCooldown(float deltaTime) {
        if (remainingCooldownSeconds > 0) {
            remainingCooldownSeconds = UMath.clampf(remainingCooldownSeconds - deltaTime,
                0, cooldownSeconds);
        }
    }

    public record ActionResult(boolean success, String message, AbilityContext context) {
    }

    public ActionResult enact(AbilityContext context) {
        if (remainingCooldownSeconds > 0) {
            log.error("Ability {} is still on cooldown", name);

            return new ActionResult(false, "Ability is still on cooldown", context);
        }

        var result = behaviorWorkflow.run(context);

        return new ActionResult(
            result.succeeded(),
            result.message(),
            context);
    }
}
