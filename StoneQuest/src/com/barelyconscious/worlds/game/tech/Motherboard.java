package com.barelyconscious.worlds.game.tech;

import com.barelyconscious.worlds.entity.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * The Vitaboard's function is to link up various tech components together. It essentially
 * locks how many components can be attached to a tech device.
 *
 * It's like sockets and links on items in Path of Exile.
 *
 * The player wants to be able to upgrade their Vitaboard to allow for more components to
 * be able to run more apps and better OSes.
 *
 * It acts as an inventory for media, allowing the player to hold media more efficiently
 * when going between jobs and factions as hard drives are bulky, so it is more costly to
 * carry them around.
 */
public class Motherboard extends Actor {
    private final List<MotherboardSocket> sockets;

    public Motherboard() {
        this(new ArrayList<>());
    }

    public Motherboard(List<MotherboardSocket> sockets) {
        this.sockets = sockets;
    }

    /**
     * @return the amount of power required to run the motherboard and all its peripherals.
     */
    float getPowerConsumption() {
        float powerConsumption = 0.0f;

        for (MotherboardSocket socket : sockets) {
            powerConsumption += socket.getPowerConsumption();
        }

        return powerConsumption;
    }

    public List<MotherboardSocket> getSocketsOfType(
        MotherboardSocketType socketType
    ) {
        var results = new ArrayList<MotherboardSocket>();
        for (MotherboardSocket socket : sockets) {
            if (socket.getSocketType() == socketType) {
                results.add(socket);
            }
        }

        return results;
    }
}
