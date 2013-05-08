package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.exception.CannotDeleteException;
import com.blstream.patronage.ctf.common.exception.NotFoundException;
import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.model.GameStatusType;
import com.blstream.patronage.ctf.repository.GameRepository;
import com.blstream.patronage.ctf.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named("gameService")
public class GameServiceImpl extends CrudServiceImpl<Game, String, GameRepository> implements GameService {

    @Inject
    @Named("gameRepository")
    @Override
    public void setRepository(GameRepository repository) {
        super.repository = repository;
    }

    @Qualifier("mongoTemplate")
    @Autowired
    private MongoTemplate mongoTemplate;

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

    public List<Game> findByCriteria(String name, String status, Boolean myGames, String currentUser) {


        Query query = new Query();

        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name));
        }
        if (status != null && !status.isEmpty()) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        if(myGames) {
            query.addCriteria(Criteria.where("owner").is(currentUser));
        }

        return mongoTemplate.find(query, Game.class);

    }
}
