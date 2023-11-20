package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.combat.CombatSystem;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AIMoveComponent extends Component {

    public AIMoveComponent(Actor parent) {
        super(parent);
    }

    @Override
    public void update(EventArgs eventArgs) {
        super.update(eventArgs);
        if (!(getParent() instanceof EntityActor)) {
            log.info("I'm not eligible for participating in combat. {}", getParent().getName());
            setRemoveOnNextUpdate(true);
            return;
        }

        var combatEncounter = GameInstance.instance().getSystem(CombatSystem.class).getActiveCombatEncounter();
        if (combatEncounter == null) {
            // take a step in a random direction
            return;
        }

        var threatTable = combatEncounter.getThreatTable();
        var attacker = threatTable.getHighestThreatActor((EntityActor) getParent());
        if (attacker == null) {
            return;
        }

        log.info("Attacking highest threat combatant: {}", attacker.getName());
    }
}
