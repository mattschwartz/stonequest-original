package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.combat.CombatSystem;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AIMoveComponent extends MoveComponent {

    public AIMoveComponent(Actor parent, double moveSpeed) {
        super(parent, moveSpeed);
        lastMove = System.currentTimeMillis();
    }

    long lastMove;

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
            if (System.currentTimeMillis() - lastMove < 250) {
                return;
            }
            Vector direction;
            var ran = UMath.RANDOM.nextInt(0, 4);
            // choose a random direction to walk in
            switch (ran) {
                case 0:
                    direction = Vector.LEFT;
                    break;
                case 1:
                    direction = Vector.RIGHT;
                    break;
                case 2:
                    direction = Vector.UP;
                    break;
                case 3:
                    direction = Vector.DOWN;
                    break;
                default:
                    direction = Vector.ZERO;
            }
            lastMove = System.currentTimeMillis();
            addForce(direction, 100);

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
