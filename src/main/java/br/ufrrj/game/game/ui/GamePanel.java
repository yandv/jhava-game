package br.ufrrj.game.game.ui;

import java.awt.Graphics;

import javax.swing.JPanel;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.CommonConst;
import br.ufrrj.game.game.ui.inputs.KeyboardInputListener;
import br.ufrrj.game.game.ui.inputs.MouseInputListener;
import br.ufrrj.game.game.ui.inputs.MouseMotionListener;

public class GamePanel extends JPanel {

    public GamePanel() {
        setPreferredSize(new java.awt.Dimension(CommonGame.getInstance().getCurrentScreen().getScreen().getWidth(),
                CommonGame.getInstance().getCurrentScreen().getScreen().getHeight()));

        addKeyListener(new KeyboardInputListener());
        addMouseListener(new MouseInputListener());
        addMouseMotionListener(new MouseMotionListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        CommonGame.getInstance().getCurrentScreen().getScreen().render(g);
        if (CommonGame.getInstance().getGameSettings().isDebugMode()) {
            g.drawString(
                    "Fps " + CommonGame.getInstance().getCurrentFps() + " / "
                            + CommonGame.getInstance().getGameSettings().getMaxFps(),
                    CommonConst.GAME_WIDTH - 100, CommonConst.GAME_HEIGHT - 15);
            g.drawString("Latency " + CommonGame.getInstance().getCurrentPing() + "ms", CommonConst.GAME_WIDTH - 100,
                    CommonConst.GAME_HEIGHT - 5);
        }
    }

}
