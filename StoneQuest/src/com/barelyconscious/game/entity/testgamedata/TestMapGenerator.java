package com.barelyconscious.game.entity.testgamedata;

import com.barelyconscious.game.entity.TileActor;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.shape.Vector;

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

    private static final Map<Integer, Resources.Sprite_Resource> KEY_SPRITE = new HashMap<>() {{
        put(1, Resources.TEX_FALLGRASS);
        put(2, Resources.TEX_FALLDIRT);
        put(3, Resources.TEX_REINFORCEDCAVEFLOOR_V1);
        put(4, Resources.TEX_FALLPACKED);
        put(5, Resources.TEX_COBBLE);
        put(6, Resources.TEX_GRAVEL);
        put(7, Resources.TEX_FARM);
    }};

    private static void createTiles(final World world) {
        int tileWidth = 25;
        int tileHeight = 25;

        int i = 0;
        for (int x = 0; x < tileWidth; ++x) {
            for (int y = 0; y < tileHeight; ++y) {
                int key = map[
                    x + y * tileWidth
                    ];
                final Resources.Sprite_Resource rSprite = KEY_SPRITE.get(key);

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
