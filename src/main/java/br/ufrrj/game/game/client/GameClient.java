package br.ufrrj.game.game.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.UUID;

import com.google.common.base.Charsets;

import br.ufrrj.game.CommonConst;
import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.impl.Handshake;
import br.ufrrj.game.engine.packet.impl.keepalive.KeepAliveResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameClient {

    private SocketChannel socketChannel;

    private String userName;
    private UUID uniqueId;

    private boolean running;

    public GameClient(String userName) {
        this.userName = userName;
        this.uniqueId = UUID.nameUUIDFromBytes(("OfflinePlayer:" + userName).getBytes(Charsets.UTF_8));
    }

    public void startClient(String hostname, int serverPort) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel = SocketChannel.open(new InetSocketAddress(hostname, serverPort));
                    running = true;
                    sendPacket(new Handshake(userName, uniqueId));

                    while (running) {
                        ByteBuffer buffer = ByteBuffer.allocate(CommonConst.BUFFER_CAPACITY);
                        int read = socketChannel.read(buffer);

                        if (read == -1) {
                            running = false;
                            System.out.println("The client has been disconnected from the server.");
                            break;
                        }

                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);

                        Packet packet = Packet.fromBytes(bytes);

                        if (packet == null) {
                            System.out.println("Packet is null");
                            continue;
                        }

                        dispatchPacket(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        System.out.println("Client started");
    }

    private void dispatchPacket(Packet packet) {
        switch (packet.getPacketType()) {
        case SERVER_USER_CONNECT -> {
            System.out.println("User connected to the server");
        }
        case KEEP_ALIVE_REQUEST -> {
            sendPacket(new KeepAliveResponse());
        }
        default -> {
            System.out.println("Unknown packet type");
        }
        }
    }

    public <T extends Packet> T sendPacket(T packet) {
        if (!running) return packet;
        try {
            byte[] packetBytes = packet.toBytes();
            ByteBuffer packetBuffer = ByteBuffer.wrap(packetBytes);
            socketChannel.write(packetBuffer);
            return packet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
