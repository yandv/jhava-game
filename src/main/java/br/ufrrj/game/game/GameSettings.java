package br.ufrrj.game.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameSettings {

    private boolean debugMode;
    private int maxFps;
    private int serverTickRate;

    @Override
    public String toString() {
        return "GameSettings [maxFps=" + maxFps + ", serverTickRate=" + serverTickRate + "]";
    }

}
