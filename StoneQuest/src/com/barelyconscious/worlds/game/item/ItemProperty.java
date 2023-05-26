package com.barelyconscious.worlds.game.item;

import com.barelyconscious.worlds.entity.EntityActor;
import com.barelyconscious.worlds.entity.Stats;
import com.barelyconscious.worlds.entity.components.AdjustableValueComponent;
import com.barelyconscious.worlds.entity.components.HealthComponent;
import com.barelyconscious.worlds.entity.components.ItemPropertyComponent;
import com.barelyconscious.worlds.entity.components.AttributeComponent;
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
    public static class ItemAttributeProperty extends ItemProperty {

        private final Stats.Attribute attribute;
        private final float statValue;
        private final String propertyDescription;

        public ItemAttributeProperty(Stats.Attribute attribute, float statValue) {
            this.attribute = attribute;
            this.statValue = statValue;

            final StringBuilder sb = new StringBuilder();
            if (statValue < 0) {
                sb.append("-");
            } else if (statValue > 0) {
                sb.append("+");
            }

            sb.append(statValue).append(" ").append(attribute.name);
            propertyDescription = sb.toString();
        }

        @Override
        public void applyProperty(EntityActor entity) {
            AttributeComponent entityAttributeComponent = entity.getEntityAttributeComponent();
            AdjustableValueComponent stat = entityAttributeComponent.getStat(attribute);
            stat.adjustMaxValueBy(statValue);
        }

        @Override
        public void removeProperty(EntityActor entity) {
            AttributeComponent entityAttributeComponent = entity.getEntityAttributeComponent();
            AdjustableValueComponent stat = entityAttributeComponent.getStat(attribute);
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
    public static class ItemStatProperty extends ItemProperty {

        private final Stats.Stat attribute;
        private final float attributeValue;
        private final String propertyDescription;

        public ItemStatProperty(Stats.Stat attribute, float attributeValue) {
            this.attribute = attribute;
            this.attributeValue = attributeValue;
            this.propertyDescription = attribute.description;
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
