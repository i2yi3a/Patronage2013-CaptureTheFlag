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
import java.util.List;


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

        request.setOwner(currentUser);

        GameUI gameUI = super.update(id, request);
        GameUI response = new GameUI();
        response.setId(gameUI.getId());
        response.setMessage(String.format("Game with id: %s was updated successfully.", response.getId()));

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
                response = super.delete(id);

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

            if (resource.getStatus().equals(GameStatusType.NEW))   {

                List<String> players = resource.getPlayers();

                 return players;

            }

        if (logger.isDebugEnabled())
            logger.debug("---- /players");

        return null;

    }

    @RequestMapping(value = "{id}/signIn", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<String> playerSignIn(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- SignIn");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");
        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);
        List<String> players = resource.getPlayers();

        if (resource.getPlayers().size() < resource.getPlayersMax()) {
            if (resource.getStatus().equals(GameStatusType.NEW))   {
                if (!resource.getPlayers().contains(currentUser)) {

                    players.add(currentUser);
                    GameUI gameUI = converter.convert(resource);
                    super.update(id, gameUI);


                    return players;
                }
            }
        }
        if (logger.isDebugEnabled())
            logger.debug("---- /SignIn");

        return null;
    }

    @RequestMapping(value = "{id}/signOut", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<String> playerSignOut(@PathVariable String id) {

        if (logger.isDebugEnabled())
            logger.debug("---- SignOut");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");
        Assert.notNull(id, "ID cannot be null");
        Game resource = service.findById(id);
        List<String> players = resource.getPlayers();


        if (resource.getPlayers().contains(currentUser)) {


            players.remove(currentUser);
            GameUI response = converter.convert(resource);
            super.update(id, response);

            return players;

        }
        if (logger.isDebugEnabled())
            logger.debug("---- /SignOut");

        return null;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Iterable<GameUI> find(@RequestParam(value = "name", defaultValue = "empty") String name, @RequestParam(value = "status", defaultValue = "empty") String status, @RequestParam(value = "myGames", defaultValue = "false") Boolean myGames) {

        if (logger.isDebugEnabled())
            logger.debug("---- filter");

        String currentUser = super.getCurrentUsername();
        Assert.notNull(currentUser, "Current username cannot be null");

        Iterable<GameUI> response;

        if (name.equals("empty") & status.equals("empty") & !myGames) {
            response = super.findAll();

        } else {
            List<Game> resource = service.findByCriteria(name, status, myGames, currentUser);
            response = converter.convertModelList(resource);
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /filter");

        return response;


    }
























}
