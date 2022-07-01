package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.Stats;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;

@Log4j2
public class StatsComponent extends Component {

    private final EnumMap<Stats.StatName, AdjustableValueComponent> componentsByStatName= new EnumMap<>(Stats.StatName.class);

    public StatsComponent(final Actor parent, final Stats stats) {
        super(parent);

        stats.getStats().forEach(this::setStatValue);
    }

    public void setStatValue(final Stats.StatName name, final float value) {
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
    public void adjustValue(final Stats.StatName name, final float delta) {
        AdjustableValueComponent component = componentsByStatName.get(name);
        if (component != null) {
            component.adjust(delta);
        } else {
            log.debug("No component exists for Stat={}", name);
        }
    }

    public AdjustableValueComponent getStat(final Stats.StatName name) {
        if (!componentsByStatName.containsKey(name)) {
            componentsByStatName.put(name, new AdjustableValueComponent(getParent()));
        }
        return componentsByStatName.get(name);
    }
}
