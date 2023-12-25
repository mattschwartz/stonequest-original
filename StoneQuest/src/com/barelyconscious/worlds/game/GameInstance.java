package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.engine.Camera;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.entity.Wagon;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.systems.GuiSystem;
import com.barelyconscious.worlds.game.systems.GameSystem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * Expect that pretttty much all of this is going to go away
 * ^ lol well that didn't happen
 */
@Log4j2
public final class GameInstance {

    private static final class InstanceHolder {
        static final GameInstance instance = new GameInstance();
    }

    public static GameInstance instance() {
        return GameInstance.InstanceHolder.instance;
    }

    private final Map<Class<?>, GameSystem> gameSystems = new HashMap<>();

    /**
     * Helper method to add a log to the GUI
     *
     * @param message the message to add to the log
     */
    public static void log(String message) {
        var gui = instance().getSystem(GuiSystem.class);
        gui.getWorldLogPanel().addLogEntry(message);
    }

    @Getter
    @Setter
    private World world;

    @Getter
    @Setter
    private GameState gameState;

    @Getter
    @Setter
    private PlayerController playerController = new PlayerController();

    @Getter
    @Setter
    private PlayerPersonalDevice playerPersonalDevice;

    /**
     * A device used by the player to store and interact with media
     */
    @Getter
    @Setter
    private Camera camera;

    public void registerSystem(GameSystem system) {
        if (gameSystems.containsKey(system.getClass())) {
            throw new IllegalArgumentException("A system of type " + system.getClass().getName() + " is already registered.");
        }
        log.info("Registered system: " + system.getClass().getName());
        gameSystems.put(system.getClass(), system);
    }

    public <T extends GameSystem> T getSystem(Class<T> systemClass) {
        if (!gameSystems.containsKey(systemClass)) {
            throw new IllegalArgumentException("No system of type " + systemClass.getName() + " is registered.");
        }
        return (T) gameSystems.get(systemClass);
    }

    public void updateSystems(EventArgs args) {
        for (GameSystem system : gameSystems.values()) {
            system.update(args);
        }
    }
}
