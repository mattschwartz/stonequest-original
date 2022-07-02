package com.barelyconscious.game.entity.gui;

import com.barelyconscious.game.entity.Actor;
import com.barelyconscious.game.entity.EventArgs;
import com.barelyconscious.game.entity.components.Component;
import com.barelyconscious.game.entity.graphics.RenderContext;
import com.barelyconscious.game.entity.graphics.Screen;
import com.barelyconscious.game.shape.Box;
import com.barelyconscious.game.shape.Vector;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * An actor that controls drawing Widgets to the user interface.
 * <p>
 * todo(p0) - should probably not be an actor
 */
public class GuiCanvas extends Actor {

    @Getter
    private int width;
    @Getter
    private int height;

    private final List<Widget> widgets;

    public GuiCanvas(final Screen screen) {
        super("GuiCanvas", Vector.ZERO);

        this.width = screen.getWidth();
        this.height = screen.getHeight();
        this.widgets = new ArrayList<>();

        addComponent(new GuiRenderComponent(this));

        screen.onResize.bindDelegate((e) -> resize(e.newWidth, e.newHeight));
    }

    public Void resize(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.widgets.forEach(t -> t.resize(new Box(0, width, 0, height)));

        return null;
    }

    public void addWidget(final Widget widget) {
        widgets.add(widget);
        widget.resize(new Box(0, width, 0, height));
    }

    private class GuiRenderComponent extends Component {

        public GuiRenderComponent(final Actor parent) {
            super(parent);
        }

        @Override
        public void guiRender(EventArgs eventArgs, RenderContext renderContext) {
            widgets.stream().filter(Widget::isEnabled).forEach(t -> t.render(eventArgs, renderContext));
        }
    }
}
