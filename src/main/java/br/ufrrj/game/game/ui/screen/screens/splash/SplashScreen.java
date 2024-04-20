package br.ufrrj.game.game.ui.screen.screens.splash;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.CommonConst;
import br.ufrrj.game.game.ui.screen.Screen;
import br.ufrrj.game.game.ui.screen.components.Background;
import br.ufrrj.game.game.ui.screen.components.Button;
import br.ufrrj.game.game.ui.screen.screens.GameScreen;
import br.ufrrj.game.game.world.entity.Player;
import br.ufrrj.game.game.world.entity.impl.PlayerImpl;

public class SplashScreen extends Screen {

    public SplashScreen() {
        super(GameScreen.SPLASH_SCREEN);
        addComponent(new Background("ui/splash/background"));

        addComponent(new Button("ui/button", this, CommonConst.GAME_WIDTH / 2, 150)
                .onClick(b -> CommonGame.getInstance().setCurrentScreen(GameScreen.SELECT_MODE_SCREEN))
                .defaultButtonBehavior());

        addComponent(new Button("ui/button", this, CommonConst.GAME_WIDTH / 2, 220, 1)
                .onClick(b -> CommonGame.getInstance().setCurrentScreen(GameScreen.OPTIONS_SCREEN))
                .defaultButtonBehavior());
        addComponent(
                new Button("ui/button", this, CommonConst.GAME_WIDTH / 2, 290, 2).defaultButtonBehavior().onClick(b -> {
                    CommonGame.getInstance().shutdown();
                }));

        Player player = new PlayerImpl("yandv");

        addComponent(graphics -> {
            player.render(graphics);
        });
    }

}
