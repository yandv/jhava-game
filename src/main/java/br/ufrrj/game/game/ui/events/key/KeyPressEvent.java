package br.ufrrj.game.game.ui.events.key;

import java.awt.event.KeyEvent;

import br.ufrrj.game.engine.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyPressEvent extends Event {

    private KeyEvent keyEvent;

}
