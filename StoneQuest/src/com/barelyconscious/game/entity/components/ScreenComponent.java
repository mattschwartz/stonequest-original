package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.RenderContext;
import com.barelyconscious.game.entity.components.Component;

public abstract class ScreenComponent extends Component {

    public ScreenComponent(final Actor parent) {
        super(parent);
    }

    public void render(final RenderContext renderContext) {
    }

    public void guiRender(final RenderContext renderContext) {
    }

    @Override
    public void update(EventArgs eventArgs) {
    }
}
