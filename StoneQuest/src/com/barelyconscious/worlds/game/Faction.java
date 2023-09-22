package com.barelyconscious.worlds.game;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Faction {

    private final String id;
    private static final Map<Faction, List<Pair<Faction, Double>>>
        factionRelations = new HashMap<>();
}
