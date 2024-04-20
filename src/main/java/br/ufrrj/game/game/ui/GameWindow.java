package br.ufrrj.game.game.ui;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GamePanel());
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        requestFocus();
    }

}
