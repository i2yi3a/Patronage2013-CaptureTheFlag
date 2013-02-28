package com.blstream.patronage.ctf.web.controller.secured;

import com.blstream.patronage.ctf.common.web.controller.BaseRestController;
import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
 * This is a representation of trusted (secured) controller where all players
 * can by managed using CRUD logic model.
 *
 * Controller context: /api/secured/players
 */
@Controller
@RequestMapping("/api/secured/players")
public class PlayerController extends BaseRestController<Player, String, PlayerService> {

    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Inject
    @Named("playerService")
    @Override
    public void setService(PlayerService service) {
        super.service = service;
    }
}
