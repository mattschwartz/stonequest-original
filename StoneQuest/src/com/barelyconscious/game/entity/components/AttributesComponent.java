package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EntityAttributes;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;

@Log4j2
public class AttributesComponent extends Component {

    private final EnumMap<EntityAttributes.AttributeName, AdjustableValueComponent> componentsByAttributeName = new EnumMap<>(EntityAttributes.AttributeName.class);

    public AttributesComponent(final Actor parent, final EntityAttributes attributes) {
        super(parent);

        attributes.getAttributes().forEach(this::setAttributeValue);
    }

    public void setAttributeValue(final EntityAttributes.AttributeName name, final float value) {
        AdjustableValueComponent component = componentsByAttributeName.get(name);
        if (component == null) {
            component = new AdjustableValueComponent(getParent(), value, value);
            componentsByAttributeName.put(name, component);
        } else {
            component.updateValues(value, value);
        }
    }

    /**
     * helper method to adjust the stat component by delta.
     * if attribute doesn't exist then nothing is adjusted
     */
    public void adjustValue(final EntityAttributes.AttributeName name, final float delta) {
        AdjustableValueComponent component = componentsByAttributeName.get(name);
        if (component != null) {
            component.adjust(delta);
        } else {
            log.debug("No component exists for Attribute={}", name);
        }
    }

    public AdjustableValueComponent getStat(final EntityAttributes.AttributeName name) {
        if (!componentsByAttributeName.containsKey(name)) {
            componentsByAttributeName.put(name, new AdjustableValueComponent(getParent()));
        }
        return componentsByAttributeName.get(name);
    }
}
