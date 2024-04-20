package br.ufrrj.game.game.ui.events.mouse.button;

import java.awt.event.MouseEvent;

import br.ufrrj.game.game.ui.events.mouse.MouseClickEvent;
import br.ufrrj.game.game.ui.screen.components.Button;
import lombok.Getter;

@Getter
public class MouseButtonClickEvent extends MouseClickEvent {

    private Button button;

    public MouseButtonClickEvent(MouseEvent mouseEvent, Button button) {
        super(mouseEvent);
        this.button = button;
    }

}
