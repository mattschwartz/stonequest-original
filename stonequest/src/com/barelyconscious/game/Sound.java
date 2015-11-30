/* *****************************************************************************
 * Project:          Roguelike2.0
 * File name:        Sound.java
 * Author:           Matt Schwartz
 * Date created:     07.28.2012
 * Redistribution:   You are free to use, reuse, and edit any of the text in
                     this file.  You are not allowed to take credit for code
                     that was not written fully by yourself, or to remove 
                     credit from code that was not written fully by yourself.  
                     Please email schwamat@gmail.com for issues or concerns.
 * File description: 
 **************************************************************************** */

package com.barelyconscious.game;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    public static final Sound ATTACK_MELEE  = new Sound("/sound/attack_melee.wav");
    public static final Sound DRINK_POTION  = new Sound("/sound/drink_potion.wav");
    public static final Sound READ_SCROLL   = new Sound("/sound/read_scroll.wav");
    public static final Sound EQUIP_ITEM    = new Sound("/sound/equip_leather_2.wav");
    public static final Sound LOOT_ITEM     = new Sound("/sound/equip_metal.wav");
    public static final Sound ENTITY_DEATH  = new Sound("/sound/entity_death.wav");
    public static final Sound LEVEL_UP      = new Sound("/sound/level_up.wav");
    
    public static final Sound TITLE_MUSIC   = new Sound("/sound/title_music_2_1.wav");
    
    private AudioClip soundClip;
    
    public Sound(String soundFile) {
        try {
            soundClip = Applet.newAudioClip(Sound.class.getResource(soundFile));
        } catch(Throwable e) {
            System.err.println(" [ERR] in Sound (" + soundFile + "): " + e);
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

    void stop() {
        soundClip.stop();
    }
    
    void loop() {
        soundClip.stop();
        soundClip.loop();
    }
} // Sound