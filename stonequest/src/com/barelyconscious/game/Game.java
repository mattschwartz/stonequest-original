/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Game.java
 * Author:           Matt Schwartz
 * Date created:     07.01.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Note that using the max window size and setting frame 
 *                   decoration to false creates a "windowless fullscreen"
 *                   effect.
 **************************************************************************** */

package com.barelyconscious.game;

import com.barelyconscious.game.input.InputHandler;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.player.Inventory;
import com.barelyconscious.game.menu.TextLog;
import com.barelyconscious.game.menu.InventoryMenu;
import com.barelyconscious.game.graphics.LineElement;
import com.barelyconscious.game.graphics.Map;
import com.barelyconscious.game.graphics.tiles.Tile;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Food;
import com.barelyconscious.game.item.Potion;
import com.barelyconscious.game.item.Projectile;
import com.barelyconscious.game.item.Scroll;
import com.barelyconscious.game.item.Weapon;
import com.barelyconscious.game.menu.AttributesMenu;
import com.barelyconscious.game.menu.BuffBar;
import com.barelyconscious.game.menu.LootPickupMenu;
import com.barelyconscious.game.menu.ToolTipMenu;
import com.barelyconscious.game.player.StatBonus;
import com.barelyconscious.game.player.activeeffects.Curse;
import com.barelyconscious.game.player.activeeffects.Poison;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Game implements Runnable {
    private static final JFrame window = new JFrame();
    private boolean running = false;
    
    private final static String GAME_TITLE = "StoneQuest";
    private final static String GAME_VERSION = "0.6.1";
    
    // Game components
    public static Screen screen;
    public static TextLog textLog;
    public static Player player;
    public static BuffBar buffBar;
    public static AttributesMenu attributesMenu;
    public static ToolTipMenu toolTipMenu;
    public static Inventory inventory;
    public static InventoryMenu invenMenu;
    public static LootPickupMenu lootWindow;
    public static WorldFrame world;
    public static Map map;
    public static InputHandler inputHandler;
    
    private static int width;
    private static int height;
    
    /**
     * Initializes the game window.
     */
    public void init() {
        width = 1280;
        height = 700;
        
        screen = new Screen(60, 40);
        textLog = new TextLog(45, 50, 100);
        player = new Player(textLog);
        buffBar = new BuffBar(player);
        attributesMenu = new AttributesMenu(player);
        toolTipMenu = new ToolTipMenu();
        inventory = new Inventory();
        lootWindow = new LootPickupMenu();
        map = new Map(width / Common.TILE_SIZE, height / Common.TILE_SIZE);
        world = new WorldFrame(textLog, map);
        invenMenu = new InventoryMenu(world, player, inventory, textLog);
        inputHandler = new InputHandler();
        
        window.setTitle(GAME_TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
//        window.setUndecorated(true); // Makes the frame go bye-bye
        
        window.add(screen);
        
        resize(width, height);
        
        window.setVisible(true);
        window.addKeyListener(inputHandler);
        window.requestFocus();
        
        textLog.writeFormattedString("Welcome to " + GAME_TITLE + " v" + GAME_VERSION + "!", null, new LineElement(GAME_TITLE + " v" + GAME_VERSION, true, Common.THEME_FG_COLOR_RGB));
        textLog.writeFormattedString("Press ? for help and instructions.", null);
        
        disposableFunctionToAddStuffToThePlayersInventory();
    } // init
    
    /**
     * Start the game.
     */
    private void start() {
        running = true;
        new Thread(this).start();
    } // start
    
    /**
     * Gracefully quit the program by dumping any save data.
     */
    public static void stop() {
        System.err.println("saving data");
        System.exit(0);
    } // stop
    
    /**
     * Animation and frame updates.  Method copied from Notch's LD22 entry.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        boolean shouldRender;
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        int frames = 0;
        int ticks = 0;
        long lastTimer2 = System.currentTimeMillis();
        
        init();
        
        while (running) {
            now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            shouldRender = true;
//            while (unprocessed >= 1) {
//                ticks++;
//                world.tick();
//                unprocessed -= 1;
//                shouldRender = true;
//            } // while
            
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
            } // try-catch
            
            window.requestFocus();
            
            if (shouldRender) {
                frames++;
                screen.clear();
                screen.render();
            } // if
            
            if (System.currentTimeMillis() - lastTimer2 > 1000) {
                lastTimer2 += 1000;
                System.out.println(ticks + " ticks, " + frames + " fps");
                frames = 0;
                ticks = 0;
            } // if
        } // while
    } // run
    
    /**
     * Resizes the game's window.
     * @param width the number of tiles wide
     * @param height the number of tiles high
     */
    public static void resize(int width, int height) {
        window.setMinimumSize(new Dimension(width * Common.SCALE, height * Common.SCALE));
        window.pack();
        
        screen.resizeScreen(width, height);
        attributesMenu.resizeMenu(width, height);
        invenMenu.resizeMenu(width, height);
        lootWindow.resizeMenu(width, height);
        toolTipMenu.resizeMenu(width, height);
        textLog.resizeMenu(width, height);
        buffBar.resizeMenu(width, height);
        world.onResize();
        
        window.setLocationRelativeTo(null);
    } // resize
    
    public static int getWidth() {
        return width;
    } // getWidth
    
    public static int getHeight() {
        return height;
    } // getHeight
    
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
    } // main
    
    public static void disposableFunctionToAddStuffToThePlayersInventory() {
         Armor lHelm = new Armor("Leather Helmet", 2567, 15, Player.HELM_SLOT, Tile.ARMOR_HELMET_LEATHER_TILE_ID, new StatBonus(Player.ACCURACY, 11), new StatBonus(Player.FIRE_MAGIC, 54), new StatBonus(Player.HOLY_MAGIC, 22), new StatBonus(Player.DEFENSE, 25), new StatBonus(Player.HITPOINTS, 12), new StatBonus(Player.AGILITY, 5));
        Armor lChest = new Armor ("Leather Chest", 1500, 27, Player.CHEST_SLOT, Tile.ARMOR_CHEST_LEATHER_TILE_ID);
        Armor lGreaves = new Armor ("Leather Greaves", 220, 15, Player.GREAVES_SLOT, Tile.ARMOR_GREAVES_LEATHER_TILE_ID);
        Armor lboots = new Armor("Leather Boots", 1789, 10, Player.BOOTS_SLOT, Tile.ARMOR_BOOTS_LEATHER_TILE_ID);
        Armor lBelt = new Armor("Leather Belt", 1677, 11, Player.BELT_SLOT, Tile.ARMOR_BELT_LEATHER_TILE_ID);
        Armor lShield = new Armor ("Wooden Shield", 1500, 55, Player.OFF_HAND_SLOT, Tile.ARMOR_SHIELD_WOOD_TILE_ID);
        Weapon wSword = new Weapon("Wooden Sword", 1358, 1, 4, Tile.SWORD_TILE_ID);
        Armor bNeck = new Armor("Epic Awesome SLJ Necklace", 256888, 0, Player.NECK_SLOT, Tile.ARMOR_NECKLACE_TILE_ID, new StatBonus(Player.HITPOINTS, 200));
        Armor bRing = new Armor("Lousy Piece of Boring Ring", 0, 0, Player.RING_SLOT, Tile.ARMOR_RING_TILE_ID);
        Armor bERing = new Armor("Broken Earring", 0, 0, Player.EARRING_SLOT, Tile.ARMOR_EARRING_TILE_ID);
        
        lChest.setRarityColor(Common.LEGENDARY_ITEM_COLOR_RGB);
        
        Food apple = new Food("Apple", 0, 1, Tile.FOOD_TILE_ID, 99f);
        Food apple2 = new Food("Apple", 0, 2, Tile.FOOD_TILE_ID, 99f);
        Potion healthPotion = new Potion("Potion of Might", 2990, 1, 125, Potion.STATBUFF, Tile.POTION_TILE_ID, new StatBonus(Player.HITPOINTS, 21), new StatBonus(Player.STRENGTH, 15));
        Potion newPotion = new Potion("Potion of Awesome", 159, 3, 29, Potion.STATBUFF, Tile.POTION_TILE_ID, new StatBonus(Player.DEFENSE, 12), new StatBonus(Player.STRENGTH, 3));
        Potion pootion = new Potion("Potion of Pi", 1, 5, 95, Potion.STATBUFF, Tile.POTION_TILE_ID, new StatBonus(Player.STRENGTH, 1), new StatBonus(Player.ACCURACY, 11));
        Potion antiMagicPotion = new Potion("Antimagic Potion", 1589, 2, 0, Potion.ANTIMAGIC, Tile.POTION_TILE_ID);
        Potion antivenomPotion = new Potion("Antivenom", 18890, 3, 1, Potion.ANTIVENOM, Tile.POTION_TILE_ID);
        
        Curse curse = new Curse("Curse of Evilness", 299, new StatBonus(Player.ACCURACY, 15), new StatBonus(Player.DEFENSE, 5));
        Curse curse2 = new Curse("Curse of Evilness", 1000, new StatBonus(Player.AGILITY, 15), new StatBonus(Player.DEFENSE, 5));
        Curse curse3 = new Curse("Curse of Evilness", 1335, new StatBonus(Player.AGILITY, 15), new StatBonus(Player.DEFENSE, 5));
        Curse curse4 = new Curse("Curse of Evilness", 1240, new StatBonus(Player.AGILITY, 15), new StatBonus(Player.DEFENSE, 5));
        
        Poison poi = new Poison("Snake Venom", 69, 98756f, 7);
        Poison poi2 = new Poison("Spider Bite", 24, 0.5f, 6);
        
        Projectile someBronzeArrows = new Projectile("Bronze-tipped Arrows", 255, 15, Tile.ARROW_TILE_ID, true, Projectile.BRONZE_TIP);
        
        Scroll scr1 = new Scroll("Invisibility", 258, 1, Tile.SCROLL_TILE_ID, new StatBonus(Player.ACCURACY, 255f));
        
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