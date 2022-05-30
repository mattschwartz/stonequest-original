package com.barelyconscious.game.module;

import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.physics.Physics;
import com.barelyconscious.game.entity.Screen;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.exception.InvalidGameConfigurationException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;

public class WorldsModule extends AbstractModule {

    public static final String DEFAULT_WORLD_NAMED = "DefaultWorld";

    private final String propertiesFilepath;

    public WorldsModule(final String propertiesFilepath) {
        checkArgument(StringUtils.isNotBlank(propertiesFilepath),
            "propertiesFile cannot be blank");
        this.propertiesFilepath = propertiesFilepath;
    }

    @Override
    protected void configure() {
        try {
            final Properties properties = new Properties();
            final InputStream inputStream = Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource(propertiesFilepath))
                .openStream();

            properties.load(inputStream);
            Names.bindProperties(binder(), properties);
        } catch (final Exception ex) {
            throw new InvalidGameConfigurationException(String.format(
                "Failed while trying to load properties from filepath='%s': %s",
                    propertiesFilepath,
                    ex.getMessage()));
        }
    }

    @Singleton
    @Provides
    Physics providesPhysics() {
        return new Physics();
    }

    @Singleton
    @Provides
    GameInstance providesGameInstance() {
        return new GameInstance();
    }

    @Singleton
    @Provides
    @Named(DEFAULT_WORLD_NAMED)
    World providesWorld() {
        return new World();
    }

    @Singleton
    @Provides
    Screen providesScreen(
        @Named("window.width") final int screenWidth,
        @Named("window.height") final int screenHeight
    ) {
        return new Screen(screenWidth, screenHeight);
    }
}
