package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.components.JobActionComponent;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import lombok.Getter;

import java.awt.Color;

@Getter
public class Hero extends EntityActor {

    private final Inventory inventory;

    @Getter
    private final HeroClassType heroClassType;

    public Hero(
        final String name,
        final Vector transform,
        final int entityLevel,
        final float currentPower,
        final float maxPower,
        final Stats entityStats,
        final float currentExperience,
        final Inventory inventory,
        final HeroClassType heroClassType
    ) {
        super(name,
            transform,
            entityLevel,
            currentExperience,
            currentPower,
            maxPower,
            entityStats);
        this.inventory = inventory;
        this.heroClassType = heroClassType;

        addComponent(new HeroSelectedSpriteComponent(this));
        addComponent(new HeroSlotIdSpriteComponent(this));
        addComponent(new JobActionComponent(this));
    }

    private static class HeroSelectedSpriteComponent extends SpriteComponent {

        public HeroSelectedSpriteComponent(Actor parent) {
            super(
                parent,
                Resources.getSprite(ResourceSprite.HERO_SELECTION_SPRITE),
                RenderLayer.DOODADS
            );
        }

        @Override
        public void update(EventArgs eventArgs) {
            setRenderEnabled(GameInstance.instance().getHeroSelected() == getParent());
        }

        @Override
        public void render(EventArgs eventArgs, RenderContext renderContext) {
            renderContext.render(
                sprite.getTexture(),
                (int) getParent().transform.x - 8,
                (int) getParent().transform.y - 14,
                48, 48,
                RenderLayer.ENTITIES
            );
        }
    }

    private static class HeroSlotIdSpriteComponent extends Component {

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
                (int) worldPos.x - 2,
                (int) worldPos.y - 12,
                11, 16,
                RenderLayer.GUI
            );

            final GameInstance.PartySlot slot = GameInstance.instance()
                .getSlotByHero((Hero) getParent());

            final FontContext font = renderContext.getFontContext();
            font.setRenderLayer(RenderLayer.GUI);
            font.setColor(Color.yellow);

            font.drawString(
                Integer.toString(slot.index + 1),
                FontContext.TextAlign.LEFT,
                (int) screenPos.x,
                (int) screenPos.y);
        }
    }
}
