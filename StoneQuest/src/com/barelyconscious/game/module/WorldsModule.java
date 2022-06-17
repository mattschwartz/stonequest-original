package com.barelyconscious.game.module;

import com.barelyconscious.game.entity.Engine;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.playercontroller.PlayerController;
import com.barelyconscious.game.physics.Physics;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.exception.InvalidGameConfigurationException;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.InputStream;
import java.time.Clock;
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

            bind(World.class).annotatedWith(Names.named(DEFAULT_WORLD_NAMED)).to(World.class);

        } catch (final Exception ex) {
            throw new InvalidGameConfigurationException(String.format(
                "Failed while trying to load properties from filepath='%s': %s",
                propertiesFilepath,
                ex.getMessage()));
        }
    }

    @Singleton
    @Provides
    JFrame providesJFrame(
        @Named("window.width") final int screenWidth,
        @Named("window.height") final int screenHeight,
        @Named("window.title") final String windowTitle,
        @Named("version") final String version,
        final Screen screen,
        final MouseInputHandler mouseInputHandler,
        final KeyInputHandler keyInputHandler
    ) {
        final JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(screenWidth, screenHeight));
        frame.setTitle(windowTitle + " v" + version);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);

        screen.addToFrame(frame);

        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                screen.resizeScreen(frame.getWidth(), frame.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        frame.pack();

        screen.getCanvas().setFocusTraversalKeysEnabled(false);
        screen.getCanvas().addMouseListener(mouseInputHandler);
        screen.getCanvas().addMouseMotionListener(mouseInputHandler);

        screen.getCanvas().addKeyListener(keyInputHandler);

        return frame;
    }

    @Singleton
    @Provides
    Engine providesEngine(
        final GameInstance gameInstance,
        @Named(DEFAULT_WORLD_NAMED) final World world,
        final Screen screen,
        final Physics physics,
        final Clock clock,
        @Named("game.maxFramesPerSecond") final long fps,
        @Named("game.maxUpdatesPerSecond") final long ups
    ) {
        return new Engine(
            gameInstance,
            world,
            screen,
            physics,
            clock,
            RateLimiter.create(ups),
            RateLimiter.create(fps));
    }

    @Singleton
    @Provides
    Physics providesPhysics() {
        return new Physics();
    }

    @Singleton
    @Provides
    GameInstance providesGameInstance(final World world, final PlayerController playerController) {
        return GameInstance.createInstance(world, playerController);
    }

    @Provides
    @Singleton
    Inventory providesInventory() {
        return new Inventory(16);
    }

    @Singleton
    @Provides
    PlayerController providesPlayerController(
        final Inventory inventory,
        final MouseInputHandler mouseInputHandler,
        final KeyInputHandler keyInputHandler
    ) {
        return new PlayerController(
            inventory,
            mouseInputHandler,
            keyInputHandler);
    }

    @Singleton
    @Provides
    World providesWorld() {
        return new World();
    }

    @Singleton
    @Provides
    Screen providesScreen(
        @Named("window.width") final int screenWidth,
        @Named("window.height") final int screenHeight
    ) {
        final Canvas canvas = new Canvas();
        canvas.setSize(screenWidth, screenHeight);
        return new Screen(canvas, screenWidth, screenHeight);
    }

    @Singleton
    @Provides
    MouseInputHandler providesMouseInputHandler() {
        return MouseInputHandler.instance();
    }

    @Singleton
    @Provides
    KeyInputHandler providesKeyInputHandler() {
        return new KeyInputHandler();
    }

    @Singleton
    @Provides
    Clock providesClock() {
        return Clock.systemDefaultZone();
    }
}
