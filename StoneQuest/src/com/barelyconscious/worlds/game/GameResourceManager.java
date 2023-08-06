package com.barelyconscious.worlds.game;

import com.barelyconscious.worlds.GameRunner;
import com.barelyconscious.worlds.common.exception.MissingResourceException;
import com.barelyconscious.worlds.game.resources.SpriteResource;
import com.barelyconscious.worlds.game.resources.spritesheet.SpritesheetManager;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameResourceManager {

    private static final class InstanceHolder {
        static final GameResourceManager instance = new GameResourceManager();
    }

    public static GameResourceManager instance() {
        return GameResourceManager.InstanceHolder.instance;
    }

    private GameResourceManager() {
    }

    private final Map<String, SpriteResource> cache = new HashMap<>();

    /**
     * @param name follows format `namespace::image_name`
     * @return the sprite resource with the given name or null if no such sprite resource exists
     */
    public SpriteResource get(final String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        return null;
    }

    @AllArgsConstructor
    enum Namespaces {
        UI("ui"),
        ENTITIES("entities"),
        TILES("tiles"),
        ITEMS("items"),
        EFFECTS("effects"),
        MISC("misc");

        private final String namespace;
    }

    public static void loadResources() {
        SpritesheetManager.loadItemsSpritesheet(SpritesheetManager.Namespace.ITEMS, "sprites/items_spritesheet.json", "sprites/items_spritesheet.png");
        SpritesheetManager.loadItemsSpritesheet(SpritesheetManager.Namespace.TEXTURE, "tiles/texture_spritesheet.json", "tiles/texture_spritesheet.png");
    }

    public void loadSprites() {
        for (Namespaces namespace : Namespaces.values()) {
            String path = "res/sprites/";// + namespace.namespace + "/";
            final BufferedImage sheet;
            try {

                GameResourceManager.class.getClassLoader().getResource(path);

//
//
//
//                final InputStream inputStream = Objects.requireNonNull(GameRunner.class.getClassLoader()
//                        .getResource(path))
//                    .openStream();
//                sheet = ImageIO.read(inputStream);
            } catch (final Exception e) {
                throw new MissingResourceException("Failed to load resource: " + path, e);
            }
        }

        // look at source directory
        // each directory contains sprites
        // directory name is the name of the sprite sheet
        // each sprite sheet is a collection of sprites from folder
        // outputs a sprite sheet definition, namespaced by folder and sprite name
        // builds cache
    }
}
