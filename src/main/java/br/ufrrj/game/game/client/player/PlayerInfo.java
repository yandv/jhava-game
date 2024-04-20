package br.ufrrj.game.game.client.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerInfo {

    private String userName;
    private int ping;

    public PlayerInfo(String userName) {
        this.userName = userName;
        this.ping = 0;
    }

}
