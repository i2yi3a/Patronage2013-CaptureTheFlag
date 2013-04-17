package com.blstream.patronage.ctf.web.controller.secured;

import com.blstream.patronage.ctf.common.web.controller.BaseRestController;
import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

@Controller
@RequestMapping("/api/secured/games")
public class GameController extends BaseRestController<Game, String, GameService> {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Inject
    @Named("gameService")
    @Override
    public void setService(GameService service) {
        super.service = service;
    }

}
