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

import com.barelyconscious.game.graphics.Font;
import com.barelyconscious.game.player.Player;
import com.barelyconscious.game.graphics.gui.Cursors;
import com.barelyconscious.game.graphics.gui.DialogPane;
import com.barelyconscious.game.graphics.gui.windows.InterfaceDelegate;
import com.barelyconscious.game.input.KeyHandler;
import com.barelyconscious.game.input.MouseHandler;
import com.barelyconscious.game.item.Armor;
import com.barelyconscious.game.item.Item;
import com.barelyconscious.game.player.AttributeMod;
import com.barelyconscious.game.spawnable.Entity;
import com.barelyconscious.game.spawnable.entities.SewerRatEntity;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game implements Runnable {

    public static final JFrame applicationWindow = new JFrame();
    public static final int WORLD_HEIGHT_OFFS_Y = 112;
    private boolean running = false;
    private static final String GAME_TITLE = "StoneQuest";
    private static final String GAME_VERSION = "0.7.0";
    public static Screen screen;
    public static World world;
    public static KeyHandler keyHandler = new KeyHandler();
    public static MouseHandler mouseHandler = new MouseHandler();
    private int width;
    private int height;
    // dbg
    public static long frametime;
    public static int frames2;

    /**
     * Initializes the game window and all of its components.
     */
    public void initGame() {
        // Generate 1000 random words 
        new Thread() {
            @Override
            public void run() {
                Common.generateGibberish(1000);
            } // run
        }.start();

        setSize(1280, 758);

        screen = new Screen(width, height);
        Font.init(screen);

        // Set up the application's input handler objects
        setInputHandlers();

        // Set up other window properties 
        setApplicationWindowProperties();

        // Set the application's large and small icons
        setApplicationIcons();

        // Change the application's cursor to a custom-created one
        Cursors.setCursor(Cursors.DEFAULT_CURSOR);

        // Set up the interface to be rendered
        world = new World(screen.getVisibleWidth(), screen.getVisibleHeight() - WORLD_HEIGHT_OFFS_Y);
        world.spawnPlayer(new Player("cassiius"), 35, 14);
        InterfaceDelegate.getInstance().init(screen.getWidth(), screen.getHeight());
        screen.addBackgroundComponent(world);

        // Add a resize listener
        applicationWindow.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent evt) {
                onResize(applicationWindow.getWidth(), applicationWindow.getHeight());
            } // componentsResized

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        onResize(width, height);

        // dbg
//        DialogPane dpane = new DialogPane(500, 195, "Tip - Movement in StoneQuest", "Movement is tile-based and can be accomplished one of three ways:\n\n-The WASD keys\n-The arrow keys or\n-Clicking on a tile\n\nYou can turn off tips like these in the Options menu");
//        screen.addAlwaysOnTopComponent(dpane);
        
//        int x, y;
//        for (int i = 0; i < 75; i++) {
//            x = (int)(Math.random() * 150);
//            y = (int)(Math.random() * 150);
//            world.spawnSprite(new SewerRatEntity((int)(Math.random() * 15)+1, x, y));
//        }
        
        
        Armor clothRobe = new Armor("Cloth robe", 1, 69, 12, Entity.CHEST_SLOT_ID, Item.ITEM_ROOT_FILE_PATH + "armor/chest/clothRobe.png", world.getPlayer(), new AttributeMod(Entity.HEALTH_ATTRIBUTE, 43.5), new AttributeMod(Entity.STRENGTH_ATTRIBUTE, 22));
        Armor clothHat = new Armor("Cloth hat", 1, 69, 12, Entity.HELMET_SLOT_ID, Item.ITEM_ROOT_FILE_PATH + "armor/helmet/clothHat.png", world.getPlayer(), new AttributeMod(Entity.HEALTH_ATTRIBUTE, 43.5), new AttributeMod(Entity.STRENGTH_ATTRIBUTE, 22));
//        Item leatherBikini = new Armor("Leather Bikini", 1, 69, 12, Entity.CHEST_SLOT_ID, Item.ITEM_ROOT_FILE_PATH + "armor/chest/leatherBikini.png", world.getPlayer());
        Item ironShield = new Armor("Iron shield", 1, 100, 55, Entity.OFF_HAND_SLOT_ID, Item.ITEM_ROOT_FILE_PATH + "armor/shield/ironShield.png", world.getPlayer());
        Item essenceOfMind = new Item("Essence of Mind", 0, 155, 3, Item.ITEM_ROOT_FILE_PATH + "salvage/essenceOfMind.png", world.getPlayer()) {

            @Override
            public String getType() {
                return "salvage";
            }
            
        };
        world.getPlayer().getInventory().addItem(clothRobe);
        world.getPlayer().getInventory().addItem(clothHat);
//        world.getPlayer().getInventory().addItem(leatherBikini);
        world.getPlayer().getInventory().addItem(ironShield);
        world.getPlayer().getInventory().addItem(essenceOfMind);
    } // init

    /**
     * Sets the size of the application to width, height.
     *
     * @param width
     * @param height
     */
    private void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        applicationWindow.setMinimumSize(new Dimension(width, height));
        applicationWindow.pack();
    } // setSize

    /**
     * Load and set the application icons for both the small and large sizes,
     * the large icon is what is displayed in the OS-dependent toolbar-like area
     * and the small icon is what is displayed in the OS-dependent application
     * window (if applicable, i.e., Windows-based OSs).
     */
    private void setApplicationIcons() {
        Image applicationIcon32; // 32x32
        Image applicationIcon16; // 16x16
        List<Image> icons;

        try {
            icons = new ArrayList<Image>();
            applicationIcon32 = ImageIO.read(Game.class.getResourceAsStream("/gfx/applicationIcon.png"));
            applicationIcon16 = ImageIO.read(Game.class.getResourceAsStream("/gfx/applicationIconSmall.png"));

            icons.add(applicationIcon16);
            icons.add(applicationIcon32);

            applicationWindow.setIconImages(icons);
        } catch (IOException ex) {
            System.err.println("Failed to load application icon: " + ex);
        }
    } // setApplicationIcons

    /**
     * Add input (mouse and keyboard) handler objects to the application so that
     * the user can interact properly with the game.
     */
    private void setInputHandlers() {
        applicationWindow.addKeyListener(keyHandler);
        screen.addMouseListener(mouseHandler);
        screen.addMouseMotionListener(mouseHandler);
        screen.addMouseWheelListener(mouseHandler);
    } // setInputHandlers

    /**
     * Set other application window properties such as adding the game's
     * rendering engine to the window and its title, and other JFrame
     * properties.
     */
    private void setApplicationWindowProperties() {
        applicationWindow.add(screen);
        applicationWindow.setTitle(GAME_TITLE);
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationWindow.setLocationRelativeTo(null);
        applicationWindow.setVisible(true);
        applicationWindow.setFocusTraversalKeysEnabled(false);
    } // setApplicationWindowProperties

    /**
     * Resizes the game's window as well as all game components
     *
     * @param width the number of tiles wide
     * @param height the number of tiles high
     */
    public void onResize(int width, int height) {
        screen.resizeScreen(width, height);

        width = screen.getWidth();
        height = screen.getHeight();

        InterfaceDelegate.getInstance().resize(width, height);
        world.resize(screen.getVisibleWidth(), InterfaceDelegate.getInstance().getVisibleHeight());
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
     * Animation and JFrame updates. Method copied and modified from Notch's
     * LD22 entry.
     */
    @Override
    public void run() {
        int frames = 0;
        long end, start;
        long lastTimer2 = System.currentTimeMillis();

        start = System.currentTimeMillis();
        initGame();
        end = System.currentTimeMillis();
        System.err.println(" [TEST] Startup took " + (end-start) + " ms");
        writeWelcomeMessage();

        while (running) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(" [ERR] Fatal error in run(): " + e);
                System.exit(1);
            } // try-catch

            frames++;

            start = System.nanoTime();
            screen.clear();
            screen.render();
            end = System.nanoTime();

            if (System.currentTimeMillis() - lastTimer2 > 1000) {
                lastTimer2 += 1000;
                frametime = end - start;
                frames2 = frames;
                frames = 0;
            } // if

            if (!applicationWindow.hasFocus()) {
                applicationWindow.requestFocusInWindow();
            } // if
        } // while
    } // run
    
    /**
     * 
     * @return the current player
     */
    public static Player getCurrentPlayer() {
        return world.getPlayer();
    } // getCurrentPlayer
    
    /**
     * 
     * @return the entire world
     */
    public static World getWorld() {
        return world;
    }// getWorld
    
    /**
     * Returns the width, in pixels, of the frame
     *
     * @return
     */
    public static int getGameWidth() {
        return screen.getWidth();
    } // getPixelWidth

    /**
     * Returns the height, in pixels, of the frame
     *
     * @return
     */
    public static int getGameHeight() {
        return screen.getHeight();
    } // getPixelHeight

    private void writeWelcomeMessage() {
        // Write a welcome message to the text log
//        textLog.writeFormattedString("Welcome to " + GAME_TITLE + " v"
//                + GAME_VERSION + "!", Common.FONT_NULL_RGB, new LineElement(
//                GAME_TITLE + " v" + GAME_VERSION, true,
//                Common.themeForegroundColor));
//
//        textLog.writeFormattedString("Press ? for help and instructions.",
//                Common.FONT_NULL_RGB);
    } // writeWelcomeMessage

    /**
     * Self-explanatory
     *
     * @param args unused
     */
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.Start();
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
