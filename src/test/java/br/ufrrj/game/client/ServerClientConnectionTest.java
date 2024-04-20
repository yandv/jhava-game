package br.ufrrj.game.client;

import java.io.IOException;

import br.ufrrj.game.game.client.GameClient;
import br.ufrrj.game.server.impl.GameServerCommon;

public class ServerClientConnectionTest {

    public static void main(String[] args) throws IOException {
        GameServerCommon gameServerCommon = new GameServerCommon();

        int serverPort = gameServerCommon.startServer();

        if (serverPort > 0) {
            System.out.println("Server started on port " + serverPort);
            GameClient gameClient = new GameClient("yandv");

            gameClient.startClient("localhost", serverPort);
        }
    }
}
