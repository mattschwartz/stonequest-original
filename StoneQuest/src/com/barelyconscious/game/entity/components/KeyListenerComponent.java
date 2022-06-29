package com.barelyconscious.game.entity.components;

import com.barelyconscious.game.entity.*;
import com.barelyconscious.game.entity.input.*;

import java.awt.event.*;
import java.util.function.*;

public class KeyListenerComponent extends Component {

    private final Character keyCharTrigger;
    private final Integer keyCodeTrigger;
    private final Function<KeyEvent, Void> callback;

    public KeyListenerComponent(final Actor parent, final char keyCharTrigger, final Function<KeyEvent, Void> callback) {
        super(parent);
        KeyInputHandler.instance().onKeyPressed.bindDelegate(this::onKeyPressed);
        this.keyCharTrigger = keyCharTrigger;
        this.keyCodeTrigger = null;
        this.callback = callback;
    }

    public KeyListenerComponent(final Actor parent, final int keyCodeTrigger, final Function<KeyEvent, Void> callback) {
        super(parent);
        KeyInputHandler.instance().onKeyPressed.bindDelegate(this::onKeyPressed);
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
