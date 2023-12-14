package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.combat.CombatSystem;
import com.barelyconscious.worlds.game.systems.combat.DamagingAbility;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AIMoveComponent extends MoveComponent {

    public AIMoveComponent(Actor parent, double moveSpeed) {
        super(parent, moveSpeed);
        lastMove = System.currentTimeMillis();
    }

    /**
     * how quickly the AI makes decisions
     */
    private long decisionSpeed = 100;
    private long lastMove;

    @Override
    public void update(EventArgs eventArgs) {
        super.update(eventArgs);
        if (!(getParent() instanceof EntityActor)) {
            log.info("I'm not eligible for participating in combat. {}", getParent().getName());
            setRemoveOnNextUpdate(true);
            return;
        }

        if (!canMakeDecision()) {
            return;
        }

        if (!inCombat()) {
            // make a random decision
            Decision decision;
            var ran = UMath.RANDOM.nextInt(100);
            if (ran < 10) {
                decision = Decision.WALK;
            } else {
                decision = Decision.NOTHING;
            }
            if (decision == Decision.WALK) {
                randomWalk();
            }
        } else {
            EntityActor attacker = getCombatTarget();

            var direction = attacker.getTransform().minus(getParent().getTransform()).unitVector();
            if (attacker.getTransform().minus(getParent().getTransform()).magnitude() <= 64) {
                // get combat system
                var combatSystem = GameInstance.instance().getSystem(CombatSystem.class);
                combatSystem.applyDamage((EntityActor) getParent(), attacker, new DamagingAbility(5));
                lastMove += 1000;
            }
            addForce(direction, 16);
        }
    }

    enum Decision {
        WALK,
        NOTHING,
    }

    /**
     * idk the name. just returns true if enough time has passed since
     * the last decision was made
     */
    private boolean canMakeDecision() {
        long now = System.currentTimeMillis();
        if (now - lastMove >= decisionSpeed) {
            lastMove = now;
            return true;
        }
        return false;
    }

    private void randomWalk() {
        Vector direction = new Vector(
            UMath.RANDOM.nextDouble() * 2 - 1,
            UMath.RANDOM.nextDouble() * 2 - 1);
        addForce(direction, 16);
    }

    private EntityActor getCombatTarget() {
        if (!inCombat()) {
            return null;
        }

        var combatEncounter = GameInstance.instance().getSystem(CombatSystem.class).getActiveCombatEncounter();
        var threatTable = combatEncounter.getThreatTable();

        return threatTable.getHighestThreatActor((EntityActor) getParent());
    }

    private boolean inCombat() {
        var combatEncounter = GameInstance.instance().getSystem(CombatSystem.class).getActiveCombatEncounter();
        if (combatEncounter == null) {
            return false;
        }
        var threatTable = combatEncounter.getThreatTable();
        var attacker = threatTable.getHighestThreatActor((EntityActor) getParent());
        return attacker != null;
    }
}
