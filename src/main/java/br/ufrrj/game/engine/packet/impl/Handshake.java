package br.ufrrj.game.engine.packet.impl;

import java.util.UUID;

import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.PacketType;
import lombok.Getter;

@Getter
public class Handshake extends Packet {

    private String userName;
    private UUID uniqueId;

    public Handshake(String userName, UUID uniqueId) {
        super(PacketType.HANDSHAKE);
        this.userName = userName;
        this.uniqueId = uniqueId;
    }

}
