package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;

/**
 * Simple componet that destroys an actor when its health falls to 0.
 */
public class DestroyOnDeathComponent extends Component{

    /**
     * How long the actor will remain in the game before removing.
     */
    private final float corpseDuration;
    private float remainingCorpseDuration;

    public DestroyOnDeathComponent(final Actor parent, final float corpseDuration) {
        super(parent);
        this.corpseDuration = corpseDuration;
        this.remainingCorpseDuration = corpseDuration;
    }

    private boolean hasBeenKilled = false;

    @Override
    public void update(EventArgs eventArgs) {
        if (hasBeenKilled) {
            remainingCorpseDuration -= eventArgs.deltaTime;
            if (remainingCorpseDuration <= 0) {
                getParent().destroy();
                setEnabled(false);
                return;
            }
            return;
        }
        final HealthComponent health = getParent().getComponent(HealthComponent.class);
        if (health != null) {
            hasBeenKilled = health.getCurrentHealth() <= 0;
            if (hasBeenKilled) {
                // disable all other components
                getParent().getComponents().stream()
                    .filter(t -> t != this)
                    .forEach(t -> t.setEnabled(false));
            }
        }
    }
}
