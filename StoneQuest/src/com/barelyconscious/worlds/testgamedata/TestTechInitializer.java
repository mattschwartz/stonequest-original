package com.barelyconscious.worlds.testgamedata;

import com.barelyconscious.worlds.entity.BasicPowerSupply;
import com.barelyconscious.worlds.entity.PlayerPersonalDevice;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.tech.Motherboard;
import com.barelyconscious.worlds.game.tech.MotherboardSocket;
import com.barelyconscious.worlds.game.tech.MotherboardSocketType;
import com.google.common.collect.Lists;

public class TestTechInitializer {

    public static void init() {
        var powerSupplySocket = MotherboardSocket.builder()
            .socketType(MotherboardSocketType.POWER)
            .powerConsumption(10)
            .build();
        var processorSocket = MotherboardSocket.builder()
            .socketType(MotherboardSocketType.PROCESSOR)
            .powerConsumption(10)
            .build();
        var memorySocket1 = MotherboardSocket.builder()
            .socketType(MotherboardSocketType.MEMORY)
            .powerConsumption(10)
            .build();
        var memorySocket2 = MotherboardSocket.builder()
            .socketType(MotherboardSocketType.MEMORY)
            .powerConsumption(10)
            .build();

        var motherboard = new Motherboard(Lists.newArrayList(
            powerSupplySocket,
            processorSocket,
            memorySocket1,
            memorySocket2
        ));

        var basicPowerSupply = new BasicPowerSupply();

        powerSupplySocket.socketItem(basicPowerSupply);

        GameInstance.instance().setPlayerPersonalDevice(
            new PlayerPersonalDevice(motherboard)
        );
    }
}
