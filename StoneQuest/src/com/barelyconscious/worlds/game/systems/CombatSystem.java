package com.barelyconscious.worlds.game.systems;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;

import java.util.Random;

/**
 * Commands combat encounters between entities.
 */
public class CombatSystem {

    private static final Random RAND = new Random(6111991L);

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

        return RAND.nextDouble() * (str + maxWeaponDamage) + minWeaponDamage + ap * 2.5;
    }

    public void meleeAttack(EntityActor attacker, EntityActor defender) {
        double precision = attacker.stat(StatName.PRECISION).get().getCurrentValue();

        double random = RAND.nextDouble() * 100;
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
