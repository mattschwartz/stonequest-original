package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.ScreenComponent;
import com.barelyconscious.game.physics.PhysicsComponent;
import lombok.extern.log4j.Log4j2;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Log4j2
public final class Engine {

    private final GameInstance gameInstance;
    private final World world;
    private final Screen screen;
    private final Clock clock;

    private long lastTick;
    private final Physics physics;

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
    }

    public void tick() {
        final long now = clock.millis();
        final long deltaTime = now - lastTick;
        lastTick = now;
        final EventArgs eventArgs = new EventArgs(deltaTime * 0.001f);

        final List<PhysicsComponent> physicsComponents = new ArrayList<>();
        final List<Component> updateComponents = new ArrayList<>();
        final List<ScreenComponent> screenComponents = new ArrayList<>();

        final List<Actor> actorsToRemove = new ArrayList<>();

        for (final Actor actor : world.getActors()) {
            if (actor.isDestroying()) {
                actorsToRemove.add(actor);
                // todo: NOTE that this behavior is giving destroying actors 1 final tick
                //  might want to check here in case of bug...
            }
            if (!actor.isEnabled()) {
                continue;
            }

            for (final Component component : actor.getComponents()) {
                updateComponents.add(component);

                if (component instanceof PhysicsComponent) {
                    physicsComponents.add((PhysicsComponent) component);
                } else if (component instanceof ScreenComponent) {
                    screenComponents.add((ScreenComponent) component);
                }
            }
        }

        physics.updatePhysics(eventArgs, world.getActors());

        update(eventArgs, updateComponents);
        updateScreen(screenComponents);
        updateGui(screenComponents);

        actorsToRemove.forEach(world::removeActor);
    }

    private void update(
        final EventArgs eventArgs,
        final List<Component> updateComponents
    ) {
        updateComponents.forEach(t -> t.update(eventArgs));
    }

    private void updateScreen(final List<ScreenComponent> screenComponents) {
        final RenderContext renderContext = screen.createRenderContext();

        screenComponents.forEach(t -> {
            if (isActorInView(screen.getCamera(), t.getParent())) {
                t.render(renderContext);
            }
        });
        screen.render(renderContext);
    }

    private void updateGui(final List<ScreenComponent> screenComponents) {
        final RenderContext renderContext = screen.createRenderContext();

        screenComponents.forEach(t -> {
            if (isActorInView(screen.getCamera(), t.getParent())) {
                t.guiRender(renderContext);
            }
        });
        screen.render(renderContext);
    }

    private boolean isActorInView(
        final Camera camera,
        final Actor actor
    ) {
        final float xPos = actor.transform.x;
        final float yPos = actor.transform.y;

        return xPos >= camera.getViewX() && xPos < camera.getViewX() + camera.getViewWidth()
            && yPos >= camera.getViewY() && yPos < camera.getViewY() + camera.getViewHeight();
    }
}
