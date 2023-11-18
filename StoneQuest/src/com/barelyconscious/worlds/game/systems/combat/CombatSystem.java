package com.barelyconscious.worlds.game.systems.combat;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import com.barelyconscious.worlds.game.systems.GameSystem;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Commands combat encounters between entities.
 */
@Log4j2
public class CombatSystem implements GameSystem {

    private Map<Integer, ThreatTable> combatEncounters = new HashMap<>();

    /**
     * @param encounterId the id of the combat encounter
     * @return the threat table for the given combat encounter
     */
    public ThreatTable getThreatTable(int encounterId) {
        if (!combatEncounters.containsKey(encounterId)) {
            throw new IllegalArgumentException("No combat encounter with id " + encounterId);
        }

        return combatEncounters.get(encounterId);
    }

    public int createCombatEncounter() {
        int combatEncounterId =  UMath.RANDOM.nextInt();

        combatEncounters.put(combatEncounterId, new ThreatTable());

        return combatEncounterId;
    }

    public void endCombatEncounter(int encounterId) {
        log.info("Ending combat encounter {}", encounterId);
        combatEncounters.remove(encounterId);
    }

    // `resolveAttack` should be in the combat system, but the calculations should be somewhere else

    public double calculateWeaponDamage(EntityActor entity) {
        Item weapon = entity.getEquipment().getEquippedItem(EquipmentItemTag.EQUIPMENT_RIGHT_HAND);
        System.out.println("Weapon: " + (weapon == null ? "unarmed" : weapon.getName()));


        double str = entity.trait(TraitName.STRENGTH).get().getCurrentValue();
        double ap = entity.stat(StatName.ABILITY_POWER).get().getCurrentValue();

        double minWeaponDamage = 0;
        double maxWeaponDamage = 0;

        if (weapon != null) {
            for (var prop : weapon.getProperties()) {
                if (prop instanceof ItemProperty.TraitItemProperty traitProp) {
                    if (traitProp.getTrait() == TraitName.STRENGTH) {
                        str += traitProp.getStatValue();
                    }
                } else if (prop instanceof ItemProperty.StatItemProperty statProp) {
                    if (statProp.getStat() == StatName.ABILITY_POWER) {
                        ap += statProp.getStatValue();
                    }
                }
                else if (prop instanceof ItemProperty.WeaponDamageProperty wep) {
                    minWeaponDamage = wep.getMinWeaponDamage();
                    maxWeaponDamage = wep.getMaxWeaponDamage();
                }
            }
        }

        return UMath.RANDOM.nextDouble() * (str + maxWeaponDamage) + minWeaponDamage + ap * 2.5;
    }

    public void meleeAttack(EntityActor attacker, EntityActor defender) {
        double precision = attacker.stat(StatName.PRECISION).get().getCurrentValue();

        double random = UMath.RANDOM.nextDouble() * 100;
        if (random <= (15 - precision)) {
            System.out.println("Missed!");
            return;
        }
        boolean isCrit = false;
        if (random >= (95 - precision)) {
            isCrit = true;
        }

        double damage = calculateWeaponDamage(attacker);

        if (!isCrit) {
            // test armor
            double armor = defender.stat(StatName.ARMOR).get().getCurrentValue();
            damage -= armor * 1.3;
            damage = UMath.clamp(damage, 0);
        }

        System.out.printf("%s attacks %s for %.2f damage",
            attacker.name, defender.name, damage);
        if (isCrit) {
            System.out.print(" (CRIT!)");
        }
        System.out.println();

        DynamicValueComponent health = defender.stat(StatName.HEALTH).get();
        health.adjustCurrentValueBy(-damage);
    }
}
