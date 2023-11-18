package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.EntityActor;

import java.util.HashMap;
import java.util.Map;

public class ThreatComponent extends Component {

    private final Map<EntityActor, Double> threatMap = new HashMap<>();

    public ThreatComponent(Actor parent) {
        super(parent);
    }

    public void addThreat(EntityActor attacker, double threat) {
        threatMap.put(attacker, threat);
    }
}
