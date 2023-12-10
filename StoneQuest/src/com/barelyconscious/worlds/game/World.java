package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.Delegate;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public final class World {

    @Getter
    private boolean isLevelLoading = false;
    private WildernessLevel loadingLevel;

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

    public final Delegate<OnWorldLoadArgs> delegateOnWorldLoaded = new Delegate<>();

    @AllArgsConstructor
    public static class OnWorldLoadArgs {
        public final WildernessLevel newLevel;
    }

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


    /**
     * Actors that are removed from the world between scenes
     */
    private final List<Actor> sceneActors = new ArrayList<>();

    /**
     * Will unload the previous level when loading a new one. happens on next game update
     * @param wildernessLevel
     */
    public void setWildernessLevel(WildernessLevel wildernessLevel) {
        if (isLevelLoading) {
            log.error("Cannot set wilderness level while another level is loading.");
            return;
        }
        log.info("Changing level to {}", wildernessLevel.getName());
        isLevelLoading = true;
        this.loadingLevel = wildernessLevel;
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

    /**
     * Updates the world, which includes territories and settlements.
     */
    public void update(EventArgs args) {
        readyLoadWorld();
        territories.forEach(territory -> territory.update(args));
        settlements.forEach(settlement -> settlement.update(args));
    }

    // make sure level loads occur during game updates only?
    public void readyLoadWorld() {
        if (!isLevelLoading) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        log.info("Loading level: " + loadingLevel.getName());

        if (wildernessLevel != null) {
            // remove wilderness children actors from world
            var children = new ArrayList<>(wildernessLevel.getChildren());
            for (final Actor actor : children) {
                removeActor(actor);
            }
            for (final Actor actor : sceneActors) {
                removeActor(actor);
            }
            sceneActors.clear();
        }

        wildernessLevel = loadingLevel;
        // add winderness children actors to world
        var children = new ArrayList<>(wildernessLevel.getChildren());
        for (final Actor actor : children) {
            addActor(actor);
        }

        long timeToLoad = System.currentTimeMillis() - currentTime;
        log.info("{} loaded. {}ms", wildernessLevel.getName(), timeToLoad);

        isLevelLoading = false;
        delegateOnWorldLoaded.call(new OnWorldLoadArgs(wildernessLevel));
    }

    public void addActor(final Actor actor) {
        actors.add(actor);
        actorsById.put(actor.id, actor);
        sceneActors.add(actor);
    }

    /**
     *
     * @param actor
     * @param isPersistent whether the actor persists between levels
     */
    public void addPersistentActor(final Actor actor) {
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
}
