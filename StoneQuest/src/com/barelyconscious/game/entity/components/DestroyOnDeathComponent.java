package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.AGravestone;
import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.GameInstance;

/**
 * Simple componet that destroys an actor when its health falls to 0.
 */
public class DestroyOnDeathComponent extends OnDeathComponent {

    private float remainingCorpseDuration;

    public DestroyOnDeathComponent(final Actor parent, final float corpseDuration) {
        super(parent, parent.getComponent(HealthComponent.class));
        this.remainingCorpseDuration = corpseDuration;
    }

    @Override
    public void update(EventArgs eventArgs) {
        if (this.isDead()) {
            // disable all other components
            getParent().getComponents().stream()
                .filter(t -> t != this)
                .forEach(t -> t.setEnabled(false));

            remainingCorpseDuration -= eventArgs.getDeltaTime();
            if (remainingCorpseDuration <= 0) {
                getParent().destroy();
                GameInstance.getInstance().getWorld()
                    .spawnActor(new AGravestone(getParent().transform));
            }
        }
    }
}
