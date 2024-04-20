package br.ufrrj.game.game.ui.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.game.ui.events.key.KeyPressEvent;
import br.ufrrj.game.game.ui.events.key.KeyReleaseEvent;
import br.ufrrj.game.game.ui.events.key.KeyTypeEvent;

public class KeyboardInputListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        CommonGame.getInstance().getEventManager().callEvent(new KeyPressEvent(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CommonGame.getInstance().getEventManager().callEvent(new KeyReleaseEvent(e));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        CommonGame.getInstance().getEventManager().callEvent(new KeyTypeEvent(e));
    }
}