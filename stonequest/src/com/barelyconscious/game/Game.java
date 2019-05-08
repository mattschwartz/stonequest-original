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
import com.barelyconscious.game.graphics.Map;
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
import java.awt.Dimension;
import javax.swing.JFrame;

public class Game implements Runnable {

    private final JFrame window = new JFrame();
    private boolean running = false;
    private final static String GAME_TITLE = "StoneQuest";
    private final static String GAME_VERSION = "0.6.9";
    // Cross-project classes
    public static Screen screen;
    public static TextLog textLog;
    public static Player player;
    public static BuffBar buffBar;
    public static AttributesMenu attributesMenu;
    public static ToolTipMenu toolTipMenu;
    public static Inventory inventory;
    public static InventoryMenu invenMenu;
    public static LootPickupMenu lootWindow;
    public static World world;
    public static Map map;
    public static MiniMap miniMap;
    public static KeyHandler keyHandler;
    public static MouseHandler mouseHandler;
    private static int width;
    private static int height;
    public static long frametime;
    public static int frames2;

    // For applet version of the game
//    @Override
//    public void init() {
//        Game game = new Game();
//        game.Start();
//        game.initGame();
//        game.run();
//    }
    /**
     * Initializes the game window.
     */
    public void initGame() {
        Common.generateGibberish(1000);

        width = 1280;
        height = 700;
//        width = 1600;
//        height = 900;

        screen = new Screen(width, height);
        window.add(screen);
        textLog = new TextLog(45, 50, 100);
        player = new Player();
        inventory = new Inventory();

        map = new Map(1024, 1024, width, height);
        world = new World();
        miniMap = new MiniMap();

        buffBar = new BuffBar();
        toolTipMenu = new ToolTipMenu(player);
        attributesMenu = new AttributesMenu();
        lootWindow = new LootPickupMenu();
        invenMenu = new InventoryMenu();
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        window.setTitle(GAME_TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
//        window.setUndecorated(true); // Makes the frame go bye-bye

        onResize(width, height);

        window.setVisible(true);
        window.addKeyListener(keyHandler);
        screen.addMouseListener(mouseHandler);
        screen.addMouseMotionListener(mouseHandler);
        screen.addMouseWheelListener(mouseHandler);

        window.setFocusTraversalKeysEnabled(false);

        registerClickableAreas();
        registerHoverableAreas();
        registerKeyListeners();

        Common.setThemeForeground(Common.THEME_PASTEL_GREEN);

        // debug stuff
        disposableFunctionToAddStuffToThePlayersInventory();
        world.addLoot(new Loot(new Item("Superior Belt of Pants Holding", 39202, Tile.BELT_IRON_TILE_ID), 0, 0));
    } // init

    /**
     * Resizes the game's window as well as all game components
     *
     * @param width the number of tiles wide
     * @param height the number of tiles high
     */
    public void onResize(int width, int height) {
        window.setMinimumSize(new Dimension(width, height));
        window.pack();

        screen.resizeScreen(width, height);

        width = screen.getWidth();
        height = screen.getHeight();

        lootWindow.resize(width, height);
        toolTipMenu.resize(width, height);
        textLog.resize(width, height);
        miniMap.resizeMenu(width, height);
        buffBar.resize(width, height);
        attributesMenu.resize(width, height);
        world.resize(width, height);
        invenMenu.resize(width, height);
        window.setLocationRelativeTo(null);
    } // resize

    private void registerClickableAreas() {
        mouseHandler.registerClickableListener(world);
        mouseHandler.registerClickableListener(attributesMenu);
        mouseHandler.registerClickableListener(invenMenu);
//        mouseHandler.registerClickableListener(lootWindow);
    } // registerClickableAreas

    private void registerHoverableAreas() {
        mouseHandler.registerHoverableListener(world);
        mouseHandler.registerHoverableListener(attributesMenu);
        mouseHandler.registerHoverableListener(invenMenu);
//        mouseHandler.registerHoverableListener(lootWindow);
    } // registerHoverableAreas

    private void registerKeyListeners() {
        keyHandler.registerKeyInputListener(attributesMenu);
        keyHandler.registerKeyInputListener(invenMenu);
        keyHandler.registerKeyInputListener(textLog);
//        mouseHandler.registerHoverableListener(lootWindow);
    } // registerKeyListeners

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

        initGame();
        writeWelcomeMessage();
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

            if (!window.hasFocus()) {
                window.requestFocusInWindow();
            } // if
            else {
                // frame has focus
            } // else
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

    private void writeWelcomeMessage() {
        // Write a welcome message to the text log
        textLog.writeFormattedString("Welcome to " + GAME_TITLE + " v"
                + GAME_VERSION + "!", Common.FONT_NULL_RGB, new LineElement(
                GAME_TITLE + " v" + GAME_VERSION, true,
                Common.themeForegroundColor));

        textLog.writeFormattedString("Press ? for help and instructions.",
                Common.FONT_NULL_RGB);
    } // writeWelcomeMessage

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
        Armor lHelm = new Armor("Leather Helmet", 2567, 15, Player.HELM_SLOT_ID, Tile.HELMET_LEATHER_TILE_ID, new AttributeMod(Player.ACCURACY, -11), new AttributeMod(Player.FIRE_MAGIC_BONUS, 54), new AttributeMod(Player.HOLY_MAGIC_BONUS, 22), new AttributeMod(Player.DEFENSE, 25), new AttributeMod(Player.HITPOINTS, 12), new AttributeMod(Player.AGILITY, 5), new AttributeMod(Player.DEFENSE, 200));
        Armor lChest = new Armor("Leather Chest", 1500, 27, Player.CHEST_SLOT_ID, Tile.CHEST_LEATHER_TILE_ID);
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
        Potion healthPotion = new Potion("Potion of Might", 2990, 1, new StatPotionEffect("Potion of Might", 129, new AttributeMod(Player.HITPOINTS, 21), new AttributeMod(Player.STRENGTH, 15)));
        Potion newPotion = new Potion("Potion of Awesome", 159, 3, new StatPotionEffect("Potion of Awesome", 300, new AttributeMod(Player.DEFENSE, 12), new AttributeMod(Player.STRENGTH, 3)));
        Potion pootion = new Potion("Potion of Pi", 1, 5, new StatPotionEffect("Potion of Pi", 95, new AttributeMod(Player.DEFENSE, 12), new AttributeMod(Player.STRENGTH, 3), new AttributeMod(Player.AGILITY, 15), new AttributeMod(Player.DEFENSE, 55), new AttributeMod(Player.FIRE_MAGIC_BONUS, 180)));
        Potion antiMagicPotion = new Potion("Antimagic Potion", 1589, 2, new AntimagicPotionEffect("Antimagic Potion"));
        Potion antivenomPotion = new Potion("Antivenom", 18890, 3, new AntitoxinPotionEffect("Antivenom"));

        Curse curse = new Curse("Curse of Evilness", 299, new AttributeMod(Player.ACCURACY, 15), new AttributeMod(Player.DEFENSE, 5));
        Curse curse2 = new Curse("Curse of Evilness", 1000, new AttributeMod(Player.AGILITY, 15), new AttributeMod(Player.DEFENSE, 5));
        Curse curse3 = new Curse("Curse of Evilness", 1335, new AttributeMod(Player.AGILITY, 155), new AttributeMod(Player.DEFENSE, 25));
        Curse curse4 = new Curse("Curse of Evilness", 1240, new AttributeMod(Player.AGILITY, 16), new AttributeMod(Player.DEFENSE, 15));

        Poison poi = new Poison("Snake Venom", 69, 1.5f, 7);
        Poison poi2 = new Poison("Spider Bite", 24, 0.5f, 6);

        Projectile someBronzeArrows = new Projectile("Bronze-tipped Arrows", 255, 15, Tile.ARROW_TILE_ID, true, Projectile.BRONZE_TIP);

        Scroll scr1 = new Scroll("Invisibility", 258, 1, new AttributeMod(Player.ACCURACY, 255f));
        
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
        
        Game.world.addLoot(new Loot(key, 80, 100));
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
