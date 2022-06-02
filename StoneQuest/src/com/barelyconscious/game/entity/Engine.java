package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.physics.Physics;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
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

    private final RateLimiter upsLimiter;
    private final RateLimiter fpsLimiter;

    private long lastTick;
    private long lastRenderTick;
    private final Physics physics;

    private long frames = 0;
    private long gameClockMillis = 1;
    private float averageFps = 0;

    private boolean isRunning = false;

    public Engine(
        final GameInstance gameInstance,
        final World world,
        final Screen screen,
        final Physics physics,
        final Clock clock,
        final RateLimiter ups,
        final RateLimiter fps
    ) {
        checkArgument(gameInstance != null, "gameInstance is null");
        checkArgument(world != null, "world is null");
        checkArgument(screen != null, "screen is null");
        checkArgument(physics != null, "physics is null");
        checkArgument(clock != null, "clock is null");
        checkArgument(ups != null, "ups is null");
        checkArgument(fps != null, "fps is null");

        this.gameInstance = gameInstance;
        this.world = world;
        this.screen = screen;
        this.physics = physics;
        this.clock = clock;
        this.upsLimiter = ups;
        this.fpsLimiter = fps;

        gameInstance.setCamera(screen.getCamera());
    }

    public void start() {
        isRunning = true;
        this.lastTick = clock.millis();
        this.lastRenderTick = clock.millis();

        final Thread threadUpdate = new Thread(() -> {
            while (isRunning) {
                upsLimiter.acquire();
                tick();
            }
        });
        threadUpdate.start();

        while (isRunning) {
            fpsLimiter.acquire();
            renderTick();
        }

        try {
            threadUpdate.join(5000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted after game stop");
            e.printStackTrace();
        }

        System.out.println("Game no longer running");
    }

    public void stop() {
        isRunning = false;
    }

    private EventArgs buildEventArgs() {
        final long now = clock.millis();
        final long deltaTime = now - lastRenderTick;
        lastRenderTick = now;
        return new EventArgs(
            deltaTime * 0.001f,
            gameInstance.getPlayerController().getMouseScreenPos(),
            gameInstance.getPlayerController().getMouseWorldPos());
    }

    long next = 100;

    public void renderTick() {
        final EventArgs eventArgs = buildEventArgs();
        screen.clear();
        final RenderContext renderContext = screen.createRenderContext();

        ++frames;
        if (gameClockMillis >= next) {
            averageFps = ((float) frames) / (gameClockMillis * 0.001f);
            next += 250;
        }

        for (final Actor actor : world.getActors()) {
            if (!actor.isEnabled() || actor.isDestroying()) {
                continue;
            }

            for (final Component c : actor.getComponents()) {
                if (c.isRenderEnabled()) {
                    c.render(eventArgs, renderContext);
                    c.guiRender(eventArgs, renderContext);
                }
            }
        }

        renderDebug(eventArgs, renderContext);

        screen.render(renderContext);
    }


    public void tick() {
        final EventArgs eventArgs = buildEventArgs();
        gameClockMillis += eventArgs.getDeltaTime() * 1000;
        final List<Component> componentsToUpdate = new ArrayList<>();
        final List<Actor> actorsToRemove = new ArrayList<>();

        for (final Actor actor : world.getActors()) {
            if (actor.isDestroying()) {
                actorsToRemove.add(actor);
                continue;
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

        actorsToRemove.forEach(world::removeActor);
    }

    private void update(
        final EventArgs eventArgs,
        final List<Component> updateComponents
    ) {
        updateComponents.forEach(t -> t.update(eventArgs));
    }

    // todo(p0) - obviously this shouldn't be part of the Engine
    private void renderDebug(EventArgs eventArgs, RenderContext renderContext) {
        if (eventArgs.getDeltaTime() > .2f) {
            renderContext.renderString(
                String.format("FPS: %d (time: %.1fms)",
                    (int) averageFps,
                    eventArgs.getDeltaTime()),
                Color.red,
                0, 12,
                RenderLayer._DEBUG);
        }  else {
            renderContext.renderString(
                String.format("FPS: %d (time: %.1fms)",
                    (int) averageFps,
                    eventArgs.getDeltaTime()),
                Color.yellow,
                0, 12,
                RenderLayer._DEBUG);
        }

        renderContext.renderString(
            String.format("Game clock: %.2fs",
                gameClockMillis * 0.001f),
            Color.yellow,
            0, 28,
            RenderLayer._DEBUG);
        renderContext.renderString("Total actors: " + world.getActors().size(),
            Color.yellow,
            0, 44,
            RenderLayer._DEBUG);
    }
}
