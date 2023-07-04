package com.barelyconscious.worlds.game.tech;

/**
 * Socket types that can be used to connect peripherals to the motherboard.
 */
public enum MotherboardSocketType {
    /**
     * The processor socket which is used to determine the kind of OS capable of
     * being run on this VitaComm device.
     */
    PROCESSOR,
    /**
     * Determines how many concurrent processes can be run on the device, such as
     * the OS and apps. Better OS's require more memory consumption.
     */
    MEMORY,
    /**
     * Determines how much media and how many apps can be stored on the device.
     */
    STORAGE,
    /**
     * Connectors like USB for how to extract data from other devices, like USB sticks and
     * HDD.
     */
    DATA_BUS,
    /**
     * Determines the communication capabilities of the device. Bluetooth would be required
     * for nearby devices, such as a keyboard or mouse, while wifi would be required for
     * connecting to the internet and talking to the Vitalink back in the player's faction.
     */
    NETWORK,
    /**
     * A socket that can be used to connect a power supply to the motherboard.
     */
    POWER
}
