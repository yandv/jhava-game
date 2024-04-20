package br.ufrrj.game.game.ui.screen.components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.function.Consumer;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.engine.events.EventHandler;
import br.ufrrj.game.engine.events.EventListener;
import br.ufrrj.game.engine.sprite.Sprite;
import br.ufrrj.game.engine.sprite.SpriteLoader;
import br.ufrrj.game.game.ui.events.mouse.MouseClickEvent;
import br.ufrrj.game.game.ui.events.mouse.MouseMoveEvent;
import br.ufrrj.game.game.ui.events.mouse.button.MouseHoverButtonEvent;
import br.ufrrj.game.game.ui.screen.Screen;
import br.ufrrj.game.game.ui.screen.ScreenComponent;
import lombok.Getter;
import lombok.Setter;

@EventListener
@Getter
public class Button implements ScreenComponent {

    public static Consumer<Button> EMPTY_CONSUMER = (v) -> {
    };

    private Sprite sprite;

    private int x, y;

    @Setter
    private int row, col;

    private Consumer<Button> hoverHandler = EMPTY_CONSUMER;
    private Consumer<Button> clickHandler = EMPTY_CONSUMER;

    @Setter
    private boolean hovering;
    private boolean defaultButtonBehavior;

    private Screen parentScreen;

    private Rectangle bounds;

    public Button(String spriteName, Screen parentScreen, int x, int y, int row, int col) {
        this.sprite = SpriteLoader.loadSprite(spriteName);
        this.parentScreen = parentScreen;

        this.x = x - (sprite.getAtlas().getWidth() / 2);
        this.y = y;

        this.row = row;
        this.col = col;

        this.bounds = new Rectangle(this.x, this.y, sprite.getAtlas().getWidth(), sprite.getAtlas().getHeight());
    }

    public Button(String spriteName, Screen parentScreen, int x, int y, int row) {
        this(spriteName, parentScreen, x, y, row, 0);
    }

    public Button(String spriteName, Screen parentScreen, int x, int y) {
        this(spriteName, parentScreen, x, y, 0, 0);
    }

    @Override
    public void onComponentMount() {
        setCol(0);
    }

    @EventHandler
    public void onMouseMove(MouseMoveEvent event) {
        if (isInside(event.getMouseEvent())) {
            if (isHovering()) return;

            if (defaultButtonBehavior) {
                setCol(1);
            }

            setHovering(true);
            CommonGame.getInstance().getEventManager()
                    .callEvent(new MouseHoverButtonEvent(event.getMouseEvent(), Button.this, true));
            hoverHandler.accept(Button.this);
            return;
        }

        if (!isHovering()) return;

        if (defaultButtonBehavior) {
            setCol(0);
        }

        setHovering(false);
        CommonGame.getInstance().getEventManager()
                .callEvent(new MouseHoverButtonEvent(event.getMouseEvent(), Button.this, false));
        hoverHandler.accept(Button.this);
    }

    @EventHandler
    public void onMouseClick(MouseClickEvent event) {
        if (isInside(event.getMouseEvent())) {
            if (defaultButtonBehavior) setCol(2);
            CommonGame.getInstance().getEventManager()
                    .callEvent(new MouseHoverButtonEvent(event.getMouseEvent(), Button.this, false));
            clickHandler.accept(Button.this);
        }
    }

    public Button defaultButtonBehavior() {
        this.defaultButtonBehavior = !this.defaultButtonBehavior;
        return this;
    }

    public Button onClick(Consumer<Button> clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public Button onHover(Consumer<Button> hoverHandler) {
        this.hoverHandler = hoverHandler;
        return this;
    }

    public void render(Graphics graphics) {
        this.sprite.drawImage(graphics, row, col, x, y);
    }
}
