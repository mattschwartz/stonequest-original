package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Basically like a container for an ability that can be used by an actor.
 */
@Getter
@Log4j2
public class AbilityComponent extends Component {

    private final Ability ability;

    private final Delegate<Object> delegateOnCooldownComplete = new Delegate<>();
    private final Delegate<Object> delegateOnCast = new Delegate<>();
    private final Delegate<Object> delegateOnCastComplete = new Delegate<>();
    private final Delegate<Object> delegateOnCastInterrupted = new Delegate<>();
    private final Delegate<Object> delegateOnCastFailed = new Delegate<>();
    private final Delegate<Object> delegateOnCastSuccess = new Delegate<>();

    public AbilityComponent(Actor parent, Ability ability) {
        super(parent);
        this.ability = ability;
    }

    @Override
    public void update(EventArgs eventArgs) {
        super.update(eventArgs);

        ability.updateCooldown(eventArgs.getDeltaTime());
    }

    public void enact() {
        var context = AbilityContext.builder()
            .caster(getParent())
            .build();

        Ability.ActionResult actionResult = ability.enact(context);

        if (actionResult.success()) {
            delegateOnCastSuccess.call(actionResult.context());
        } else {
            delegateOnCastFailed.call(actionResult.context());
        }

        log.info("Ability {} was {}.", ability.getName(), actionResult.success() ? "successful" : "unsuccessful");
    }
}
