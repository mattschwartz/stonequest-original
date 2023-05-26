package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.Stats;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;

@Log4j2
public class AttributeComponent extends Component {

    private final EnumMap<Stats.Attribute, AdjustableValueComponent> componentsByStatName= new EnumMap<>(Stats.Attribute.class);

    public AttributeComponent(final Actor parent, final Stats stats) {
        super(parent);

        stats.getStats().forEach(this::setStatValue);
    }

    public void setStatValue(final Stats.Attribute name, final float value) {
        AdjustableValueComponent component = componentsByStatName.get(name);
        if (component == null) {
            component = new AdjustableValueComponent(getParent(), value, value);
            componentsByStatName.put(name, component);
        } else {
            component.updateValues(value, value);
        }
    }

    /**
     * helper method to adjust the stat component by delta.
     * if stat doesn't exist then nothing is adjusted
     */
    public void adjustValue(final Stats.Attribute name, final float delta) {
        AdjustableValueComponent component = componentsByStatName.get(name);
        if (component != null) {
            component.adjust(delta);
        } else {
            log.debug("No component exists for Stat={}", name);
        }
    }

    public AdjustableValueComponent getStat(final Stats.Attribute name) {
        if (!componentsByStatName.containsKey(name)) {
            componentsByStatName.put(name, new AdjustableValueComponent(getParent()));
        }
        return componentsByStatName.get(name);
    }
}
