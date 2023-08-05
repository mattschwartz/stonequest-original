package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class World {

    private final List<Actor> actors;

    public World() {
        actors = new CopyOnWriteArrayList<>();
    }

    public void addActor(final Actor actor) {
        actors.add(actor);
    }

    // kind of wasn't the most intuitive name
    @Deprecated
    public void spawnActor(final Actor actor) {
        actors.add(actor);
    }

    public synchronized boolean removeActor(final Actor actor) {
        return actors.remove(actor);
    }

    public synchronized List<Actor> getActors() {
        return actors;
    }

    public void loadWorld() {
    }

    public void unloadWorld() {
    }

    public Actor getActorAt(final Vector worldPos) {
        return getActorAt(worldPos.x, worldPos.y);
    }

    public Actor getActorAt(final float worldX, final float worldY) {
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
