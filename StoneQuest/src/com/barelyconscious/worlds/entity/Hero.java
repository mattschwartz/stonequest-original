package com.barelyconscious.worlds.entity;

import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.engine.EventArgs;
import com.barelyconscious.worlds.engine.graphics.FontContext;
import com.barelyconscious.worlds.engine.graphics.RenderContext;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.components.JobActionComponent;
import com.barelyconscious.worlds.game.hero.HeroClassType;
import com.barelyconscious.worlds.game.resources.ResourceSprite;
import com.barelyconscious.worlds.game.resources.Resources;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.Inventory;
import com.barelyconscious.worlds.game.systems.HeroSystem;
import lombok.Getter;

import java.awt.Color;

@Getter
public class Hero extends EntityActor {

    @Getter
    private final HeroClassType heroClassType;

    public Hero(
        final String name,
        final Vector transform,
        final HeroClassType heroClassType
    ) {
        super(name, transform);
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
            var selectedHero = GameInstance.instance()
                .getSystem(HeroSystem.class)
                .getHeroSelected();

            setRenderEnabled(selectedHero == getParent());
        }

        @Override
        public void render(EventArgs eventArgs, RenderContext renderContext) {
            renderContext.render(
                sprite.getTexture(),
                (int) getParent().getTransform().x - 8,
                (int) getParent().getTransform().y - 14,
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
                getParent().getTransform().x + 12,
                getParent().getTransform().y + 52);
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

            final HeroSystem.PartySlot slot = GameInstance.instance()
                .getSystem(HeroSystem.class)
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
