package br.ufrrj.game.server;

import java.io.IOException;

import br.ufrrj.game.CommonConst;

public interface GameServer {

    default int startServer() throws IOException {
        return startServer(CommonConst.SERVER_PORT);
    }

    int startServer(int serverPort) throws IOException;

}
