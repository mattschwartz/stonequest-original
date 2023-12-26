package com.barelyconscious.worlds.game.entities;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.common.shape.Vector;
import com.barelyconscious.worlds.engine.graphics.RenderLayer;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.BoxColliderComponent;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.entity.components.NametagComponent;
import com.barelyconscious.worlds.entity.components.SpriteComponent;
import com.barelyconscious.worlds.game.GameInstance;
import com.barelyconscious.worlds.game.resources.BetterSpriteResource;
import com.barelyconscious.worlds.game.systems.GuiSystem;

import java.util.List;

public class CollectorNpc extends Actor {

        public CollectorNpc(Vector transform) {
            super("The Collector", transform);

            addComponent(new SpriteComponent(this,
                new BetterSpriteResource("entities::collector_npc1"),
                RenderLayer.ENTITIES));
            addComponent(new BoxColliderComponent(this, true, false, Box.square(64)));
            addComponent(new NametagComponent(this, Box.square(64), "Click to talk"));

            var mouse = new MouseListenerComponent(this, Box.square(64));
            mouse.delegateOnMouseClicked.bindDelegate((args) -> {
                var gui = GameInstance.instance().getSystem(GuiSystem.class);
                gui.getCollectorDialogPanel().show();

                GameInstance.log(getName() + " says: " + getRandomSalutation());

                return null;
            });
            addComponent(mouse);
        }

        public static String getRandomSalutation() {
            return SALUTATIONS.get((int) (Math.random() * SALUTATIONS.size()));
        }

        private final static List<String> SALUTATIONS = List.of(
            "Greetings, techno-traveler!",
            "Hello there, bearer of digital wonders!",
            "Salutations, keeper of the electronic realm!",
            "Ahoy, digital voyager! What have you brought me today?",
            "Well met, bringer of technological tidings!",
            "Hey, savvy tech-treasure seeker! What's in your bag of wonders?",
            "Hello, seeker of circuits and screens! What can I do for you?",
            "Greetings, wanderer of the digital landscape! What stories do your devices hold?",
            "Ah, the harbinger of gadgets approaches! What can I do for you today?",
            "Hello, fellow tech enthusiast! What goodies do you have for trade?"
        );
}
