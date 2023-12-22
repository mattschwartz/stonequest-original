package com.barelyconscious.worlds.game.systems.combat;

import com.barelyconscious.worlds.common.UMath;
import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.game.item.Item;
import com.barelyconscious.worlds.game.item.ItemProperty;
import com.barelyconscious.worlds.game.item.tags.EquipmentItemTag;
import com.barelyconscious.worlds.game.systems.GameSystem;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Commands combat encounters between entities.
 */
@Log4j2
public class CombatSystem implements GameSystem {

    public static final class CombatEncounter {
        private final ThreatTable threatTable;

        public CombatEncounter(ThreatTable threatTable) {
            this.threatTable = threatTable;
        }

        public ThreatTable getThreatTable() {
            return threatTable;
        }
    }

    @Data
    @Builder
    public static final class CombatState {
        private CombatEncounter activeCombatEncounter;
    }

    public void killEntity(EntityActor entity) {
        GameInstance.log(String.format("%s has perished.", entity.name));

        var activeCombatEncounter = GameInstance.instance().getGameState()
            .getCombatState()
            .getActiveCombatEncounter();

        // todo: maybe other stuff will happen too later
        activeCombatEncounter.threatTable
            .removeCombatant(entity);
    }

    /**
     * Resolves damage-dealing abilities and attacks.
     * <p>
     * Determines the attacker's bonuses, crit chance, precision and all that
     * Determines the defender's defenses, dodge chance, armor and all that
     *
     * @param attacker      the actor who is attacking
     * @param defender      the actor who is defending
     * @param damageAbility the ability that is being used to deal damage
     */
    public void applyDamage(EntityActor attacker, EntityActor defender, DamagingAbility damageAbility) {
        var activeCombatEncounter = GameInstance.instance().getGameState()
            .getCombatState()
            .getActiveCombatEncounter();

        if (activeCombatEncounter == null) {
            activeCombatEncounter = createCombatEncounter(attacker, defender);
            GameInstance.instance().getGameState()
                .getCombatState()
                .setActiveCombatEncounter(activeCombatEncounter);
        }

        var health = defender.getHealthComponent();
        if (health != null && health.isEnabled()) {
            GameInstance.log(String.format("%s dealt %d damage to %s with %s",
                attacker.getName(),
                (int) damageAbility.damage,
                defender.getName(),
                damageAbility.damageType));
            health.adjustCurrentValueBy(-damageAbility.damage);
        }

        var threatTable = activeCombatEncounter.getThreatTable();
        threatTable.addThreat(attacker, defender, damageAbility.damage * damageAbility.threatMultiplier);

        log.info("{} has generated {} threat to {}",
            attacker.getName(),
            damageAbility.damage * damageAbility.threatMultiplier,
            defender.getName());
    }

    /**
     * An encounter begins when two or enemies engage in conflict.
     *
     * @param instigator
     * @param defenders
     * @return
     */
    public CombatEncounter createCombatEncounter(EntityActor instigator,
                                                 EntityActor... defenders) {
        var threatTable = new ThreatTable();

        // add all actors to the threat table
        threatTable.addCombatant(instigator);
        if (defenders != null) {
            for (var defender : defenders) {
                threatTable.addCombatant(defender);
            }
        }

        return new CombatEncounter(threatTable);
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
                } else if (prop instanceof ItemProperty.WeaponDamageProperty wep) {
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
