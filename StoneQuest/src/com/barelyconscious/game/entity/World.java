package com.barelyconscious.game.entity;

import java.util.ArrayList;
import java.util.List;

public final class World {

    private final List<Actor> actors;

    public World() {
        actors = new ArrayList<>();
    }

    public void spawnActor(final Actor actor) {
        actors.add(actor);
    }

    public boolean removeActor(final Actor actor) {
        return actors.remove(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }
}
