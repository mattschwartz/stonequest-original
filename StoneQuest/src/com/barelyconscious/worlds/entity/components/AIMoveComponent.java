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
            randomWalk();
        } else {
            EntityActor attacker = getCombatTarget();

            var direction = attacker.getTransform().minus(getParent().getTransform()).unitVector();
            addForce(direction, 16);
        }
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
