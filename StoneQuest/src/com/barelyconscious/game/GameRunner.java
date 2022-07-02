package com.barelyconscious.game;

import com.barelyconscious.game.entity.AEntity;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.Camera;
import com.barelyconscious.game.entity.Engine;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;
import com.barelyconscious.game.entity.Hero;
import com.barelyconscious.game.entity.HeroClassType;
import com.barelyconscious.game.entity.Inventory;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.TileActor;
import com.barelyconscious.game.entity.World;
import com.barelyconscious.game.entity.components.BoxColliderComponent;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.DestroyOnDeathComponent;
import com.barelyconscious.game.entity.components.DropOnDeathComponent;
import com.barelyconscious.game.entity.components.HealthBarComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.MoveComponent;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.components.StatChangeOverTime;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.entity.gui.GuiCanvas;
import com.barelyconscious.game.entity.gui.HeroQuickbarPanel;
import com.barelyconscious.game.entity.gui.LayoutData;
import com.barelyconscious.game.entity.gui.VDim;
import com.barelyconscious.game.entity.gui.widgets.InventoryBagWidget;
import com.barelyconscious.game.entity.gui.widgets.TileInfoWidget;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.entity.input.MouseInputHandler;
import com.barelyconscious.game.entity.item.GameItems;
import com.barelyconscious.game.entity.playercontroller.PlayerController;
import com.barelyconscious.game.entity.resources.GUISpriteSheet;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.entity.tile.Tile;
import com.barelyconscious.game.module.WorldsModule;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.val;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;

import static com.barelyconscious.game.entity.resources.GUISpriteSheet.Resources.INV_ITEM_SLOT_BACKGROUND;

public final class GameRunner {

