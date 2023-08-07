package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
public class Ability {

    private String name;
    private BetterSpriteResource icon;
    private double cooldownSeconds;
    private double remainingCooldownSeconds;
    private BehaviorWorkflow behaviorWorkflow;

    protected void setBehaviorWorkflow(BehaviorWorkflow behaviorWorkflow) {
        this.behaviorWorkflow = behaviorWorkflow;
    }

    public Ability(
        String name,
        double cooldownSeconds
    ) {
        this.name = name;
        this.cooldownSeconds = cooldownSeconds;
        this.remainingCooldownSeconds = 0;
    }

    public void updateCooldown(double deltaTime) {
        if (remainingCooldownSeconds > 0) {
            remainingCooldownSeconds = UMath.clamp(remainingCooldownSeconds - deltaTime,
                0, cooldownSeconds);
        }
    }

    public record ActionResult(boolean success, String message, AbilityContext context) {
    }

    public ActionResult enact(AbilityContext context) {
        if (remainingCooldownSeconds > 0) {
            return new ActionResult(false, "Ability is still on cooldown", context);
        }

        var result = behaviorWorkflow.run(context);

        if (result.succeeded()) {
            remainingCooldownSeconds = cooldownSeconds;
        }

        return new ActionResult(
            result.succeeded(),
            result.message(),
            context);
    }
}
