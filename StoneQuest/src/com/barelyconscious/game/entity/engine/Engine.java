package com.barelyconscious.game.entity.engine;

import com.barelyconscious.game.delegate.Delegate;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.YieldingCallback;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.physics.Physics;
import com.barelyconscious.game.shape.Vector;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.awt.Color;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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

    private long lastRenderTimeMillis;
    private long lastUpdateTimeMillis;
    private final Physics physics;

    private long frames = 0;
    private long gameClockMillis = 1;
    private float averageFps = 0;

    private boolean isRunning = false;

    public static class JobRunContext {
        @Getter
        private final String jobId;
        @Getter
        private EventArgs eventArgs;
        private final List<YieldingCallback> yields = new ArrayList<>();

        public JobRunContext(final String jobId, final EventArgs e) {
            this.jobId = jobId;
            this.eventArgs = e;
        }

        public boolean isCompleted() {
            return yields.isEmpty();
        }

        public void yield(final long yieldForMillis, final Function<JobRunContext, Void> callback) {
            yields.add(new YieldingCallback(yieldForMillis, callback));
        }

        private void setEventArgs(EventArgs eventArgs) {
            this.eventArgs = eventArgs;
        }
    }

    public static class JobExecution {

        @Getter
        private final String jobId;
        @Getter
        private final String createdDateTime;

        public final Delegate<Void> delegateOnJobSucceeded = new Delegate<>();
        public final Delegate<Exception> delegateOnJobFailed = new Delegate<>();
        /**
         * Delegate always fired whether the job completed successfully or not.
         */
        public final Delegate<Void> delegateOnCompleted = new Delegate<>();

        private final JobRunContext runContext;
        private final Function<JobRunContext, Void> job;
        private boolean jobExecuted;
        private final Stopwatch stopwatch;

        public JobExecution(final EventArgs eventArgs, final Function<JobRunContext, Void> job) {
            this.job = job;
            this.jobExecuted = false;
            this.jobId = UUID.randomUUID().toString();
            this.createdDateTime = Clock.systemDefaultZone().toString();
            this.stopwatch = Stopwatch.createStarted();

            this.runContext = new JobRunContext(jobId, eventArgs);
        }

        private boolean isJobComplete(final EventArgs eventArgs) {
            if (!jobExecuted) {
                job.apply(runContext);
                jobExecuted=true;
            }

            final List<Exception> failedJobs = new ArrayList<>();
            runContext.setEventArgs(eventArgs);
            runContext.yields.removeIf(cb -> {
                try {
                    return cb.tickAndCall(runContext);
                } catch (final Exception e) {
                    log.warn("[Job={}] Failed to update: {}", jobId, e.getMessage(), e);
                    failedJobs.add(e);
                }
                return true;
            });

            if (runContext.isCompleted()) {
                log.debug("Completed job={} in {}ms", jobId, stopwatch.elapsed(TimeUnit.MILLISECONDS));
                if (failedJobs.isEmpty()) {
                    delegateOnJobSucceeded.call(null);
                } else {
                    delegateOnJobFailed.call(failedJobs.get(0));
                }
                delegateOnCompleted.call(null);
                return true;
            }

            return false;
        }
    }

    private final Queue<JobExecution> pendingJobExecutions = new ConcurrentLinkedDeque<>();

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

        final Thread threadUpdate = new Thread(() -> {
            this.lastUpdateTimeMillis = clock.millis();
            while (isRunning) {
                upsLimiter.acquire();

                final long now = clock.millis();
                final long deltaTime = now - lastUpdateTimeMillis;
                tick(buildEventArgs(deltaTime));

                this.lastUpdateTimeMillis = clock.millis();
            }
        });
        threadUpdate.start();

        while (isRunning) {
            this.lastRenderTimeMillis = clock.millis();
            fpsLimiter.acquire();

            final long now = clock.millis();
            final long deltaTime = now - lastRenderTimeMillis;
            renderTick(buildEventArgs(deltaTime));

            this.lastRenderTimeMillis = clock.millis();
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

    public void renderTick(final EventArgs eventArgs) {
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
            renderDebug(eventArgs, renderContext);
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
        RenderContext renderContext
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
            gameInstance.getPlayerController().getMouseScreenPos(),
            gameInstance.getPlayerController().getMouseWorldPos(),
            pendingJobExecutions);
    }
}
