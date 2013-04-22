package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.exception.CannotDeleteException;
import com.blstream.patronage.ctf.common.exception.NotFoundException;
import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.model.GameStatusType;
import com.blstream.patronage.ctf.repository.GameRepository;
import com.blstream.patronage.ctf.service.GameService;
import org.springframework.util.Assert;

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

    @Override
    public void delete(String id) {
        Assert.notNull(id, "Game id couldn't be null");

        if (!repository.exists(id))
            throw new NotFoundException(String.format("Game with id: %s doesn't exists.", id));

        Game game = findById(id);
        Assert.notNull(game);

        if (GameStatusType.IN_PROGRESS.equals(game.getStatus()))
            throw new CannotDeleteException(String.format("Game: %s in progress."));

        repository.delete(id);
    }
}
