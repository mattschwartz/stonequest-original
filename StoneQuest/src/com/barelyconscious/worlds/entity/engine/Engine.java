package com.barelyconscious.worlds.entity.engine;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.GameInstance;
import com.barelyconscious.worlds.entity.World;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.graphics.RenderContext;
import com.barelyconscious.worlds.entity.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.graphics.Screen;
import com.barelyconscious.worlds.entity.playercontroller.MouseKeyboardPlayerController;
import com.barelyconscious.worlds.entity.playercontroller.PlayerController;
import com.barelyconscious.worlds.physics.Physics;
import com.barelyconscious.worlds.shape.Vector;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.awt.Color;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("UnstableApiUsage")
@Log4j2
public final class Engine {

    private final Clock clock;

    private final RateLimiter upsLimiter;
    private final RateLimiter fpsLimiter;

    private final Physics physics;

    private long frames = 0;
    private long gameClockMillis = 1;
    private float averageFps = 0;

    private boolean isRunning = false;

    private final Queue<JobExecution> pendingJobExecutions = new ConcurrentLinkedDeque<>();

    public Engine(
        final Physics physics,
        final Clock clock,
        final RateLimiter ups,
        final RateLimiter fps
    ) {
        checkArgument(physics != null, "physics is null");
        checkArgument(clock != null, "clock is null");
        checkArgument(ups != null, "ups is null");
        checkArgument(fps != null, "fps is null");

        this.physics = physics;
        this.clock = clock;
        this.upsLimiter = ups;
        this.fpsLimiter = fps;
    }

    private GameInstance gameInstance;
    private World world;
    private Screen screen;
    private PlayerController playerController;
    private boolean readyToStart = false;

    public void prestart(
        @NonNull final GameInstance gameInstance,
        @NonNull final World world,
        @NonNull final Screen screen,
        @NonNull final MouseKeyboardPlayerController playerController
    ) {
        this.gameInstance = gameInstance;
        this.world = world;
        this.screen = screen;
        this.playerController = playerController;

        gameInstance.setCamera(screen.getCamera());
        gameInstance.changeWorld(world);
        gameInstance.setPlayerController(playerController);
        readyToStart = true;
    }

    public void start() {
        if (!readyToStart) {
            throw new RuntimeException("Engine has not been configured.");
        }

        isRunning = true;

        final Thread threadUpdate = new Thread(() -> {
            long lastUpdateTimeMillis = clock.millis();
            while (isRunning) {
                upsLimiter.acquire();

                final long now = clock.millis();
                final long deltaTime = now - lastUpdateTimeMillis;
                tick(buildEventArgs(deltaTime));

                lastUpdateTimeMillis = clock.millis();
            }
        });
        threadUpdate.start();

        long lastRenderTimeMillis = clock.millis();
        while (isRunning) {
            fpsLimiter.acquire();

            final long now = clock.millis();
            final long deltaTime = now - lastRenderTimeMillis;
            renderTick(buildEventArgs(deltaTime));

            lastRenderTimeMillis = clock.millis();
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

    long next = 100;

    public void renderTick(
        final EventArgs eventArgs
    ) {
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

            final List<Component> componentsToRemove = new ArrayList<>();
            for (final Component c : actor.getComponents()) {
                if (c.isRemoveOnNextUpdate()) {
                    componentsToRemove.add(c);
                    continue;
                }

                if (c.isRenderEnabled()) {
                    c.render(eventArgs, renderContext);
                    c.guiRender(eventArgs, renderContext);
                }
            }
            componentsToRemove.forEach(actor::removeComponent);
        }

        if (EventArgs.IS_DEBUG) {
            renderDebug(eventArgs, renderContext, world);
        }

        screen.render(renderContext);
    }

    public void tick(final EventArgs eventArgs) {
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

            final List<Component> componentsToRemove = new ArrayList<>();
            for (final Component c : actor.getComponents()) {
                if (c.isRemoveOnNextUpdate()) {
                    componentsToRemove.add(c);
                    continue;
                }
                if (c.isEnabled()) {
                    componentsToUpdate.add(c);
                }
            }
            componentsToRemove.forEach(actor::removeComponent);
        }

        physics.updatePhysics(eventArgs, world.getActors());
        // Run jobs submitted from last tick
        runJobs(eventArgs);
        update(eventArgs, componentsToUpdate);

        actorsToRemove.forEach(world::removeActor);

        eventArgs.getWorldContext().applyActorOperations();
    }

    private void runJobs(final EventArgs eventArgs) {
        // make sure new calls aren't coming in before calling all actions
        // to prevent jobs from being able to submit new jobs and creating
        // an infinite loop or significantly slowing down updates
        eventArgs.stopAcceptingJobs();

        final Stopwatch sw = Stopwatch.createStarted();
        pendingJobExecutions.removeIf(t -> t.isJobComplete(eventArgs));
        sw.stop();

        if (sw.elapsed(TimeUnit.MILLISECONDS) > 5) {
            log.error("Running jobs took {}ms", sw.elapsed(TimeUnit.MILLISECONDS));
        }

        // allows other types of update to enqueue jobs on this tick
        // todo: this may not be a good idea so check here if bug
        eventArgs.startAcceptingJobs();
    }

    private void update(
        final EventArgs eventArgs,
        final List<Component> updateComponents
    ) {
        updateComponents.forEach(t -> t.update(eventArgs));
    }

    // todo(p0) - obviously this shouldn't be part of the Engine
    private void renderDebug(
        EventArgs eventArgs,
        RenderContext renderContext,
        World world
    ) {
        final Vector screenPos = renderContext.camera.screenToWorldPos(new Vector(5, 5));
        renderContext.renderRect(
            new Color(33, 33, 33, 200),
            true,
            (int) screenPos.x,
            (int) screenPos.y,
            150,
            50,
            RenderLayer._DEBUG
        );

        if (eventArgs.getDeltaTime() > .2f) {
            renderContext.getFontContext().renderString(
                String.format("FPS: %d (time: %.1fms)",
                    (int) averageFps,
                    eventArgs.getDeltaTime()),
                Color.red,
                5, 17,
                RenderLayer._DEBUG);
        } else {
            renderContext.getFontContext().renderString(
                String.format("FPS: %d (time: %.1fms)",
                    (int) averageFps,
                    eventArgs.getDeltaTime()),
                Color.yellow,
                5, 17,
                RenderLayer._DEBUG);
        }

        renderContext.getFontContext().renderString(
            String.format("Game clock: %.1fs",
                gameClockMillis * 0.001f),
            Color.yellow,
            5, 33,
            RenderLayer._DEBUG);
        renderContext.getFontContext().renderString("Total actors: " + world.getActors().size(),
            Color.yellow,
            5, 49,
            RenderLayer._DEBUG);
        renderContext.getFontContext().renderString("Active jobs: " + pendingJobExecutions.size(),
            Color.yellow,
            5, 65,
            RenderLayer._DEBUG);
    }

    private EventArgs buildEventArgs(final long deltaTime) {
        return new EventArgs(
            deltaTime * 0.001f,
            playerController.getMouseScreenPos(),
            playerController.getMouseWorldPos(),
            pendingJobExecutions,
            playerController,
            world);
    }
}
