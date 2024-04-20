package br.ufrrj.game.game.ui.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.game.ui.events.mouse.MouseClickEvent;

public class MouseInputListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        CommonGame.getInstance().getEventManager().callEvent(new MouseClickEvent(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
