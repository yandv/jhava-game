package br.ufrrj.game.game.world;

import java.util.Map;

import lombok.Getter;

@Getter
public enum Material {

    AIR(0),

    WHITE_GLASS(20), GREEN_GLASS(20, 1);

    private int id;
    private short durability;

    Material(int id, int durability) {
        this.id = id;
        this.durability = (short) durability;
    }

    Material(int id) {
        this(id, 0);
    }

    private static Map<Integer, Material> materialMap;
    private static Map<String, Material> materialNameMap;

    static {
        for (Material material : values()) {
            if (!materialMap.containsKey(material.getId())) materialMap.put(material.getId(), material);
            materialNameMap.put(material.name(), material);
        }
    }

    public static Material getById(int id) {
        return materialMap.get(id);
    }

    public static Material getByName(String name) {
        return materialNameMap.get(name.toUpperCase());
    }

}
