package br.ufrrj.game.game.ui.events.mouse;

import java.awt.event.MouseEvent;

import br.ufrrj.game.engine.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MouseClickEvent extends Event {

    private MouseEvent mouseEvent;

}
