package br.ufrrj.game.game.world.entity;

import br.ufrrj.game.engine.sprite.Sprite;
import br.ufrrj.game.engine.sprite.SpriteLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {

    PLAYER("player");

    private final Sprite sprite;

    EntityType(String spriteName) {
        this.sprite = SpriteLoader.loadSprite("game/entity/" + spriteName);
    }

}
