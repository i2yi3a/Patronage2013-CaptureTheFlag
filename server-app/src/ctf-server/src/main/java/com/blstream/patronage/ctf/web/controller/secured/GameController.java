package com.blstream.patronage.ctf.web.controller.secured;

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
}
