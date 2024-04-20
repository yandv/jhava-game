package br.ufrrj.game.engine.packet.impl.keepalive;

import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.PacketType;

public class KeepAliveResponse extends Packet {

    public KeepAliveResponse() {
        super(PacketType.KEEP_ALIVE_RESPONSE);
    }
    
}
