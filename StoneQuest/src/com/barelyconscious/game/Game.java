/* *****************************************************************************
 * Project:          StoneQuest
 * File displayName:        Game.java
 * Author:           Matt Schwartz
 * Date created:     07.01.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove
 *                   credit from code that was not written fully by yourself.
 *                   Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: The game's main, running thread.  In control of rendering
 *                   data to the screen and performing animations as well as keeping
 *                   track of game cross-project classes.
 **************************************************************************** */
package com.barelyconscious.game;

import com.barelyconscious.game.graphics.GameMap;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.input.MouseHandler;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.menu.*;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.systems.SystemsComposer;
import com.barelyconscious.systems.WindowManager;
import com.barelyconscious.systems.MessageSystem;
import sun.plugin2.message.Message;

import javax.swing.*;
import java.time.Clock;
import java.util.ArrayList;

import static com.barelyconscious.game.CreateItemsKt.createTestSubjects;

public class Game implements Runnable {

    private boolean running = false;
    // Cross-project classes
    public static Screen screen;
    public static Player player;
    public static Inventory inventory;
    public static World world;
    public static GameMap gameMap;
    public static KeyHandler keyHandler;
    public static MouseHandler mouseHandler;
    private static int width;
    private static int height;
    public static long frametime;
    public static int frames2;

    private static WindowManager windowManager;
    private static MessageSystem messageSystem;

    private final SystemsComposer composer = SystemsComposer.Companion.getINSTANCE();

    /**
     * Initializes the game window.
     */
    public void initGame(final Clock clock) {
        Common.generateGibberish(1000);

        width = 1280;
        height = 700;

//        messageSystem = composer.getDependency(MessageSystem.class);
        screen = new Screen(width, height);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler(messageSystem);

        windowManager = new WindowManager(
            keyHandler,
            mouseHandler,
            new JFrame(),
            messageSystem);
        windowManager.setView(screen);

        player = new Player(messageSystem);

        gameMap = new GameMap(1024, 1024, width, height);

        final MiniMap miniMap = new MiniMap();
        final ToolTipMenu toolTipMenu = new ToolTipMenu(player);
        final LootPickupMenu lootPickupMenu = new LootPickupMenu(toolTipMenu, messageSystem);

        inventory = new Inventory(messageSystem);

        world = new World(
            player,
            gameMap,
            toolTipMenu,
            lootPickupMenu,
            mouseHandler,
            windowManager,
            messageSystem,
            clock);

        final BuffBar buffBar = new BuffBar(miniMap);
        final AttributesMenu attributesMenu = new AttributesMenu(miniMap);
        final InventoryMenu invenMenu = new InventoryMenu();

        windowManager.addWidget(toolTipMenu);
        windowManager.addWidget(lootPickupMenu);
        windowManager.addWidget(attributesMenu);
        windowManager.addWidget(invenMenu);
        windowManager.addWidget(miniMap);
        windowManager.addWidget(buffBar);
//        windowManager.addWidget(composer.getTextLog());

        screen.addRenderable(miniMap);
        screen.addRenderable(buffBar);
        screen.addRenderable(invenMenu);
        screen.addRenderable(attributesMenu);
//        screen.addRenderable(composer.getTextLog());

        onResize(width, height);

        screen.addMouseListener(mouseHandler);
        screen.addMouseMotionListener(mouseHandler);
        screen.addMouseWheelListener(mouseHandler);

        mouseHandler.registerClickableListener(world);
        windowManager.registerClickableAreas();
        mouseHandler.registerHoverableListener(world);
        windowManager.registerHoverableAreas();
        windowManager.registerKeyListeners();

        Common.setThemeForeground(Common.THEME_PASTEL_GREEN);

        // debug stuff
        createTestSubjects(player, inventory, messageSystem);
        final Item fake = new Item(
            "Superior Belt of Pants Holding",
            39202,
            1,
            "A description",
            new ArrayList<>(),
            0,
            1,
            Tile.BELT_IRON_TILE_ID
        );
        world.addLoot(new Loot(fake, 0, 0, messageSystem));
    } // init

    /**
     * Resizes the game's window as well as all game components
     *
     * @param width  the number of tiles wide
     * @param height the number of tiles high
     */
    public void onResize(int width, int height) {
        windowManager.resize(width, height);

        screen.resizeScreen(width, height);

        width = screen.getWidth();
        height = screen.getHeight();

        world.resize(width, height);
    } // resize

    /**
     * Start the game's main running thread.
     */
    private void Start() {
        running = true;
        new Thread(this).start();
    } // start

    /**
     * Called when the player quits the game.
     */
    public static void Stop() {
        System.err.println("saving data");
        System.exit(0);
    } // stop

    /**
     * Animation and JFrame updates. Method copied from Notch's LD22 entry.
     */
    @Override
    public void run() {
        int frames = 0;
        long lastTimer2 = System.currentTimeMillis();

        final Clock utcClock = Clock.systemUTC();

        initGame(utcClock);
        windowManager.writeWelcomeMessage();
        long end, start;

        while (running) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.err.println(" [ERR] Fatal error in run(): " + e);
                System.exit(1);
            } // try-catch

            frames++;

            start = System.nanoTime();
            screen.clear();
            screen.render();
            end = System.nanoTime();
//            world.tick();

            if (System.currentTimeMillis() - lastTimer2 > 1000) {
                lastTimer2 += 1000;
                frametime = end - start;
                frames2 = frames;
                frames = 0;
            } // if

            windowManager.update();
        } // while
    } // run

    /**
     * Returns the width, in pixels, of the frame
     *
     * @return
     */
    public static int getGameWidth() {
        return width;
    } // getPixelWidth

    /**
     * Returns the height, in pixels, of the frame
     *
     * @return
     */
    public static int getGameHeight() {
        return height;
    } // getPixelHeight

    /**
     * Self-explanatory
     *
     * @param args unused
     */
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.Start();


//        while (true) {
//            Common.setThemeForeground(Common.THEME_PASTEL_GREEN);
//            Thread.sleep(500);
//            Common.setThemeForeground(Common.THEME_PASTEL_RED);
//            Thread.sleep(500);
//            Common.setThemeForeground(Common.THEME_PASTEL_BLUE);
//            Thread.sleep(500);
//        }

//        Sound.AMBIENT_OUTSIDE.loop();
//        Sound.RAIN.loop();
    } // main

    /*
     // closing down the window makes sense as a method, so here are
     // the salient parts of what happens with the JFrame extending class ..

     public class FooWindow extends JFrame {
     public FooWindow() {
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setBounds(5, 5, 400, 300);  // yeah yeah, this is an example ;P
     setVisible(true);
     }
     public void pullThePlug() {
     WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
     Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
     }
     }

     // Here's how that would be employed from elsewhere -

     // someplace the window gets created ..
     FooWindow fooey = new FooWindow();
     ...
     // and someplace else, you can close it thusly
     fooey.pullThePlug();
     */
} // Game
