package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;

/**
 * The player's Personal Device which serves as a means of interfacing with the tech
 * side of the game. The PPD is used to store and interact with media, pull data from
 * recovered tech in the world, and to communicate with the Vitalink back in the player's
 * faction.
 *
 * The player only has the one PPD and its various components upgraded over the course
 * of the game.
 */
public class PlayerPersonalDevice extends Actor {

    public PlayerPersonalDevice() {
        super("Player Personal Device", Vector.ZERO);
    }

}
