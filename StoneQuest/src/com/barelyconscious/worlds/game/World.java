package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.Settlement;
import com.barelyconscious.worlds.entity.Territory;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class World {

    private Settlement playerSettlement;
    private List<Settlement> settlements;
    private List<Territory> territories;

    private final Map<String, Actor> actorsById = new HashMap<>();

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

    private final List<Actor> actors;

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
}
