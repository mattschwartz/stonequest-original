package com.barelyconscious.worlds.terminal.commands;

import com.barelyconscious.worlds.entity.Territory;
import com.barelyconscious.worlds.game.GameInstance;
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
                .ask(scn, true);

            if (!userAccepted) {
                return CommandLineResults.CONTINUE;
            }
        }
    }

    private Void details(Scanner scn, CommandLineArgs args) {
        GameInstance gi = GameInstance.instance();
        ChancellorSystem ts = gi.getSystem(ChancellorSystem.class);
        List<Territory> territories = ts.getTerritoriesOwnedByVillage(gi.getPlayerVillage());

        for (var territory : territories) {
            System.out.printf("%s%n", territory.name);
            for (var resource : territory.getAvailableResources()) {
                System.out.printf("  • %s (%d%% richness)%n",
                    resource.resource.getName(),
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
