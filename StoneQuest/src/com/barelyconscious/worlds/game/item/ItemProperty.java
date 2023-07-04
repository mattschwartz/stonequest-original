package com.barelyconscious.worlds.game.item;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.game.StatName;
import com.barelyconscious.worlds.game.TraitName;
import com.barelyconscious.worlds.entity.components.DynamicValueComponent;
import com.barelyconscious.worlds.entity.components.ItemPropertyComponent;
import lombok.Getter;
import lombok.NonNull;

public abstract class ItemProperty {

    /**
     * @return a human-friendly string that can be shown to players to explain the property.
     */
    public abstract String getPropertyDescription();

    /**
     * Applies the property to the provided entity. Eg when equipping an item that gives a bonus to strength should add
     * strength to the wielding entity.
     */
    public abstract void applyProperty(final EntityActor entity);

    /**
     * Removes the property from the actor. Eg when removing an item that gives a bonus to strength should remove the
     * added strength from the wielding entity.
     */
    public abstract void removeProperty(final EntityActor entity);

    @Getter
    public static class TraitItemProperty extends ItemProperty {

        private final TraitName trait;
        private final float statValue;
        private final String propertyDescription;

        public TraitItemProperty(TraitName trait, float statValue) {
            this.trait = trait;
            this.statValue = statValue;

            final StringBuilder sb = new StringBuilder();
            if (statValue < 0) {
                sb.append("-");
            } else if (statValue > 0) {
                sb.append("+");
            }

            sb.append(statValue).append(" ").append(trait.name);
            propertyDescription = sb.toString();
        }

        @Override
        public void applyProperty(EntityActor entity) {
            entity.trait(trait).adjustMaxValueBy(statValue);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            entity.trait(trait).adjustMaxValueBy(-statValue);
        }
    }

    @Getter
    public static class HealthItemProperty extends ItemProperty {

        private final float healthAmount;
        private final String propertyDescription;

        public HealthItemProperty(float healthAmount) {
            this.healthAmount = healthAmount;

            final StringBuilder sb = new StringBuilder();
            if (healthAmount < 0) {
                sb.append("-");
            } else if (healthAmount > 0) {
                sb.append("+");
            }

            sb.append(healthAmount).append(" health");
            propertyDescription = sb.toString();
        }

        @Override
        public void applyProperty(EntityActor entity) {
            DynamicValueComponent healthComponent = entity.getHealthComponent();
            healthComponent.adjustMaxValueBy(healthAmount);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            DynamicValueComponent healthComponent = entity.getHealthComponent();
            healthComponent.adjustMaxValueBy(-healthAmount);
        }
    }

    @Getter
    public static class ItemStatProperty extends ItemProperty {

        private final StatName stat;
        private final float statValue;
        private final String propertyDescription;

        public ItemStatProperty(StatName stat, float statValue) {
            this.stat = stat;
            this.statValue = statValue;
            this.propertyDescription = stat.description;
        }

        @Override
        public void applyProperty(EntityActor entity) {
            entity.stat(stat).adjustMaxValueBy(statValue);
        }

        @Override
        public void removeProperty(@NonNull EntityActor entity) {
            entity.stat(stat).adjustMaxValueBy(-statValue);
        }
    }

    @Getter
    public static class WeaponDamageProperty extends ItemProperty {

        private final float minWeaponDamage;
        private final float maxWeaponDamage;
        private final String propertyDescription;
        private final float weaponSpeed;

        public WeaponDamageProperty(float minWeaponDamage, float maxWeaponDamage, float weaponSpeed) {
            this.minWeaponDamage = minWeaponDamage;
            this.maxWeaponDamage = maxWeaponDamage;
            this.weaponSpeed = weaponSpeed;

            final StringBuilder sb = new StringBuilder();
            sb.append(minWeaponDamage).append("-").append(maxWeaponDamage).append(" damage");
            sb.append(" (").append(weaponSpeed).append("s)");
            propertyDescription = sb.toString();
        }

        @Override
        public String getPropertyDescription() {
            return null;
        }

        @Override
        public void applyProperty(EntityActor entity) {

        }

        @Override
        public void removeProperty(EntityActor entity) {

        }
    }

    /**
     * A property that adds the specified component to the entity.
     */
    @Getter
    public static class ComponentItemProperty extends ItemProperty {

        private final ItemPropertyComponent component;
        private final String propertyDescription;

        public ComponentItemProperty(final ItemPropertyComponent component) {
            this.component = component;
            this.propertyDescription = component.getPropertyDescription();
        }

        @Override
        public void applyProperty(EntityActor entity) {
            entity.addComponent(component);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            entity.removeComponent(component);
        }
    }
}
