package br.ufrrj.game.engine.sprite;

public class SpriteNotFoundException extends RuntimeException {

    public SpriteNotFoundException(String spriteName) {
        super("The sprite " + spriteName + " was not found!");
    }

}
