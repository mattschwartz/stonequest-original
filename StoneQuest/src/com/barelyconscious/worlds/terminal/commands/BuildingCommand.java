package com.barelyconscious.worlds.terminal.commands;

import com.barelyconscious.worlds.GameRunnerCLI;
import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.TerritorySystem;
import com.barelyconscious.worlds.terminal.InputDialog;

import java.util.List;
import java.util.Scanner;

public class BuildingCommand extends Command {

    @Override
    public CommandLineResults run(Scanner scn, CommandLineArgs args) {
        if (!args.parameters.isEmpty()) {
            System.out.println("You provided some args bro good for you. Returning to main menu");
            return CommandLineResults.CONTINUE;
        }

        System.out.println("    +-----------------+");
        System.out.println("    |  BUILDING MENU  |");
        System.out.println("    | 'esc' to return |");
        System.out.println("    +-----------------+");

        while (true) {

            boolean userAccepted = InputDialog.question("What would you build?")
                .answer("A building for Gathering", () -> buildGatheringHut(scn, args))
                .answer("A building for Crafting", () -> buildCraftingBuilding(scn, args))
                .answer("A building for Production", () -> buildProductionBuilding(scn, args))
                .ask(scn, true);

            if (!userAccepted) {
                return CommandLineResults.CONTINUE;
            } else {
                GameRunnerCLI.tick();
            }

            System.out.println("    +-----------------+");
            System.out.println("    |  BUILDING MENU  |");
            System.out.println("    | 'esc' to return |");
            System.out.println("    +-----------------+");
            System.out.println("> ");

            if ("esc".equalsIgnoreCase(scn.nextLine())) {
                return CommandLineResults.CONTINUE;
            }
        }
    }

    private Void buildGatheringHut(Scanner scn, CommandLineArgs args) {
        GameInstance gi = GameInstance.instance();
        TerritorySystem ts = gi.getSystem(TerritorySystem.class);

        List<Territory.TerritoryResource> availableResources = ts.getTerritoriesOwnedByVillage(gi.getPlayerVillage())
            .stream().map(Territory::getAvailableResources)
            .flatMap(List::stream)
            .toList();

        if (availableResources.isEmpty()) {
            System.out.println("The land is barren.");
            return null;
        } else {
            System.out.println("The area is ripe with resources.");
        }

        var choice = InputDialog.pollObjects("What would you like to build?", availableResources)
            .withFormatter(t -> String.format("%s (%d%% richness)",
                t.resource.getName(),
                (int) (t.richness * 100)))
            .prompt(scn, true);

        if (choice == null) {
            System.out.println("Nothing selected.");
            return null;
        } else {
            System.out.println("Building a hut for " + choice.resource.getName());
        }

        return null;
    }

    private Void buildCraftingBuilding(Scanner scn, CommandLineArgs args) {
        System.out.println("Building a crafting building");
        return null;
    }

    private Void buildProductionBuilding(Scanner scn, CommandLineArgs args) {
        System.out.println("Building a production building");
        return null;
    }
}
