package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import lombok.Getter;
import lombok.Setter;

/**
 * A component which can be visible/hidden from the player depending on this state.
 *
 * If the player cannot see this Actor for some reason, then its sprite component will not be
 * rendered to the screen.
 */
public class PlayerVisibilityComponent extends Component {

    @Getter
    @Setter
    private boolean playerCanSee;

    public PlayerVisibilityComponent(Actor parent) {
        super(parent);
    }

    private final float _debugTogglePeriod = .15f;
    private float _debugHideTimeRemaining = _debugTogglePeriod;
    private boolean isHiding = false;

    private SpriteComponent spriteComponent;
    private SpriteComponent getSpriteComponent() {
        if (spriteComponent == null) {
            spriteComponent = getParent().getComponent(SpriteComponent.class);
        }
        return spriteComponent;
    }

    @Override
    public void update(EventArgs eventArgs) {
        if (isHiding) {
            _debugHideTimeRemaining -= eventArgs.getDeltaTime();
            if (_debugHideTimeRemaining <= 0) {
                isHiding = false;
                _debugHideTimeRemaining = _debugTogglePeriod;
                getSpriteComponent().setRenderEnabled(true);
            }
        } else {
            _debugHideTimeRemaining -= eventArgs.getDeltaTime();
            if (_debugHideTimeRemaining <= 0) {
                isHiding = true;
                _debugHideTimeRemaining = _debugTogglePeriod;
                getSpriteComponent().setRenderEnabled(false);
            }
        }
    }
}
