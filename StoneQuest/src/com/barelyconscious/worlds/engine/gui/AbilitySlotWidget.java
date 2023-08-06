package com.barelyconscious.worlds.engine.gui;

import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.entity.components.AbilityComponent;
import lombok.extern.log4j.Log4j2;

import java.awt.event.MouseEvent;

@Log4j2
public class AbilitySlotWidget extends MouseInputWidget {

    private final AbilityComponent abilityComponent;

    public AbilitySlotWidget(
        int keyBinding,
        AbilityComponent abilityComponent,
        LayoutData layout
    ) {
        super(layout);
        this.abilityComponent = abilityComponent;

        // todo bind delegate to keyBinding
    }

    @Override
    protected void onRender(EventArgs eventArgs, RenderContext renderContext) {
        super.onRender(eventArgs, renderContext);

        renderContext.render(RenderContext.RenderRequest.builder()
                .spriteResource(abilityComponent.getAbility().getIcon())
            .build());
    }

    @Override
    public boolean onMouseClicked(MouseEvent e) {
        log.info("Ability {} clicked",
            abilityComponent.getAbility().getName());
        return super.onMouseClicked(e);
    }
}
