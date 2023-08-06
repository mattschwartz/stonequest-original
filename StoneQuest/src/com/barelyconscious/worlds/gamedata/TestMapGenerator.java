package com.barelyconscious.worlds.gamedata;

import com.barelyconscious.worlds.entity.TileActor;
import com.barelyconscious.worlds.game.World;
import com.barelyconscious.worlds.engine.input.MouseInputHandler;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.entity.Tile;
import com.barelyconscious.worlds.common.shape.Vector;

import java.util.HashMap;
import java.util.Map;

public class TestMapGenerator {
    public static void generateMapTiles(final World world) {
        createTiles(world);
    }

    private static final int[] map = new int[] {
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,
        3,1,1,1,1,1,6,5,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,
        3,7,7,7,7,1,6,5,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,
        3,7,7,7,7,1,6,5,6,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,3,
        3,7,7,7,7,1,6,5,6,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,3,
        3,1,1,1,1,1,6,5,6,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,
        3,1,1,1,1,1,6,5,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,3,
        3,1,1,1,1,1,6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3,
        3,1,1,1,1,1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,3,
        3,1,1,1,1,1,2,2,2,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,3,
        3,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2,3,
        3,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2,3,
        3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,2,2,2,2,3,3,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3,
        3,2,4,4,4,2,2,2,2,3,3,1,1,1,1,1,1,1,3,3,2,2,2,2,3,
        3,2,4,4,4,2,2,3,3,3,1,1,1,1,1,1,1,2,3,3,2,2,2,2,3,
        3,2,2,2,2,2,2,3,3,3,1,1,1,1,1,1,1,1,3,3,2,2,2,2,3,
        3,2,2,2,2,2,2,2,3,3,2,1,1,1,1,3,3,2,2,2,2,2,2,2,3,
        3,2,2,2,2,2,2,2,2,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,2,2,4,2,2,2,4,2,4,2,2,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,2,4,4,4,4,2,2,4,4,2,2,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,4,2,4,4,2,2,4,2,4,2,2,2,2,2,2,2,2,2,2,2,3,
        3,2,2,2,2,2,2,2,4,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,
        3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,};

    private static final Map<Integer, BetterSpriteResource> KEY_SPRITE = new HashMap<>() {{
        put(1, new BetterSpriteResource("texture::fallgrass"));
        put(2, new BetterSpriteResource("texture::falldirt"));
        put(3, new BetterSpriteResource("texture::reinforcedcaveFloor_v1"));
        put(4, new BetterSpriteResource("texture::falldirt"));
        put(5, new BetterSpriteResource("texture::cobble"));
        put(6, new BetterSpriteResource("texture::gravel"));
        put(7, new BetterSpriteResource("texture::farm"));
    }};

    private static void createTiles(final World world) {
        int tileWidth = 16;
        int tileHeight = 16;

        int i = 0;
        for (int x = 0; x < tileWidth; ++x) {
            for (int y = 0; y < tileHeight; ++y) {
                int key = map[
                    x + y * tileWidth
                    ];
                final BetterSpriteResource rSprite = KEY_SPRITE.get(key);

                int width = 64;
                int height =64;

                final Tile tile = new Tile(0, "Grass", key == 3, false);
                final Vector transform = new Vector(x * width, y * height);
                final TileActor aTile = new TileActor(transform, tile, rSprite,
                    width, height, MouseInputHandler.instance());

                if (x < 3 && y < 3) {
                    aTile.setOpacity(TileActor.OpacityPresets.VISIBLE);
                } else if (x < 8 && y < 8) {
                    aTile.setOpacity(TileActor.OpacityPresets.LIGHTLY_OBSCURED);
                } else {
                    aTile.setOpacity(TileActor.OpacityPresets.HEAVILY_OBSCURED);
                }

                world.spawnActor(aTile);
            }
        }
    }
}
