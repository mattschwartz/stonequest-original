package com.barelyconscious.worlds.terminal.commands;

import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.rng.TerritoryGeneration;
import com.barelyconscious.worlds.game.systems.BuildingSystem;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.barelyconscious.worlds.terminal.InputDialog;

import java.util.List;
import java.util.Scanner;

public class TerritoryCommand extends Command {

    @Override
    public CommandLineResults run(Scanner scn, CommandLineArgs args) {
        System.out.println("+------------------+");
        System.out.println("|  TERRITORY MENU  |");
        System.out.println("+------------------+");

        while (true) {
            boolean userAccepted = InputDialog.question("What would you do?")
                .answer("Overview", () -> overview(scn, args))
                .answer("Details", () -> details(scn, args))
                .answer("Spawn", () -> spawn(scn, args))
                .ask(scn, true);

            if (!userAccepted) {
                return CommandLineResults.CONTINUE;
            }
        }
    }

    /**
     * Spawn a territory as if we were loading it into the game world.
     * @param scn
     * @param args
     * @return
     */
    private Void spawn(Scanner scn, CommandLineArgs args) {
        Territory selectATerritoryToSpawn = InputDialog.pollObjects("Select a territory to spawn", GameInstance.instance()
                .getSystem(ChancellorSystem.class)
                .getTerritoriesOwnedByVillage(GameInstance.instance().getPlayerVillage()))
            .withFormatter((territory) -> territory.name)
            .prompt(scn, true);

        var tg = new TerritoryGeneration();
        var territoryContainer = tg.generateTerritory(selectATerritoryToSpawn);

        var map = new String[TerritoryGeneration.NUM_TILES_ROWS][TerritoryGeneration.NUM_TILES_COLS];
        // fill map with .'s
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[0].length; ++y) {
                map[x][y] = "◼️";
            }
        }
        for (var child : territoryContainer.getChildren()) {
            int x = (int) (child.getTransform().x / 32);
            int y = (int) (child.getTransform().y / 32);
            if (child instanceof EntityActor) {
                map[x][y] = "👹";
            } else if (child instanceof Building) {
                map[x][y] = "🏠";
            } else if (child instanceof ResourceDeposit) {
                map[x][y] = "🌱";
            }
        }

        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[0].length; ++y) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }

        return null;
    }

    private Void details(Scanner scn, CommandLineArgs args) {
        GameInstance gi = GameInstance.instance();
        ChancellorSystem cs = gi.getSystem(ChancellorSystem.class);
        List<Territory> territories = cs.getTerritoriesOwnedByVillage(gi.getPlayerVillage());

        for (var territory : territories) {
            System.out.printf("%s%n", territory.name);
            List<Building> buildingsWithinTerritory = cs.getBuildingsWithinTerritory(territory);
            if (buildingsWithinTerritory != null) {
                for (var building : buildingsWithinTerritory) {
                    System.out.printf("  • 🏠 %s%n", building.name);
                }
            }

            for (var resource : territory.getAvailableResources()) {
                System.out.printf("  • 🌱 %s (%d%% richness)%n",
                    resource.item.getName(),
                    (int) (resource.richness * 100));
            }
        }

        return null;
    }

    private Void overview(Scanner scn, CommandLineArgs args) {
        GameInstance gi = GameInstance.instance();
        System.out.printf("You own %s territories%n", gi
            .getSystem(ChancellorSystem.class)
            .getTerritoriesOwnedByVillage(gi.getPlayerVillage())
            .size());
        return null;
    }
}
