package com.barelyconscious.worlds.game.abilitysystem;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    /**
     * Delegate fired whenever the ability goes on or off cooldown.
     */
    public static final Delegate<OnCooldownChangedEvent> delegateOnCooldownChanged = new Delegate<>();
    /**
     * Delegate fired whenever the ability is initially cast.
     */
    public static final Delegate<OnAbilityCastEvent> delegateOnAbilityCast = new Delegate<>();
    /**
     * Delegate fired whenever the ability fails to cast.
     */
    public static final Delegate<OnAbilityFailedEvent> delegateOnAbilityFailed = new Delegate<>();

    @AllArgsConstructor
    public static class OnAbilityFailedEvent {
        public final Ability ability;
        public final AbilityContext context;
        public final String message;
    }

    @AllArgsConstructor
    public static class OnAbilityCastEvent {
        public final Ability ability;
        public final AbilityContext context;
    }

    @AllArgsConstructor
    public static class OnCooldownChangedEvent {
        public final Ability ability;
        public final boolean onCooldown;
        public final double cooldownSeconds;
        public final double remainingCooldownSeconds;
    }

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
        if (isOnCooldown()) {
            remainingCooldownSeconds = UMath.clamp(remainingCooldownSeconds - deltaTime,
                0, cooldownSeconds);
            if (!isOnCooldown()) {
                delegateOnCooldownChanged.call(new OnCooldownChangedEvent(
                    this, false, cooldownSeconds, remainingCooldownSeconds));
            }
        }
    }

    public boolean isOnCooldown() {
        return remainingCooldownSeconds > UMath.EPSILON;
    }

    public record ActionResult(boolean success, String message, AbilityContext context) {
    }

    public final ActionResult enact(AbilityContext context) {
        if (remainingCooldownSeconds > 0) {
            final String msg = "Ability is still on cooldown";
            delegateOnAbilityFailed.call(new OnAbilityFailedEvent(this, context, msg));
            return new ActionResult(false, msg, context);
        }

        delegateOnAbilityCast.call(new OnAbilityCastEvent(this, context));
        var result = behaviorWorkflow.run(context);

        if (result.succeeded()) {
            remainingCooldownSeconds = cooldownSeconds;
            delegateOnCooldownChanged.call(new OnCooldownChangedEvent(
                this, true, cooldownSeconds, remainingCooldownSeconds));
        } else {
            delegateOnAbilityFailed.call(new OnAbilityFailedEvent(this, context, result.message()));
        }

        return new ActionResult(
            result.succeeded(),
            result.message(),
            context);
    }
}
