/* *****************************************************************************
 * Project:          Roguelike2.0
 * File displayName:        Sound.java
 * Author:           Matt Schwartz
 * Date created:     07.28.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email stonequest.bcgames@gmail.com for issues or concerns.
 * File description: Holds the sound files which are played when certain events
                     occur in the game.  Sound files are by default located under
                     /res/sound and expects sound files to be in the .wav format.
                     Sounds are played using Java's built in AudioClip class.
 **************************************************************************** */

package com.barelyconscious.game;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    public static final Sound ATTACK_MELEE  = new Sound("/sound/attack_melee.wav");
    public static final Sound DRINK_POTION  = new Sound("/sound/drink_potion.wav");
    public static final Sound READ_SCROLL   = new Sound("/sound/read_scroll.wav");
    public static final Sound EQUIP_ITEM    = new Sound("/sound/equip_leather_2.wav");
    public static final Sound LOOT_COINS     = new Sound("/sound/world/loot_coins.wav");
    public static final Sound ENTITY_DEATH  = new Sound("/sound/entity_death.wav");
    public static final Sound LEVEL_UP      = new Sound("/sound/level_up.wav");
    public static final Sound LEFT_BUTTON_CLICK = new Sound("/sound/GUI/button_click_left.wav");
    public static final Sound RIGHT_BUTTON_CLICK = new Sound("/sound/GUI/button_click_right.wav");
    public static final Sound TITLE_MUSIC   = new Sound("/sound/music/title_music_2_1.wav");
    
    public static final Sound SIGH = new Sound("/sound/world/sigh.wav");
    
    public static final Sound AMBIENT_LOW_RUMBLE = new Sound("/sound/ambient/low_rumble.wav");
    public static final Sound AMBIENT_OUTSIDE = new Sound("/sound/ambient/outside_forest.wav");
    
    public static final Sound RAIN = new Sound("/sound/ambient/rain.wav");
    
    public static final Sound CHICKEN_CLUCK = new Sound("/sound/world/entities/cluck.wav");
    
    private AudioClip soundClip;
    
    public Sound(String soundFile) {
        try {
            soundClip = Applet.newAudioClip(Sound.class.getResource(soundFile));
        } catch(Throwable e) {
            System.err.println(" [ERR] Failed to load sound file (" + soundFile + "): " + e);
        } // catch
    } // constructor
    
    public void play() {
        new Thread() {
            @Override
            public void run() {
                soundClip.stop();
                soundClip.play();
            } // run
        }.start();
    } // play
    
    public void loop() {
        new Thread() {
            @Override
            public void run() {
                soundClip.loop();
            } // run
        }.start();
    } // loop
} // Sound
