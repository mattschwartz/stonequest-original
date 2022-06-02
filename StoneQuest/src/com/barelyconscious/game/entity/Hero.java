package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.components.SpriteComponent;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.RenderLayer;
import com.barelyconscious.game.entity.resources.ResourceSprite;
import com.barelyconscious.game.entity.resources.Resources;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

import java.awt.*;

@Getter
public class Hero extends AEntity {

    private final Inventory inventory;

    public Hero(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentHealth,
        final float maxHealth,
        final float currentPower,
        final float maxPower,
        final Stats entityStats,
        final float currentExperience,
        final Inventory inventory
    ) {
        super(name,
            transform,
            entityLevel,
            currentExperience,
            currentHealth,
            maxHealth,
            currentPower,
            maxPower,
            entityStats);
        this.inventory = inventory;
        addComponent(new HeroSelectedSpriteComponent(this));
        addComponent(new HeroSlotIdSpriteComponent(this));
    }

    private class HeroSelectedSpriteComponent extends SpriteComponent {

        public HeroSelectedSpriteComponent(Actor parent) {
            super(
                parent,
                Resources.getSprite(ResourceSprite.HERO_SELECTION_SPRITE),
                RenderLayer.DOODADS
            );
        }

        @Override
        public void update(EventArgs eventArgs) {
            setRenderEnabled(GameInstance.getInstance().getHeroSelected() == getParent());
        }

        @Override
        public void render(EventArgs eventArgs, RenderContext renderContext) {
            renderContext.render(
                sprite.image,
                (int) getParent().transform.x - 8,
                (int) getParent().transform.y - 8,
                48, 48,
                RenderLayer.ENTITIES
            );
        }
    }

    private class HeroSlotIdSpriteComponent extends Component {

        public HeroSlotIdSpriteComponent(Actor parent) {
            super(parent);
        }

        @Override
        public void render(EventArgs eventArgs, RenderContext renderContext) {
            final Vector worldPos = new Vector(
                getParent().transform.x + 12,
                getParent().transform.y + 52);
            final Vector screenPos = renderContext.camera
                .worldToScreenPos(worldPos);

            renderContext.renderRect(
                Color.black,
                true,
                (int) worldPos.x-2,
                (int) worldPos.y-12,
                11, 16,
                RenderLayer.GUI
            );

            final GameInstance.PartySlot slot = GameInstance.getInstance()
                    .getSlotByHero((Hero)getParent());

            renderContext.renderString(
                "" + (slot.index + 1),
                Color.yellow,
                (int) screenPos.x,
                (int) screenPos.y,
                RenderLayer.GUI
            );
        }
    }
}
