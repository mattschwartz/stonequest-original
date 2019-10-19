package com.barelyconscious.services.audio

import java.applet.Applet
import java.applet.AudioClip
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class PlayableSound private constructor(soundFilePath: String) {

    private val audioClip: AudioClip =
        Applet.newAudioClip(PlayableSound::class.java.getResource(soundFilePath))

    fun play() {
        audioClip.play()
    }

    fun stop() {
        audioClip.stop()
    }

    companion object {
        private const val NUM_SOUND_THREADS = 16
        val executorService: ExecutorService = Executors.newFixedThreadPool(NUM_SOUND_THREADS)

        val ATTACK_MELEE = PlayableSound("/sound/attack_melee.wav")
        val DRINK_POTION = PlayableSound("/sound/drink_potion.wav")
        val READ_SCROLL = PlayableSound("/sound/read_scroll.wav")
        val EQUIP_ITEM = PlayableSound("/sound/equip_leather_2.wav")
        val LOOT_COINS = PlayableSound("/sound/world/loot_coins.wav")
        val ENTITY_DEATH = PlayableSound("/sound/entity_death.wav")
        val LEVEL_UP = PlayableSound("/sound/level_up.wav")
        val LEFT_BUTTON_CLICK = PlayableSound("/sound/GUI/button_click_left.wav")
        val RIGHT_BUTTON_CLICK = PlayableSound("/sound/GUI/button_click_right.wav")
        val TITLE_MUSIC = PlayableSound("/sound/music/title_music_2_1.wav")

        val SIGH = PlayableSound("/sound/world/sigh.wav")

        val AMBIENT_LOW_RUMBLE = PlayableSound("/sound/ambient/low_rumble.wav")
        val AMBIENT_OUTSIDE = PlayableSound("/sound/ambient/outside_forest.wav")

        val RAIN = PlayableSound("/sound/ambient/rain.wav")

        val CHICKEN_CLUCK = PlayableSound("/sound/world/entities/cluck.wav")
    }
}
