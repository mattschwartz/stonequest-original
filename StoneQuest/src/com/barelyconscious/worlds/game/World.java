package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class World {

    /**
     * The currently loaded wilderness level/scene/area.
     */
    @Getter
    private WildernessLevel wildernessLevel;

    @Getter
    @Setter
    private Settlement playerSettlement;

    @Getter
    private final List<Settlement> settlements = new ArrayList<>();

    @Getter
    private final List<Territory> territories = new ArrayList<>();

    private final List<Actor> actors;
    private final Map<String, Actor> actorsById = new HashMap<>();

    public final Map<Territory, List<BuildingActor>> territoryToBuildings = new HashMap<>();
    /**
     * Signifies the relationship between every settlement and the territories it owns.
     *
     * the null settlement corresponds to neutral territories
     */
    public final Map<Settlement, List<Territory>> settlementToTerritories = new HashMap<>();
    /**
     * Signifies the relationship between every territory and the settlement that owns it.
     */
    public final Map<Territory, Settlement> territoryToSettlement = new HashMap<>();

    public void setWildernessLevel(WildernessLevel wildernessLevel) {
        this.wildernessLevel = wildernessLevel;
    }

    /**
     * todo - for the CLI
     */
    public Optional<Actor> findActorById(final String id) {
        return Optional.ofNullable(actorsById.get(id));
    }

    /**
     * todo - for the CLI
     * @param name
     * @return
     */
    public Optional<Actor> findActorByName(final String name) {
        return actors.stream()
            .filter(actor -> actor.name.equalsIgnoreCase(name))
            .findFirst();
    }

    public World() {
        actors = new CopyOnWriteArrayList<>();
    }

    public void addActor(final Actor actor) {
        actors.add(actor);
        actorsById.put(actor.id, actor);
    }

    public synchronized boolean removeActor(final Actor actor) {
        actor.setParent(null);
        actorsById.remove(actor.id);
        return actors.remove(actor);
    }

    public synchronized List<Actor> getActors() {
        return actors;
    }

    public Actor getActorAt(final Vector worldPos) {
        return getActorAt(worldPos.x, worldPos.y);
    }

    public Actor getActorAt(final double worldX, final double worldY) {
        final List<Actor> matches = new ArrayList<>();
        for (final Actor a : actors) {
            if (a.getBoundingBox().contains((int) worldX, (int) worldY)) {
                matches.add(a);
            }
        }

        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() == 1) {
            return matches.get(0);
        } else {
            int index = matches.size() - 1;
            return matches.get(index);
        }
    }

    /**
     * Updates the world, which includes territories and settlements.
     */
    public void update(EventArgs args) {
        territories.forEach(territory -> territory.update(args));
        settlements.forEach(settlement -> settlement.update(args));
    }
}
