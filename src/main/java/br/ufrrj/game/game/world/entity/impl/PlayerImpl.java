package br.ufrrj.game.game.world.entity.impl;

import java.util.UUID;

import com.google.common.base.Charsets;

import br.ufrrj.game.game.world.entity.EntityType;
import br.ufrrj.game.game.world.entity.Player;

public class PlayerImpl extends LivingEntityImpl implements Player {

    public PlayerImpl(String playerName) {
        super(UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8)), EntityType.PLAYER, 20);
    }

}
