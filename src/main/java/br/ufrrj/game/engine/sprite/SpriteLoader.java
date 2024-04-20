package br.ufrrj.game.engine.sprite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import br.ufrrj.game.engine.utils.JsonBuilder;

public class SpriteLoader {

    public static final String BUTTONS = "";

    public static Sprite loadSprite(String spriteName) {
        try {
            URL spriteImageUrl = SpriteLoader.class.getClassLoader().getResource("assets/" + spriteName + ".png");
            URL spriteAtlasUrl = SpriteLoader.class.getClassLoader()
                    .getResource("assets/" + spriteName + ".atlas.json");

            if (spriteImageUrl == null) {
                throw new SpriteNotFoundException(spriteName);
            }

            var image = ImageIO.read(new File(spriteImageUrl.getFile()));
            var jsonObject = spriteAtlasUrl == null
                    ? JsonBuilder.createObjectBuilder().addProperty("width", image.getWidth())
                            .addProperty("height", image.getHeight()).build()
                    : JsonParser.parseReader(new JsonReader(new FileReader(new File(spriteAtlasUrl.getFile()))))
                            .getAsJsonObject();

            return new Sprite(image, SpriteAtlas.loadFromJson(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
