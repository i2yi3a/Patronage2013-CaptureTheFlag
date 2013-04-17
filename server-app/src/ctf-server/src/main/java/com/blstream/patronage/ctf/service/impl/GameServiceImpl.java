package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.repository.GameRepository;
import com.blstream.patronage.ctf.service.GameService;

import javax.inject.Inject;
import javax.inject.Named;


@Named("gameService")
public class GameServiceImpl extends CrudServiceImpl<Game, String, GameRepository> implements GameService {

    @Inject
    @Named("gameRepository")
    @Override
    public void setRepository(GameRepository repository) {
        super.repository = repository;
    }
}
