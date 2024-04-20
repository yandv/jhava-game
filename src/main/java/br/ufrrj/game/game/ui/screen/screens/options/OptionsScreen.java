package br.ufrrj.game.game.ui.screen.screens.options;

import br.ufrrj.game.CommonGame;
import br.ufrrj.game.CommonConst;
import br.ufrrj.game.game.ui.screen.Screen;
import br.ufrrj.game.game.ui.screen.components.Button;
import br.ufrrj.game.game.ui.screen.screens.GameScreen;

public class OptionsScreen extends Screen {

    public OptionsScreen() {
        super(GameScreen.OPTIONS_SCREEN);

        addComponent(new Button("ui/button", this, CommonConst.GAME_WIDTH / 2, 150).defaultButtonBehavior());
        addComponent(new Button("ui/button", this, CommonConst.GAME_WIDTH / 2, 290, 2)
                .onClick(b -> CommonGame.getInstance().setCurrentScreen(GameScreen.SPLASH_SCREEN))
                .defaultButtonBehavior());
    }

}
