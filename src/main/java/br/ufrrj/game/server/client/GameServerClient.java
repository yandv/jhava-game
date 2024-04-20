package br.ufrrj.game.server.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import br.ufrrj.game.CommonConst;
import br.ufrrj.game.engine.packet.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameServerClient {

    @Setter
    private String userName;

    private String address;
    private int port;

    private SocketChannel socketChannel;

    @Setter
    private ClientStatus clientStatus;
    @Setter
    private long lastKeepAlive;
    @Setter
    private boolean currentAlive;

    public GameServerClient(String userName, SocketChannel socketChannel) throws IOException {
        this.userName = userName;

        if (socketChannel.getRemoteAddress() instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
            this.address = inetSocketAddress.getAddress().getHostAddress();
            this.port = inetSocketAddress.getPort();
        } else {
            throw new IllegalArgumentException("Invalid remote address");
        }

        this.socketChannel = socketChannel;
        this.clientStatus = ClientStatus.HANDSHAKE;
        this.lastKeepAlive = System.currentTimeMillis();
    }

    public String getRealName() {
        return getFullHostString() + " (" + userName + ")";
    }

    public void stopConnection() {
        try {
            socketChannel.close();
            setClientStatus(ClientStatus.DISCONNECTED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFullHostString() {
        return address + ":" + port;
    }

    public boolean isReady() {
        return clientStatus == ClientStatus.READY;
    }

    public <T extends Packet> T sendPacket(T packet) {
        if (clientStatus == ClientStatus.DISCONNECTED)
            return packet;

        try {
            socketChannel.write(ByteBuffer.wrap(packet.toBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }

    public boolean isNeedingKeepAlive() {
        return (System.currentTimeMillis() - getLastKeepAlive()) >= CommonConst.KEEP_ALIVE_INTERVAL;
    }

}
