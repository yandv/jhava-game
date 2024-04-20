package br.ufrrj.game;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrrj.game.engine.manager.EventManager;
import br.ufrrj.game.game.GameSettings;
import br.ufrrj.game.game.ui.GameWindow;
import br.ufrrj.game.game.ui.screen.screens.GameScreen;
import lombok.Getter;

@Getter
public class CommonGame implements Runnable {

    @Getter
    private static CommonGame instance;

    public static void main(String[] args) {
        var argList = List.of(args);

        boolean isDebugMode = argList.stream().anyMatch(s -> s.equalsIgnoreCase("--debug"));
        int maxFps = argList.stream().filter(s -> s.startsWith("--max-fps=")).map(s -> s.replace("--max-fps=", ""))
                .map(Integer::parseInt).findFirst().orElse(-1);
        int serverTickRate = argList.stream().filter(s -> s.startsWith("--server-ticks="))
                .map(s -> s.replace("--server-ticks=", "")).map(Integer::parseInt).findFirst()
                .orElse(CommonConst.DEFAULT_SERVER_TICK_RATE);

        var gameSettings = new GameSettings(isDebugMode, maxFps, serverTickRate);

        new CommonGame(gameSettings);
    }

    private Logger logger;

    private EventManager eventManager;

    private GameScreen currentScreen;
    private GameWindow gameWindow;

    private long currentPing;

    private long currentFps;
    private boolean running;

    private GameSettings gameSettings;

    public CommonGame(GameSettings gameSettings) {
        instance = this;
        this.logger = Logger.getGlobal();

        this.gameSettings = gameSettings;

        log("Settings: " + gameSettings.toString());

        log("The game is starting...");

        this.eventManager = new EventManager();

        this.currentScreen = GameScreen.SPLASH_SCREEN;
        GameScreen.SPLASH_SCREEN.getScreen().onComponentMount();
        this.gameWindow = new GameWindow();

        this.running = true;
        new Thread(this).start();
    }

    public void setCurrentScreen(GameScreen gameScreen) {
        this.currentScreen.getScreen().onComponentUnmount();
        this.currentScreen = gameScreen;
        this.currentScreen.getScreen().onComponentMount();
    }

    public void shutdown() {
        System.exit(1);
        running = false;
    }

    @Override
    public void run() {
        long previousTime = System.currentTimeMillis();
        long lastFrameTick = System.currentTimeMillis();
        long lastTick = System.currentTimeMillis();

        int frames = 0;

        while (running) {
            long now = System.currentTimeMillis();

            if (now - previousTime >= 1000) {
                previousTime = now;
                this.currentFps = gameSettings.getMaxFps() == -1 ? frames : Math.min(frames, gameSettings.getMaxFps());
                frames = 0;
            }

            if (now - lastFrameTick >= 1000 / gameSettings.getMaxFps()) {
                lastFrameTick = System.currentTimeMillis();
                frames++;
                repaint();
            }

            if (now - lastTick >= 1000 / gameSettings.getServerTickRate()) {
                lastTick = System.currentTimeMillis();
                onTick();
            }
        }
    }

    public void repaint() {
        gameWindow.repaint();
    }

    public void onTick() {

    }

    public void log(String string) {
        log(Level.INFO, string);
    }

    public void log(Level info, String string) {
        if (this.gameSettings.isDebugMode()) {
            logger.log(info, "[GAME-THREAD] " + string);
        }
    }

}
