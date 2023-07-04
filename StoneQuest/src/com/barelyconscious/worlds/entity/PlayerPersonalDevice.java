package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.tech.Motherboard;
import com.barelyconscious.worlds.game.tech.MotherboardSocketType;
import lombok.Getter;

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

    @Getter
    private Motherboard motherboard;

    public PlayerPersonalDevice(final Motherboard motherboard) {
        super("Player Personal Device", Vector.ZERO);
        this.motherboard = motherboard;
        this.motherboard.setParent(this);
    }

    public void setMotherboard(final Motherboard newMotherboard) {

        // transfer components from old motherboard to new motherboard

        for (MotherboardSocketType socketType :
            MotherboardSocketType.values()) {

            var existingSockets = this.motherboard.getSocketsOfType(socketType);
            var newSockets = newMotherboard.getSocketsOfType(socketType);

            if (existingSockets.size() < newSockets.size()) {
                // fail
            } else {
                // transfer sockets from old motherboard to new motherboard

            }
        }

        this.motherboard = newMotherboard;
    }
}
