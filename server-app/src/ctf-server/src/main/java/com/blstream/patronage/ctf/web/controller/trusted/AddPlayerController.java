package com.blstream.patronage.ctf.web.controller.trusted;

import com.blstream.patronage.ctf.common.errors.ErrorCodeType;
import com.blstream.patronage.ctf.common.exception.AlreadyExistsException;
import com.blstream.patronage.ctf.common.web.controller.AbstractRestController;
import com.blstream.patronage.ctf.web.ui.MessageUI;
import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.service.PlayerService;
import com.blstream.patronage.ctf.web.ui.PlayerUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Copyright 2013 BLStream
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * User: lahim
 * Date: 1/22/13
 *
 * This class is a representation of trusted (unsecured) player controller
 * where new users can be added.
 *
 * Controller context: /api/players
 */
@Controller
@RequestMapping("/api/players")
public class AddPlayerController extends AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(AddPlayerController.class);

    private PlayerService playerService;

    /**
     * This method create new player in data base.
     *
     * Request context: /api/players/add
     * Request method: POST
     * Request consumes: JSON
     * Response produces: JSON
     *
     * @param playerUI
     * @return MessageUI
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody MessageUI addNewPlayer(@RequestBody PlayerUI playerUI) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- addNewPlayer method invoked ----");
        }

        Assert.notNull(playerUI, "PlayerUI cannot be null");

        MessageUI message = new MessageUI();

        try {
            Player player = playerService.createNewPlayer(playerUI);

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Player %s was created successfully", player.getPortalUser().getUsername()));
            }

            // TODO: get message from properties file: error-codes.properties
            // message.setMessage(SUCCESS);
            message.setErrorCode(ErrorCodeType.SUCCESS);
        } catch (AlreadyExistsException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("New player was not created.");
            }

            // TODO: get message from properties file: error-codes.properties
            message.setError(ErrorCodeType.PLAYER_ALREADY_EXISTS.toString());
            message.setErrorDescription(e.getMessage());
            message.setErrorCode(ErrorCodeType.PLAYER_ALREADY_EXISTS);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("New player was not created.", e);
            }

            // TODO: get message from properties file: error-codes.properties
            message.setError(ErrorCodeType.CANNOT_CREATE_NEW_PLAYER.toString());
            message.setErrorDescription(e.getMessage());
            message.setErrorCode(ErrorCodeType.CANNOT_CREATE_NEW_PLAYER);
        }

        return message;
    }

    @Inject
    @Named("playerService")
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