    public static void main(final String[] args) {
        final String propertiesFilePath = "Worlds.properties";
        final Injector injector = Guice.createInjector(new WorldsModule(propertiesFilePath));

        final GameInstance gi = injector.getInstance(GameInstance.class);

        final JFrame frame = injector.getInstance(JFrame.class);
        final World world = injector.getInstance(World.class);
        final Screen screen = injector.getInstance(Screen.class);
        final MouseInputHandler mouseInputHandler = injector.getInstance(MouseInputHandler.class);
        final KeyInputHandler keyInputHandler = injector.getInstance(KeyInputHandler.class);
        _createMap(world, mouseInputHandler, keyInputHandler, screen.getCamera());
        _populateTestWorld(world, screen, mouseInputHandler, keyInputHandler);
        final Engine engine = injector.getInstance(Engine.class);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Stopping engine...");
                engine.stop();
                super.windowClosing(e);
            }
        });

        val aGui = new GuiCanvas(screen);
        TileInfoWidget tiw = new TileInfoWidget(LayoutData.builder()
            .anchor(new VDim(0.5f, 0, -45, 15))
            .size(new VDim(0, 0, 15, 45))
            .build());
        tiw.setEnabled(false);
        aGui.addWidget(tiw);

        world.spawnActor(aGui);

        _initTest(world, screen, aGui);

        final PlayerController playerController = injector.getInstance(PlayerController.class);
        _setupInventory(aGui, playerController.getInventory());

        frame.requestFocus();

        engine.start();
        System.out.println("Saving game...");
        System.out.println("Cleaning up...");
    }

    private static void _initTest(final World world, final Screen screen, final GuiCanvas aGui) {
        val heroNicnole = new Hero(
            "Nicnole",
            new Vector(200, 264),
            3,
            14,
            16,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 13f);
                put(Stats.StatName.DEXTERITY, 9f);
                put(Stats.StatName.CONSTITUTION, 18f);
                put(Stats.StatName.INTELLIGENCE, 11f);
                put(Stats.StatName.WISDOM, 13f);
                put(Stats.StatName.CHARISMA, 11f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.MACHINIST);
        heroNicnole.addComponent(new MoveComponent(heroNicnole, 32f));
        heroNicnole.addComponent(new SpriteComponent(heroNicnole, Resources.getSprite(ResourceSprite.HERO_2), RenderLayer.ENTITIES));
        heroNicnole.addComponent(new BoxColliderComponent(heroNicnole, true, true, new Box(0, 32, 0, 32)));
        heroNicnole.addComponent(new HealthBarComponent(heroNicnole, heroNicnole.getComponent(HealthComponent.class)));

        world.spawnActor(heroNicnole);
        aGui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                -(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2),
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            heroNicnole,
            Resources.getSprite(ResourceSprite.HERO_2)));

        val heroJohn = new Hero(
            "John",
            new Vector(186, 299),
            38,
            24,
            24,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 10f);
                put(Stats.StatName.DEXTERITY, 15f);
                put(Stats.StatName.CONSTITUTION, 15f);
                put(Stats.StatName.INTELLIGENCE, 11f);
                put(Stats.StatName.WISDOM, 15f);
                put(Stats.StatName.CHARISMA, 11f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.SHADOW_ASSASSIN);
        heroJohn.addComponent(new MoveComponent(heroJohn, 32f));
        heroJohn.addComponent(new SpriteComponent(heroJohn, Resources.getSprite(ResourceSprite.HERO_3), RenderLayer.ENTITIES));
        heroJohn.addComponent(new BoxColliderComponent(heroJohn, true, true, new Box(0, 32, 0, 32)));
        heroJohn.addComponent(new HealthBarComponent(heroJohn, heroJohn.getComponent(HealthComponent.class)));
        heroJohn.addComponent(new StatChangeOverTime(heroJohn,
            heroJohn.getComponent(HealthComponent.class),
            6,
            0.5f,
            1f));

        world.spawnActor(heroJohn);
        aGui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                +(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2)
                    + 16,
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            heroJohn,
            Resources.getSprite(ResourceSprite.HERO_3)));
        world.spawnActor(aGui);

        GameInstance.getInstance().setHero(heroJohn, GameInstance.PartySlot.RIGHT);
        GameInstance.getInstance().setHero(heroNicnole, GameInstance.PartySlot.MIDDLE);
        GameInstance.getInstance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
    }

    private static void _populateTestWorld(final World world, final Screen screen, final MouseInputHandler mouseInputHandler, final KeyInputHandler keyInputHandler) {
        val heroPaul = new Hero(
            "Paul",
            new Vector(200, 200),
            3,
            11,
            12,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.STRENGTH, 7f);
                put(Stats.StatName.DEXTERITY, 13f);
                put(Stats.StatName.CONSTITUTION, 12f);
                put(Stats.StatName.INTELLIGENCE, 7f);
                put(Stats.StatName.WISDOM, 14f);
                put(Stats.StatName.CHARISMA, 15f);
            }}),
            144f,
            new Inventory(28),
            HeroClassType.PRIEST);
        heroPaul.addComponent(new MoveComponent(heroPaul, 32f));
        heroPaul.addComponent(new SpriteComponent(heroPaul, Resources.getSprite(ResourceSprite.HERO_1), RenderLayer.ENTITIES));
        heroPaul.addComponent(new BoxColliderComponent(heroPaul, true, true, new Box(0, 32, 0, 32)));
        heroPaul.addComponent(new HealthBarComponent(heroPaul, heroPaul.getComponent(HealthComponent.class)));

        world.spawnActor(heroPaul);
        GameInstance.getInstance().setHero(heroPaul, GameInstance.PartySlot.LEFT);

        val aGui = new GuiCanvas(screen);

        aGui.addWidget(new HeroQuickbarPanel(LayoutData.builder()
            .anchor(new VDim(0.5f, 1,
                -(GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth() / 2)
                    - GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth()
                    - 16,
                -GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight() - 28))
            .size(new VDim(0, 0,
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getWidth(),
                GUISpriteSheet.Resources.HERO_UNITFRAME_BACKDROP.getRegion().getHeight()))
            .build(),
            heroPaul,
            Resources.getSprite(ResourceSprite.HERO_1)));

        world.spawnActor(aGui);

        val aRat = new AEntity(
            "Sewer Rat",
            new Vector(264f, 208f),
            1,
            0,
            0,
            0,
            new Stats(new HashMap<>() {{
                put(Stats.StatName.CONSTITUTION, 10f);
            }}));
        aRat.addComponent(new BoxColliderComponent(aRat, true, true, new Box(0, 32, 0, 32)));
        aRat.addComponent(new SpriteComponent(aRat, Resources.getSprite(ResourceSprite.SEWER_RAT)));
        aRat.addComponent(new HealthBarComponent(aRat, aRat.getComponent(HealthComponent.class)));
        aRat.addComponent(new DestroyOnDeathComponent(aRat, 0));

        world.spawnActor(aRat);

        val aBullet = new Actor("Bullet", new Vector(264f, 100f));
        aBullet.addComponent(new MoveComponent(aBullet, 32f));
        aBullet.addComponent(new BoxColliderComponent(aBullet, false, true, new Box(0, 32, 0, 32)));
        aBullet.addComponent(new SpriteComponent(aBullet, Resources.getSprite(ResourceSprite.POTION)));

        aBullet.getComponent(BoxColliderComponent.class)
            .delegateOnEnter.bindDelegate((col) -> {
                if (col.causedByActor != aBullet) {
                    return null;
                }

                final Actor hit = col.hit;

                final HealthComponent health = hit.getComponent(HealthComponent.class);
                if (health != null && health.isEnabled()) {
                    health.adjust(-6);
                }

                aBullet.destroy();
                return null;
            });

        aBullet.getComponent(MoveComponent.class)
            .addForce(Vector.DOWN, 100f);

        world.spawnActor(aBullet);

        aRat.addComponent(new DropOnDeathComponent(aRat, GameItems.WILLOW_BARK.toItem()));
    }

    private static void _setupInventory(
        final GuiCanvas gui,
        final Inventory inventory
    ) {
        inventory.addItem(GameItems.WILLOW_BARK.toItem());
        inventory.addItem(GameItems.CURED_LEATHER.toItem());
        inventory.addItem(GameItems.IRON_ORE.toItem());
        inventory.addItem(GameItems.STREAM_DRIVE.toItem());
        inventory.addItem(GameItems.STREAM_DRIVE.toItem());

        val wBackpack = new InventoryBagWidget(LayoutData.builder()
            .anchor(new VDim(1, 0.5f,
                -(INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth() + 75),
                -(INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight() / 2)))
            .size(new VDim(0, 0,
                INV_ITEM_SLOT_BACKGROUND.getRegion().getWidth(),
                INV_ITEM_SLOT_BACKGROUND.getRegion().getHeight()))
            .build(), inventory, 4, 4);

        gui.addWidget(wBackpack);
    }

    private static final Random RANDOM = new Random(100L);

    private static void _createMap(
        final World world,
        final MouseInputHandler mouseInputHandler,
        final KeyInputHandler keyInputHandler,
        final Camera camera
    ) {
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
                    mouseInputHandler);
                world.spawnActor(aTile);
            }
        }

        val aCamera = new Actor(camera.transform);

        class TranslateMoveComponent extends Component {
            public TranslateMoveComponent() {
                super(aCamera);
            }

            private Vector desiredLocation = camera.transform;

            public void translate(final Vector delta) {
                desiredLocation = desiredLocation.plus(delta);
            }

            @Override
            public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
                camera.transform = desiredLocation;
            }
        }

        final TranslateMoveComponent cameraMoveComponent = new TranslateMoveComponent();
        aCamera.addComponent(cameraMoveComponent);
        keyInputHandler.onKeyPressed.bindDelegate(keyEvent -> {
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                cameraMoveComponent.translate(Vector.RIGHT.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                cameraMoveComponent.translate(Vector.LEFT.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                cameraMoveComponent.translate(Vector.UP.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                cameraMoveComponent.translate(Vector.DOWN.multiply(100f));
            }
            return null;
        });
        world.spawnActor(aCamera);
    }
}
