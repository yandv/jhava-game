package br.ufrrj.game.engine.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Sprite {

    private BufferedImage image;
    private SpriteAtlas atlas;

    public int getWidth() {
        return atlas.getWidth();
    }

    public int getHeight() {
        return atlas.getHeight();
    }

    public BufferedImage getSubimage(int row, int col) {
        return image.getSubimage(col * getWidth(), row * getHeight(), getWidth(), getHeight());
    }

    public void drawImage(Graphics g, int row, int col, int x, int y) {
        g.drawImage(getSubimage(row, col), x, y, getWidth(), getHeight(), null);
    }

}
