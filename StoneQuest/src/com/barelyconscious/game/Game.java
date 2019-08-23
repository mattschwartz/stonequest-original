/* *****************************************************************************
 * Project:          StoneQuest
 * File name:        Game.java
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

import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.menu.InventoryMenu;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.GameMap;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.input.MouseHandler;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Food;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.item.Key;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.menu.AttributesMenu;
import com.barelyconscious.game.menu.BuffBar;
import com.barelyconscious.game.menu.LootPickupMenu;
import com.barelyconscious.game.menu.MiniMap;
import com.barelyconscious.game.menu.ToolTipMenu;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.player.activeeffects.AntimagicPotionEffect;
import com.barelyconscious.game.player.activeeffects.AntitoxinPotionEffect;
import com.barelyconscious.game.player.activeeffects.Curse;
import com.barelyconscious.game.player.activeeffects.Poison;
import com.barelyconscious.game.player.activeeffects.StatPotionEffect;
import com.barelyconscious.game.spawnable.Loot;
import com.barelyconscious.services.WindowManager;

import java.awt.Dimension;
import java.time.Clock;
import javax.swing.JFrame;

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

    private static final TextLog textLog = new TextLog(45, 50, 100);

    /**
     * Initializes the game window.
     */
    public void initGame(final Clock clock) {
        Common.generateGibberish(1000);

        width = 1280;
        height = 700;

        screen = new Screen(width, height);

        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler(textLog);

        windowManager = new WindowManager(
            keyHandler,
            mouseHandler,
            new JFrame());
        windowManager.setView(screen);

        player = new Player(textLog);

        gameMap = new GameMap(1024, 1024, width, height);

        final MiniMap miniMap = new MiniMap();
        final ToolTipMenu toolTipMenu = new ToolTipMenu(player);
        final LootPickupMenu lootPickupMenu = new LootPickupMenu(toolTipMenu, textLog);

        inventory = new Inventory(textLog);

        world = new World(
            player,
            textLog,
            gameMap,
            toolTipMenu,
            lootPickupMenu,
            mouseHandler,
            clock
        );

        final BuffBar buffBar = new BuffBar(miniMap);
        final AttributesMenu attributesMenu = new AttributesMenu(miniMap);
        final InventoryMenu invenMenu = new InventoryMenu();

        windowManager.addWidget(buffBar);
        windowManager.addWidget(toolTipMenu);
        windowManager.addWidget(lootPickupMenu);
        windowManager.addWidget(attributesMenu);
        windowManager.addWidget(invenMenu);
        windowManager.addWidget(textLog);
        windowManager.addWidget(miniMap);

        screen.addRenderable(buffBar);
        screen.addRenderable(miniMap);
        screen.addRenderable(invenMenu);
        screen.addRenderable(attributesMenu);
        screen.addRenderable(textLog);

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
        disposableFunctionToAddStuffToThePlayersInventory();
        world.addLoot(new Loot(new Item("Superior Belt of Pants Holding", 39202, Tile.BELT_IRON_TILE_ID), 0, 0, textLog));
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

    public static void disposableFunctionToAddStuffToThePlayersInventory() {
        Armor lHelm = new Armor("Leather Helmet", 2567, 15, Player.HELM_SLOT_ID, Tile.HELMET_CLOTH_TILE_ID, new AttributeMod(Player.ACCURACY, -11), new AttributeMod(Player.FIRE_MAGIC_BONUS, 54), new AttributeMod(Player.HOLY_MAGIC_BONUS, 22), new AttributeMod(Player.DEFENSE, 25), new AttributeMod(Player.HITPOINTS, 12), new AttributeMod(Player.AGILITY, 5), new AttributeMod(Player.DEFENSE, 200));
        Armor lChest = new Armor("Leather Chest", 1500, 27, Player.CHEST_SLOT_ID, Tile.CHEST_CLOTH_TILE_ID);
        Armor lGreaves = new Armor("Leather Greaves", 220, 15, Player.GREAVES_SLOT_ID, Tile.GREAVES_LEATHER_TILE_ID);
        Armor lboots = new Armor("Leather Boots", 1789, 10, Player.BOOTS_SLOT_ID, Tile.BOOTS_LEATHER_TILE_ID);
        Armor lBelt = new Armor("Leather Belt", 1677, 11, Player.BELT_SLOT_ID, Tile.BELT_LEATHER_TILE_ID);
        Armor lShield = new Armor("Wooden Shield", 1500, 55, Player.OFF_HAND_SLOT_ID, Tile.OFFHAND_SHIELD_WOOD_TILE_ID);
        Weapon wSword = new Weapon("Wooden Sword", 1358, 1, 4, Tile.MAINHAND_SWORD_TILE_ID);
        Armor bNeck = new Armor("Epic Awesome SLJ Necklace", 256888, 0, Player.NECK_SLOT_ID, Tile.NECKLACE_TILE_ID, new AttributeMod(Player.HITPOINTS, 200));
        Armor bRing = new Armor("Lousy Piece of Boring Ring", 0, 0, Player.RING_SLOT_ID, Tile.RING_TILE_ID);
        Armor bERing = new Armor("Broken Earring", 0, 0, Player.EARRING_SLOT_ID, Tile.EARRING_TILE_ID);

        lChest.setRarityColor(Common.ITEM_RARITY_RARE_RGB);

        Food apple = new Food("Apple", 0, 1, Tile.FOOD_TILE_ID, 99f);
        Food apple2 = new Food("Apple", 0, 2, Tile.FOOD_TILE_ID, 99f);
        Potion healthPotion = new Potion("Potion of Might", 2990, 1, new StatPotionEffect(129, "Potion of Might", new AttributeMod(Player.HITPOINTS, 21), new AttributeMod(Player.STRENGTH, 15)));
        Potion newPotion = new Potion("Potion of Awesome", 159, 3, new StatPotionEffect(300, "Potion of Awesome", new AttributeMod(Player.DEFENSE, 12), new AttributeMod(Player.STRENGTH, 3)));
        Potion pootion = new Potion("Potion of Pi", 1, 5, new StatPotionEffect(95, "Potion of Pi", new AttributeMod(Player.DEFENSE, 12), new AttributeMod(Player.STRENGTH, 3), new AttributeMod(Player.AGILITY, 15), new AttributeMod(Player.DEFENSE, 55), new AttributeMod(Player.FIRE_MAGIC_BONUS, 180)));
        Potion antiMagicPotion = new Potion("Antimagic Potion", 1589, 2, Tile.ANTIMAGIC_POTION_TILE_ID, new AntimagicPotionEffect("Antimagic Potion"));
        Potion antivenomPotion = new Potion("Antivenom", 18890, 3, new AntitoxinPotionEffect("Antivenom"));

        Curse curse = new Curse("Curse of Evilness", 299, new AttributeMod(Player.ACCURACY, 15), new AttributeMod(Player.DEFENSE, 5));
        Curse curse2 = new Curse("Curse of Evilness", 1000, new AttributeMod(Player.AGILITY, 15), new AttributeMod(Player.DEFENSE, 5));
        Curse curse3 = new Curse("Curse of Evilness", 1335, new AttributeMod(Player.AGILITY, 155), new AttributeMod(Player.DEFENSE, 25));
        Curse curse4 = new Curse("Curse of Evilness", 1240, new AttributeMod(Player.AGILITY, 16), new AttributeMod(Player.DEFENSE, 15));

        Poison poi = new Poison("Snake Venom", 69, 1.5f, 7, textLog);
        Poison poi2 = new Poison("Spider Bite", 24, 0.5f, 6, textLog);

        Projectile someBronzeArrows = new Projectile("Bronze-tipped Arrows", 255, 15, Tile.ARROW_TILE_ID, true, Projectile.BRONZE_TIP);

        Scroll scr1 = new Scroll("Invisibility", 258, 1, textLog, new AttributeMod(Player.ACCURACY, 255f));

        Key key = new Key("Key, bitches", 255, 1);

        inventory.addItem(scr1);

        player.applyDebuff(poi);
        player.applyDebuff(curse);
        player.applyDebuff(curse2);
        player.applyDebuff(curse3);
        player.applyDebuff(curse4);
        player.applyDebuff(poi2);

        inventory.addItem(apple);
        inventory.addItem(healthPotion);
        inventory.addItem(apple2);
        inventory.addItem(newPotion);
        inventory.addItem(pootion);
        inventory.addItem(antiMagicPotion);
        inventory.addItem(antivenomPotion);

        inventory.addItem(lHelm);
        inventory.addItem(lChest);
        inventory.addItem(lGreaves);
        inventory.addItem(lboots);
        inventory.addItem(lBelt);
        inventory.addItem(lShield);
        inventory.addItem(wSword);
        inventory.addItem(bNeck);
        inventory.addItem(bRing);
        inventory.addItem(bERing);

        inventory.addItem(someBronzeArrows);

        Game.world.addLoot(new Loot(key, 80, 100, textLog));
    }
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
