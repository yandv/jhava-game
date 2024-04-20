package br.ufrrj.game.server.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import br.ufrrj.game.CommonConst;
import br.ufrrj.game.CommonGame;
import br.ufrrj.game.engine.packet.Packet;
import br.ufrrj.game.engine.packet.impl.Handshake;
import br.ufrrj.game.engine.packet.impl.keepalive.KeepAliveRequest;
import br.ufrrj.game.engine.packet.impl.server.UserConnect;
import br.ufrrj.game.engine.utils.IpUtils;
import br.ufrrj.game.server.GameServer;
import br.ufrrj.game.server.client.ClientStatus;
import br.ufrrj.game.server.client.GameServerClient;
import lombok.Getter;

@Getter
public class GameServerCommon implements GameServer {

    private ServerSocketChannel serverSocketChannel;
    private Map<String, GameServerClient> clients;

    private boolean running;

    public GameServerCommon() {
        this.clients = new ConcurrentHashMap<>();

        this.running = false;
    }

    public int startServer() throws IOException {
        return startServer(CommonConst.SERVER_PORT);
    }

    public int startServer(int serverPort) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, serverSocketChannel.validOps());
        this.running = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteBuffer buffer = ByteBuffer.allocate(CommonConst.BUFFER_CAPACITY);

                startKeepAliveThread();

                while (running) {
                    try {
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = selectedKeys.iterator();
                        while (iter.hasNext()) {
                            SelectionKey key = iter.next();

                            switch (key.readyOps()) {
                            case SelectionKey.OP_ACCEPT -> {
                                SocketChannel socketChannel = serverSocketChannel.accept();
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selector, SelectionKey.OP_READ);

                                String address = IpUtils.getIpAddress(socketChannel);

                                clients.put(address, new GameServerClient("Unknown", socketChannel));
                                log("The client " + address + " established a connection with the server");
                            }
                            case SelectionKey.OP_READ -> {
                                SocketChannel socketChannel = (SocketChannel) key.channel();
                                GameServerClient gameServerClient = clients.get(IpUtils.getIpAddress(socketChannel));

                                try {
                                    buffer.clear();
                                    int read = socketChannel.read(buffer);

                                    if (read == -1) {
                                        clients.remove(gameServerClient.getFullHostString());
                                        socketChannel.close();
                                        key.cancel();
                                        log("The client " + gameServerClient.getFullHostString()
                                                + " disconnected from the server");
                                        iter.remove();
                                        continue;
                                    }

                                    buffer.flip();
                                    byte[] bytes = new byte[buffer.remaining()];
                                    buffer.get(bytes);

                                    Packet packet = Packet.fromBytes(bytes);

                                    if (packet == null) {
                                        log("The client " + gameServerClient.getFullHostString()
                                                + " sent an invalid packet.");
                                        iter.remove();
                                        continue;
                                    }

                                    dispatchPacket(gameServerClient, packet);
                                } catch (IOException e) {
                                    clients.remove(gameServerClient.getFullHostString());
                                    socketChannel.close();
                                    key.cancel();
                                    log("The client " + gameServerClient.getFullHostString()
                                            + " disconnected from the server");
                                }
                            }
                            }

                            iter.remove();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }).start();
        return serverPort;
    }

    private void startKeepAliveThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Iterator<GameServerClient> iterator = clients.values().iterator();

                    while (iterator.hasNext()) {
                        GameServerClient gameServerClient = iterator.next();

                        switch (gameServerClient.getClientStatus()) {
                        case READY -> {
                            if (!gameServerClient.isCurrentAlive() && System.currentTimeMillis()
                                    - gameServerClient.getLastKeepAlive() >= CommonConst.MAX_KEEP_ALIVE_WAIT) {
                                log("The client " + gameServerClient.getFullHostString() + " lost connection.");
                                gameServerClient.stopConnection();
                                iterator.remove();
                            } else if (gameServerClient.isNeedingKeepAlive()) {
                                gameServerClient.sendPacket(new KeepAliveRequest());
                                gameServerClient.setCurrentAlive(false);
                            }
                        }
                        case HANDSHAKE -> {
                            if (System.currentTimeMillis() - gameServerClient.getLastKeepAlive() >= 1000) {
                                gameServerClient.stopConnection();
                                log("The client " + gameServerClient.getFullHostString()
                                        + " did not send a handskae packet in time. Disconnected!");
                                iterator.remove();
                            }
                        }
                        case DISCONNECTED -> {
                            iterator.remove();
                            log("The client " + gameServerClient.getFullHostString()
                                    + " disconnected from the server.");
                        }
                        }
                    }

                    try {
                        Thread.sleep(CommonConst.KEEP_ALIVE_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void log(String log) {
        CommonGame.getInstance().log(Level.INFO, log);
    }

    public <T extends Packet> void broadcastPacket(T packet) {
        clients.values().stream().filter(GameServerClient::isReady).forEach(client -> client.sendPacket(packet));
    }

    public void dispatchPacket(GameServerClient gameServerClient, Packet packet) {
        switch (packet.getPacketType()) {
        case HANDSHAKE -> {
            Handshake handshake = (Handshake) packet;
            gameServerClient.setUserName(handshake.getUserName());
            gameServerClient.setClientStatus(ClientStatus.READY);
            gameServerClient.setCurrentAlive(true);
            log("The client " + gameServerClient.getRealName() + " handshake with the server.");
            broadcastPacket(new UserConnect(handshake.getUserName()));
        }
        case KEEP_ALIVE_RESPONSE -> {
            gameServerClient.setLastKeepAlive(System.currentTimeMillis());
            gameServerClient.setCurrentAlive(true);
        }
        default -> {
        }
        }
    }

}
