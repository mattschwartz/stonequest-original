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

import com.barelyconscious.game.file.FileHandler;
import com.barelyconscious.game.graphics.FontService;
import com.barelyconscious.game.services.SceneService;
import com.barelyconscious.util.ConsoleWriter;
import com.barelyconscious.util.StringHelper;

public class Game implements Runnable {

    private SceneService sceneService;
    private boolean running = false;
    // dbg
    public static long frametime;
    public static int frames2;
    
    /**
     * Initializes the game window and all of its components.
     */
    public void initGame() {
        sceneService = SceneService.INSTANCE;
        
        startServices();
        
        // Generate 1000 random words 
        new Thread() {
            @Override
            public void run() {
                StringHelper.generateGibberish(1000);
            }
        }.start();
    }
    
    private void startServices() {
        FileHandler.INSTANCE.start();
        sceneService.start();
        FontService.INSTANCE.start();
    }

    /**
     * Start the game's main running thread.
     */
    private void Start() {
        running = true;
        new Thread(this).start();
    }

    /**
     * Called when the player quits the game.
     */
    public static void Stop() {
        System.err.println("saving data");
        System.exit(0);
    }

    /**
     * Animation and JFrame updates. Method copied and modified from Notch's
     * LD22 entry.
     */
    @Override
    public void run() {
        long end, start;
        int frames = 0;
        long lastTimer2 = System.currentTimeMillis();

        initGame();

        while (running) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                ConsoleWriter.writeError("Fatal error in run(): " + e);
                System.exit(1);
            }

            frames++;

            start = System.nanoTime();
            sceneService.render();
            end = System.nanoTime();

            if (System.currentTimeMillis() - lastTimer2 > 1000) {
                lastTimer2 += 1000;
                frametime = end - start;
                frames2 = frames;
                frames = 0;
            }

            if (!sceneService.hasFocus()) {
                sceneService.requestFocusInWindow();
            }
        }
    }

    /**
     * Self-explanatory?
     *
     * @param args unused
     */
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.Start();
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
}
