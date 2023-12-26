package com.barelyconscious.worlds.game.entities;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.NametagComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.systems.GuiSystem;

import java.util.List;

public class ExtractorNpc extends Actor {

    public ExtractorNpc(Vector transform) {
        super("Extractor", transform);

        addComponent(new SpriteComponent(this,
            new BetterSpriteResource("entities::extractor_npc1"),
            RenderLayer.ENTITIES));
        addComponent(new NametagComponent(this, Box.square(64)));

        var mlc = new MouseListenerComponent(this, Box.square(64));
        mlc.delegateOnMouseClicked.bindDelegate((eventArgs) -> {
            var gui = GameInstance.instance().getSystem(GuiSystem.class);
            gui.getExtractorDialogPanel().show();

            GameInstance.log(getName() + " says: " + getRandomSalutation());

            return null;
        });
        addComponent(mlc);
    }

    public static String getRandomSalutation() {
        return SALUTATIONS.get((int) (Math.random() * SALUTATIONS.size()));
    }

    private final static List<String> SALUTATIONS = List.of(
        "What kin I do fer you now?",
        "How can I help ya?",
        "What can I do for ya?",
        "What can I do for ya, stranger?",
        "Hey there",
        "How are ya?"
    );
}
