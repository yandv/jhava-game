package br.ufrrj.game.engine.packet.impl.keepalive;

import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.PacketType;

public class KeepAliveRequest extends Packet {

    public KeepAliveRequest() {
        super(PacketType.KEEP_ALIVE_REQUEST);
    }
    
}
