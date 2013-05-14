package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.exception.CannotDeleteException;
import com.blstream.patronage.ctf.common.exception.NotFoundException;
import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.model.GameStatusType;
import com.blstream.patronage.ctf.repository.GameRepository;
import com.blstream.patronage.ctf.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@Named("gameService")
public class GameServiceImpl extends CrudServiceImpl<Game, String, GameRepository> implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

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
        if(myGames != null && myGames) {
            query.addCriteria(Criteria.where("owner").is(currentUser));
        }

        return mongoTemplate.find(query, Game.class);
    }

    @Override
    public List<Game> findNearest(Double[] latLng, Double range, GameStatusType status) {

        if (logger.isDebugEnabled())
            logger.debug("---- findNearest");

        double lat = latLng[0];
        double lng = latLng[1];
        double rangeInMeters;

        if (range == null)
            rangeInMeters = (20000 * 0.01) / 1000;
        else rangeInMeters = (range * 0.01) / 1000;


        Query query = new Query();

        if (status != null)
            query.addCriteria(Criteria.where("status").is(status.name()));

        List<Game> games = new ArrayList<Game>();

        NearQuery nearQuery = NearQuery.near(lat, lng).maxDistance(rangeInMeters).num(20).query(query);
        GeoResults<Game> result = mongoTemplate.geoNear(nearQuery, Game.class);

        if (result != null) {
            for (GeoResult<Game> geoResult : result.getContent()) {
                Game game = geoResult.getContent();
                games.add(game);
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info(String.format("Game list size: %d", games.size()));
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /findNearest");

        return games;
    }
}
