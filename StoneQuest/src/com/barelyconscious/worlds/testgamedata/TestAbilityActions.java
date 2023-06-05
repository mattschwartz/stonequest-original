package com.barelyconscious.worlds.testgamedata;

import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.BehaviorWorkflow;
import com.barelyconscious.worlds.game.abilitysystem.behaviors.EntityHasPowerBehavior;

public class TestAbilityActions {

    public void create () {
        var renewAction = new Ability(
                "Renew",
                10,
                0,
                0,
                new BehaviorWorkflow(
                    new EntityHasPowerBehavior(10)
                )
        );
    }
}
