package com.blstream.patronage.ctf.service;

import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.model.GameStatusType;

import java.util.List;


public interface GameService extends CrudService<Game, String> {

    List<Game> findByCriteria(String name, String status, Boolean myGames, String currentUser);

    List<Game> findNearest(Double[] latLng, Double range, GameStatusType status);
}
