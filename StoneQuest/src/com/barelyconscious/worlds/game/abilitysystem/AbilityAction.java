package com.barelyconscious.worlds.game.abilitysystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@AllArgsConstructor
@Log4j2
public class AbilityAction {

    private String name;
    private float cooldownSeconds;
    private float remainingCooldownSeconds;
    private float castTime;
    private BehaviorWorkflow behaviorWorkflow;

    public void enact(AbilityContext context) {
        if (remainingCooldownSeconds > 0) {
            log.error("Ability {} is still on cooldown", name);
            return;
        }

        BehaviorWorkflow.WorkflowExecutionResult run = behaviorWorkflow.run(context);
        if (!run.succeeded()) {
            log.error("Failed to execute ability {}: {}", name, run.message());
        }
    }
}
