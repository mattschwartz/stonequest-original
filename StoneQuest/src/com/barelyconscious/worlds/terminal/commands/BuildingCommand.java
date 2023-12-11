package com.barelyconscious.worlds.terminal.commands;

import com.barelyconscious.worlds.entity.wilderness.Territory;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.systems.ChancellorSystem;
import com.barelyconscious.worlds.game.systems.SettlementSystem;
import com.barelyconscious.worlds.game.types.TerritoryResource;
import com.barelyconscious.worlds.terminal.InputDialog;

import java.util.List;
import java.util.Scanner;

public class BuildingCommand extends Command {

    @Override
    public CommandLineResults run(Scanner scn, CommandLineArgs args) {
        if (!args.parameters.isEmpty()) {
            System.out.println("You provided some args bro good for you. Returning to main menu");
            return CommandLineResults.TICK;
        }

        System.out.println("+-----------------+");
        System.out.println("|  BUILDING MENU  |");
        System.out.println("| 'esc' to return |");
        System.out.println("+-----------------+");

        boolean userAccepted = InputDialog.question("What would you build?")
            .answer("A building for Gathering", () -> buildGatheringHut(scn, args))
            .answer("A building for Crafting", () -> buildCraftingBuilding(scn, args))
            .answer("A building for Production", () -> buildProductionBuilding(scn, args))
            .ask(scn, true);

        if (!userAccepted) {
            return CommandLineResults.CONTINUE;
        } else {
            return CommandLineResults.TICK;
        }
    }

    private Void buildGatheringHut(Scanner scn, CommandLineArgs args) {
        GameInstance gi = GameInstance.instance();
        ChancellorSystem ts = gi.getSystem(ChancellorSystem.class);

        List<TerritoryResource> availableResources = ts.getTerritoriesOwnedByVillage(GameInstance.instance().getSystem(SettlementSystem.class).getPlayerSettlement())
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
                t.item.getName(),
                (int) (t.richness * 100)))
            .prompt(scn, true);

        if (choice == null) {
            System.out.println("Nothing selected.");
            return null;
        } else {
            System.out.println("Building a hut for " + choice.item.getName());
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
