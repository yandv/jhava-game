package br.ufrrj.game.engine.packet;

import br.ufrrj.game.engine.packet.impl.Handshake;
import br.ufrrj.game.engine.packet.impl.keepalive.KeepAliveRequest;
import br.ufrrj.game.engine.packet.impl.keepalive.KeepAliveResponse;
import br.ufrrj.game.engine.packet.impl.server.UserConnect;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PacketType {

    HANDSHAKE(Handshake.class),

    KEEP_ALIVE_REQUEST(KeepAliveRequest.class), KEEP_ALIVE_RESPONSE(KeepAliveResponse.class),
    
    SERVER_USER_CONNECT(UserConnect.class);

    private Class<? extends Packet> clazz;

}
