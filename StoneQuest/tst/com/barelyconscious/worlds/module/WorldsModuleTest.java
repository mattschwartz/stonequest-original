package com.barelyconscious.worlds.module;

import com.barelyconscious.worlds.entity.GameInstance;
import com.barelyconscious.worlds.physics.Physics;
import com.barelyconscious.worlds.entity.graphics.Screen;
import com.barelyconscious.worlds.entity.World;
import com.barelyconscious.worlds.exception.InvalidGameConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class WorldsModuleTest {

    private static final String TEST_PROPERTIES_FILE_PATH = "Worlds.TEST.properties";

    private Injector classUnderTest;

    @BeforeEach
    void setup() {
        classUnderTest = Guice.createInjector(
            new WorldsModule(TEST_PROPERTIES_FILE_PATH));
    }

    @Test
    void constructor_withInvalidArguments_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new WorldsModule(null));
        assertThrows(IllegalArgumentException.class,
            () -> new WorldsModule(""));
        assertThrows(IllegalArgumentException.class,
            () -> new WorldsModule("    "));
    }

    @Test
    void configure_withInvalidPropertiesFile_shouldThrowIOException() {
        String missingPropertiesFilepath = UUID.randomUUID().toString();
        final WorldsModule worldsModule_withInvalidPropertiesFile = new WorldsModule(
            missingPropertiesFilepath);
        assertThrows(InvalidGameConfigurationException.class,
            worldsModule_withInvalidPropertiesFile::configure);
    }

    @ParameterizedTest
    @ValueSource(classes = {
        Physics.class,
        GameInstance.class,
        World.class,
        Screen.class
    })
    void provides_withValidInstanceKey_shouldReturnNonnullDependency(
        final Class<?> klass
    ) {
        assertNotNull(classUnderTest.getInstance(klass));
    }
}
