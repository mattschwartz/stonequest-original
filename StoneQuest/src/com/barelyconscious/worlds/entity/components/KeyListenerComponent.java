package com.barelyconscious.worlds.entity.components;

import com.barelyconscious.worlds.engine.input.KeyInputHandler;
import com.barelyconscious.worlds.entity.Actor;

import java.awt.event.KeyEvent;
import java.util.function.Function;

public class KeyListenerComponent extends Component {

    private final Character keyCharTrigger;
    private final Integer keyCodeTrigger;
    private final Function<KeyEvent, Void> callback;

    public KeyListenerComponent(final Actor parent, final char keyCharTrigger, final Function<KeyEvent, Void> callback) {
        super(parent);
        KeyInputHandler.instance().delegateOnKeyPressed.bindDelegate(this::onKeyPressed);
        this.keyCharTrigger = keyCharTrigger;
        this.keyCodeTrigger = null;
        this.callback = callback;
    }

    public KeyListenerComponent(final Actor parent, final int keyCodeTrigger, final Function<KeyEvent, Void> callback) {
        super(parent);
        KeyInputHandler.instance().delegateOnKeyPressed.bindDelegate(this::onKeyPressed);
        this.keyCharTrigger = null;
        this.keyCodeTrigger = keyCodeTrigger;
        this.callback = callback;
    }

    private Void onKeyPressed(final KeyEvent keyEvent) {
        if ((keyCharTrigger != null && keyEvent.getKeyChar() == keyCharTrigger)
            || (keyCodeTrigger != null && keyEvent.getKeyCode() == keyCodeTrigger)) {
            callback.apply(keyEvent);
        }
        return null;
    }
}
