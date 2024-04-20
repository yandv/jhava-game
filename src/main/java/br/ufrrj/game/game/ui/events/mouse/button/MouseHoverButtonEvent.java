package br.ufrrj.game.game.ui.events.mouse.button;

import java.awt.event.MouseEvent;

import br.ufrrj.game.game.ui.events.mouse.MouseClickEvent;
import br.ufrrj.game.game.ui.screen.components.Button;
import lombok.Getter;

@Getter
public class MouseHoverButtonEvent extends MouseClickEvent {

    private Button button;
    private boolean hovering;

    public MouseHoverButtonEvent(MouseEvent mouseEvent, Button button, boolean hovering) {
        super(mouseEvent);
        this.button = button;
        this.hovering = hovering;
    }

}
