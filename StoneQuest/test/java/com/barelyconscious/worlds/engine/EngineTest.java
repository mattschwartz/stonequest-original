package com.barelyconscious.worlds.engine;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.engine.graphics.Screen;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EngineTest {

    @Mock
    private GameInstance mockGameInstance;
    @Mock
    private World mockWorld;
    @Mock
    private Screen mockScreen;
    @Mock
    private Physics mockPhysics;
    @Mock
    private Clock mockClock;

    private Engine classUnderTest;

    @Mock
    private RateLimiter mockUpsLimiter;
    @Mock
    private RateLimiter mockFpsLimiter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        classUnderTest = new Engine(
            mockPhysics,
            mockClock,
            mockUpsLimiter,
            mockFpsLimiter);
        classUnderTest.prestart(
            mockGameInstance,
            mockWorld,
            mockScreen,
            mock(PlayerController.class));
    }

    @Test
    void constructor_invalidArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockPhysics, null, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockPhysics, mockClock, null, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockPhysics, mockClock, mockUpsLimiter, null));
    }

    @Test
    void tick_withPhysicsComponent_shouldUpdatePhysics() {
        Actor actor_withPhysicsComponent = new Actor();
        BoxColliderComponent mockPhysicsComponent = mock(BoxColliderComponent.class);

        when(mockPhysicsComponent.isEnabled()).thenReturn(true);
        when(mockPhysicsComponent.isRemoveOnNextUpdate()).thenReturn(false);
        actor_withPhysicsComponent.addComponent(mockPhysicsComponent);

        mockWorld.addActor(actor_withPhysicsComponent);
        when(mockWorld.getActors()).thenReturn(
            Lists.newArrayList(actor_withPhysicsComponent));

        var eventArgs = mock(EventArgs.class);
        when(eventArgs.getWorldContext()).thenReturn(mock(WorldUpdateContext.class));
        classUnderTest.tick(eventArgs);

        verify(mockPhysics).updatePhysics(eventArgs, mockWorld.getActors());
        verify(mockPhysicsComponent).update(eventArgs);
    }
}
