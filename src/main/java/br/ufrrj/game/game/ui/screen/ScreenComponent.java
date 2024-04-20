package br.ufrrj.game.game.ui.screen;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public interface ScreenComponent {

    default Rectangle getBounds() {
        return null;
    }

    default boolean isCurrentScreen() {
        return true;
    }

    default boolean isInside(MouseEvent event) {
        if (!isCurrentScreen()) return false;
        if (getBounds() == null) return false;
        return getBounds().contains(event.getPoint());
    }

    default void onComponentMount() {
    }

    default void onComponentUnmount() {
    }

    void render(Graphics graphics);

}
