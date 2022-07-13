package com.barelyconscious.game.entity;

import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.input.KeyInputHandler;
import com.barelyconscious.game.shape.Vector;

import java.awt.event.KeyEvent;

public class CameraActor extends Actor {

    public CameraActor(final Camera camera) {
        super(camera.transform);

        final TranslateMoveComponent cameraMoveComponent = new TranslateMoveComponent(this, camera);
        addComponent(cameraMoveComponent);

        KeyInputHandler.instance().onKeyPressed.bindDelegate(keyEvent -> {
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                cameraMoveComponent.translate(Vector.RIGHT.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                cameraMoveComponent.translate(Vector.LEFT.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                cameraMoveComponent.translate(Vector.UP.multiply(100f));
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                cameraMoveComponent.translate(Vector.DOWN.multiply(100f));
            }
            return null;
        });
    }

    private static class TranslateMoveComponent extends Component {
        private final Camera camera;

        public TranslateMoveComponent(final Actor actor, final Camera camera) {
            super(actor);
            this.camera = camera;
        }

        private Vector desiredLocation = getParent().transform;

        public void translate(final Vector delta) {
            desiredLocation = desiredLocation.plus(delta);
        }

        @Override
        public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
            camera.transform = desiredLocation;
        }
    }
}
