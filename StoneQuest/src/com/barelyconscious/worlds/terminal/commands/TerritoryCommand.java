package com.barelyconscious.worlds.terminal.commands;

import com.barelyconscious.worlds.entity.wilderness.WildernessLevel;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.terminal.InputDialog;

import java.util.Scanner;

public class TerritoryCommand extends Command {

    @Override
    public CommandLineResults run(Scanner scn, CommandLineArgs args) {
        System.out.println("+------------------+");
        System.out.println("|  TERRITORY MENU  |");
        System.out.println("+------------------+");

        if (!args.parameters.isEmpty()) {
            if ("load".equalsIgnoreCase(args.parameters.get(0))) {
                load(scn, args);
                return CommandLineResults.TICK;
            } else if ("look".equalsIgnoreCase(args.parameters.get(0))) {
                look(scn, args);
                return CommandLineResults.TICK;
            } else if ("details".equalsIgnoreCase(args.parameters.get(0))) {
                details(scn, args);
                return CommandLineResults.TICK;
            } else if ("overview".equalsIgnoreCase(args.parameters.get(0))) {
                overview(scn, args);
                return CommandLineResults.TICK;
            }
        }

        while (true) {
            boolean userAccepted = InputDialog.question("What would you do?")
                .answer("Overview", () -> overview(scn, args))
                .answer("Details", () -> details(scn, args))
                .answer("Load", () -> load(scn, args))
                .answer("Look", () -> look(scn, args))
                .ask(scn, true);

            if (!userAccepted) {
                return CommandLineResults.CONTINUE;
            }
        }
    }

    /**
     * Load a territory as if we were loading it into the game world.
     */
    private Void load(Scanner scn, CommandLineArgs args) {

//        final Territory selectATerritoryToSpawn;
//        // check if args contains a territory index
//        if (args.parameters.size() > 1) {
//            int territoryIndex = Integer.parseInt(args.parameters.get(1));
//            selectATerritoryToSpawn = GameInstance.instance().getSystem(ChancellorSystem.class)
//                .getTerritoriesOwnedByVillage(GameInstance.instance().getSystem(SettlementSystem.class).getPlayerSettlement())
//                .get(territoryIndex);
//        } else {
//            selectATerritoryToSpawn = InputDialog.pollObjects("Select a territory to spawn", GameInstance.instance()
//                    .getSystem(ChancellorSystem.class)
//                    .getTerritoriesOwnedByVillage(GameInstance.instance().getSystem(SettlementSystem.class).getPlayerSettlement()))
//                .withFormatter((territory) -> territory.name)
//                .prompt(scn, true);
//        }
//
//
//        var wilderness = TerritoryGenerator.wildernessBuilder()
//            .territory(selectATerritoryToSpawn)
//            .generate();
//
//        var map = new String[TerritoryGenerator.NUM_TILES_ROWS][TerritoryGenerator.NUM_TILES_COLS];
//        // fill map with .'s
//        for (int x = 0; x < map.length; ++x) {
//            for (int y = 0; y < map[0].length; ++y) {
//                map[x][y] = "◼️";
//            }
//        }
//        for (var child : wilderness.getChildren()) {
//            int x = (int) (child.getTransform().x / 32);
//            int y = (int) (child.getTransform().y / 32);
//            if (child instanceof EntityActor) {
//                map[x][y] = "👹";
//            } else if (child instanceof BuildingActor) {
//                map[x][y] = "🏠";
//            } else if (child instanceof ResourceDeposit) {
//                map[x][y] = "🌱";
//            } else {
//                map[x][y] = child.name;
//            }
//        }
//
//        for (int x = 0; x < map.length; ++x) {
//            for (int y = 0; y < map[0].length; ++y) {
//                System.out.print(map[x][y]);
//            }
//            System.out.println();
//        }
//
//        GameInstance.instance().getWorld()
//            .setWildernessLevel(wilderness);

        return null;
    }

    private Void details(Scanner scn, CommandLineArgs args) {
//        GameInstance gi = GameInstance.instance();
//        ChancellorSystem cs = gi.getSystem(ChancellorSystem.class);
//        List<Territory> territories = cs.getTerritoriesOwnedByVillage(GameInstance.instance().getSystem(SettlementSystem.class).getPlayerSettlement());
//
//        for (var territory : territories) {
//            System.out.printf("%s%n", territory.name);
//
//            System.out.println("  Biome: " + territory.getBiome().name());
//            System.out.println("  Climate: " + territory.getClimate().name());
//            System.out.printf("  Hostility: %d%%%n", (int) (territory.getHostility() * 100));
//            System.out.printf("  Corruption: %d%%%n", (int) (territory.getCorruption() * 100));
//
//            List<BuildingActor> buildingsWithinTerritory = cs.getBuildingsWithinTerritory(territory);
//            if (buildingsWithinTerritory != null) {
//                for (var building : buildingsWithinTerritory) {
//                    System.out.printf("  • 🏠 %s%n", building.name);
//                }
//            }
//
//            for (var resource : territory.getAvailableResources()) {
//                System.out.printf("  • 🌱 %s (%d%% richness)%n",
//                    resource.item.getName(),
//                    (int) (resource.richness * 100));
//            }
//        }

        return null;
    }

    private Void overview(Scanner scn, CommandLineArgs args) {
//        GameInstance gi = GameInstance.instance();
//        System.out.printf("You own %s territories%n", gi
//            .getSystem(ChancellorSystem.class)
//            .getTerritoriesOwnedByVillage(GameInstance.instance().getSystem(SettlementSystem.class).getPlayerSettlement())
//            .size());
        return null;
    }

    private Void look(Scanner scn, CommandLineArgs args) {
        WildernessLevel wild = GameInstance.instance().getWorld().getWildernessLevel();

        if (wild == null) {
            System.out.println("You are not in the wilderness");
            return null;
        }

        System.out.printf("This territory has %d enemies%n", wild.getEntities().size());
        System.out.printf("This territory has %d buildings%n", wild.getBuildings().size());
        System.out.printf("This territory has %d deposits%n", wild.getDeposits().size());

        return null;
    }
}
