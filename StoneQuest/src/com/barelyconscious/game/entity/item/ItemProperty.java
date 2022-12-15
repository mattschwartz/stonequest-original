package com.barelyconscious.game.entity.item;

import com.barelyconscious.game.entity.EntityActor;
import com.barelyconscious.game.entity.EntityAttributes;
import com.barelyconscious.game.entity.Stats;
import com.barelyconscious.game.entity.components.AdjustableValueComponent;
import com.barelyconscious.game.entity.components.HealthComponent;
import com.barelyconscious.game.entity.components.ItemPropertyComponent;
import com.barelyconscious.game.entity.components.StatsComponent;
import lombok.Getter;

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
    public static class StatItemProperty extends ItemProperty {

        private final Stats.StatName statName;
        private final float statValue;
        private final String propertyDescription;

        public StatItemProperty(Stats.StatName statName, float statValue) {
            this.statName = statName;
            this.statValue = statValue;

            final StringBuilder sb = new StringBuilder();
            if (statValue < 0) {
                sb.append("-");
            } else if (statValue > 0) {
                sb.append("+");
            }

            sb.append(statValue).append(" ").append(statName.name);
            propertyDescription = sb.toString();
        }

        @Override
        public void applyProperty(EntityActor entity) {
            StatsComponent entityStatsComponent = entity.getEntityStatsComponent();
            AdjustableValueComponent stat = entityStatsComponent.getStat(statName);
            stat.adjustMaxValueBy(statValue);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            StatsComponent entityStatsComponent = entity.getEntityStatsComponent();
            AdjustableValueComponent stat = entityStatsComponent.getStat(statName);
            stat.adjustMaxValueBy(-statValue);
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
            HealthComponent healthComponent = entity.getHealthComponent();
            healthComponent.adjustMaxValueBy(healthAmount);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            HealthComponent healthComponent = entity.getHealthComponent();
            healthComponent.adjustMaxValueBy(-healthAmount);
        }
    }

    @Getter
    public static class AttributeItemProperty extends ItemProperty {

        private final EntityAttributes.AttributeName attribute;
        private final float attributeValue;
        private final String propertyDescription;

        public AttributeItemProperty(EntityAttributes.AttributeName attribute, float attributeValue) {
            this.attribute = attribute;
            this.attributeValue = attributeValue;
            this.propertyDescription = attribute.getAttributeDescription();
        }

        @Override
        public void applyProperty(EntityActor entity) {
//            AttributeComponent entityAttributeComponent = entity.getEntityAttributeComponent();
//            AdjustableValueComponent attribute = entityAttributeComponent.getAttribute(attribute);
//            attribute.adjustMaxValueBy(attributeValue);
        }

        @Override
        public void removeProperty(EntityActor entity) {
//            AttributeComponent entityAttributeComponent = entity.getEntityAttributeComponent();
//            AdjustableValueComponent attribute = entityAttributeComponent.getAttribute(attribute);
//            attribute.adjustMaxValueBy(-attributeValue);
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
