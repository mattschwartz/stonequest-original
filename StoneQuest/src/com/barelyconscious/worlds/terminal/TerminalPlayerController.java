package com.barelyconscious.worlds.terminal;

import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.entity.*;
import com.barelyconscious.worlds.entity.components.AbilityComponent;
import com.barelyconscious.worlds.game.*;
import com.barelyconscious.worlds.game.abilitysystem.Ability;
import com.barelyconscious.worlds.game.abilitysystem.AbilityContext;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.playercontroller.PlayerController;
import com.barelyconscious.worlds.game.systems.BuildingSystem;

import java.util.List;
import java.util.Scanner;

public class TerminalPlayerController extends PlayerController {

    public static final int CONTINUE = 0;
    public static final int TICK = 1;
    public static final int EXIT = 2;

    private final World world;
    private final Scanner scn;

    public TerminalPlayerController(Scanner scn, World world) {
        this.scn = scn;
        this.world = world;
    }

    /**
     * @return true if the game should continue running, false otherwise
     */
    public int handleInput() {
        String input = scn.nextLine();
        if (handleAbilityCasting(input)) {
            return TICK;
        }
        if (handleExamine(input)) {
            return CONTINUE;
        }
        if (handleInventory(input)) {
            return CONTINUE;
        }
        if (handleLook(input)) {
            return CONTINUE;
        }
        if (handleGather(input)) {
            return TICK;
        }
        if (handleBuilding(input)) {
            return TICK;
        }

        switch (input) {
            case "rest":
            case "wait":
                System.out.println("You twiddle your thumbs.");
                return TICK;

            case "list":
                System.out.println("Actors: " + world.getActors().size());
                break;

            case "whoami":
                System.out.println("You are " + GameInstance.instance().getHeroSelected().name);
                break;
            case "party":
                System.out.println("Party:");
                for (var slotId : GameInstance.PartySlot.values()) {
                    System.out.println("  " + GameInstance.instance().getHeroBySlot(slotId).name);
                }
                break;

            case "stats":
                prettyPrintStats(GameInstance.instance().getHeroSelected());

                return CONTINUE;

            case "facing":
                Vector facing = GameInstance.instance().getHeroSelected().facing;
                if (Vector.UP.equals(facing)) {
                    System.out.println("Facing: NORTH");
                } else if (Vector.DOWN.equals(facing)) {
                    System.out.println("Facing: SOUTH");
                } else if (Vector.LEFT.equals(facing)) {
                    System.out.println("Facing: WEST");
                } else if (Vector.RIGHT.equals(facing)) {
                    System.out.println("Facing: EAST");
                } else {
                    System.out.println("Facing: " + facing);
                }
                return CONTINUE;

            case "north":
                GameInstance.instance().getHeroSelected().facing = Vector.UP;
                return TICK;
            case "south":
                GameInstance.instance().getHeroSelected().facing = Vector.DOWN;
                return TICK;
            case "east":
                GameInstance.instance().getHeroSelected().facing = Vector.RIGHT;
                return TICK;
            case "west":
                GameInstance.instance().getHeroSelected().facing = Vector.LEFT;
                return TICK;

            case "switch 1":
                GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.LEFT);
                return CONTINUE;
            case "switch 2":
                GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.MIDDLE);
                return CONTINUE;
            case "switch 3":
                GameInstance.instance().setHeroSelectedSlot(GameInstance.PartySlot.RIGHT);
                return CONTINUE;
            case "exit":
            case "quit":
                return EXIT;
        }

        return CONTINUE;
    }

    private boolean handleBuilding(String input) {
        if (!input.startsWith("build")) {
            return false;
        }

        System.out.println("Build what?");
        System.out.println("\t[1] Gathering building");
        System.out.println("\t[2] Crafting building");
        System.out.println("\t[3] Production building");
        System.out.print("> ");
        String choice = scn.nextLine();
        try {
            int buildingType = Integer.parseInt(choice);
            switch (buildingType) {
                case 1:
                    System.out.println("Building gathering building...");
                    List<Actor> resourceNodes = world.getActors().stream()
                        .filter(t -> t instanceof ResourceNode)
                        .toList();

                    for (int i = 1; i <= resourceNodes.size(); ++i) {
                        System.out.printf("\t[%d] %s\n", i, resourceNodes.get(i - 1).name);
                    }

                    System.out.print("> ");
                    choice = scn.nextLine();
                    int resourceNodeIndex = Integer.parseInt(choice);
                    ResourceNode resourceNode = (ResourceNode) resourceNodes.get(resourceNodeIndex - 1);

                    GameInstance gi = GameInstance.instance();
                    Village playerVillage = gi.getPlayerVillage();
                    BuildingSystem buildingSystem = gi.getBuildingSystem();

                    HarvesterBuilding harvesterBuilding = buildingSystem.constructHarvesterBuilding(
                        resourceNode, playerVillage);

                    harvesterBuilding.delegateOnItemProduced.bindDelegate((item) -> {
                        System.out.println("Produced an item: " + item.item.getName());
                        return null;
                    });
                    harvesterBuilding.delegateOnProductionHalted.bindDelegate((e) -> {
                        System.out.println("Production halted");
                        return null;
                    });

                    break;
                case 2:
                    System.out.println("Building crafting building...");
                    break;
                case 3:
                    System.out.println("Building production building...");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid choice.");
        }

        return true;
    }

    private boolean handleLook(String input) {
        if (!input.startsWith("look")) {
            return false;
        }

        System.out.println("\tNearby entities:");
        for (Actor actor : world.getActors()) {
            if (!(actor instanceof TileActor)) {
                System.out.printf("\t%s\n", actor.name);
            }
        }
        return true;
    }

    private boolean handleInventory(String input) {
        if (!input.equals("inv") && !input.equals("i") && !input.equals("bag")) {
            return false;
        }

        var pouch = GameInstance.instance().getWagon().getResourcePouch();
        var storage = GameInstance.instance().getWagon().getStorage();
        System.out.printf("Pouch (%d/%d):\n", pouch.currentSize, pouch.capacity);
        printInventory(pouch);

        System.out.printf("Storage (%d/%d):\n", storage.currentSize, storage.capacity);
        printInventory(storage);

        return true;
    }

    private boolean handleExamine(String input) {
        if (!input.startsWith("examine")) {
            return false;
        }

        System.out.println("Examine what?");
        System.out.print("\t> ");
        String examineChoice = scn.nextLine();
        if (examineChoice.equals("self")) {
            System.out.println("You are " + GameInstance.instance().getHeroSelected().name);
        } else {
            world.findActorById(examineChoice).ifPresentOrElse(actor -> {
                System.out.println("You see " + actor.name);
            }, () -> {
                world.findActorByName(examineChoice).ifPresentOrElse(actor -> {
                    System.out.println("You see " + actor.name);
                    if (actor instanceof EntityActor) {
                        prettyPrintStats((EntityActor) actor);
                    } else if (actor instanceof ResourceNode) {
                        printInventory(((ResourceNode) actor).prospect(null));
                    } else if (actor instanceof HarvesterBuilding) {
                        printInventory(((HarvesterBuilding) actor).getStockpile());
                    }
                }, () -> {
                    System.out.println("You don't see anything like that.");
                });
            });

        }

        return true;
    }

    private boolean handleGather(String input) {
        if (!input.startsWith("gather")) {
            return false;
        }

        System.out.println("Gather what?");
        System.out.print("\t> ");
        String gatherChoice = scn.nextLine();
        world.findActorByName(gatherChoice).ifPresentOrElse(actor -> {
            if (actor instanceof ResourceNode res) {
                Item harvest = res.harvest(GameInstance.instance().getHeroSelected());
                if (harvest != null) {
                    System.out.println("You gather " + harvest.getName());
                    GameInstance.instance().getWagon().getResourcePouch().addItem(harvest);
                } else {
                    System.out.println("You don't find anything.");
                }
            }
        }, () -> {
            System.out.println("You don't see anything like that.");
        });
        return true;
    }

    private void printInventory(Inventory inventory) {
        for (var item : inventory.getItems()) {
            if (item != null && item.item != null) {
                System.out.println("\t" + item.item.getName() + " x" + item.stackSize);
            } else {
                System.out.println("\t------------");
            }
        }
    }

    private void printInventory(List<Item> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("Nothing found.");
        } else {
            System.out.println("You find:");
            for (var item : items) {
                System.out.println("\t" + item.getName());
            }
        }
    }

    private boolean handleAbilityCasting(String line) {
        if (!line.startsWith("cast")) {
            return false;
        }

        List<AbilityComponent> abilities = GameInstance.instance().getHeroSelected().getComponentsOfType(AbilityComponent.class);
        if (abilities != null) {
            System.out.println("You know the following abilities:");
            for (int i = 0; i < abilities.size(); i++) {
                System.out.println("  " + i + ": " + abilities.get(i).getAbility().getName());
            }

            System.out.print("Cast what?\n\t> ");
            String choice = scn.nextLine();
            try {
                int choiceNum = Integer.parseInt(choice);
                if (choiceNum >= 0 && choiceNum < abilities.size()) {
                    System.out.println("Casting: " + abilities.get(choiceNum).getAbility().getName());
                    Ability.ActionResult result = abilities.get(choiceNum).getAbility().enact(AbilityContext.builder()
                        .world(world)
                        .caster(GameInstance.instance().getHeroSelected())
                        .build());
                    if (!result.success()) {
                        System.out.println("Failed to cast ability: " + result.message());
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Try again next time");
            }
        } else {
            System.out.println("No learned abilities.");
        }

        return true; // handled
    }

    private void prettyPrintStats(final EntityActor entityActor) {
        System.out.println("\t+--- TRAITS ---+--------------------------------+--------- STATS --------+-------------------------+");
        System.out.println("\t|              |  Offenses                      | Defenses               |  Resources              |");
        System.out.printf("\t|  STR     %2.0f  +--------------------------------+------------------------+-------------------------+%n",
            entityActor.trait(TraitName.STRENGTH).get().getCurrentValue());
        System.out.printf("\t|  DEX     %2.0f  |                                |  Health       %3.0f/%3.0f  |                         |%n",
            entityActor.trait(TraitName.DEXTERITY).get().getCurrentValue(),
            entityActor.stat(StatName.HEALTH).get().getCurrentValue(),
            entityActor.stat(StatName.HEALTH).get().getMaxValue());
        System.out.printf("\t|  CON     %2.0f  |  Ability Power        %3.0f/%3.0f  |  Armor        %3.0f/%3.0f  |  Energy        %3.0f/%3.0f  |%n",
            entityActor.trait(TraitName.CONSTITUTION).get().getCurrentValue(),
            entityActor.stat(StatName.ABILITY_POWER).get().getCurrentValue(),
            entityActor.stat(StatName.ABILITY_POWER).get().getMaxValue(),

            entityActor.stat(StatName.ARMOR).get().getCurrentValue(),
            entityActor.stat(StatName.ARMOR).get().getMaxValue(),

            entityActor.stat(StatName.ENERGY).get().getCurrentValue(),
            entityActor.stat(StatName.ENERGY).get().getMaxValue());

        System.out.printf("\t|  INT     %2.0f  |  Ability Speed        %3.0f/%3.0f  |  Fortitude    %3.0f/%3.0f  |  Focus         %3.0f/%3.0f  |%n",
            entityActor.trait(TraitName.INTELLIGENCE).get().getCurrentValue(),
            entityActor.stat(StatName.ABILITY_SPEED).get().getCurrentValue(),
            entityActor.stat(StatName.ABILITY_SPEED).get().getMaxValue(),

            entityActor.stat(StatName.FORTITUDE).get().getCurrentValue(),
            entityActor.stat(StatName.FORTITUDE).get().getMaxValue(),

            entityActor.stat(StatName.FOCUS).get().getCurrentValue(),
            entityActor.stat(StatName.FOCUS).get().getMaxValue());
        System.out.printf("\t|  FAI     %2.0f  |  Precision            %3.0f/%3.0f  |  Warding      %3.0f/%3.0f  |  Spirit        %3.0f/%3.0f  |%n",
            entityActor.trait(TraitName.FAITH).get().getCurrentValue(),
            entityActor.stat(StatName.PRECISION).get().getCurrentValue(),
            entityActor.stat(StatName.PRECISION).get().getMaxValue(),
            entityActor.stat(StatName.WARDING).get().getCurrentValue(),
            entityActor.stat(StatName.WARDING).get().getMaxValue(),
            entityActor.stat(StatName.SPIRIT).get().getCurrentValue(),
            entityActor.stat(StatName.SPIRIT).get().getMaxValue());
        System.out.println("\t+--------------+--------------------------------+------------------------+-------------------------+");

    }
}
