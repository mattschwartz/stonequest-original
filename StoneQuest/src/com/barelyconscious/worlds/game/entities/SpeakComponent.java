package com.barelyconscious.worlds.game.entities;

import com.barelyconscious.worlds.common.shape.Box;
import com.barelyconscious.worlds.entity.Actor;
import com.barelyconscious.worlds.entity.components.Component;
import com.barelyconscious.worlds.entity.components.MouseListenerComponent;
import com.barelyconscious.worlds.game.GameInstance;

import java.awt.event.MouseEvent;

public class SpeakComponent extends MouseListenerComponent {

    private final String speech;

    public SpeakComponent(Actor parent, Box listenerBounds, String speech) {
        super(parent, listenerBounds);
        this.speech = speech;
    }


    @Override
    public boolean onMouseClicked(MouseEvent e) {
        super.onMouseClicked(e);
        GameInstance.log(getParent().getName() + " says: " + speech);
        return false;
    }
}
