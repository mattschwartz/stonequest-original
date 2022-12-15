package com.barelyconscious.worlds.engine;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.common.shape.Vector;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class WorldUpdateContext {

    private final World world;
    private final List<Actor> actorsToAdd = new ArrayList<>();
    private final List<Actor> actorsToRemove = new ArrayList<>();

    public Optional<Actor> getActorAt(final Vector worldPos) {
        return Optional.ofNullable(world.getActorAt(worldPos));
    }

    public List<Actor> getActors() {
        return world.getActors();
    }

    public void addActor(final Actor actor) {
        actorsToAdd.add(actor);
    }

    public void removeActor(final Actor actor) {
        actorsToRemove.add(actor);
    }

    // calls add and remove then clears the list
    void applyActorOperations() {
        actorsToRemove.forEach(world::removeActor);
        actorsToAdd.forEach(world::addActor);
    }
}
