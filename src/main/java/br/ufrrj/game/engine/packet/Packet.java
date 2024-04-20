package br.ufrrj.game.engine.packet;

import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufrrj.game.CommonConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Packet {

    private final PacketType packetType;

    private String packetName = this.getClass().getSimpleName();

    public byte[] toBytes() {
        return CommonConst.GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    public static Packet fromBytes(byte[] bytes) {
        String string = new String(bytes, StandardCharsets.UTF_8);

        JsonObject jsonObject;

        try {
            jsonObject = JsonParser.parseString(string).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (!jsonObject.has("packetType")) {
            throw new IllegalArgumentException("Packet does not have a packetType field");
        }

        PacketType packetType = PacketType.valueOf(jsonObject.get("packetType").getAsString());

        return CommonConst.GSON.fromJson(jsonObject, packetType.getClazz());
    }

}
