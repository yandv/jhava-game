package br.ufrrj.game.game.world;

import br.ufrrj.game.engine.sprite.Sprite;
import br.ufrrj.game.engine.sprite.SpriteLoader;
import lombok.Getter;

@Getter
public class World {

    private Sprite sprite;

    public World(String worldName) {
        this.sprite = SpriteLoader.loadSprite("game/world/" + worldName);
    }

}
