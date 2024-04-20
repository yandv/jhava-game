package br.ufrrj.game.game.ui.screen.components;

import java.awt.Graphics;

import br.ufrrj.game.CommonConst;
import br.ufrrj.game.engine.sprite.Sprite;
import br.ufrrj.game.engine.sprite.SpriteLoader;
import br.ufrrj.game.game.ui.screen.ScreenComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Background implements ScreenComponent {

    private Sprite backgroundSprite;

    public Background(String spriteName) {
        this.backgroundSprite = SpriteLoader.loadSprite(spriteName);
    }

    @Override
    public void render(Graphics graphics) {
        var backgroundImage = backgroundSprite.getImage();
        graphics.drawImage(backgroundImage, CommonConst.GAME_WIDTH / 2 - backgroundImage.getWidth() / 2, (int) 45,
                backgroundImage.getWidth(), backgroundImage.getHeight(), null);
    }

}
