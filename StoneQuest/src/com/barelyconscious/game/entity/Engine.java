package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.physics.Physics;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("UnstableApiUsage")
@Log4j2
public final class Engine {

    private final GameInstance gameInstance;
    private final World world;
    private final Screen screen;
    private final Clock clock;

    private final RateLimiter rateLimiter;

    private long lastTick;
    private final Physics physics;

    private boolean isRunning = false;

    public Engine(
        final GameInstance gameInstance,
        final World world,
        final Screen screen,
        final Physics physics,
        final Clock clock
    ) {
        checkArgument(gameInstance != null, "gameInstance is null");
        checkArgument(world != null, "world is null");
        checkArgument(screen != null, "screen is null");
        checkArgument(physics != null, "physics is null");
        checkArgument(clock != null, "clock is null");

        this.gameInstance = gameInstance;
        this.world = world;
        this.screen = screen;
        this.physics = physics;
        this.clock = clock;
        this.lastTick = clock.millis();
        rateLimiter = RateLimiter.create(30);
    }

    public void start() {
        isRunning = true;

        while (isRunning) {
            rateLimiter.acquire();
            tick();
        }
        System.out.println("Game no longer running");
    }

    public void stop() {
        isRunning = false;
    }

    public void tick() {
        final long now = clock.millis();
        final long deltaTime = now - lastTick;
        lastTick = now;
        final EventArgs eventArgs = new EventArgs(deltaTime * 0.001f);

        final List<Component> componentsToUpdate = new ArrayList<>();
        final List<Actor> actorsToRemove = new ArrayList<>();

        // todo: NOTE that this behavior is giving destroying actors 1 final tick
        //  might want to check here in case of bug...
        for (final Actor actor : world.getActors()) {
            if (actor.isDestroying()) {
                actorsToRemove.add(actor);
            }
            if (!actor.isEnabled()) {
                continue;
            }

            for (final Component c : actor.getComponents()) {
                if (c.isEnabled()) {
                    componentsToUpdate.add(c);
                }
            }
        }

        physics.updatePhysics(eventArgs, world.getActors());

        update(eventArgs, componentsToUpdate);
        updateScreen(eventArgs, componentsToUpdate);
        updateGui(eventArgs, componentsToUpdate);

        actorsToRemove.forEach(world::removeActor);
    }

    private void update(
        final EventArgs eventArgs,
        final List<Component> updateComponents
    ) {
        updateComponents.forEach(t -> t.update(eventArgs));
    }

    private void updateScreen(final EventArgs eventArgs, final List<Component> components) {
        screen.clear();

        final RenderContext renderContext = screen.createRenderContext();

        components.forEach(t -> t.render(eventArgs, renderContext));
        screen.render(renderContext);
    }

    private void updateGui(final EventArgs eventArgs, final List<Component> components) {
        final RenderContext renderContext = screen.createRenderContext();

        components.forEach(t -> t.guiRender(eventArgs, renderContext));
        screen.render(renderContext);
    }
}
