package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.game.resources.Resources;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
public class Ability {

    private String name;
    private Resources.Sprite_Resource icon;
    private float cooldownSeconds;
    private float remainingCooldownSeconds;
    private BehaviorWorkflow behaviorWorkflow;

    protected void setBehaviorWorkflow(BehaviorWorkflow behaviorWorkflow) {
        this.behaviorWorkflow = behaviorWorkflow;
    }

    public Ability(
        String name,
        float cooldownSeconds
    ) {
        this.name = name;
        this.cooldownSeconds = cooldownSeconds;
        this.remainingCooldownSeconds = 0;
    }

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
