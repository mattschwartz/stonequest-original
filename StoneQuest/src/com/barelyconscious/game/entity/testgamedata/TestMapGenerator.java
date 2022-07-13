package com.barelyconscious.game.entity.testgamedata;

import com.barelyconscious.game.entity.TileActor;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.shape.Vector;
import lombok.val;

import java.util.Random;

public class TestMapGenerator {
    private static final Random RANDOM = new Random(100L);

    public static void generateMapTiles(final World world) {
        createTiles(world);
    }

    private static void createTiles(final World world) {
        for (int x = 0; x < 75; ++x) {
            for (int y = 0; y < 75; ++y) {
                final int grassTileId = RANDOM.nextInt(100);
                ResourceSprite rSprite;
                if (grassTileId > 23) {
                    rSprite = ResourceSprite.GRASS_1;
                } else if (grassTileId > 15) {
                    rSprite = ResourceSprite.GRASS_2;
                } else {
                    rSprite = ResourceSprite.GRASS_3;
                }

                val aTile = new TileActor(new Vector(x * rSprite.width, y * rSprite.height), new Tile(
                    0,
                    "Grass",
                    rSprite,
                    false,
                    false),
                    rSprite.width,
                    rSprite.height,
                    MouseInputHandler.instance());
                world.spawnActor(aTile);
            }
        }
    }
}
