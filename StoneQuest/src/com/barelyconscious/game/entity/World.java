package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public final class World {

    private final List<Actor> actors;

    public World() {
        actors = new CopyOnWriteArrayList<>();
    }

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

    public void saveWorld() {
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
