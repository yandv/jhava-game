package br.ufrrj.game.game.ui.screen.screens;

import br.ufrrj.game.game.ui.screen.Screen;
import lombok.Getter;

@Getter
public enum GameScreen {

    SPLASH_SCREEN, SELECT_MODE_SCREEN, OPTIONS_SCREEN;

    private Screen screen;

    static {
        SPLASH_SCREEN.screen = new br.ufrrj.game.game.ui.screen.screens.splash.SplashScreen();
        SELECT_MODE_SCREEN.screen = new br.ufrrj.game.game.ui.screen.screens.play.SelectModeScreen();
        OPTIONS_SCREEN.screen = new br.ufrrj.game.game.ui.screen.screens.options.OptionsScreen();
    }
}
