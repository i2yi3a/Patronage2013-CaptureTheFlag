package com.blstream.patronage.ctf.web.ui;

import java.util.ArrayList;

/**
 * User: mkr
 * Date: 6/3/13
 */
public class PlayerListUI {

    public Iterable<String> players;

    public Iterable<String> getPlayers() {
        if (players == null)
            players = new ArrayList<String>();
        return players;
    }

    public void setPlayers(Iterable<String> players) {
        this.players = players;
    }
}
