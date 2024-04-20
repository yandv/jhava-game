package br.ufrrj.game.engine.sprite;

import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpriteAtlas {

    private int width;
    private int height;

    public static SpriteAtlas loadFromJson(JsonObject jsonObject) {
        return new SpriteAtlas(
                jsonObject.get("width").getAsInt(),
                jsonObject.get("height").getAsInt());
    }

}
