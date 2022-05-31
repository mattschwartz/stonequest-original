package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.physics.Physics;
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
            mockGameInstance,
            mockWorld,
            mockScreen,
            mockPhysics,
            mockClock,
            mockUpsLimiter,
            mockFpsLimiter);
    }

    @Test
    void constructor_invalidArguments_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(null, mockWorld, mockScreen, mockPhysics, mockClock, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, null, mockScreen, mockPhysics, mockClock, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, mockWorld, null, mockPhysics, mockClock, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, mockWorld, mockScreen, null, mockClock, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, mockWorld, mockScreen, mockPhysics, null, mockUpsLimiter, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, mockWorld, mockScreen, mockPhysics, mockClock, null, mockFpsLimiter));
        assertThrows(IllegalArgumentException.class,
            () -> new Engine(mockGameInstance, mockWorld, mockScreen, mockPhysics, mockClock, mockUpsLimiter, null));
    }

    @Test
    void tick_withPhysicsComponent_shouldUpdatePhysics() {
        Actor actor_withPhysicsComponent = mock(Actor.class);

        BoxColliderComponent mockPhysicsComponent = mock(BoxColliderComponent.class);
        when(actor_withPhysicsComponent.isEnabled()).thenReturn(true);
        when(actor_withPhysicsComponent.isDestroying()).thenReturn(false);
        when(actor_withPhysicsComponent.getComponents()).thenReturn(
            Lists.newArrayList(mockPhysicsComponent));
        when(mockWorld.getActors()).thenReturn(
            Lists.newArrayList(actor_withPhysicsComponent));

        classUnderTest.tick();

        verify(mockPhysics).updatePhysics(any(EventArgs.class), eq(mockWorld.getActors()));
        verify(mockPhysicsComponent).update(any());
    }
}
