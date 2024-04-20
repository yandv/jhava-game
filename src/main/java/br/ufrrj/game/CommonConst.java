package br.ufrrj.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonConst {

    public static final Gson GSON = new GsonBuilder().create();
    public static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final long KEEP_ALIVE_INTERVAL = 1000 * 2;
    public static final long MAX_KEEP_ALIVE_WAIT = 1000 * 5;
    public static final long MAX_HANDSHAKE_WAIT = 1000 * 10;

    public static final int BUFFER_CAPACITY = 4096;

    public static final int SERVER_PORT = 3950;

    public static final int DEFAULT_SERVER_TICK_RATE = 128;

    public final static float SCALE = 2.0f;

    public final static int TILES_DEFAULT_SIZE = 16;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    /*
     * Entities
     */

    public final static double DEFAULT_ENTITY_HEALTH = 20.0D;

}
