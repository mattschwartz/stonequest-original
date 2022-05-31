package com.barelyconscious.game.entity;

import com.barelyconscious.game.shape.Vector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    /**
     * converts the screen position as viewed through the provided camera into the corresponding
     * position in the game world.
     */
    public Vector screenToWorldPos(final Camera camera, final Vector screenPos) {
        return new Vector(
            screenPos.x + camera.getWorldX(),
            screenPos.y + camera.getWorldY());
    }

    public Vector worldToScreenPos(final Camera camera, final Vector worldPos) {
        return new Vector(
            worldPos.x - camera.getWorldX(),
            worldPos.y - camera.getWorldY());
    }
}
