package com.blstream.patronage.ctf.web.ui;

import java.util.ArrayList;

/**
 * User: mkr
 * Date: 6/3/13
 */
public class GameListUI {

    private Iterable<GameUI> games;

    public Iterable<GameUI> getGames() {
        if (games == null)
            games = new ArrayList<GameUI>();
        return games;
    }

    public void setGames(Iterable<GameUI> games) {
        this.games = games;
    }
}
