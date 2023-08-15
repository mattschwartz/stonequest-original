package com.barelyconscious.worlds.entity;

import java.util.List;

public class VillageActor extends Actor {

    // keeps track of
    // stock, how much wood and ore and things are in the village's stockpile
    // citizens in the village
    // politics
    // buildings
    private List<BuildingActor> buildings;
    // job postings
    // reputation
    // territories
    private List<TerritoryActor> territories;

}
