package br.ufrrj.game.engine.packet.impl.server;

import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.PacketType;
import lombok.Getter;

@Getter
public class UserConnect extends Packet {

    private String userName;

    public UserConnect(String userName) {
        super(PacketType.SERVER_USER_CONNECT);
        this.userName = userName;
    }


}
