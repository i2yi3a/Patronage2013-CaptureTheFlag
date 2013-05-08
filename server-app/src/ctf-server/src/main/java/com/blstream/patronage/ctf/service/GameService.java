package com.blstream.patronage.ctf.service;

import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.Game;

import java.util.List;


public interface GameService extends CrudService<Game, String> {

    public List<Game> findByCriteria(String name, String status, Boolean myGames, String currentUser);
}
