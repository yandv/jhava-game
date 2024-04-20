package br.ufrrj.game.game.ui.inputs;

import java.awt.event.MouseEvent;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.game.ui.events.mouse.MouseMoveEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        CommonGame.getInstance().getEventManager().callEvent(new MouseMoveEvent(e));
    }

}
