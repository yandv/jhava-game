package br.ufrrj.game.game.world;

import br.ufrrj.game.game.world.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Block {

    private Location location;
    private Material material;

    public Block(Location location) {
        this(location, Material.AIR);
    }

}
