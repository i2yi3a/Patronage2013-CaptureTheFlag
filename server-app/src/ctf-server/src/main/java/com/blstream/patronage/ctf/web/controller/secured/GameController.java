package com.blstream.patronage.ctf.web.controller.secured;

import com.blstream.patronage.ctf.common.errors.ErrorCodeType;
import com.blstream.patronage.ctf.common.web.controller.BaseRestController;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.model.GameStatusType;
import com.blstream.patronage.ctf.service.GameService;
import com.blstream.patronage.ctf.web.converter.BaseUIConverter;
import com.blstream.patronage.ctf.web.ui.GameUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.util.MyAsserts.assertNotNull;


@Controller
@RequestMapping("/api/secured/games")
public class GameController extends BaseRestController<GameUI, Game, String, GameService> {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController() {
        super(GameUI.class);
    }

    @Inject
    @Named("gameService")
    @Override
    protected void setService(GameService service) {
        super.service = service;
    }

    @Inject
    @Named("gameUIConverter")
    @Override
    protected void setConverter(BaseUIConverter<GameUI, Game, String> converter) {
        super.converter = converter;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody GameUI create(@RequestBody GameUI request) {
        if (logger.isDebugEnabled())
            logger.debug("---- create");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");

        if (logger.isInfoEnabled()) {
            logger.info(String.format("User: %s tries to create a new game: %s", currentUser, request.getName()));
        }

        request.setStatus(GameStatusType.NEW);
        request.setOwner(currentUser);

        GameUI gameUI = super.create(request);

        if (!ErrorCodeType.SUCCESS.equals(gameUI.getErrorCodeType())) {
            return gameUI;
        }

        GameUI response = new GameUI();
        response.setId(gameUI.getId());
        response.setMessage(String.format("Game with id: %s was created successfully.", response.getId()));

        if (logger.isDebugEnabled())
            logger.debug("---- /create");

        return response;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GameUI update(@PathVariable String id, @RequestBody GameUI request) {
        if (logger.isDebugEnabled())
            logger.debug("---- update");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");

        if (logger.isInfoEnabled()) {
            logger.info(String.format("User: %s tries to update an existing game: %s", currentUser, request.getName()));
        }
        GameUI response;
        Game resource = service.findById(id);
        if (GameStatusType.NEW.equals(resource.getStatus())) {
            if (currentUser.equals(resource.getOwner())) {
                super.update(id, request);
                response = createResponseErrorMessage(ErrorCodeType.SUCCESS);
                response.setMessage(String.format("Game with id: %s was updated successfully.", resource.getId()));
            } else {
                response = createResponseErrorMessage(ErrorCodeType.RESOURCE_CANNOT_BE_UPDATED, "Only game owner can update game");
            }
        } else {
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_CANNOT_BE_UPDATED, "Only New games can be updated");
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /update");

        return response;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GameUI delete(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- delete");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");
        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);
        GameUI response;

        if (logger.isInfoEnabled()) {
            logger.info(String.format("User: %s tries to delete game: %s", currentUser, resource.getName()));
        }

        if (currentUser.equals(resource.getOwner())) {
            if (resource.getStatus().equals(GameStatusType.NEW))   {
                super.delete(id);
                response = createResponseErrorMessage(ErrorCodeType.SUCCESS);
            }else {
                response = createResponseErrorMessage(ErrorCodeType.RESOURCE_CANNOT_BE_DELETED, "Only New Games can be deleted");
            }
        }else {
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_CANNOT_BE_DELETED, "Only Game Owner can delete game");
              }
        if (logger.isDebugEnabled())
            logger.debug("---- /delete");

         return response;
    }


    @RequestMapping(value = "{id}/players", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<String> players(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- players");

        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);

        List<String> players = resource.getPlayers();

        if (logger.isDebugEnabled())
            logger.debug("---- /players");

        return players;

    }

    @RequestMapping(value = "{id}/signIn", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GameUI playerSignIn(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- SignIn");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");
        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);
        List<String> players = resource.getPlayers();
        GameUI response;

        if (resource.getStatus().equals(GameStatusType.NEW) || resource.getStatus().equals(GameStatusType.IN_PROGRESS)) {
            if (resource.getPlayers().size() < resource.getPlayersMax()) {
                if (!resource.getPlayers().contains(currentUser)) {

                    players.add(currentUser);
                    GameUI gameUI = converter.convert(resource);
                    update(id, gameUI);
                    response = createResponseErrorMessage(ErrorCodeType.SUCCESS);
                } else {
                    response = createResponseErrorMessage(ErrorCodeType.PLAYER_IS_ALREADY_SIGNED_IN);
                }
            } else {
                response = createResponseErrorMessage(ErrorCodeType.GAME_REACHED_MAXIMUM_OF_PLAYERS);
            }
        } else {
            response = createResponseErrorMessage(ErrorCodeType.PLAYER_CANNOT_BE_SIGN_IN);
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /SignIn");

        return response;
    }

    @RequestMapping(value = "{id}/signOut", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody GameUI playerSignOut(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- SignOut");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");
        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);
        List<String> players = resource.getPlayers();
        GameUI response;

        if (resource.getPlayers().contains(currentUser)) {
            players.remove(currentUser);
            GameUI convert = converter.convert(resource);
            update(id, convert);
            response = createResponseErrorMessage(ErrorCodeType.SUCCESS);
        } else {
            response = createResponseErrorMessage(ErrorCodeType.PLAYER_CANNOT_BE_SIGN_OUT);
        }
        if (logger.isDebugEnabled())
            logger.debug("---- /SignOut");

        return response;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Iterable<GameUI> find(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "myGamesOnly", required = false) Boolean myGamesOnly) {

        if (logger.isDebugEnabled())
            logger.debug("---- filter");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");

        Iterable<GameUI> response;

        if (name == null && status == null && myGamesOnly == null) {
            response = super.findAll();
        } else {
            List<Game> resource = service.findByCriteria(name, status, myGamesOnly, currentUser);
            response = converter.convertModelList(resource);
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /filter");

        return response;
    }

    @RequestMapping(value = "/nearest", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Iterable<GameUI> findNearest(
            @RequestParam(value = "latLng", required = true) Double[] latLng,
            @RequestParam(value = "range", required = false) Double range,
            @RequestParam(value = "status", required = false) GameStatusType status) {

        if (logger.isDebugEnabled())
            logger.debug("---- findNearest");

        Iterable<GameUI> response;
        List<Game> games;

        if (logger.isDebugEnabled()) {
            logger.debug("Finding nearest games...");
        }

        assertNotNull(latLng);
        assertNotNull(latLng[0]);
        assertNotNull(latLng[1]);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("latLng: %s", latLng));
            logger.debug(String.format("range: %s", range));
            logger.debug(String.format("status: %s", status));
        }

        games = service.findNearest(latLng, range, status);

        if (logger.isDebugEnabled())
            logger.debug(String.format("Count of nearest games: %d", games.size()));

        response = converter.convertModelList(games);

        if (logger.isDebugEnabled())
            logger.debug("---- /findNearest");

        return response;
    }

}
